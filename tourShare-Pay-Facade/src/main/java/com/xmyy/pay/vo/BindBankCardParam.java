package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(value = "确认绑定银行卡入参")
public class BindBankCardParam implements Serializable {

    @NotNull(message = "bizUserId不能为空")
    @ApiModelProperty(value = "商户系统唯一ID", required = true)
    private String bizUserId;

    @NotNull(message = "流水号不能为空")
    @ApiModelProperty(value = "流水号", required = true)
    private String tranceNum;

    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式不正确")
    @ApiModelProperty(value = "银行预留手机", required = true)
    private String phone;

    @Pattern(regexp = "^\\d{6}$", message = "验证码格式不正确")
    @ApiModelProperty(value = "短信验证码", required = true)
    private String verificationCode;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
