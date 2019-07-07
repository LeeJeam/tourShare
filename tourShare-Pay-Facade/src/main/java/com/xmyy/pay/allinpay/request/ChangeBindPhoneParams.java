package com.xmyy.pay.allinpay.request;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class ChangeBindPhoneParams extends YunParams implements Serializable {

    private String bizUserId;

    private String oldPhone;

    private String newPhone;

    private String newVerificationCode;

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

    public String getNewVerificationCode() {
        return newVerificationCode;
    }

    public void setNewVerificationCode(String newVerificationCode) {
        this.newVerificationCode = newVerificationCode;
    }

    public ChangeBindPhoneParams() {
    }

    public ChangeBindPhoneParams(String bizUserId, String oldPhone, String newPhone, String newVerificationCode) {
        this.bizUserId = bizUserId;
        this.oldPhone = oldPhone;
        this.newPhone = newPhone;
        this.newVerificationCode = newVerificationCode;
    }

    @Override
    public String toString() {
        return "ChangeBindPhoneParams{" +
                "bizUserId='" + bizUserId + '\'' +
                ", oldPhone='" + oldPhone + '\'' +
                ", newPhone='" + newPhone + '\'' +
                ", newVerificationCode='" + newVerificationCode + '\'' +
                '}';
    }
}
