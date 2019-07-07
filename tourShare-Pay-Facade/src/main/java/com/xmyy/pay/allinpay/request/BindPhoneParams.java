package com.xmyy.pay.allinpay.request;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class BindPhoneParams extends YunParams implements Serializable {

    private String bizUserId;

    private String phone;

    private String verificationCode;

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

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public BindPhoneParams() {
    }

    public BindPhoneParams(String bizUserId, String phone, String verificationCode) {
        this.bizUserId = bizUserId;
        this.phone = phone;
        this.verificationCode = verificationCode;
    }

    @Override
    public String toString() {
        return "BindPhoneParams{" +
                "bizUserId='" + bizUserId + '\'' +
                ", phone='" + phone + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                '}';
    }
}
