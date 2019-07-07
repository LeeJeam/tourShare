package com.xmyy.pay.allinpay.response;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class UnbindBankCardResult implements Serializable {

    private String bizUserId;

    private String cardNo;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public String toString() {
        return "UnbindBankCardResult{" +
                "bizUserId='" + bizUserId + '\'' +
                ", cardNo='" + cardNo + '\'' +
                '}';
    }
}
