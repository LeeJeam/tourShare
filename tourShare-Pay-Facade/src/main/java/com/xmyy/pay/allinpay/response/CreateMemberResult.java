package com.xmyy.pay.allinpay.response;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/4.
 */
public class CreateMemberResult implements Serializable {

    private String userId;

    private String bizUserId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    @Override
    public String toString() {
        return "CreateMemberResult{" +
                "userId='" + userId + '\'' +
                ", bizUserId='" + bizUserId + '\'' +
                '}';
    }
}
