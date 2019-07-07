package com.xmyy.pay.allinpay.request;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class RefundParams extends YunParams implements Serializable {

    private String bizOrderNo;

    private String oriBizOrderNo;

    private String bizUserId;

    private String refundType;

    private JSONArray refundList;

    private String backUrl;

    private Long amount;

    private Long couponAmount;

    private Long feeAmount;

    private String extendInfo;

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public void setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
    }

    public String getOriBizOrderNo() {
        return oriBizOrderNo;
    }

    public void setOriBizOrderNo(String oriBizOrderNo) {
        this.oriBizOrderNo = oriBizOrderNo;
    }

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public JSONArray getRefundList() {
        return refundList;
    }

    public void setRefundList(JSONArray refundList) {
        this.refundList = refundList;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
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

    public RefundParams() {
    }

    public RefundParams(String bizOrderNo, String oriBizOrderNo, String bizUserId, JSONArray refundList, String backUrl, Long amount, Long couponAmount, Long feeAmount, String extendInfo) {
        this.bizOrderNo = bizOrderNo;
        this.oriBizOrderNo = oriBizOrderNo;
        this.bizUserId = bizUserId;
        this.refundList = refundList;
        this.backUrl = backUrl;
        this.amount = amount;
        this.couponAmount = couponAmount;
        this.feeAmount = feeAmount;
        this.extendInfo = extendInfo;
    }

    @Override
    public String toString() {
        return "RefundParams{" +
                "bizOrderNo='" + bizOrderNo + '\'' +
                ", oriBizOrderNo='" + oriBizOrderNo + '\'' +
                ", bizUserId='" + bizUserId + '\'' +
                ", refundList=" + refundList +
                ", backUrl='" + backUrl + '\'' +
                ", amount=" + amount +
                ", couponAmount=" + couponAmount +
                ", feeAmount=" + feeAmount +
                ", extendInfo='" + extendInfo + '\'' +
                '}';
    }
}
