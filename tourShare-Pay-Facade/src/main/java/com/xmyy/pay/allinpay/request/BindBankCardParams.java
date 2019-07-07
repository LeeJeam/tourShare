package com.xmyy.pay.allinpay.request;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class BindBankCardParams extends YunParams implements Serializable {

    private String bizUserId;

    private String tranceNum;

    private String transDate;

    private String phone;

    private String verificationCode;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public String getTranceNum() {
        return tranceNum;
    }

    public void setTranceNum(String tranceNum) {
        this.tranceNum = tranceNum;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
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

    public BindBankCardParams() {
    }

    public BindBankCardParams(String bizUserId, String tranceNum, String transDate, String phone, String verificationCode) {
        this.bizUserId = bizUserId;
        this.tranceNum = tranceNum;
        this.transDate = transDate;
        this.phone = phone;
        this.verificationCode = verificationCode;
    }

    @Override
    public String toString() {
        return "BindBankCardParams{" +
                "bizUserId='" + bizUserId + '\'' +
                ", tranceNum='" + tranceNum + '\'' +
                ", transDate='" + transDate + '\'' +
                ", phone='" + phone + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                '}';
    }
}
