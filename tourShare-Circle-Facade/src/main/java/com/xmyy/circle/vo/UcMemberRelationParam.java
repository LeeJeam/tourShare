package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "关注买手参数")
public class UcMemberRelationParam implements Serializable {

    @ApiModelProperty(value = "用户类型(1买手，2买家/背包客)")
    private Integer toMemberType;

    @NotNull
    @ApiModelProperty(value = "被关注用户ID")
    private Long toMemberId;

    public Long getToMemberId() {
        return toMemberId;
    }

    public void setToMemberId(Long toMemberId) {
        this.toMemberId = toMemberId;
    }

    public Integer getToMemberType() {
        return toMemberType;
    }

    public void setToMemberType(Integer toMemberType) {
        this.toMemberType = toMemberType;
    }

}
