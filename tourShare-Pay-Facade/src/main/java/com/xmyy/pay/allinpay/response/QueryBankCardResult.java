package com.xmyy.pay.allinpay.response;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class QueryBankCardResult implements Serializable {

    private String bindCardList;

    public String getBindCardList() {
        return bindCardList;
    }

    public void setBindCardList(String bindCardList) {
        this.bindCardList = bindCardList;
    }

    @Override
    public String toString() {
        return "QueryBankCardResult{" +
                "bindCardList=" + bindCardList +
                '}';
    }
}
