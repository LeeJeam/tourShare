package com.xmyy.pay.allinpay.response;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class ChangeBindPhoneResult implements Serializable {

    private String bizUserId;

    private String oldPhone;

    private String newPhone;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public String getOldPhone() {
        return oldPhone;
    }

    public void setOldPhone(String oldPhone) {
        this.oldPhone = oldPhone;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    @Override
    public String toString() {
        return "ChangeBindPhoneResult{" +
                "bizUserId='" + bizUserId + '\'' +
                ", oldPhone='" + oldPhone + '\'' +
                ", newPhone='" + newPhone + '\'' +
                '}';
    }
}
