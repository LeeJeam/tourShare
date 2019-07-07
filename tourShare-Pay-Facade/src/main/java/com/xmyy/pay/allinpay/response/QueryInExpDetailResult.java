package com.xmyy.pay.allinpay.response;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class QueryInExpDetailResult implements Serializable {

    private String bizUserId;

    private Long totalNum;

    private String inExpDetail;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public String getInExpDetail() {
        return inExpDetail;
    }

    public void setInExpDetail(String inExpDetail) {
        this.inExpDetail = inExpDetail;
    }

    @Override
    public String toString() {
        return "QueryInExpDetailResult{" +
                "bizUserId='" + bizUserId + '\'' +
                ", totalNum=" + totalNum +
                ", inExpDetail=" + inExpDetail +
                '}';
    }
}
