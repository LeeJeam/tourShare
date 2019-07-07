package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(value = "请求绑定银行卡入参")
public class ApplyBindBankCardParam implements Serializable {

    @NotNull(message = "用户类型不能为空")
    @ApiModelProperty(value = "用户类型(1买手，2背包客/买家)", required = true)
    private Integer memberType;

    @NotBlank(message = "银行卡号不能为空")
    @ApiModelProperty(value = "银行卡号", required = true)
    private String cardNo;

    @ApiModelProperty(value = "绑卡方式（7收银宝快捷，默认）", hidden = true)
    private Long cardCheck = 7L;

    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式不正确")
    @ApiModelProperty(value = "银行预留手机", required = true)
    private String phone;

    @NotBlank(message = "身份证号不能为空")
    @ApiModelProperty(value = "身份证号", required = true)
    private String identityNo;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Long getCardCheck() {
        return cardCheck;
    }

    public void setCardCheck(Long cardCheck) {
        this.cardCheck = cardCheck;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
