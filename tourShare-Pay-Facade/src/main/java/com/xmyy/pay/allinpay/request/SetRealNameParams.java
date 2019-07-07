package com.xmyy.pay.allinpay.request;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class SetRealNameParams extends YunParams implements Serializable {

    private String bizUserId;

    private Boolean isAuth;

    private String name;

    private Long identityType;

    private String identityNo;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public Boolean getAuth() {
        return isAuth;
    }

    public void setAuth(Boolean auth) {
        isAuth = auth;
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

    public SetRealNameParams() {
    }

    public SetRealNameParams(String bizUserId, Boolean isAuth, String name, Long identityType, String identityNo) {
        this.bizUserId = bizUserId;
        this.isAuth = isAuth;
        this.name = name;
        this.identityType = identityType;
        this.identityNo = identityNo;
    }

    @Override
    public String toString() {
        return "SetRealNameParams{" +
                "bizUserId='" + bizUserId + '\'' +
                ", isAuth=" + isAuth +
                ", name='" + name + '\'' +
                ", identityType=" + identityType +
                ", identityNo='" + identityNo + '\'' +
                '}';
    }
}
