package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmyy.circle.mapper.UcDynamicCircleMapper;
import com.xmyy.circle.model.DgFavorites;
import com.xmyy.circle.model.DgPraiseRecord;
import com.xmyy.circle.model.UcDynamicCircle;
import com.xmyy.circle.service.DgFavoritesService;
import com.xmyy.circle.service.DgPraiseRecordService;
import com.xmyy.circle.service.DgTagsService;
import com.xmyy.circle.service.UcDynamicCircleService;
import com.xmyy.circle.vo.*;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.ServiceCode;
import com.xmyy.common.util.ModelMapUtil;
import com.xmyy.common.util.RelativeDateFormat;
import com.xmyy.common.util.ToolsUtil;
import com.xmyy.common.vo.MemberInfo;
import com.xmyy.manage.service.WsSensitiveService;
import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.service.UcBuyerService;
import com.xmyy.member.service.UcSellerService;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 动态  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = UcDynamicCircleService.class)
@CacheConfig(cacheNames = "UcDynamicCircle")
public class UcDynamicCircleServiceImpl extends BaseServiceImpl<UcDynamicCircle, UcDynamicCircleMapper> implements UcDynamicCircleService {

    @Resource
    private UcBuyerService buyerService;
    @Resource
    private DgTagsService tagsService;
    @Resource
    private DgPraiseRecordService praiseRecordService;
    @Resource
    private DgFavoritesService favoritesService;
    @Resource
    private WsSensitiveService sensitiveService;
    @Resource
    private UcSellerService sellerService;


    @Override
    @Transactional
    public Object addCircle(DynamicCircleParam params) {
        UcDynamicCircle circle = InstanceUtil.to(params, UcDynamicCircle.class);

        //标题敏感字的检查
        Set<String> titles = sensitiveService.findSensitives(params.getTitle());
        if (CollectionUtils.isNotEmpty(titles)) {
            return "标题存在敏感字，如下：" + StringUtils.join(titles, ",");
        }

        if (params.getTypeId() == EnumConstants.CircleType.note.getValue()) {
            if (StringUtils.isBlank(params.getContent())) {
                return "笔记内容不能为空";
            }

            //敏感字的检查
            Set<String> sensitives = sensitiveService.findSensitives(params.getContent());
            if (CollectionUtils.isNotEmpty(sensitives)) {
                return "笔记内容存在敏感字，如下：" + StringUtils.join(sensitives, ",");
            }
            Set<String> imgs = ToolsUtil.getImgStr(circle.getContent(), 3);
            if (!imgs.isEmpty())
                circle.setCoverRsurl(StringUtils.join(imgs, ","));

        } else {
            if (StringUtils.isBlank(params.getVideoRsurl())) {
                return "视频地址不能为空";
            }

            if (StringUtils.isBlank(params.getCoverRsurl())) {
                return "视频封面不能为空";
            }
        }

        circle.setReadCount(0);
        circle.setCommentCount(0);
        circle.setPraiseCount(0);
        circle.setState(EnumConstants.State.NORMAL.value());

        super.update(circle);
        return null;
    }


    @Override
    @Transactional
    public CircleDetailResult getInfo(Long id, Long currUser) {
        UcDynamicCircle dynamicCircle = super.queryById(id);
        if (dynamicCircle == null) {
            return null;
        }
        //更新阅读数
        dynamicCircle.setReadCount(dynamicCircle.getReadCount() + 1);
        update(dynamicCircle);

        CircleDetailResult info = InstanceUtil.to(dynamicCircle, CircleDetailResult.class);
        MemberInfo info1 = sellerService.getMemberInfo(dynamicCircle.getSellerId(), EnumConstants.MemberType.seller.getValue());
        if (info1 != null) {
            info.setAvatarRsurl(info1.getAvatarRsurl());
            info.setNickName(info1.getNickName());
            info.setOftenPlace(info1.getOftenPlace());
            info.setIsSelf(info1.getIsSelf());
        }

        if (currUser != null) {
            List<DgFavorites> favorites = favoritesService.getFavoritesByMemberId(currUser, info.getId(), info.getTypeId());
            info.setIsCollected(CollectionUtils.isNotEmpty(favorites) ? EnumConstants.YesOrNo.YES.getValue() : EnumConstants.YesOrNo.NO.getValue());

            List<DgPraiseRecord> praises = praiseRecordService.getPraisesByMemberId(currUser, info.getId(), "circle_id_");
            info.setIsPraised(CollectionUtils.isNotEmpty(praises) ? EnumConstants.YesOrNo.YES.getValue() : EnumConstants.YesOrNo.NO.getValue());
        }

        return info;
    }


    @Override
    @Transactional(readOnly = true)
    public Pagination<CirclePageResult> list(CirclePageParam param) {
        //封装查询参数
        EntityWrapper<UcDynamicCircle> ew = new EntityWrapper<>();
        ew.eq("state", EnumConstants.State.NORMAL.value());
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());

        int count = 0;
        if (param.getType() == 1) {
            //发现列表，笔记视频都展示
            if (StringUtils.isNotBlank(param.getClassifyTags())) {
                ew.like("classify_tags", param.getClassifyTags());
            }
            ew.orderDesc(Lists.newArrayList("is_top", "top_time", "create_time", "comment_count", "praise_count"));
            count = mapper.selectCount(ew);

        } else if (param.getType() == 3) {
            //买家端买手详情，买手列表/买手端我的动态，笔记视频都展示
            ew.eq("seller_id_", param.getSellerId());
            ew.orderDesc(Lists.newArrayList("is_top", "top_time", "create_time", "comment_count", "praise_count"));
            count = mapper.selectCount(ew);

        } else if (param.getType() == 4) {
            //搜索列表，默认搜笔记
            ew.where("(classify_tags like {0} or content like {1} or title like {2})", "%" + param.getKeywords() + "%", "%" + param.getKeywords() + "%", "%" + param.getKeywords() + "%");
            ew.eq("type_id", param.getTypeId() == null ? EnumConstants.CircleType.note.getValue() : param.getTypeId());
            ew.orderDesc(Lists.newArrayList("create_time", "comment_count", "praise_count"));
            count = mapper.selectCount(ew);

        } else {
            //首页动态，先根据指定查，没有再根据个人标签查，没有再根据系统推荐标签查
            ew.eq("is_top", EnumConstants.YesOrNo.YES.getValue());
            ew.orderDesc(Lists.newArrayList("is_top", "top_time", "create_time", "comment_count", "praise_count"));
            count = mapper.selectCount(ew);

            if (count == 0) {
                ew = new EntityWrapper<>();
                ew.eq("state", EnumConstants.State.NORMAL.value());
                ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());

                if (param.getBuyerId() != null) {
                    //个人标签
                    UcBuyer buyer = buyerService.queryById(param.getBuyerId());
                    if (buyer != null && StringUtils.isNotBlank(buyer.getClassifyTags())) {
                        ew.where("classify_tags REGEXP replace({0}, ',', '|')", buyer.getClassifyTags());
                        count = mapper.selectCount(ew);
                    }
                }

                if (count == 0) {
                    //系统默认热门标签
                    List<String> tags = tagsService.tagList();
                    if (!CollectionUtils.isEmpty(tags)) {
                        ew.where("classify_tags REGEXP replace({0}, ',', '|')", StringUtils.join(tags, ","));
                        count = mapper.selectCount(ew);
                    }

                }
                ew.orderDesc(Lists.newArrayList("create_time", "comment_count", "praise_count"));
            }
        }

        List<CirclePageResult> list = null;
        if (count > 0) {
            //分页查询，封装
            RowBounds rb = new RowBounds((param.getCurrent() - 1) * param.getSize(), param.getSize());
            List<UcDynamicCircle> mlist = mapper.selectPage(rb, ew);

            list = mlist.stream().map(o -> {
                CirclePageResult r = InstanceUtil.to(o, CirclePageResult.class);
                MemberInfo info = sellerService.getMemberInfo(r.getSellerId(), EnumConstants.MemberType.seller.getValue());
                if (info != null) {
                    r.setAvatarRsurl(info.getAvatarRsurl());
                    r.setNickName(info.getNickName());
                    r.setIsSelf(info.getIsSelf());
                    if (info.getIsSelf() != null) {
                        r.setIsSelfStr(EnumConstants.YesOrNo.of(info.getIsSelf()).getSellerLabel());
                    }
                }

                if (StringUtils.isNotBlank(r.getContent())) {
                    r.setContent(r.getContent().replaceAll("(<.*?>)|(&nbsp;)", ""));
                    r.setContent(r.getContent().length() > 40 ? r.getContent().substring(0, 40) + "..." : r.getContent());
                }

                if (StringUtils.isNotBlank(r.getCoverRsurl())) { //封面最多返回3张图片
                    String[] coverUrlstrs = r.getCoverRsurl().split(",");
                    StrBuilder sb = new StrBuilder();
                    for (int i = 0; i < (coverUrlstrs.length > 3 ? 3 : coverUrlstrs.length); i++) {
                        sb.append(coverUrlstrs[i] + ",");
                    }
                    r.setCoverRsurl(sb.toString().substring(0, sb.length() - 1));
                }

                if (r.getCreateTime() != null) {
                    r.setCreateTimeStr(RelativeDateFormat.format(r.getCreateTime()));
                }
                return r;
            }).collect(Collectors.toCollection(ArrayList::new));

            //买家点过的赞，返回标记位
            if (param.getBuyerId() != null) {
                List<Long> entityIds = list.parallelStream().map(CirclePageResult::getId).collect(Collectors.toList());
                List<DgPraiseRecord> praises = praiseRecordService.getPraisesByMemberId(param.getBuyerId(), entityIds, "circle_id_");
                List<Long> circles = praises.parallelStream().map(DgPraiseRecord::getCircleId).collect(Collectors.toList());
                list.parallelStream().forEach(r -> r.setIsPraised(circles.contains(r.getId()) ? EnumConstants.YesOrNo.YES.getValue() : EnumConstants.YesOrNo.NO.getValue()));
            }
        }

        Pagination<CirclePageResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        page.setTotal(count);

        return page;
    }


    @Override
    @Transactional(readOnly = true)
    public Object myList(Long memberId, Integer typeId, Integer current, Integer size) {
        if (memberId == null) {
            return "用户ID不能为空";
        }
        if (typeId == null) {
            return "动态类型不能为空";
        }
        if (current == null || current < 1) {
            current = 1;
        }
        if (size == null || size < 1) {
            size = 1;
        }

        EntityWrapper<UcDynamicCircle> ew = new EntityWrapper<>();
        ew.eq("state", EnumConstants.State.NORMAL.value());
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("seller_id_", memberId);
        ew.eq("type_id", typeId);
        ew.orderDesc(Lists.newArrayList("is_top", "top_time", "create_time", "comment_count", "praise_count"));
        int count = mapper.selectCount(ew);

        List<CirclePageResult> list = null;
        if (count > 0) {
            //分页查询动态信息
            RowBounds rb = new RowBounds((current - 1) * size, size);
            List<UcDynamicCircle> mlist = mapper.selectPage(rb, ew);

            list = mlist.stream().map(o -> {
                CirclePageResult r = InstanceUtil.to(o, CirclePageResult.class);
                MemberInfo info = sellerService.getMemberInfo(r.getSellerId(), EnumConstants.MemberType.seller.getValue());
                if (info != null) {
                    r.setAvatarRsurl(info.getAvatarRsurl());
                    r.setNickName(info.getNickName());
                    r.setIsSelf(info.getIsSelf());
                    if (info.getIsSelf() != null) r.setIsSelfStr(EnumConstants.YesOrNo.of(info.getIsSelf()).getSellerLabel());
                }

                if (StringUtils.isNotBlank(r.getContent())) {
                    r.setContent(r.getContent().replaceAll("(<.*?>)|(&nbsp;)", ""));
                    r.setContent(r.getContent().length() > 40 ? r.getContent().substring(0, 40) + "..." : r.getContent());
                }

                if (StringUtils.isNotBlank(r.getCoverRsurl())) { //封面最多返回3张图片
                    String[] coverUrlstrs = r.getCoverRsurl().split(",");
                    StrBuilder sb = new StrBuilder();
                    for (int i = 0; i < (coverUrlstrs.length > 3 ? 3 : coverUrlstrs.length); i++) {
                        sb.append(coverUrlstrs[i] + ",");
                    }
                    r.setCoverRsurl(sb.toString().substring(0, sb.length() - 1));
                }

                if (r.getCreateTime() != null) {
                    r.setCreateTimeStr(RelativeDateFormat.format(r.getCreateTime()));
                }
                return r;
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        Pagination<CirclePageResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(current);
        page.setSize(size);
        page.setTotal(count);
        page.setPages(count % size == 0 ? count / size : count / size + 1);

        return page;
    }


    @Override
    @Transactional
    public Object addPlayCount(Long id) {
        UcDynamicCircle dynamicCircle = super.queryById(id);
        if (dynamicCircle != null) {
            dynamicCircle.setPlayCount(dynamicCircle.getPlayCount() + 1);
        }
        super.update(dynamicCircle);
        return null;
    }


    @Override
    @Transactional
    public Object praise(Long id, Long memberId, Integer memberType) {
        UcDynamicCircle dynamicCircle = super.queryById(id);
        if (dynamicCircle != null) {
            DgPraiseRecord record = new DgPraiseRecord();
            record.setCircleId(dynamicCircle.getId());
            record.setMemberId(memberId);
            record.setMemberType(memberType);

            String code = ServiceCode.ServeCode_600.code();
            String msg = null;
            boolean isAdd = praiseRecordService.addPraise(record);

            if (isAdd) {
                dynamicCircle.setPraiseCount(dynamicCircle.getPraiseCount() == null ? 1 : dynamicCircle.getPraiseCount() + 1);
                msg = "点赞成功";
            } else {
                dynamicCircle.setPraiseCount(dynamicCircle.getPraiseCount() > 0 ? dynamicCircle.getPraiseCount() - 1 : 0);
                code = ServiceCode.ServeCode_601.code();
                msg = "取消点赞成功";
            }

            super.update(dynamicCircle);

            return ModelMapUtil.setModelMap(code, msg);
        }
        return "动态不存在";
    }


    @Override
    @Transactional
    public Object updateFavorites(Long id, Boolean isAdd) {
        UcDynamicCircle dynamicCircle = super.queryById(id);
        if (dynamicCircle != null) {
            if (isAdd) {
                dynamicCircle.setFavoritesCount(dynamicCircle.getFavoritesCount() == null ? 1 : dynamicCircle.getFavoritesCount() + 1);
            } else {
                dynamicCircle.setFavoritesCount(dynamicCircle.getFavoritesCount() != null && dynamicCircle.getFavoritesCount() > 0 ? dynamicCircle.getFavoritesCount() - 1 : 0);
            }

            return super.update(dynamicCircle);
        }
        return null;
    }


    @Override
    @Transactional
    public void cancelTopOverTime() {
        //封装查询参数
        EntityWrapper<UcDynamicCircle> ew = new EntityWrapper();
        ew.where("top_end_time <= NOW()");
        ew.eq("state", EnumConstants.State.NORMAL.value());
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("is_top", EnumConstants.YesOrNo.YES.getValue());

        UcDynamicCircle circle = new UcDynamicCircle();
        circle.setIsTop(EnumConstants.YesOrNo.NO.getValue());

        mapper.update(circle, ew);

    }


    @Override
    @Transactional
    public Object batchDel(BatchCancelParam params) {
        if (CollectionUtils.isNotEmpty(params.getIds())) {
            params.getIds().parallelStream().forEach(p -> {
                UcDynamicCircle pp = super.queryById(p);
                if (pp != null) {
                    super.del(pp.getId(), pp.getSellerId());
                }
            });
        }
        return null;
    }

}
