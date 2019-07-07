package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(value = "确认绑定手机入参")
public class BindPhoneParam implements Serializable {

    @NotNull(message = "用户类型不能为空")
    @ApiModelProperty(value = "用户类型(1买手，2背包客/买家)", required = true)
    private Integer memberType;

    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式不正确")
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    @Pattern(regexp = "^\\d{6}$", message = "验证码格式不正确")
    @ApiModelProperty(value = "短信验证码", required = true)
    private String verificationCode;

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
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
