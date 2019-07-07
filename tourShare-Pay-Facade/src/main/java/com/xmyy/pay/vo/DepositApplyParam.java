package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "充值入参")
public class DepositApplyParam implements Serializable {

    @ApiModelProperty(value = "充值金额", required = true)
    private Long amount;

    @ApiModelProperty(value = "支付方式", required = true)
    private String payMethod;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }
}
