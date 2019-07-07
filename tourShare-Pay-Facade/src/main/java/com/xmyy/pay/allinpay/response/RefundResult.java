package com.xmyy.pay.allinpay.response;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class RefundResult implements Serializable {

    private String payStatus;

    private String payFailMessage;

    private String orderNo;

    private String bizOrderNo;

    private Long amount;

    private Long couponAmount;

    private Long feeAmount;

    private String extendInfo;

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayFailMessage() {
        return payFailMessage;
    }

    public void setPayFailMessage(String payFailMessage) {
        this.payFailMessage = payFailMessage;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public void setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Long couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Long getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Long feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    @Override
    public String toString() {
        return "RefundResult{" +
                "payStatus='" + payStatus + '\'' +
                ", payFailMessage='" + payFailMessage + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", bizOrderNo='" + bizOrderNo + '\'' +
                ", amount=" + amount +
                ", couponAmount=" + couponAmount +
                ", feeAmount=" + feeAmount +
                ", extendInfo='" + extendInfo + '\'' +
                '}';
    }
}
