package com.xmyy.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.search.core.PaginationUtils;
import com.xmyy.search.core.SearchUtils;
import com.xmyy.search.core.config.EsConfig;
import com.xmyy.search.service.ProductSearchService;
import com.xmyy.search.vo.ProductSearchParam;
import com.xmyy.search.vo.ProductSearchResult;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 商品ES搜索  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = ProductSearchService.class)
public class ProductSearchServiceImpl implements ProductSearchService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private SearchLogServiceImpl logService;
    @Autowired
    protected EsConfig esConfig;

    private String getIndexName() {
        return esConfig.getProductIndexName();
    }

    public long getTotalCount() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices(getIndexName()).withTypes(SearchUtils.TYPE_PRODUCT).build();
        long count = elasticsearchTemplate.count(searchQuery);
        return count;
    }

    public Object search(ProductSearchParam query) {
        executor.execute(() -> logService.save(query.getKeyword(), "PRODUCT"));

        String[] searchField = {"categoryName", "categoryName2", "brandName", "title", "buyRegion", "shopName"};
        MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(query.getKeyword(), searchField);
        FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort("id").order(SortOrder.DESC).unmappedType("long");

        Pageable pageable = PageRequest.of(query.getCurrent() - 1, query.getSize());
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices(getIndexName()).withTypes(SearchUtils.TYPE_PRODUCT)
                .withQuery(matchQueryBuilder)
                .withSort(fieldSortBuilder)
                .withPageable(pageable)
                .build();
        AggregatedPage<ProductSearchResult> results = elasticsearchTemplate.queryForPage(searchQuery, ProductSearchResult.class);
        PaginationUtils.contentWrapper(results, c -> c.setPrice(c.getMinPrice()));
        return PaginationUtils.from(results, pageable);
    }

}
