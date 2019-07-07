package com.xmyy.pay.allinpay.request;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class GetMemberInfoParams extends YunParams implements Serializable {

    private String bizUserId;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    @Override
    public String toString() {
        return "GetMemberInfoParams{" +
                "bizUserId='" + bizUserId + '\'' +
                '}';
    }
}
