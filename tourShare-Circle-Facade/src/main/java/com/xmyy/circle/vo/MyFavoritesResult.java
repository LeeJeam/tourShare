package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "我的收藏")
public class MyFavoritesResult implements Serializable {

    private Long id;

    @ApiModelProperty(value = "依赖实体类型（1，笔记；2，视频；3，商品）")
    private Integer entityTypeId;

    @ApiModelProperty(value = "依赖实体id")
    private Long entityId;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "动态视频路径")
    private String videoRsurl;

    @ApiModelProperty(value = "商品图片/动态封面地址(多个以英文逗号隔开)")
    private String coverRsurl;

    @ApiModelProperty(value = "动态标题/商品名称")
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

    @ApiModelProperty(value = "分类标签(多个以英文逗号隔开)")
    private String classifyTags;

    @ApiModelProperty(value = "收藏数")
    private Integer favoritesCount;

    @ApiModelProperty(value = "商品类目")
    private String category;

    @ApiModelProperty(value = "最小价格")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "创建者")
    private String createName;

    @ApiModelProperty(value = "状态值")
    private String stateStr;

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Integer getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(Integer favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public String getClassifyTags() {
        return classifyTags;
    }

    public void setClassifyTags(String classifyTags) {
        this.classifyTags = classifyTags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(Integer entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
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

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
