package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@ApiModel(value = "阅读系统消息入参")
public class DgSysMessageReadParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "系统消息_ID", required = true)
    private Long messageId;

    @NotNull
    @ApiModelProperty(value = "用户类型（1买手，2买家，3背包客）", required = true)
    private Integer memberType;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

}
