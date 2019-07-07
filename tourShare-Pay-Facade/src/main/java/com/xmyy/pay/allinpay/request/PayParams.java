package com.xmyy.pay.allinpay.request;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class PayParams extends YunParams implements Serializable {

    private String bizUserId;

    private String bizOrderNo;

    private String tradeNo;

    private String verificationCode;

    private String consumerIp;

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

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getConsumerIp() {
        return consumerIp;
    }

    public void setConsumerIp(String consumerIp) {
        this.consumerIp = consumerIp;
    }

    public PayParams() {
    }

    public PayParams(String bizUserId, String bizOrderNo, String tradeNo, String verificationCode, String consumerIp) {
        this.bizUserId = bizUserId;
        this.bizOrderNo = bizOrderNo;
        this.tradeNo = tradeNo;
        this.verificationCode = verificationCode;
        this.consumerIp = consumerIp;
    }

    @Override
    public String toString() {
        return "PayParams{" +
                "bizUserId='" + bizUserId + '\'' +
                ", bizOrderNo='" + bizOrderNo + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", consumerIp='" + consumerIp + '\'' +
                '}';
    }

}
