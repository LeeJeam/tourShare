package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "后台查询买手列表入参")
public class SellerManagePageParam implements Serializable {

    @ApiModelProperty(value = "页码（默认1）")
    private Integer current = 1;

    @ApiModelProperty(value = "页大小（默认10）")
    private Integer size = 10;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "性别(1男，2女)")
    private Integer gender;

    @ApiModelProperty(value = "用户名")
    private String realName;

    @ApiModelProperty(value = "是否自营（1是，0否）")
    private Integer isSelf;

    @ApiModelProperty(value = "是否置顶（0否，1是）")
    private Integer isTop;

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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
