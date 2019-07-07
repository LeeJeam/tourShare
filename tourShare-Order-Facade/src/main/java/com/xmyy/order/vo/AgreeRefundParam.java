package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "买手确认退款入参")
public class AgreeRefundParam implements Serializable {

    @NotNull(message = "售后单ID不能为空")
    @ApiModelProperty(value = "售后单ID", required = true)
    private Long orderReturnId;

    @NotBlank(message = "平台交易密码不能为空")
    @ApiModelProperty(value = "小背包交易密码", required = true)
    private String payPassword;

    public Long getOrderReturnId() {
        return orderReturnId;
    }

    public void setOrderReturnId(Long orderReturnId) {
        this.orderReturnId = orderReturnId;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }
}
