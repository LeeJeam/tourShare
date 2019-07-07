package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "申请退款入参")
public class RefundParam  implements Serializable {

    @NotNull
    @ApiModelProperty(value = "退款订单ID", required = true)
    private Long orderId;

    @NotBlank
    @ApiModelProperty(value = "退款收款人UUID", required = true)
    private String bizUserId;

    @NotNull
    @ApiModelProperty(value = "金额：分", required = true)
    private Long amount;

    @NotNull
    @ApiModelProperty(value = "手续费：分", required = true)
    private Long fee;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
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
