package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "提现界面数据")
public class PrepareToDrawResult implements Serializable {

    @ApiModelProperty(value = "账户余额")
    private BigDecimal amount;

    @ApiModelProperty(value = "绑定的银行卡列表")
    private List<BankCardResult> bankCardList;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<BankCardResult> getBankCardList() {
        return bankCardList;
    }

    public void setBankCardList(List<BankCardResult> bankCardList) {
        this.bankCardList = bankCardList;
    }
}
