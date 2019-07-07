package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(value = "买家快速登陆入参")
public class BuyerQuickLoginParam extends MemberCommon implements Serializable {

    @ApiModelProperty(value = "手机号码", required = true)
    @Pattern(regexp = "^(1\\d{10})$", message = "无效的手机号码")
    private String mobile;

    @ApiModelProperty(value = "登陆地", required = true)
    @NotBlank(message = "登陆地不能为空")
    private String loginCountry;

    @ApiModelProperty(value = "登陆设备（1安卓，2苹果，3小程序，4PC）", required = true)
    @NotNull(message = "登陆设备不能为空")
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
}
