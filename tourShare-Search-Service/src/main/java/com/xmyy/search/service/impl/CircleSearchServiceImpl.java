package com.xmyy.search.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.circle.model.DgPraiseRecord;
import com.xmyy.circle.service.DgPraiseRecordService;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.RelativeDateFormat;
import com.xmyy.search.core.SearchUtils;
import com.xmyy.search.core.SimpleSearchQueryBuilder;
import com.xmyy.search.service.CircleSearchService;
import com.xmyy.search.vo.CircleDoc;
import com.xmyy.search.vo.CirclePageParam;
import com.xmyy.search.vo.CirclePageResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 动态ES搜索  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = CircleSearchService.class)
public class CircleSearchServiceImpl implements CircleSearchService {
    private static Logger logger = LoggerFactory.getLogger(CircleSearchServiceImpl.class);

    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private SearchLogServiceImpl logService;
    @Autowired
    private CircleServiceImpl circleService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private DgPraiseRecordService praiseRecordService;

    /***
     * 手工设置的热门关键词
     */
    public final static String SEARCH_HOTS_KEY = EnumConstants.AdminDicType.SEARCH_HOTS.getValue();

    public Object search(CirclePageParam query) {
        logger.debug("search begin().");
        logger.info("search query: {}", query);
        query.setTypeId(query.getTypeId() == null ? EnumConstants.CircleType.note.getValue() : query.getTypeId());
        // 保存
        executor.execute(() -> logService.save(query.getKeywords(), "CIRCLE"));
        // 查询对象
        SimpleSearchQueryBuilder builder = this.createSearchBuilder(query);
        Pagination<CirclePageResult> page = queryWithBuilder(query, builder);

        //获取背包客的行程
        List<Long> circleIds = page.getRecords().parallelStream().map(r -> {
            if (r.getIsSelf() != null) r.setIsSelfStr(EnumConstants.YesOrNo.of(r.getIsSelf()).getSellerLabel());

            if (StringUtils.isNotBlank(r.getContent())) {
                r.setContent(r.getContent().replaceAll("(<.*?>)|(&nbsp;)", ""));
                r.setContent(r.getContent().length() > 40 ? r.getContent().substring(0, 40) + "..." : r.getContent());
            }


            if (r.getCreateTime() != null) r.setCreateTimeStr(RelativeDateFormat.format(r.getCreateTime()));

            return r.getId();
        }).collect(Collectors.toList());

        if (query.getBuyerId() != null && CollectionUtils.isNotEmpty(circleIds)) {

            List<DgPraiseRecord> praises = praiseRecordService.getPraisesByMemberId(query.getBuyerId(), circleIds, "circle_id_");
            List<Long> circles = praises.parallelStream().map(m -> {
                return m.getCircleId();
            }).collect(Collectors.toList());

            page.getRecords().parallelStream().forEach(r -> {
                r.setIsPraised(circles.contains(r.getId()) ? EnumConstants.YesOrNo.YES.getValue() : EnumConstants.YesOrNo.NO.getValue());
            });

        }
        return page;

    }

    protected SimpleSearchQueryBuilder createSearchBuilder(CirclePageParam query) {

        SimpleSearchQueryBuilder builder = SimpleSearchQueryBuilder.from(this.circleService.getIndexName(), SearchUtils.TYPE_CIRCLE)
                .withPageable(query.getCurrent() - 1, query.getSize());

        logger.info("createSearchBuilder begin");

        builder.match(query.getKeywords(), "realName", "nickName", "content", "title", "classifyTags")
                .bool()
                .mustTerm("typeId", query.getTypeId())
                .end();

        //默认
        builder.withSortBuilder(SortBuilders.fieldSort("createTime").order(SortOrder.DESC).unmappedType("date"))
                .withSortBuilder(SortBuilders.fieldSort("commentCount").order(SortOrder.DESC).unmappedType("int"))
                .withSortBuilder(SortBuilders.fieldSort("praiseCount").order(SortOrder.DESC).unmappedType("int"));

        logger.info("createSearchBuilder end");

        return builder;
    }

    protected Pagination<CirclePageResult> queryWithBuilder(CirclePageParam query, SimpleSearchQueryBuilder builder) {

        logger.info("queryWithBuilder begin");

        final SimpleSearchQueryBuilder.QueryResult qresult = builder.doQueryResult(elasticsearchTemplate);

        logger.info("queryWithBuilder 1");

        //查询结果
        Pagination<CirclePageResult> page = builder.doQuery(q -> {

            Pagination<CirclePageResult> datas = SearchUtils.mapToYooyoPage(qresult.getPage(CircleDoc.class), pd -> {
                CirclePageResult r = InstanceUtil.to(pd, CirclePageResult.class);
                //r.setClassifyTags(StringUtils.join(pd.getClassifyTags(), ","));

                return r;
            });
            return datas;
        });


        logger.info("queryWithBuilder end");
        page.setCurrent(query.getCurrent());
        page.setSize(query.getSize());
        return page;
    }
}
