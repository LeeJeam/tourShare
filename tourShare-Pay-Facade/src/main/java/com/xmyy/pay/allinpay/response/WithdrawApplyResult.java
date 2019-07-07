package com.xmyy.pay.allinpay.response;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class WithdrawApplyResult implements Serializable {

    private String payStatus;

    private String payFailMessage;

    private String bizUserId;

    private String orderNo;

    private String bizOrderNo;

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

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
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

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    @Override
    public String toString() {
        return "WithdrawApplyResult{" +
                "payStatus='" + payStatus + '\'' +
                ", payFailMessage='" + payFailMessage + '\'' +
                ", bizUserId='" + bizUserId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", bizOrderNo='" + bizOrderNo + '\'' +
                ", extendInfo='" + extendInfo + '\'' +
                '}';
    }
}
