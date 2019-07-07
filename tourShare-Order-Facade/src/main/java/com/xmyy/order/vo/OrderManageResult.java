package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value = "后台管理订单列表")
public class OrderManageResult implements Serializable {

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单时间（字符串）")
    private String orderTime;

    @ApiModelProperty(value = "物品名称（商品为名称，需求为需求描述）")
    private String productName;

    @ApiModelProperty(value = "商品图片")
    private String productImage;

    @ApiModelProperty(value = "数量")
    private Integer productNum;

    @ApiModelProperty(value = "物品单价")
    private BigDecimal price;

    @ApiModelProperty(value = "实付款")
    private BigDecimal payMoney;

    @ApiModelProperty(value = "辛苦费（需求形成的订单才可能有）")
    private BigDecimal toilsomePrice;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "交易状态（0交易中，1交易完成）")
    private Integer tradeStatus;

    @ApiModelProperty(value = "订单状态(1待付款，2待发货，3待收货，4交易成功，5售后处理中，6退款成功，7交易关闭，8已删除)")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单类型（1预售，2现货，3需求）")
    private Integer orderType;

    @ApiModelProperty(value = "需求单号（需求订单才有）")
    private String demandOrderNo;

    @ApiModelProperty(value = "运单号")
    private String wayBillNo;

    @ApiModelProperty(value = "是否鉴定(0否，1是)")
    private Integer isAppraisal;

    @ApiModelProperty(value = "是否评价(0否，1是)")
    private Integer isEvaluate;

    @ApiModelProperty(value = "备注")
    private String remark;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getDemandOrderNo() {
        return demandOrderNo;
    }

    public void setDemandOrderNo(String demandOrderNo) {
        this.demandOrderNo = demandOrderNo;
    }

    public String getWayBillNo() {
        return wayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        this.wayBillNo = wayBillNo;
    }

    public Integer getIsAppraisal() {
        return isAppraisal;
    }

    public void setIsAppraisal(Integer isAppraisal) {
        this.isAppraisal = isAppraisal;
    }

    public Integer getIsEvaluate() {
        return isEvaluate;
    }

    public void setIsEvaluate(Integer isEvaluate) {
        this.isEvaluate = isEvaluate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getToilsomePrice() {
        return toilsomePrice;
    }

    public void setToilsomePrice(BigDecimal toilsomePrice) {
        this.toilsomePrice = toilsomePrice;
    }
}
