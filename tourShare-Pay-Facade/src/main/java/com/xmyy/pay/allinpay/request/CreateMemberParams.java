package com.xmyy.pay.allinpay.request;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Simon on 2018/9/4.
 */
public class CreateMemberParams extends YunParams implements Serializable{

    private String bizUserId;

    private Long memberType;

    private Long source;

    private HashMap extendParam;

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

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public HashMap getExtendParam() {
        return extendParam;
    }

    public void setExtendParam(HashMap extendParam) {
        this.extendParam = extendParam;
    }

    public CreateMemberParams() {
    }

    public CreateMemberParams(String bizUserId, Long memberType, Long source, HashMap extendParam) {
        this.bizUserId = bizUserId;
        this.memberType = memberType;
        this.source = source;
        this.extendParam = extendParam;
    }

    @Override
    public String toString() {
        return "CreateMemberParams{" +
                "bizUserId='" + bizUserId + '\'' +
                ", memberType=" + memberType +
                ", source=" + source +
                ", extendParam=" + extendParam +
                '}';
    }
}
