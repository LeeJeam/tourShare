package com.xmyy.pay.allinpay.response;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class GetBankCardBinResult implements Serializable {

    private String cardBinInfo;

    public String getCardBinInfo() {
        return cardBinInfo;
    }

    public void setCardBinInfo(String cardBinInfo) {
        this.cardBinInfo = cardBinInfo;
    }
}
