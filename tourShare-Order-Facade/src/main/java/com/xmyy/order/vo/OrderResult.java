package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "订单列表中一张订单")
public class OrderResult implements Serializable {

    @ApiModelProperty(value = "订单下商品列表")
    private List<GoodsResult> goodsList;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单类型（1预售，2现货，3需求）")
    private Integer orderType;

    @ApiModelProperty(value = "买家ID")
    private Long buyerId;

    @ApiModelProperty(value = "买家UUID")
    private String buyerUUID;

    @ApiModelProperty(value = "买家昵称")
    private String buyerNickname;

    @ApiModelProperty(value = "买家头像")
    private String buyerImage;

    @ApiModelProperty(value = "买手ID")
    private Long sellerId;

    @ApiModelProperty(value = "买手UUID")
    private String sellerUUID;

    @ApiModelProperty(value = "买手昵称")
    private String sellerNickname;

    @ApiModelProperty(value = "买手头像")
    private String sellerImage;

    @ApiModelProperty(value = "是否背包客（0否，1是）")
    private Integer isPacker;

    @ApiModelProperty(value = "购买商品总数")
    private Integer productNum;

    @ApiModelProperty(value = "订单总实付金额")
    private BigDecimal payMoney;

    @ApiModelProperty(value = "物流单号")
    private String waybillNo;

    @ApiModelProperty(value = "鉴定费")
    private BigDecimal appraisalPrice;

    @ApiModelProperty(value = "鉴定视频地址")
    private String appraisalUrl;

    @ApiModelProperty(value = "评价状态，0待评价 1已评价 2已追评")
    private Integer evaluateStatus;

    @ApiModelProperty(value = "订单状态显示文字")
    private String orderStatusInfo;

    @ApiModelProperty(value = "评价状态显示文字")
    private String evaluateStatusInfo;

    @ApiModelProperty(value = "辛苦费（只有需求有）")
    private BigDecimal toilsomePrice;

    @ApiModelProperty(value = "订单取消原因")
    private String cancelText;

    public List<GoodsResult> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsResult> goodsList) {
        this.goodsList = goodsList;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public BigDecimal getAppraisalPrice() {
        return appraisalPrice;
    }

    public void setAppraisalPrice(BigDecimal appraisalPrice) {
        this.appraisalPrice = appraisalPrice;
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

    public Integer getEvaluateStatus() {
        return evaluateStatus;
    }

    public void setEvaluateStatus(Integer evaluateStatus) {
        this.evaluateStatus = evaluateStatus;
    }

    public String getOrderStatusInfo() {
        return orderStatusInfo;
    }

    public void setOrderStatusInfo(String orderStatusInfo) {
        this.orderStatusInfo = orderStatusInfo;
    }

    public String getEvaluateStatusInfo() {
        return evaluateStatusInfo;
    }

    public void setEvaluateStatusInfo(String evaluateStatusInfo) {
        this.evaluateStatusInfo = evaluateStatusInfo;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getAppraisalUrl() {
        return appraisalUrl;
    }

    public void setAppraisalUrl(String appraisalUrl) {
        this.appraisalUrl = appraisalUrl;
    }

    public BigDecimal getToilsomePrice() {
        return toilsomePrice;
    }

    public void setToilsomePrice(BigDecimal toilsomePrice) {
        this.toilsomePrice = toilsomePrice;
    }

    public String getCancelText() {
        return cancelText;
    }

    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
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
