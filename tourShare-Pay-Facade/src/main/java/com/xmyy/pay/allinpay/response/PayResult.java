package com.xmyy.pay.allinpay.response;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class PayResult implements Serializable {

    private String payStatus;

    private String payFailMessage;

    private String bizUserId;

    private String bizOrderNo;

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

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public void setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
    }

    @Override
    public String toString() {
        return "PayResult{" +
                "payStatus='" + payStatus + '\'' +
                ", payFailMessage='" + payFailMessage + '\'' +
                ", bizUserId='" + bizUserId + '\'' +
                ", bizOrderNo=" + bizOrderNo +
                '}';
    }
}
