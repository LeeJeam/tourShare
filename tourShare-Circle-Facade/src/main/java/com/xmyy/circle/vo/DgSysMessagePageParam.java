package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@ApiModel(value = "查询系统消息入参")
public class DgSysMessagePageParam implements Serializable{

    @NotNull
    @ApiModelProperty(value = "用户类型（1买手，2买家，3背包客）", required = true)
    private Integer memberType;

    @ApiModelProperty(value = "是否已读（-1未读；1已读；0全部）默认0")
    private Integer read = 0;

    @ApiModelProperty(value = "消息类型（1系统消息，2公告消息）默认1")
    private Integer messageType = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码（默认1）")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小（默认10）")
    private Integer size = 10;

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

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }
}
