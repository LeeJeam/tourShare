package com.xmyy.pay.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "绑定的银行卡")
public class BankCardResult implements Serializable {

    @ApiModelProperty(value = "银行卡号")
    private String bankCardNo;

    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "发卡行代码")
    private String bankCode;

    @ApiModelProperty(value = "银行卡LOGO")
    private String logo;

    @ApiModelProperty(value = "银行卡背景图")
    private String bgImage;

    @ApiModelProperty(value = "客服热线")
    private String hotline;

    @ApiModelProperty(value = "银行卡类型（1借记卡；2信用卡）")
    private Long cardType;

    @ApiModelProperty(value = "绑定状态（1已绑定；2已解除）")
    private Long bindState;

    @ApiModelProperty(value = "银行预留手机号码")
    private String phone;

    @JsonIgnore
    @ApiModelProperty(value = "是否完成快捷支付签约（用于接收数据，不返回）")
    private Boolean isQUICKPAYCard;

    @ApiModelProperty(value = "是否完成快捷支付签约（0否；1是）")
    private Integer isQuickPayCard;

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Long getCardType() {
        return cardType;
    }

    public void setCardType(Long cardType) {
        this.cardType = cardType;
    }

    public Long getBindState() {
        return bindState;
    }

    public void setBindState(Long bindState) {
        this.bindState = bindState;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsQUICKPAYCard() {
        return isQUICKPAYCard;
    }

    public void setIsQUICKPAYCard(Boolean isQUICKPAYCard) {
        this.isQUICKPAYCard = isQUICKPAYCard;
    }

    public Integer getIsQuickPayCard() {
        return isQuickPayCard;
    }

    public void setIsQuickPayCard(Integer isQuickPayCard) {
        this.isQuickPayCard = isQuickPayCard;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

}
