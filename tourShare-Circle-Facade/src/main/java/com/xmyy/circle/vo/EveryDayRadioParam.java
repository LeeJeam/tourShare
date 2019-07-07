package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


@ApiModel(value ="今日提醒入参")
public class EveryDayRadioParam implements Serializable {

    @ApiModelProperty(value = "类型（0其他，1买手接收需求，2买家付款成功，3买家提醒发货，4买家申请售后）")
    private Integer type;

    @ApiModelProperty(value = "推送给买手或背包客")
    private Long toMemberId;

    @ApiModelProperty(value = "用户类型（1买手，3背包客）")
    private Integer toMemberType;

    @ApiModelProperty(value = "买家用户ID")
    private Long memberId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getToMemberId() {
        return toMemberId;
    }

    public void setToMemberId(Long toMemberId) {
        this.toMemberId = toMemberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getToMemberType() {
        return toMemberType;
    }

    public void setToMemberType(Integer toMemberType) {
        this.toMemberType = toMemberType;
    }

}
