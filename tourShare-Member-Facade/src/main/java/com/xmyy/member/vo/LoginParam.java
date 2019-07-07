package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(value = "登陆入参")
public class LoginParam implements Serializable {

    @ApiModelProperty(value = "手机号码", required = true)
    @Pattern(regexp = "^(1\\d{10})$", message = "无效的手机号码")
    private String mobile;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "登陆地", required = true)
    private String loginCountry;

    @ApiModelProperty(value = "登陆设备（1安卓，2苹果，3小程序，4PC）", required = true)
    @NotNull(message = "登陆设备")
    private Integer loginSource;

    public Integer getLoginSource() {
        return loginSource;
    }

    public void setLoginSource(Integer loginSource) {
        this.loginSource = loginSource;
    }

    public String getLoginCountry() {
        return loginCountry;
    }

    public void setLoginCountry(String loginCountry) {
        this.loginCountry = loginCountry;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
