package com.xmyy.pay.allinpay.request;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class GetOrderDetailParams extends YunParams implements Serializable {

    private String bizUserId;

    private String bizOrderNo;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public void setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
    }

    public GetOrderDetailParams() {
    }

    public GetOrderDetailParams(String bizUserId, String bizOrderNo) {
        this.bizUserId = bizUserId;
        this.bizOrderNo = bizOrderNo;
    }

    @Override
    public String toString() {
        return "GetOrderDetailParams{" +
                "bizUserId='" + bizUserId + '\'' +
                ", bizOrderNo='" + bizOrderNo + '\'' +
                '}';
    }
}
