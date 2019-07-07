package com.xmyy.pay.allinpay.request;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class QueryBalanceParams extends YunParams implements Serializable {

    private String bizUserId;

    private String accountSetNo;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public String getAccountSetNo() {
        return accountSetNo;
    }

    public void setAccountSetNo(String accountSetNo) {
        this.accountSetNo = accountSetNo;
    }

    public QueryBalanceParams() {
    }

    public QueryBalanceParams(String bizUserId, String accountSetNo) {
        this.bizUserId = bizUserId;
        this.accountSetNo = accountSetNo;
    }

    @Override
    public String toString() {
        return "QueryBalanceParams{" +
                "bizUserId='" + bizUserId + '\'' +
                ", accountSetNo='" + accountSetNo + '\'' +
                '}';
    }
}
