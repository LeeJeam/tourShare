package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "银行卡BIN")
public class BankCardBinResult implements Serializable {

    @ApiModelProperty(value = "卡 bin")
    private String cardBin;

    @ApiModelProperty(value = "卡类型（1借记卡；2信用卡）")
    private Long cardType;

    @ApiModelProperty(value = "发卡行代码")
    private String bankCode;

    @ApiModelProperty(value = "发卡行名称")
    private String bankName;

    @ApiModelProperty(value = "卡名")
    private String cardName;

    @ApiModelProperty(value = "卡片长度")
    private Long cardLenth;

    @ApiModelProperty(value = "状态（1：有效；0：无效")
    private Long cardState;

    @ApiModelProperty(value = "卡种名称")
    private String cardTypeLabel;

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }

    public Long getCardType() {
        return cardType;
    }

    public void setCardType(Long cardType) {
        this.cardType = cardType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Long getCardLenth() {
        return cardLenth;
    }

    public void setCardLenth(Long cardLenth) {
        this.cardLenth = cardLenth;
    }

    public Long getCardState() {
        return cardState;
    }

    public void setCardState(Long cardState) {
        this.cardState = cardState;
    }

    public String getCardTypeLabel() {
        return cardTypeLabel;
    }

    public void setCardTypeLabel(String cardTypeLabel) {
        this.cardTypeLabel = cardTypeLabel;
    }
}
