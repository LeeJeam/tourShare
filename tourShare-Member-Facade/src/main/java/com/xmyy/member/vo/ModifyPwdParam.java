package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(value = "找回/重置密码入参")
public class ModifyPwdParam extends MemberCommon implements Serializable {

    @ApiModelProperty(value = "手机号码", required = true)
    @NotBlank
    @Pattern(regexp = "^(1\\d{10})$", message = "无效的手机号码")
    private String  mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
