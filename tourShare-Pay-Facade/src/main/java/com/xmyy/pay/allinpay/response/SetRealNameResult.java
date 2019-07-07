package com.xmyy.pay.allinpay.response;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class SetRealNameResult implements Serializable {

    private String bizUserId;

    private String name;

    private Long identityType;

    private String identityNo;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Long identityType) {
        this.identityType = identityType;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    @Override
    public String toString() {
        return "SetRealNameResult{" +
                "bizUserId='" + bizUserId + '\'' +
                ", name='" + name + '\'' +
                ", identityType=" + identityType +
                ", identityNo='" + identityNo + '\'' +
                '}';
    }
}
