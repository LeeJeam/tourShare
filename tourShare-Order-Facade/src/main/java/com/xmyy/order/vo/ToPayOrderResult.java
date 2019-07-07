package com.xmyy.order.vo;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "返回支付所需的数据")
public class ToPayOrderResult implements Serializable {

    @ApiModelProperty(value = "订单ID列表")
    private List<Long> orderIdList;

    @ApiModelProperty(value = "付款用户ID")
    private Long payerId;

    @ApiModelProperty(value = "收款列表（收款人UUID+收款金额）")
    private JSONArray recieverList;

    @ApiModelProperty(value = "总金额：分")
    private Long amount;

    @ApiModelProperty(value = "手续费：分")
    private Long fee;

    public List<Long> getOrderIdList() {
        return orderIdList;
    }

    public void setOrderIdList(List<Long> orderIdList) {
        this.orderIdList = orderIdList;
    }

    public Long getPayerId() {
        return payerId;
    }

    public void setPayerId(Long payerId) {
        this.payerId = payerId;
    }

    public JSONArray getRecieverList() {
        return recieverList;
    }

    public void setRecieverList(JSONArray recieverList) {
        this.recieverList = recieverList;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

}
