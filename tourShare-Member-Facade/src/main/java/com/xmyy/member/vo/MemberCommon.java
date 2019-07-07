package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@ApiModel(value = "用户公共字段")
public class MemberCommon implements Serializable {

    @ApiModelProperty(value = "短信验证码")
    private String smsCode;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "密码2")
    private String twoPassword;

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTwoPassword() {
        return twoPassword;
    }

    public void setTwoPassword(String twoPassword) {
        this.twoPassword = twoPassword;
    }
}
