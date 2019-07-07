package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "请求绑定银行卡返回")
public class ApplyBindBankCardMyResult implements Serializable {

    @ApiModelProperty(value = "商户系统唯一ID")
    private String bizUserId;

    @ApiModelProperty(value = "流水号")
    private String tranceNum;

    @ApiModelProperty(value = "申请时间（YYYYMMDD）,提现绑卡时返回")
    private String transDate;

    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "银行卡类型（1储蓄卡；2信用卡）")
    private Long cardType;

    @ApiModelProperty(value = "银行预留手机")
    private String phone;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public String getTranceNum() {
        return tranceNum;
    }

    public void setTranceNum(String tranceNum) {
        this.tranceNum = tranceNum;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Long getCardType() {
        return cardType;
    }

    public void setCardType(Long cardType) {
        this.cardType = cardType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
