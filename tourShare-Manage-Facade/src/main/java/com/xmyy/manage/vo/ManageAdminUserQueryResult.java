package com.xmyy.manage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("用户信息")
public class ManageAdminUserQueryResult implements Serializable {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty(value = "登陆帐户")
    private String account;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "性别(1:男;2:女)")
    private Integer gender;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty("角色名称，多个用逗号隔开")
    private String roleNames;

    @ApiModelProperty("工号")
    private String staffNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }
}
