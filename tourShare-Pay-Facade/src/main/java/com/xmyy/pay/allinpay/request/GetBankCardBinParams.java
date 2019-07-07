package com.xmyy.pay.allinpay.request;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class GetBankCardBinParams extends YunParams implements Serializable {

    private String cardNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public GetBankCardBinParams() {
    }

    public GetBankCardBinParams(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public String toString() {
        return "GetBankCardBinParams{" +
                "cardNo='" + cardNo + '\'' +
                '}';
    }
}
