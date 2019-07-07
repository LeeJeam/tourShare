package com.xmyy.circle.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@ApiModel(value = "商品详情的评价列表")
public class EvaluateByProductIdResult implements Serializable {

    @ApiModelProperty(value = "商品评价ID")
    private Long id;

    @ApiModelProperty(value = "买家ID")
    private Long buyerId;

    @ApiModelProperty(value = "昵称昵称")
    private String nickName;

    @ApiModelProperty(value = "买家头像")
    private String avatarRsurl;

    @ApiModelProperty(value = "点赞数")
    private Integer praiseCount;

    @ApiModelProperty(value = "是否点赞（0否，1是）")
    private Integer isPraise;

    @ApiModelProperty(value = "评价内容")
    private String content;

    @ApiModelProperty(value = "评价图片数组")
    private List<String> pics;

    @ApiModelProperty(value = "追评内容")
    private String reviewContent;

    @ApiModelProperty(value = "评价时间")
    private Date createTime;

    @ApiModelProperty(value = "追价时间")
    private Date reviewTime;

    @ApiModelProperty(value = "追评间隔天数")
    private Long dayDiff;

    @JsonIgnore
    private String picRsurl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarRsurl() {
        return avatarRsurl;
    }

    public void setAvatarRsurl(String avatarRsurl) {
        this.avatarRsurl = avatarRsurl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Long getDayDiff() {
        return dayDiff;
    }

    public void setDayDiff(Long dayDiff) {
        this.dayDiff = dayDiff;
    }

    public Integer getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(Integer isPraise) {
        this.isPraise = isPraise;
    }

    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public String getPicRsurl() {
        return picRsurl;
    }

    public void setPicRsurl(String picRsurl) {
        this.picRsurl = picRsurl;
    }
}
