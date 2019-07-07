package com.xmyy.pay.allinpay.request;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class SendVerificationCodeParams extends YunParams implements Serializable {

    private String bizUserId;

    private String phone;

    private Long verificationCodeType;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getVerificationCodeType() {
        return verificationCodeType;
    }

    public void setVerificationCodeType(Long verificationCodeType) {
        this.verificationCodeType = verificationCodeType;
    }

    public SendVerificationCodeParams() {
    }

    public SendVerificationCodeParams(String bizUserId, String phone, Long verificationCodeType) {
        this.bizUserId = bizUserId;
        this.phone = phone;
        this.verificationCodeType = verificationCodeType;
    }

    @Override
    public String toString() {
        return "SendVerificationCodeParams{" +
                "bizUserId='" + bizUserId + '\'' +
                ", phone='" + phone + '\'' +
                ", verificationCodeType=" + verificationCodeType +
                '}';
    }
}
