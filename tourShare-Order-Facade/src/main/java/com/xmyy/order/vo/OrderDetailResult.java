package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "订单详情")
public class OrderDetailResult extends OrderResult implements Serializable {

    @ApiModelProperty(value = "收货地址")
    private String consigneeAddress;

    @ApiModelProperty(value = "收货人姓名")
    private String consigneeName;

    @ApiModelProperty(value = "收货人电话")
    private String consigneePhone;

    @ApiModelProperty(value = "订单形成时间")
    private Date orderTime;

    @ApiModelProperty(value = "订单形成时间字符串yyyy-MM-dd HH:mm:ss")
    private String orderTimeString;

    @ApiModelProperty(value = "待支付订单的支付过期时间字符串")
    private String outOfPayTime;

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderTimeString() {
        return orderTimeString;
    }

    public void setOrderTimeString(String orderTimeString) {
        this.orderTimeString = orderTimeString;
    }

    public String getOutOfPayTime() {
        return outOfPayTime;
    }

    public void setOutOfPayTime(String outOfPayTime) {
        this.outOfPayTime = outOfPayTime;
    }
}
