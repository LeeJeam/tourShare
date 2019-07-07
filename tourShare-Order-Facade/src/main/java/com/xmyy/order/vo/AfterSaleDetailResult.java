package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "售后详情")
public class AfterSaleDetailResult extends AfterSaleResult implements Serializable {

    @ApiModelProperty(value = "退货单编号")
    private String returnNo;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal returnMoney;

    @ApiModelProperty(value = "退货原因文字")
    private String reasonText;

    @ApiModelProperty(value = "退货申请时间")
    private Date returnSubmitTime;

    @ApiModelProperty(value = "退货申请时间字符串")
    private String returnSubmitTimeInfo;

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    public BigDecimal getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    public Date getReturnSubmitTime() {
        return returnSubmitTime;
    }

    public void setReturnSubmitTime(Date returnSubmitTime) {
        this.returnSubmitTime = returnSubmitTime;
    }

    public String getReturnSubmitTimeInfo() {
        return returnSubmitTimeInfo;
    }

    public void setReturnSubmitTimeInfo(String returnSubmitTimeInfo) {
        this.returnSubmitTimeInfo = returnSubmitTimeInfo;
    }
}
