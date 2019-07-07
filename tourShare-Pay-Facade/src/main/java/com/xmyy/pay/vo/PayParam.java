package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(value = "后台+短信验证码确认入参")
public class PayParam implements Serializable {

    @NotBlank
    @ApiModelProperty(value = "商户用户UUID", required = true)
    private String bizUserId;

    @NotBlank
    @ApiModelProperty(value = "支付单号", required = true)
    private String bizOrderNo;

    @ApiModelProperty(value = "交易编号（快捷支付必传）")
    private String tradeNo;

    @Pattern(regexp = "^\\d{6}$", message = "验证码格式不正确")
    @ApiModelProperty(value = "短信验证码", required = true)
    private String verificationCode;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public void setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
