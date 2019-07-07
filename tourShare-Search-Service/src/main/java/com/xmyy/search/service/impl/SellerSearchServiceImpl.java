package com.xmyy.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.xmyy.circle.service.DgCountryService;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.ToolsUtil;
import com.xmyy.manage.service.AdminDicService;
import com.xmyy.manage.service.WsSensitiveService;
import com.xmyy.member.vo.SellerPageResult;
import com.xmyy.product.service.TourService;
import com.xmyy.product.vo.ApiTourInfoResult;
import com.xmyy.search.core.SearchUtils;
import com.xmyy.search.core.SimpleSearchQueryBuilder;
import com.xmyy.search.service.SellerSearchService;
import com.xmyy.search.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 买手ES搜索  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = SellerSearchService.class)
public class SellerSearchServiceImpl implements SellerSearchService {
    private static Logger logger = LoggerFactory.getLogger(SellerSearchServiceImpl.class);

    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private SearchLogServiceImpl logService;
    @Autowired
    private SellerServiceImpl sellerServiceImpl;
    @Autowired
    private WsSensitiveService sensitiveService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private AdminDicService adminDicService;
    @Autowired
    private TourService tourService;
    @Autowired
    private DgCountryService dgCountryService;


    /***
     * 手工设置的热门关键词
     */
    private final static String SEARCH_HOTS_KEY = EnumConstants.AdminDicType.SEARCH_HOTS.getValue();

    public Object search(SellerPageParam query) {
        logger.debug("search begin().");
        logger.info("search query: {}", query);

        // 保存
        executor.execute(() -> logService.save(query.getKeyword(), "MEMBER"));

        if (StringUtils.isNotBlank(query.getContinentCode())) {
            List<String> shortCodesByZhouCode = dgCountryService.getCountryShortCodesByZhouCode(query.getContinentCode());
            if (CollectionUtils.isEmpty(shortCodesByZhouCode)) {
                return "获取洲包含国家列表为空";
            }
            query.setShortCodes(shortCodesByZhouCode);
        }

        if (query.getIsSelf() != null && query.getIsSelf() == 2) {
            // 查询对象
            SimpleSearchQueryBuilder builder = this.createPackerSearchBuilder(query);
            Pagination<SellerPageResult> page = queryWithBuilder(query, builder);

            //获取背包客的行程
            List<Long> memberIds = page.getRecords().parallelStream().map(l -> {
                l.setIsSelf(2);
                return l.getId();
            }).collect(Collectors.toList());

            Map<String, ApiTourInfoResult> tourMap = null;
            try {
                tourMap = tourService.queryNewTourByMemberIds(memberIds);
            } catch (Exception e) {
                logger.error("获取行程出错：" + e.getMessage());
            }

            if (tourMap != null) {
                Map<String, ApiTourInfoResult> finalTourMap = tourMap;
                page.getRecords().parallelStream().forEach(l -> {
                    if (finalTourMap.get(l.getId().toString()) != null) {
                        List<SellerPageResult.TourSiteDto> tours = new ArrayList<>();
                        InstanceUtil.copyList(finalTourMap.get(l.getId().toString()).getTourSites(), tours, SellerPageResult.TourSiteDto.class);
                        l.setTours(tours);
                    }
                });
            }
            return page;
        } else {
            // 查询对象
            SimpleSearchQueryBuilder builder = this.createSearchBuilder(query);
            return queryWithBuilder(query, builder);
        }

    }

    private SimpleSearchQueryBuilder createSearchBuilder(SellerPageParam query) {

        SimpleSearchQueryBuilder builder = SimpleSearchQueryBuilder.from(this.sellerServiceImpl.getIndexName(), SearchUtils.TYPE_SELLER)
                .withPageable(query.getCurrent() - 1, query.getSize());

        logger.info("createSearchBuilder begin");

        subBuilder(query, builder);

        logger.info("createSearchBuilder end");

        return builder;
    }

    private SimpleSearchQueryBuilder createPackerSearchBuilder(SellerPageParam query) {


        SimpleSearchQueryBuilder builder = SimpleSearchQueryBuilder.from(this.sellerServiceImpl.getPackerIndexName(), SearchUtils.TYPE_PACKER)
                .withPageable(query.getCurrent() - 1, query.getSize());

        logger.info("createSearchBuilder begin");

        subBuilder(query, builder);

        logger.info("createSearchBuilder end");

        return builder;
    }

    private void subBuilder(SellerPageParam query, SimpleSearchQueryBuilder builder) {
        if (StringUtils.isNotBlank(query.getKeyword()) && ToolsUtil.isMobile(query.getKeyword())) {
            builder.bool()
                    .mustTerm("mobile", query.getKeyword())
                    .mustTerm("state", EnumConstants.State.NORMAL.value())
                    .end();
        } else {
            List<String> shortCodes = query.getShortCodes();
            String liveCountryShortCode = query.getLiveCountryShortCode();
            if (liveCountryShortCode != null) {
                liveCountryShortCode = liveCountryShortCode.toUpperCase();
            }

            if (query.getIsSelf() != null && query.getIsSelf() == 2) {
                builder.match(query.getKeyword(), "realName", "nickName", "oftenPlace", "liveCountryShortCode", "classifyTags")
                        .bool()
                        .mustTerms("liveCountryShortCode.keyword", CollectionUtils.isEmpty(shortCodes) ? liveCountryShortCode : shortCodes)
                        .end();
            } else {
                builder.match(query.getKeyword(), "realName", "nickName", "oftenPlace", "liveCountryShortCode", "classifyTags")
                        .bool()
                        .mustTerm("isSelf", query.getIsSelf())
                        .mustTerms("liveCountryShortCode.keyword", CollectionUtils.isEmpty(shortCodes) ? liveCountryShortCode : shortCodes)
                        .end();
            }
        }

        //默认
        if (query.getOrderby() == null) {
            builder.withSortBuilder(SortBuilders.fieldSort("tradeCount").order(SortOrder.DESC).unmappedType("int"))
                    .withSortBuilder(SortBuilders.fieldSort("trustValue").order(SortOrder.DESC).unmappedType("int"));
        } else {
            switch (query.getOrderby()) {
                case 1:
                    builder.withSortBuilder(SortBuilders.fieldSort("tourTime").order(SortOrder.DESC).unmappedType("date"));
                    break;
                case 2:
                    builder.withSortBuilder(SortBuilders.fieldSort("trustValue").order(SortOrder.DESC).unmappedType("int"));
                    break;
                case 3:
                    builder.withSortBuilder(SortBuilders.fieldSort("tradeCount").order(SortOrder.DESC).unmappedType("int"));
                    break;
            }
        }
    }

    private Pagination<SellerPageResult> queryWithBuilder(SellerPageParam query, SimpleSearchQueryBuilder builder) {
        logger.info("queryWithBuilder begin");
        final SimpleSearchQueryBuilder.QueryResult qresult = builder.doQueryResult(elasticsearchTemplate);
        logger.info("queryWithBuilder 1");

        //查询结果
        Pagination<SellerPageResult> page;
        if (query.getIsSelf() != null && query.getIsSelf() == 2) {
            page = builder.doQuery(q -> {
                Pagination<SellerPageResult> datas = SearchUtils.mapToYooyoPage(qresult.getPage(PackerDoc.class), pd -> {
                    SellerPageResult r = InstanceUtil.to(pd, SellerPageResult.class);
                    if (!StringUtils.isBlank(pd.getClassifyTags())) {
                        r.setClassifyTagsList(Arrays.asList(pd.getClassifyTags().split(",")));
                    }
                    return r;
                });
                return datas;
            });
        } else {
            page = builder.doQuery(q -> {
                Pagination<SellerPageResult> datas = SearchUtils.mapToYooyoPage(qresult.getPage(SellerDoc.class), pd -> {
                    SellerPageResult r = InstanceUtil.to(pd, SellerPageResult.class);
                    r.setIsSelfStr(EnumConstants.YesOrNo.of(pd.getIsSelf()).getSellerLabel());
                    if (!StringUtils.isBlank(pd.getClassifyTags())) {
                        r.setClassifyTagsList(Arrays.asList(pd.getClassifyTags().split(",")));
                    }

                    if (!StringUtils.isBlank(pd.getProductRsurl())) {
                        ArrayList<String> imgs = Arrays.stream(pd.getProductRsurl().split(","))
                                .limit(3)
                                .collect(Collectors.toCollection(ArrayList::new));
                        r.setProductRsurlList(imgs);
                    }
                    return r;
                });
                return datas;
            });
        }

        logger.info("queryWithBuilder end");
        page.setCurrent(query.getCurrent());
        page.setSize(query.getSize());
        return page;
    }

    @Cacheable("hotSearchLisCache")
    public SellerHotSearchResult findHotSearchList(HotSearchParam query) {
        SellerHotSearchResult result = new SellerHotSearchResult();
        Set<String> manuallyHots = this.adminDicService.findHotWords(SEARCH_HOTS_KEY, query.getType(), query.getKeyword());
        List<String> totalHotWords = Lists.newArrayList(manuallyHots);
        if (totalHotWords.size() < query.getHotCount()) {
            List<String> searchHotWords = logService.getHotKeywords(query);
            totalHotWords.addAll(searchHotWords);
        }

        sensitiveService.removeSensitive(totalHotWords);
        if (totalHotWords.size() > query.getHotCount()) {
            totalHotWords = new ArrayList<String>(totalHotWords.subList(0, query.getHotCount()));
        }
        result.setHostWords(totalHotWords);
        return result;
    }
}
