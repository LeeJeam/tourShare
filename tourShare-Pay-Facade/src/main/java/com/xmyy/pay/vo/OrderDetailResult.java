package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "支付状态")
public class OrderDetailResult implements Serializable {

    @ApiModelProperty(value = "支付单号")
    private String bizOrderNo;

    @ApiModelProperty(value = "订单支付状态（1未支付；3交易失败；4交易成功；5退款成功；6关闭；7退款受理；99进行中）")
    private Integer orderStatus;

    @ApiModelProperty(value = "支付业务类型，用于APP跳转页面（1预售订单；2需求；3提现）")
    private Integer type;

    @ApiModelProperty(value = "订单ID/需求ID/提现交易记录ID")
    private Long id;

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public void setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
