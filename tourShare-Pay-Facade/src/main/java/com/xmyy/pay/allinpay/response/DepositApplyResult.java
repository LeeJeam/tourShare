package com.xmyy.pay.allinpay.response;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class DepositApplyResult implements Serializable {

    private String payStatus;

    private String payFailMessage;

    private String bizUserId;

    private String orderNo;

    private String bizOrderNo;

    private String payCode;

    private String extendInfo;

    private JSONObject weChatAPPInfo;

    private String payInfo;

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

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public JSONObject getWeChatAPPInfo() {
        return weChatAPPInfo;
    }

    public void setWeChatAPPInfo(JSONObject weChatAPPInfo) {
        this.weChatAPPInfo = weChatAPPInfo;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    @Override
    public String toString() {
        return "DepositApplyResult{" +
                "payStatus='" + payStatus + '\'' +
                ", payFailMessage='" + payFailMessage + '\'' +
                ", bizUserId='" + bizUserId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", bizOrderNo='" + bizOrderNo + '\'' +
                ", payCode='" + payCode + '\'' +
                ", extendInfo='" + extendInfo + '\'' +
                ", weChatAPPInfo=" + weChatAPPInfo +
                ", payInfo='" + payInfo + '\'' +
                '}';
    }
}
