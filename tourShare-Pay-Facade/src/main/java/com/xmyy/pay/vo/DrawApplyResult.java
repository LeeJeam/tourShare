package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "提现结果")
public class DrawApplyResult implements Serializable {

    @ApiModelProperty(value = "提现金额", required = true)
    private BigDecimal amount;

    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "银行卡号", required = true)
    private String bankCardNo;

    @ApiModelProperty(value = "提现状态列表")
    private List<DrawApplyStatus> drawApplyStatusList;

    @ApiModel(value = "提现状态")
    public static class DrawApplyStatus implements Serializable {
        @ApiModelProperty(value = "提现动作")
        private String drawOpt;
        @ApiModelProperty(value = "处理时间/预计时间")
        private String drawTime;

        public String getDrawOpt() {
            return drawOpt;
        }

        public void setDrawOpt(String drawOpt) {
            this.drawOpt = drawOpt;
        }

        public String getDrawTime() {
            return drawTime;
        }

        public void setDrawTime(String drawTime) {
            this.drawTime = drawTime;
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public List<DrawApplyStatus> getDrawApplyStatusList() {
        return drawApplyStatusList;
    }

    public void setDrawApplyStatusList(List<DrawApplyStatus> drawApplyStatusList) {
        this.drawApplyStatusList = drawApplyStatusList;
    }
}
