package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "后台行程关联的订单信息")
public class OrderInTourManageResult implements Serializable {

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单状态文字")
    private String orderStatusStr;

    @ApiModelProperty(value = "物品类目")
    private String category;

    @ApiModelProperty(value = "数量")
    private Integer productNum;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "买家ID")
    private Long buyerId;

    @ApiModelProperty(value = "辛苦费（需求形订单才可能有）")
    private BigDecimal toilsomePrice;

    @ApiModelProperty(value = "成交价")
    private BigDecimal payMoney;

    @ApiModelProperty(value = "发货状态文字")
    private String deliverStatusStr;

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

    public String getOrderStatusStr() {
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public BigDecimal getToilsomePrice() {
        return toilsomePrice;
    }

    public void setToilsomePrice(BigDecimal toilsomePrice) {
        this.toilsomePrice = toilsomePrice;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getDeliverStatusStr() {
        return deliverStatusStr;
    }

    public void setDeliverStatusStr(String deliverStatusStr) {
        this.deliverStatusStr = deliverStatusStr;
    }
}
