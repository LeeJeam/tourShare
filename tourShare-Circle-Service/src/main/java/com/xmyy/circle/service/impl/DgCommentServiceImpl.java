package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmyy.circle.mapper.DgCommentMapper;
import com.xmyy.circle.mapper.UcDynamicCircleMapper;
import com.xmyy.circle.model.DgComment;
import com.xmyy.circle.model.DgPraiseRecord;
import com.xmyy.circle.model.UcDynamicCircle;
import com.xmyy.circle.service.DgCommentService;
import com.xmyy.circle.service.DgPraiseRecordService;
import com.xmyy.circle.service.UcDynamicCircleService;
import com.xmyy.circle.vo.CommentAddParam;
import com.xmyy.circle.vo.CommentPageResult;
import com.xmyy.circle.vo.MyCommentResult;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.ServiceCode;
import com.xmyy.common.util.ModelMapUtil;
import com.xmyy.common.util.RelativeDateFormat;
import com.xmyy.common.vo.MemberInfo;
import com.xmyy.member.service.UcSellerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.JsonUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 动态评论  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = DgCommentService.class)
//@CacheConfig(cacheNames = "DgComment")
public class DgCommentServiceImpl extends BaseServiceImpl<DgComment, DgCommentMapper> implements DgCommentService {

    @Resource
    private DgPraiseRecordService praiseRecordService;
    @Resource
    private UcDynamicCircleService circleService;
    @Resource
    private UcSellerService sellerService;
    @Resource
    private UcDynamicCircleMapper dynamicCircleMapper;


    /**
     * 动态详情页评论列表
     * @param circleId 笔记/视频id
     * @param current
     * @param size
     * @param currUser  当前登录用户id 判断评论/回复是否被点赞
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Pagination<CommentPageResult> getPageByCircleId(Long circleId, Integer current, Integer size,Long currUser) {
        if(current == null) current = 1;
        if(size == null) size=10;
        //封装查询参数
        EntityWrapper<DgComment> ew = new EntityWrapper<>();
        ew.eq("circle_id_",circleId);
        ew.isNull("parent_id_");
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());

        ew.orderDesc(Lists.newArrayList("praise_count","create_time"));

        List<CommentPageResult> list = null;

        int count = mapper.selectCount(ew);
        if(count > 0 ){
            //分页查询，封装订单信息
            RowBounds rb = new RowBounds((current - 1) * size, size);
            List<DgComment> dgComments = mapper.selectPage(rb, ew);
            list = dgComments.stream().map(o -> {

                CommentPageResult r = InstanceUtil.to(o,CommentPageResult.class);
                if(StringUtils.isNotBlank(r.getJsonContent())){
                    CommentPageResult commentPageResult = JsonUtil.fromJson(r.getJsonContent(), CommentPageResult.class);
                    List<DgPraiseRecord> praises = praiseRecordService.getPraisesByMemberId(currUser, commentPageResult.getId(), "comment_id_");
                    commentPageResult.setIsPraised(CollectionUtils.isNotEmpty(praises)?EnumConstants.YesOrNo.YES.getValue():EnumConstants.YesOrNo.NO.getValue());
                    r.setComment(commentPageResult);
                }
                return r;
            }).collect(Collectors.toCollection(ArrayList::new));

            if(currUser != null){

                checkPraise(currUser, list);
            }
        }

        Pagination<CommentPageResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(current);
        page.setSize(size);
        page.setTotal(count);


        return page;
    }

    @Transactional(readOnly = true)
    public void checkPraise(Long currUser, List<CommentPageResult> list) {
        List<Long> entityIds = list.parallelStream().map(CommentPageResult::getId).collect(Collectors.toList());

        List<DgPraiseRecord> praises = praiseRecordService.getPraisesByMemberId(currUser, entityIds, "comment_id_");
        List<Long> comments = praises.parallelStream().map(m->{return m.getCommentId();}).collect(Collectors.toList());

        list.parallelStream().forEach(r->{
            r.setIsPraised(comments.contains(r.getId())?EnumConstants.YesOrNo.YES.getValue():EnumConstants.YesOrNo.NO.getValue());
        });
    }


    @Override
    @Transactional
    public Object addComment(CommentAddParam params) {
        DgComment comment = InstanceUtil.to(params,DgComment.class);
        UcDynamicCircle circle = circleService.queryById(params.getCircleId());
        if(circle == null) {
            return "不存在该动态";
        }

        DgComment parentComment = null;
        DgComment rootComment = null;
        if(params.getParentId() == null){
            //评论
            //评论人信息
            MemberInfo info = sellerService.getMemberInfo(params.getMemberId(), params.getMemberType());
            if(info != null){
                comment.setAvatarRsurl(info.getAvatarRsurl());
                comment.setNickName(info.getNickName());
            }
            comment.setPraiseCount(0);

            //被评论人信息，回复是第一层，所以被评论者就是动态
            comment.setToMemberId(circle.getSellerId());
            comment.setToMemberType(EnumConstants.MemberType.seller.getValue());
            MemberInfo sellerInfo = sellerService.getMemberInfo(circle.getSellerId(), EnumConstants.MemberType.seller.getValue());
            if(sellerInfo != null){
                comment.setToAvatarRsurl(sellerInfo.getAvatarRsurl());
                comment.setToNickName(sellerInfo.getNickName());
            }

        } else {
            //回复
            if(params.getCommentId() == null){
                return "根评论id不能为空";
            }

            rootComment = super.queryById(params.getCommentId());
            if (rootComment == null) {
                return "根评论不存在";
            }

            parentComment = super.queryById(params.getParentId());
            if(parentComment == null) {
                return "不存在父评论";
            }

            //回复者信息
            MemberInfo info = sellerService.getMemberInfo(params.getMemberId(), params.getMemberType());
            if(info != null){
                comment.setAvatarRsurl(info.getAvatarRsurl());
                comment.setNickName(info.getNickName());
            }
            comment.setPraiseCount(0);

            //被回复的是父评论/回复，所以被回复对象信息就是父评论/回复数据
            comment.setToMemberId(parentComment.getMemberId());
            comment.setToMemberType(parentComment.getMemberType());
            MemberInfo info1 = sellerService.getMemberInfo(parentComment.getMemberId(), parentComment.getMemberType());
            if(info1 != null){
                comment.setToAvatarRsurl(info1.getAvatarRsurl());
                comment.setToNickName(info1.getNickName());
            }
        }
        comment.setTypeId(circle.getTypeId());
        super.update(comment);
        rootComment = super.queryById(params.getCommentId());

        //更新动态评论数（评论/回复都算）
        circle.setCommentCount(circle.getCommentCount() + 1);
        circleService.update(circle);

        if(rootComment != null){ //有根评论，说明是回复
            if(StringUtils.isBlank(rootComment.getJsonContent())){
                rootComment.setJsonContent(JsonUtil.toJson(comment));
            }
            rootComment.setCommentCount(rootComment.getCommentCount() == null ? 1 : rootComment.getCommentCount()+1);
            super.update(rootComment);
        }

        return null;
    }


    @Override
    @Transactional
    public Object praise(Long id, Long memberId, Integer memberType) {
        DgComment comment = super.queryById(id);
        if(comment != null){
            DgPraiseRecord record = new DgPraiseRecord();
            record.setCommentId(comment.getId());
            record.setMemberId(memberId);
            record.setMemberType(memberType);

            String code = ServiceCode.ServeCode_600.code();
            String msg = null;

            boolean isAdd = praiseRecordService.addPraise(record);
            if (isAdd) {
                comment.setPraiseCount(comment.getPraiseCount() == null ? 1 : comment.getPraiseCount() + 1);
                msg = "点赞成功";
            } else {
                comment.setPraiseCount(comment.getPraiseCount() > 0 ? comment.getPraiseCount() - 1 : 0);

                code = ServiceCode.ServeCode_601.code();
                msg = "取消点赞成功";
            }

            if(comment.getParentId() != null && comment.getParentId().equals(comment.getCommentId())){
                DgComment comment1 = super.queryById(comment.getParentId());
                DgComment comment2 = JsonUtil.fromJson(comment1.getJsonContent(), DgComment.class);
                if(comment2.getId().equals(comment.getId())){
                    comment1.setJsonContent(JsonUtil.toJson(comment));
                    super.update(comment1);
                }
            }

            super.update(comment);

            return ModelMapUtil.setModelMap(code,msg);
        }
        return "不存在该评论";
    }


    @Override
    @Transactional(readOnly = true)
    public Object mylist(Integer type, Integer memberType, Long memberId, Integer current,Integer size) {
        if(current == null || current < 1) {
            current = 1;
        }
        if(size == null || size < 1) {
            size = 10;
        }
        if (type == null) {
            type = 1;
        }
        if (memberType == null) {
            return "缺少memberType";
        }

        //封装查询参数
        EntityWrapper<DgComment> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.orderDesc(Lists.newArrayList("create_time"));

        List<MyCommentResult> list = null;

        if(type == 2){
            //发出的评论
            ew.eq("member_id", memberId);
            ew.eq("member_type", memberType);
        } else {
            //收到的评论
            ew.eq("to_member_id", memberId);
            ew.eq("to_member_type", memberType);
        }

        int count = mapper.selectCount(ew);
        if(count > 0 ){
            //分页查询，封装订单信息
            RowBounds rb = new RowBounds((current - 1) * size, size);
            List<DgComment> dgComments = mapper.selectPage(rb, ew);
            list = dgComments.stream().map(o -> {

                MyCommentResult r = InstanceUtil.to(o,MyCommentResult.class);
                UcDynamicCircle circle = dynamicCircleMapper.selectById(r.getCircleId());

                if(StringUtils.isNotBlank(circle.getContent())){
                    String cric = circle.getContent().replaceAll("(<.*?>)|(&nbsp;)","");
                    r.setCircleContent(cric.length() > 40?cric.substring(0,40)+"...":cric);
                }

                r.setCoverRsurl(circle.getCoverRsurl());
                r.setTitle(circle.getTitle());
                r.setTypeId(circle.getTypeId());
                r.setVideoRsurl(circle.getVideoRsurl());
                r.setTypeId(circle.getTypeId());

                if(r.getCreateTime() != null) {
                    r.setCreateTimeStr(RelativeDateFormat.format(r.getCreateTime()));
                }

                return r;
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        Pagination<MyCommentResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(current);
        page.setSize(size);
        page.setTotal(count);
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getPageByParentId(Long parentId, Integer current, Integer size, Long currUser) {
        if(current == null) current = 1;
        if(size == null) size=10;
        //封装查询参数
        EntityWrapper<DgComment> ew1 = new EntityWrapper();

        //FIND_IN_SET 自定义的函数
        //ew1.where("FIND_IN_SET(id_,getCommentChild({0}))",parentId);
        //ew1.where("comment_id = {0} or id = {1}",parentId,parentId);
        ew1.isNotNull("parent_id_");
        ew1.eq("comment_id",parentId);
        ew1.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew1.orderDesc(Lists.newArrayList("praise_count","create_time"));


        List<CommentPageResult> list = null;

        int count = mapper.selectCount(ew1);
        if(count > 0 ){
            RowBounds rb = new RowBounds((current - 1) * size, size);
            List<DgComment> dgComments1 = mapper.selectPage(rb, ew1);

            list = dgComments1.stream().map(d -> {
                CommentPageResult re = InstanceUtil.to(d,CommentPageResult.class);
                return re;

            }).collect(Collectors.toCollection(ArrayList::new));

            if(currUser != null){

                checkPraise(currUser, list);
            }
        }

        Pagination<CommentPageResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(current);
        page.setSize(size);
        page.setTotal(count);


        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getInfo(Long parentId, Long currUser) {
        DgComment dc = super.queryById(parentId);

        CommentPageResult re = InstanceUtil.to(dc,CommentPageResult.class);
        List<CommentPageResult> commentPageResults = Lists.newArrayList(re);
        if(currUser != null){

            checkPraise(currUser, commentPageResults);
        }
        return CollectionUtils.isNotEmpty(commentPageResults)?commentPageResults.get(0):null;
    }

}
