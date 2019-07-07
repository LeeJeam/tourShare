package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "我的钱包")
public class MyWalletResult implements Serializable {

    @ApiModelProperty(value = "钱包余额")
    private BigDecimal amount;

    @ApiModelProperty(value = "是否设置支付密码（0否；1是）")
    private Integer isSetPayPwd;

    @ApiModelProperty(value = "交易明细列表")
    private List<PayDetail> payDetailList;

    @ApiModel(value = "交易明细")
    public static class PayDetail implements Serializable {
        @ApiModelProperty(value = "交易明细描述")
        private String remark;
        @ApiModelProperty(value = "交易时间(yyyy-MM-dd HH:mm:ss)")
        private String payTimeStr;
        @ApiModelProperty(value = "交易金额字符串")
        private String amountStr;
        @ApiModelProperty(value = "出入账类型（-1出账；1入账）")
        private Integer inOut;
        @ApiModelProperty(value = "订单ID（订单操作的交易明细有此值）")
        private Long orderId;
        @ApiModelProperty(value = "提现交易记录ID（提现操作的交易明细有此值）")
        private Long drawId;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPayTimeStr() {
            return payTimeStr;
        }

        public void setPayTimeStr(String payTimeStr) {
            this.payTimeStr = payTimeStr;
        }

        public String getAmountStr() {
            return amountStr;
        }

        public void setAmountStr(String amountStr) {
            this.amountStr = amountStr;
        }

        public Integer getInOut() {
            return inOut;
        }

        public void setInOut(Integer inOut) {
            this.inOut = inOut;
        }

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public Long getDrawId() {
            return drawId;
        }

        public void setDrawId(Long drawId) {
            this.drawId = drawId;
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getIsSetPayPwd() {
        return isSetPayPwd;
    }

    public void setIsSetPayPwd(Integer isSetPayPwd) {
        this.isSetPayPwd = isSetPayPwd;
    }

    public List<PayDetail> getPayDetailList() {
        return payDetailList;
    }

    public void setPayDetailList(List<PayDetail> payDetailList) {
        this.payDetailList = payDetailList;
    }
}
