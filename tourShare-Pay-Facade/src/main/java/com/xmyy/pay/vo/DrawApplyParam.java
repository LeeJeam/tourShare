package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "提现申请入参")
public class DrawApplyParam implements Serializable {

    @NotNull(message = "用户类型不能为空")
    @ApiModelProperty(value = "用户角色（1买手，2买家，3背包客）", required = true)
    private Integer memberType;

    @NotNull(message = "提现金额不能为空")
    @ApiModelProperty(value = "提现金额（分）", required = true)
    private Long amount;

    @NotBlank(message = "支付密码不能为空")
    @ApiModelProperty(value = "小背包支付密码", required = true)
    private String payPassword;

    @NotNull(message = "银行卡号不能为空")
    @ApiModelProperty(value = "银行卡号", required = true)
    private String bankCardNo;

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }
}
