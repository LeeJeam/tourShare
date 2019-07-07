package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmyy.circle.dao.DgFavoritesDao;
import com.xmyy.circle.mapper.DgFavoritesMapper;
import com.xmyy.circle.model.DgFavorites;
import com.xmyy.circle.model.UcDynamicCircle;
import com.xmyy.circle.service.DgFavoritesService;
import com.xmyy.circle.service.UcDynamicCircleService;
import com.xmyy.circle.vo.BatchCancelParam;
import com.xmyy.circle.vo.FavoritesAddParam;
import com.xmyy.circle.vo.MyFavoritesResult;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.ServiceCode;
import com.xmyy.common.util.ModelMapUtil;
import com.xmyy.common.util.RelativeDateFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 收藏  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = DgFavoritesService.class)
//@CacheConfig(cacheNames = "DgFavorites")
public class DgFavoritesServiceImpl extends BaseServiceImpl<DgFavorites, DgFavoritesMapper> implements DgFavoritesService {

    @Resource
    private UcDynamicCircleService circleService;
    @Resource
    private DgFavoritesDao favoritesDao;

    /**
     * 我的收藏
     *
     * @param type       1，笔记；2，视频；3，商品
     * @param currUser   当前登录用户
     * @param current
     * @param size
     * @param memberType 当前登陆的用户类型(1,买手，2买家),可理解为买家端，还是买手端.默认：2买家
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Pagination<MyFavoritesResult> mylist(Integer type, Long currUser, Integer current, Integer size, Integer memberType) {
        if (current == null) current = 1;
        if (size == null) size = 10;

        //封装查询参数
        EntityWrapper<DgFavorites> ew = new EntityWrapper();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("member_id", currUser);

        memberType = memberType != null && memberType.intValue() == EnumConstants.MemberType.seller.getValue() ? memberType : EnumConstants.MemberType.buyer.getValue();
        ew.eq("member_type", memberType);

        if (type == null) {
            type = EnumConstants.CircleType.video.getValue();
        }

        ew.eq("entity_type_id", EnumConstants.CircleType.valueOf(type).getValue());

        ew.orderDesc(Lists.newArrayList("create_time"));

        List<MyFavoritesResult> list = null;

        int count = mapper.selectCount(ew);

        if (count > 0) {
            //分页查询，封装订单信息
            RowBounds rb = new RowBounds((current - 1) * size, size);
            if (type.intValue() == EnumConstants.CircleType.product.getValue()) {
                Map<String, Object> map = InstanceUtil.newHashMap("member_id", currUser);
                map.put("entity_type_id", type);
                map.put("member_type", memberType);

                list = favoritesDao.findProductFavorites(map, rb);
            } else {

                List<DgFavorites> dgComments = mapper.selectPage(rb, ew);
                list = dgComments.stream().map(o -> {

                    MyFavoritesResult r = InstanceUtil.to(o, MyFavoritesResult.class);


                    if (r.getCreateTime() != null) r.setCreateTimeStr(RelativeDateFormat.format(r.getCreateTime()));

                    if (r.getEntityTypeId() != null && r.getEntityTypeId().intValue() != EnumConstants.CircleType.product.getValue()) {
                        UcDynamicCircle circle = circleService.queryById(r.getEntityId());
                        if (StringUtils.isNotBlank(circle.getContent())) {
                            String cric = circle.getContent().replaceAll("(<.*?>)|(&nbsp;)", "");
                            r.setContent(cric.length() > 40 ? cric.substring(0, 40) + "..." : cric);
                        }
                        r.setCoverRsurl(circle.getCoverRsurl());
                        r.setTitle(circle.getTitle());
                        r.setVideoRsurl(circle.getVideoRsurl());
                        r.setPraiseCount(circle.getPraiseCount());
                        r.setCommentCount(circle.getCommentCount());
                        r.setReadCount(circle.getReadCount());
                        r.setClassifyTags(circle.getClassifyTags());
                        r.setFavoritesCount(circle.getFavoritesCount());
                        r.setStateStr(EnumConstants.State.getState(circle.getState()).getShelvesLabel());

                    }
                    return r;
                }).collect(Collectors.toCollection(ArrayList::new));

            }
        }

        Pagination<MyFavoritesResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(current);
        page.setSize(size);
        page.setTotal(count);
        return page;
    }

    /**
     * 收藏/取消收藏
     *
     * @param params
     * @return
     */
    @Override
    @Transactional
    public Object updateFavorites(FavoritesAddParam params) {

        DgFavorites gp = InstanceUtil.to(params, DgFavorites.class);

        List<DgFavorites> dgFavorites = getFavoritesByMemberId(gp.getMemberId(), gp.getEntityId(), gp.getEntityTypeId());
        if (CollectionUtils.isNotEmpty(dgFavorites)) {
            dgFavorites.parallelStream().forEach(p -> {

                circleService.updateFavorites(p.getEntityId(), false);
                super.del(p.getId(), p.getMemberId());
            });
            return ModelMapUtil.setModelMap(ServiceCode.ServeCode_601.code(), "取消收藏成功");
        }

        if (gp.getAppId() != null) gp.setAppName(EnumConstants.DeviceType.valueOf(gp.getAppId()).getName());
        circleService.updateFavorites(gp.getEntityId(), true);
        super.update(gp);

        return ModelMapUtil.setModelMap(ServiceCode.ServeCode_600.code(), "收藏成功");
    }

    /**
     * 批量取消收藏
     *
     * @param params
     * @return
     */
    @Override
    @Transactional
    public Object batchCancel(BatchCancelParam params) {

        if (CollectionUtils.isNotEmpty(params.getIds())) {
            params.getIds().parallelStream().forEach(p -> {
                DgFavorites pp = super.queryById(p);
                if (pp != null) {
                    circleService.updateFavorites(pp.getEntityId(), false);
                    super.del(pp.getId(), pp.getMemberId());
                }

            });
        }

        return null;
    }

    /**
     * 根据用户获取收藏数
     *
     * @param memberId
     * @param entityId
     * @return
     */
    // @Cacheable(value = "getFavoritesByMemberId")
    @Transactional(readOnly = true)
    public List<DgFavorites> getFavoritesByMemberId(Long memberId, Long entityId, Integer entityTypeId) {
        EntityWrapper<DgFavorites> ew = new EntityWrapper();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());

        ew.eq("entity_type_id", entityTypeId);
        ew.eq("entity_id", entityId);
        ew.eq("member_id", memberId);

        List<DgFavorites> dgFavorites = this.mapper.selectList(ew);

        return dgFavorites;
    }

    /**
     * 根据用户获取收藏数
     *
     * @param memberId
     * @param entityIds
     * @return
     */
    // @Cacheable(value = "getFavoritesByMemberId")
    @Transactional(readOnly = true)
    public List<DgFavorites> getFavoritesByMemberId(Long memberId, List<Long> entityIds, Integer entityTypeId) {
        EntityWrapper<DgFavorites> ew = new EntityWrapper();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());

        ew.eq("entity_type_id", entityTypeId);
        ew.in("entity_id", entityIds);
        ew.eq("member_id", memberId);

        List<DgFavorites> dgFavorites = this.mapper.selectList(ew);

        return dgFavorites;
    }
}
