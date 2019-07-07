package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@ApiModel(value = "买手注册入参")
public class SellerAddParam extends BuyerAddParam implements Serializable {

    @NotBlank(message = "邀请码不能为空")
    @ApiModelProperty(value = "邀请码", required = true)
    private String invitationCode;

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
