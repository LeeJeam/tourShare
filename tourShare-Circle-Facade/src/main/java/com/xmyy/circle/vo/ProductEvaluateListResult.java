package com.xmyy.circle.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value = "评价列表结果")
public class ProductEvaluateListResult implements Serializable {

    @ApiModelProperty(value = "商品评价ID")
    private Long id;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "买手/背包客ID")
    private String sellerId;

    @ApiModelProperty(value = "买家ID")
    private String buyerId;

    @ApiModelProperty(value = "买家昵称")
    private String nickName;

    @ApiModelProperty(value = "买家头像")
    private String avatarRsurl;

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
    private Integer dayDiff;

    @ApiModelProperty(value = "商品快照ID")
    private Long productOrderId;

    @ApiModelProperty(value = "商品ID（点击跳转使用这个ID）")
    private Long productId;

    @ApiModelProperty(value = "商品类型(1预售，2现货，3需求)")
    private Integer productType;

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "商品封面")
    private String productCover;

    @ApiModelProperty(value = "商品单价")
    private BigDecimal productPrice;

    @JsonIgnore
    private String picRsurl;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Long getProductOrderId() {
        return productOrderId;
    }

    public void setProductOrderId(Long productOrderId) {
        this.productOrderId = productOrderId;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Integer getDayDiff() {
        return dayDiff;
    }

    public void setDayDiff(Integer dayDiff) {
        this.dayDiff = dayDiff;
    }

    public String getPicRsurl() {
        return picRsurl;
    }

    public void setPicRsurl(String picRsurl) {
        this.picRsurl = picRsurl;
    }

    public String getProductCover() {
        return productCover;
    }

    public void setProductCover(String productCover) {
        this.productCover = productCover;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }
}
