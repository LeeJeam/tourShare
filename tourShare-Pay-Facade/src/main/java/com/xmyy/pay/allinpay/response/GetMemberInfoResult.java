package com.xmyy.pay.allinpay.response;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class GetMemberInfoResult implements Serializable {

    private String bizUserId;

    private Long memberType;

    private String memberInfo;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public Long getMemberType() {
        return memberType;
    }

    public void setMemberType(Long memberType) {
        this.memberType = memberType;
    }

    public String getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(String memberInfo) {
        this.memberInfo = memberInfo;
    }

    @Override
    public String toString() {
        return "GetMemberInfoResult{" +
                "bizUserId='" + bizUserId + '\'' +
                ", memberType=" + memberType +
                ", memberInfo='" + memberInfo + '\'' +
                '}';
    }
}
