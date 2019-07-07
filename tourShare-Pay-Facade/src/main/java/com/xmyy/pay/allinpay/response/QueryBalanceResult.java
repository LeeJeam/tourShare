package com.xmyy.pay.allinpay.response;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class QueryBalanceResult implements Serializable {

    private Long allAmount;

    private Long freezenAmount;

    public Long getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(Long allAmount) {
        this.allAmount = allAmount;
    }

    public Long getFreezenAmount() {
        return freezenAmount;
    }

    public void setFreezenAmount(Long freezenAmount) {
        this.freezenAmount = freezenAmount;
    }

    @Override
    public String toString() {
        return "QueryBalanceResult{" +
                "allAmount=" + allAmount +
                ", freezenAmount=" + freezenAmount +
                '}';
    }
}
