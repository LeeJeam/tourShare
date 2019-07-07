package com.xmyy.pay.allinpay.response;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class ApplyBindBankCardResult implements Serializable {

    private String bizUserId;

    private String tranceNum;

    private String transDate;

    private String bankName;

    private String bankCode;

    private Long cardType;

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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Long getCardType() {
        return cardType;
    }

    public void setCardType(Long cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "ApplyBindBankCardResult{" +
                "bizUserId='" + bizUserId + '\'' +
                ", tranceNum='" + tranceNum + '\'' +
                ", transDate='" + transDate + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", cardType=" + cardType +
                '}';
    }
}
