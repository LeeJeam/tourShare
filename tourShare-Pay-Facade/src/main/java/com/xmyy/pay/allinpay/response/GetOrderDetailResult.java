package com.xmyy.pay.allinpay.response;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class GetOrderDetailResult implements Serializable {

    private String orderNo;

    private String bizOrderNo;

    private Long orderStatus;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public void setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
    }

    public Long getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Long orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "GetOrderDetailResult{" +
                "orderNo='" + orderNo + '\'' +
                ", bizOrderNo='" + bizOrderNo + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
