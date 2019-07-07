package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmyy.circle.mapper.DgPraiseRecordMapper;
import com.xmyy.circle.model.DgComment;
import com.xmyy.circle.model.DgPraiseRecord;
import com.xmyy.circle.model.DgProductEvaluate;
import com.xmyy.circle.model.UcDynamicCircle;
import com.xmyy.circle.service.DgCommentService;
import com.xmyy.circle.service.DgPraiseRecordService;
import com.xmyy.circle.service.DgProductEvaluateService;
import com.xmyy.circle.service.UcDynamicCircleService;
import com.xmyy.circle.vo.MyPraiseResult;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.RelativeDateFormat;
import com.xmyy.common.vo.MemberInfo;
import com.xmyy.member.service.UcSellerService;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 点赞记录 服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = DgPraiseRecordService.class)
public class DgPraiseRecordServiceImpl extends BaseServiceImpl<DgPraiseRecord, DgPraiseRecordMapper> implements DgPraiseRecordService {

    @Resource
    private UcDynamicCircleService circleService;
    @Resource
    private DgCommentService commentService;
    @Resource
    private DgProductEvaluateService evaluateService;
    @Resource
    private UcSellerService sellerService;


    @Override
    @Transactional(readOnly = true)
    public Object mylist(Integer type, Long currUser, Integer current, Integer size) {
        if (current == null || current < 1) {
            current = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }

        //封装查询参数
        EntityWrapper<DgPraiseRecord> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.isNull("evaluate_id_");
        ew.orderDesc(Lists.newArrayList("create_time"));

        List<MyPraiseResult> list = null;

        if (type != null && type == 2) {
            //发出的赞
            ew.eq("member_id", currUser);
        } else {
            //收到的赞
            ew.eq("to_member_id", currUser);
        }

        int count = mapper.selectCount(ew);
        if (count > 0) {
            //分页查询，封装
            RowBounds rb = new RowBounds((current - 1) * size, size);
            List<DgPraiseRecord> dgComments = mapper.selectPage(rb, ew);
            list = dgComments.stream().map(o -> {
                MyPraiseResult r = InstanceUtil.to(o, MyPraiseResult.class);

                if (type != null && type == 2) {
                    //发出的赞
                    r.setAvatarRsurl(o.getToAvatarRsurl());
                    r.setNickName(o.getToNickName());
                    r.setMemberId(o.getToMemberId());
                    r.setMemberType(o.getToMemberType());
                }

                if (StringUtils.isBlank(r.getAvatarRsurl()) || StringUtils.isBlank(r.getNickName())) {
                    MemberInfo info = sellerService.getMemberInfo(r.getMemberId(), r.getMemberType());
                    if (info != null) {
                        r.setAvatarRsurl(info.getAvatarRsurl());
                        r.setNickName(info.getNickName());
                    }
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

                //笔记、视频的赞
                if (r.getCircleId() != null) {
                    UcDynamicCircle circle = circleService.queryById(r.getCircleId());
                    if (StringUtils.isNotBlank(circle.getContent())) {
                        String cric = circle.getContent().replaceAll("(<.*?>)|(&nbsp;)", "");
                        r.setContent(cric.length() > 40 ? cric.substring(0, 40) + "..." : cric);
                    }
                    r.setCoverRsurl(circle.getCoverRsurl());
                    r.setTitle(circle.getTitle());
                    r.setTypeId(circle.getTypeId());
                    r.setVideoRsurl(circle.getVideoRsurl());
                    r.setPraiseCount(circle.getPraiseCount());
                    r.setCommentCount(circle.getCommentCount());
                    r.setReadCount(circle.getReadCount());
                }

                //评论的赞
                if (r.getCommentId() != null) {
                    DgComment comment = commentService.queryById(r.getCommentId());
                    r.setTypeId(comment.getTypeId());
                    if (StringUtils.isNotBlank(comment.getContent())) {
                        r.setContent(comment.getContent().length() > 40 ? comment.getContent().substring(0, 40) + "..." : comment.getContent());
                    }
                }

                return r;
            }).collect(Collectors.toCollection(ArrayList::new));

        }

        Pagination<MyPraiseResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(current);
        page.setSize(size);
        page.setTotal(count);
        return page;
    }


    @Transactional
    public Boolean addPraise(DgPraiseRecord params) {
        MemberInfo info = sellerService.getMemberInfo(params.getMemberId(), params.getMemberType());

        UcDynamicCircle circle = null;
        DgComment comment = null;
        DgProductEvaluate evaluate = null;

        Long memberId = null;
        Integer memberType = null;

        if (params.getCircleId() != null) {
            circle = circleService.queryById(params.getCircleId());
            if (circle != null) {
                memberId = circle.getSellerId();
                memberType = EnumConstants.MemberType.seller.getValue();
            }
        }
        if (params.getCommentId() != null) {
            comment = commentService.queryById(params.getCommentId());
            if (comment != null) {
                memberId = comment.getMemberId();
                memberType = comment.getMemberType();
            }
        }
        if (params.getEvaluateId() != null) {
            evaluate = evaluateService.queryById(params.getEvaluateId());
            if (evaluate != null) {
                memberId = evaluate.getBuyerId();
                memberType = EnumConstants.MemberType.buyer.getValue();
            }
        }

        params.setToMemberId(memberId);
        params.setToMemberType(memberType);

        // Map<String, Object> map = InstanceUtil.transBean2Map(params);
        // map.put("enable",1);

        List<DgPraiseRecord> praises = this.queryListByBean(params);
        if (CollectionUtils.isNotEmpty(praises)) {
            praises.parallelStream().forEach(p -> {
                // p.setEnable(EnumConstants.YesOrNo.NO.getValue());
                super.del(p.getId(), p.getMemberId());
            });
            return false;
        }

        if (info != null) {
            params.setAvatarRsurl(info.getAvatarRsurl());
            params.setNickName(info.getNickName());
        }

        MemberInfo info1 = sellerService.getMemberInfo(memberId, memberType);

        if (info1 != null) {
            params.setToAvatarRsurl(info1.getAvatarRsurl());
            params.setToNickName(info1.getNickName());
        }

        super.update(params);

        return true;
    }


    @Transactional(readOnly = true)
    public List<DgPraiseRecord> getPraisesByMemberId(Long memberId, Long entityId, String entityTypeId) {
        EntityWrapper<DgPraiseRecord> ew = new EntityWrapper();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());

        ew.eq(entityTypeId, entityId);
        ew.eq("member_id", memberId);
        List<DgPraiseRecord> dgFavorites = this.mapper.selectList(ew);

        return dgFavorites;
    }


    @Transactional(readOnly = true)
    public List<DgPraiseRecord> getPraisesByMemberId(Long memberId, List<Long> entityIds, String entityTypeId) {
        EntityWrapper<DgPraiseRecord> ew = new EntityWrapper();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());

        ew.in(entityTypeId, entityIds);
        ew.eq("member_id", memberId);

        List<DgPraiseRecord> dgFavorites = this.mapper.selectList(ew);
        return dgFavorites;
    }

    private List<DgPraiseRecord> queryListByBean(DgPraiseRecord record) {
        //封装查询参数
        EntityWrapper<DgPraiseRecord> ew = new EntityWrapper();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        if (record.getMemberId() != null) {
            ew.eq("member_id", record.getMemberId());
        }
        if (record.getMemberType() != null) {
            ew.eq("member_type", record.getMemberType());
        }
        if (record.getCircleId() != null) {
            ew.eq("circle_id_", record.getCircleId());
        }
        if (record.getCommentId() != null) {
            ew.eq("comment_id_", record.getCommentId());
        }
        if (record.getEvaluateId() != null) {
            ew.eq("evaluate_id_", record.getEvaluateId());
        }
        if (record.getToMemberId() != null) {
            ew.eq("to_member_id", record.getToMemberId());
        }
        if (record.getToMemberType() != null) {
            ew.eq("to_member_type", record.getToMemberType());
        }

        return this.mapper.selectList(ew);
    }

}
