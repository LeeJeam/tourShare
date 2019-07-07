package com.xmyy.search.service.impl;

import com.xmyy.search.core.config.EsConfig;
import com.xmyy.search.repository.SearchLogRepository;
import com.xmyy.search.vo.HotSearchParam;
import com.xmyy.search.vo.SearchLogDoc;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchLogServiceImpl {
    private static Logger logger = LoggerFactory.getLogger(SearchLogServiceImpl.class);

    @Autowired
    private SearchLogRepository searchLogRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private IndexMappingServiceImpl indexMappingServiceImpl;
    @Autowired
    private EsConfig esConfig;
    @Autowired
    private Client client;

    public String getIndexName() {
        return esConfig.getLogdataIndexName();
    }

    public void save(String keyword, String type) {

        if (StringUtils.isBlank(keyword))
            return;
        SearchLogDoc log = new SearchLogDoc();
        log.setKeyword(keyword);
        log.setSearchword(keyword);
        log.setCreateAt(new Date());
        log.setType(type);
        try {
            searchLogRepository.save(log);
        } catch (Exception e) {
            logger.error("save search log error.", e);
        }
    }

    public void saveList(List<SearchLogDoc> logs) {

        try {
            searchLogRepository.saveAll(logs);
        } catch (Exception e) {
            logger.error("save search log error.", e);
        }
    }


    public List<String> getHotKeywords(HotSearchParam query) {
        return statisHotKeywords(query, null);
    }

    /****
     * 统计热门关键词
     * @param query
     * @return
     */
    public List<String> statisHotKeywords(HotSearchParam query, Long minDocCount) {
		/*DateTime now = DateTime.now();
		SimpleSearchQueryBuilder builder = SimpleSearchQueryBuilder.from(this.getIndexName(), SearchUtils.TYPE_SEARCH_LOG)
																	.withPageable(0, query.getHotCount());
		builder.query(QueryBuilders.rangeQuery("createAt"))
				.gte(now.minusMonths(1).toDate().getTime())
				.lt(now.plusDays(1).toDate().getTime());

		builder.match(query.getKeyword(), "keyword");
		//查询结果
		//List<SearchLogDoc> searchLogDocs = builder.queryForList(elasticsearchTemplate, SearchLogDoc.class);
		*//*return searchLogDocs.stream().map(bk->bk.getKeyword().toString())
				.collect(Collectors.toList());*//*
		builder.agg("hots", "keyword.keyword", query.getHotCount()).minDocCount(minDocCount);


		Terms term = (Terms)builder.queryAggs(elasticsearchTemplate).get("hots");
		List<String> keywords = term.getBuckets()
										.stream()
										.map(bk->bk.getKey().toString())
										.collect(Collectors.toList());*/


        DateTime now = DateTime.now();
        QueryBuilder builder = null;
        if (StringUtils.isNotBlank(query.getKeyword()) && StringUtils.isNotBlank(query.getType())) {
            builder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery("keyword", query.getKeyword()))
                    .must(QueryBuilders.matchQuery("type", query.getType()))
                    .must(QueryBuilders.rangeQuery("createAt")
                            .gte(now.minusMonths(1).toDate().getTime())
                            .lt(now.plusDays(1).toDate().getTime()));
        } else if (StringUtils.isNotBlank(query.getKeyword())) {
            builder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery("keyword", query.getKeyword()))
                    .must(QueryBuilders.rangeQuery("createAt")
                            .gte(now.minusMonths(1).toDate().getTime())
                            .lt(now.plusDays(1).toDate().getTime()));
        } else if (StringUtils.isNotBlank(query.getType())) {
            builder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery("type", query.getType()))//SearchUtils.toSet()
                    .must(QueryBuilders.rangeQuery("createAt")
                            .gte(now.minusMonths(1).toDate().getTime())
                            .lt(now.plusDays(1).toDate().getTime()));
        }

        TermsAggregationBuilder aggregation = AggregationBuilders.terms("hots").field("keyword.keyword")
                .subAggregation(AggregationBuilders.topHits("top").size(0).from(query.getHotCount()))
                .order(Terms.Order.count(false));

        SearchResponse response = client.prepareSearch(this.getIndexName())
                .setQuery(builder).addAggregation(aggregation).get();

        Terms term = response.getAggregations().get("hots");
        List<String> keywords = term.getBuckets()
                .stream()
                .map(bk -> bk.getKey().toString())
                .collect(Collectors.toList());
        return keywords;

    }

    public boolean rebuildIndex() {
        return indexMappingServiceImpl.rebuildIndex(getIndexName());
    }

}
