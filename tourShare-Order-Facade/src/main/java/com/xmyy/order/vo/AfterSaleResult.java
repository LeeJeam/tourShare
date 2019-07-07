package com.xmyy.order.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "售后列表中的一项")
public class AfterSaleResult implements Serializable {

    @ApiModelProperty(value = "退货单ID")
    private Long returnOrderId;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单类型：1预售；2现货；3需求")
    private Integer orderType;

    @ApiModelProperty(value = "商品ID")
    private Long productId;

    @ApiModelProperty(value = "商品图片")
    private String productImage;

    @ApiModelProperty(value = "商品标题（预售现货为商品名称，需求为需求描述）")
    private String productTitle;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "购买数量")
    private Integer productNum;

    @ApiModelProperty(value = "商品规格参数，黑白/36")
    private String specParams;

    @ApiModelProperty(value = "需求过期时间")
    private Date deliveryTime;

    @ApiModelProperty(value = "需求过期时间字符串yyyy-MM-dd HH:mm:ss")
    private String deliveryTimeInfo;

    @ApiModelProperty(value = "买家ID")
    private Long buyerId;

    @ApiModelProperty(value = "买家UUID")
    private String buyerUUID;

    @ApiModelProperty(value = "买家昵称")
    private String buyerNickname;

    @ApiModelProperty(value = "买家头像")
    private String buyerImage;

    @ApiModelProperty(value = "是否背包客 0否；1是")
    private Integer isPacker;

    @ApiModelProperty(value = "买手ID")
    private Long sellerId;

    @ApiModelProperty(value = "买手UUID")
    private String sellerUUID;

    @ApiModelProperty(value = "买手昵称")
    private String sellerNickname;

    @ApiModelProperty(value = "买手头像")
    private String sellerImage;

    @ApiModelProperty(value = "售后状态：0售后处理中 1退款成功")
    private Integer returnStatus;

    public Long getReturnOrderId() {
        return returnOrderId;
    }

    public void setReturnOrderId(Long returnOrderId) {
        this.returnOrderId = returnOrderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public String getSpecParams() {
        return specParams;
    }

    public void setSpecParams(String specParams) {
        this.specParams = specParams;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerNickname() {
        return buyerNickname;
    }

    public void setBuyerNickname(String buyerNickname) {
        this.buyerNickname = buyerNickname;
    }

    public String getBuyerImage() {
        return buyerImage;
    }

    public void setBuyerImage(String buyerImage) {
        this.buyerImage = buyerImage;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerNickname() {
        return sellerNickname;
    }

    public void setSellerNickname(String sellerNickname) {
        this.sellerNickname = sellerNickname;
    }

    public String getSellerImage() {
        return sellerImage;
    }

    public void setSellerImage(String sellerImage) {
        this.sellerImage = sellerImage;
    }

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getDeliveryTimeInfo() {
        return deliveryTimeInfo;
    }

    public void setDeliveryTimeInfo(String deliveryTimeInfo) {
        this.deliveryTimeInfo = deliveryTimeInfo;
    }

    public String getBuyerUUID() {
        return buyerUUID;
    }

    public void setBuyerUUID(String buyerUUID) {
        this.buyerUUID = buyerUUID;
    }

    public String getSellerUUID() {
        return sellerUUID;
    }

    public void setSellerUUID(String sellerUUID) {
        this.sellerUUID = sellerUUID;
    }
}
