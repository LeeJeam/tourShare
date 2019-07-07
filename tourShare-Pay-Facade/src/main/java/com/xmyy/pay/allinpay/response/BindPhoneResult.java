package com.xmyy.pay.allinpay.response;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class BindPhoneResult implements Serializable {

    private String bizUserId;

    private String phone;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "BindPhoneResult{" +
                "bizUserId='" + bizUserId + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
