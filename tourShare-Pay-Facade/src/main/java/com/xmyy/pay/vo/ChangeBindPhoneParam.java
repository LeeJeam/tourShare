package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(value = "确认绑定手机入参")
public class ChangeBindPhoneParam implements Serializable {

    @NotNull(message = "用户类型不能为空")
    @ApiModelProperty(value = "用户类型(1买手，2背包客/买家)", required = true)
    private Integer memberType;

    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式不正确")
    @ApiModelProperty(value = "原手机号", required = true)
    private String oldPhone;

    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式不正确")
    @ApiModelProperty(value = "新手机号", required = true)
    private String newPhone;

    @Pattern(regexp = "^\\d{6}$", message = "验证码格式不正确")
    @ApiModelProperty(value = "新手机验证码", required = true)
    private String newVerificationCode;

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getOldPhone() {
        return oldPhone;
    }

    public void setOldPhone(String oldPhone) {
        this.oldPhone = oldPhone;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    public String getNewVerificationCode() {
        return newVerificationCode;
    }

    public void setNewVerificationCode(String newVerificationCode) {
        this.newVerificationCode = newVerificationCode;
    }
}
