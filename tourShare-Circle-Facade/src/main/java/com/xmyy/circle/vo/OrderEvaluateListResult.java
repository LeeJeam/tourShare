package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@ApiModel(value = "订单里的评价列表")
public class OrderEvaluateListResult implements Serializable {

    @ApiModelProperty(value = "商品评价ID")
    private Long id;

    @ApiModelProperty(value = "买家昵称")
    private String nickName;

    @ApiModelProperty(value = "买家头像")
    private String avatarRsurl;

    @ApiModelProperty(value = "评价内容")
    private String content;

    @ApiModelProperty(value = "图片数组")
    private List<String> pics;

    @ApiModelProperty(value = "评价时间")
    private Date createTime;

    @ApiModelProperty(value = "追评")
    private String reviewContent;

    @ApiModelProperty(value = "追评时间")
    private Date reviewTime;

    @ApiModelProperty(value = "商品类型(1预售，2现货，3需求)")
    private Integer productType;

    @ApiModelProperty(value = "追评间隔天数")
    private Integer dayDiff;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getDayDiff() {
        return dayDiff;
    }

    public void setDayDiff(Integer dayDiff) {
        this.dayDiff = dayDiff;
    }
}
