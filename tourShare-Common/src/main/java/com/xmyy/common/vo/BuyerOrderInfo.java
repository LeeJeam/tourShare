package com.xmyy.common.vo;

import java.io.Serializable;

public class BuyerOrderInfo implements Serializable {

    private Long id;                //买家ID
    private Long time;              //
    private Integer waitPay;        //待支付数
    private Integer waitDeliver;    //待发货数
    private Integer waitReceipted;  //待收货数
    private Integer refund;         //退款、售后数
    private Integer  waitEvaluate;  //待评价数

    public BuyerOrderInfo() {
    }

    public BuyerOrderInfo(Long id, Integer waitPay, Integer waitDeliver, Integer waitReceipted, Integer refund, Integer waitEvaluate) {
        this.id = id;
        this.waitPay = waitPay;
        this.waitDeliver = waitDeliver;
        this.waitReceipted = waitReceipted;
        this.refund = refund;
        this.waitEvaluate = waitEvaluate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

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
