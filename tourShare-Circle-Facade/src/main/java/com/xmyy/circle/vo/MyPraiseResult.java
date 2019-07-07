package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "我的赞")
public class MyPraiseResult implements Serializable {

    @ApiModelProperty(value = "评论/回复ID")
    private Long commentId;

    @ApiModelProperty(value = "动态ID")
    private Long circleId;

    @ApiModelProperty(value = "用户ID")
    private Long memberId;

    @ApiModelProperty(value = "用户类型(1买手，2买家)")
    private Integer memberType;

    @ApiModelProperty(value = "头像")
    private String avatarRsurl;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "类型（1笔记，2视频）")
    private Integer typeId;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "动态视频路径")
    private String videoRsurl;

    @ApiModelProperty(value = "动态封面地址(多个以英文逗号隔开)")
    private String coverRsurl;

    @ApiModelProperty(value = "动态标题")
    private String title;

    @ApiModelProperty(value = "点赞数")
    private Integer praiseCount;

    @ApiModelProperty(value = "评论数")
    private Integer commentCount;

    @ApiModelProperty(value = "创建时间,比如：58分钟前")
    private String createTimeStr;

    @ApiModelProperty(value = "阅读数")
    private Integer readCount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getCircleId() {
        return circleId;
    }

    public void setCircleId(Long circleId) {
        this.circleId = circleId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getAvatarRsurl() {
        return avatarRsurl;
    }

    public void setAvatarRsurl(String avatarRsurl) {
        this.avatarRsurl = avatarRsurl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }


    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideoRsurl() {
        return videoRsurl;
    }

    public void setVideoRsurl(String videoRsurl) {
        this.videoRsurl = videoRsurl;
    }

    public String getCoverRsurl() {
        return coverRsurl;
    }

    public void setCoverRsurl(String coverRsurl) {
        this.coverRsurl = coverRsurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
