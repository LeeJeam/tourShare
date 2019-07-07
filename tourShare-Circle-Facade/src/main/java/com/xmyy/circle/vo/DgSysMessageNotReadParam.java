package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@ApiModel(value = "查询未阅读系统消息入参")
public class DgSysMessageNotReadParam implements Serializable{

    @NotNull
    @ApiModelProperty(value = "用户类型（1买手；2买家；3背包客)", required = true)
    private  Integer memberType;

    @ApiModelProperty(value = "消息类型（1系统消息，2公告消息），默认1")
    private  Integer messageType = 1;

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

}
