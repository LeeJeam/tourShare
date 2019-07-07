package com.xmyy.pay.allinpay.response;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class BindBankCardResult implements Serializable {

    private String bizUserId;

    private String tranceNum;

    private String transDate;

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

    @Override
    public String toString() {
        return "BindBankCardResult{" +
                "bizUserId='" + bizUserId + '\'' +
                ", tranceNum='" + tranceNum + '\'' +
                ", transDate='" + transDate + '\'' +
                '}';
    }
}
