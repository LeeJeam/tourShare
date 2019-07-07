package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "后台获取买家/背包客列表入参")
public class BuyerManagePageParam implements Serializable {

    @ApiModelProperty(value = "页码，默认1")
    private Integer current = 1;

    @ApiModelProperty(value = "页大小，默认10")
    private Integer size = 10;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "性别(1男，2女)")
    private Integer gender;

    @ApiModelProperty(value = "用户类型（1买家；2背包客）")
    private Integer memberType;

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
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
}
