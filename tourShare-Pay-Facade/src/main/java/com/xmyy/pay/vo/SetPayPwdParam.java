package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(value = "设置平台交易密码入参")
public class SetPayPwdParam implements Serializable {

    @ApiModelProperty(value = "手机号码", required = true)
    @Pattern(regexp = "^(1\\d{10})$", message = "无效的手机号码")
    private String  mobile;

    @ApiModelProperty(value = "短信验证码")
    private String smsCode;

    @NotNull(message = "用户类型不能为空")
    @ApiModelProperty(value = "用户类型(1买手，2背包客/买家)", required = true)
    private Integer memberType;

    @ApiModelProperty(value = "小背包交易密码")
    private String PayPwd;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getPayPwd() {
        return PayPwd;
    }

    public void setPayPwd(String payPwd) {
        PayPwd = payPwd;
    }
}
