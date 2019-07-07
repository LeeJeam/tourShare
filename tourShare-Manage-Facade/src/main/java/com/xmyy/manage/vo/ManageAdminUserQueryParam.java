package com.xmyy.manage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@ApiModel("用户查询条件")
public class ManageAdminUserQueryParam implements Serializable {

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty("页码")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty("页大小")
    private Integer size = 10;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("性别代码")
    private Integer gender;

    @ApiModelProperty("手机")
    private String mobile;

    @ApiModelProperty("工号")
    private String staffNo;

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

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
