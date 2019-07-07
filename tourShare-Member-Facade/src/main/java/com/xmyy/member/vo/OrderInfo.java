package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "订单各状态统计数")
public class OrderInfo implements Serializable {

    @ApiModelProperty(value = "待支付数")
    private Integer waitPay;

    @ApiModelProperty(value = "待发货数")
    private Integer waitDeliver;

    @ApiModelProperty(value = "待收货数")
    private Integer waitReceipted;

    @ApiModelProperty(value = "退款/售后数")
    private Integer refund;

    @ApiModelProperty(value = "待评价数")
    private Integer  waitEvaluate;

    public Integer getWaitPay() {
        return waitPay;
    }

    public void setWaitPay(Integer waitPay) {
        this.waitPay = waitPay;
    }

    public Integer getWaitDeliver() {
        return waitDeliver;
    }

    public void setWaitDeliver(Integer waitDeliver) {
        this.waitDeliver = waitDeliver;
    }

    public Integer getWaitReceipted() {
        return waitReceipted;
    }

    public void setWaitReceipted(Integer waitReceipted) {
        this.waitReceipted = waitReceipted;
    }

    public Integer getRefund() {
        return refund;
    }

    public void setRefund(Integer refund) {
        this.refund = refund;
    }

    public Integer getWaitEvaluate() {
        return waitEvaluate;
    }

    public void setWaitEvaluate(Integer waitEvaluate) {
        this.waitEvaluate = waitEvaluate;
    }
}
