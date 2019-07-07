package com.xmyy.manage.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ManageAdminRoleQueryResult implements Serializable {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色代码")
    private String roleCode;

    @ApiModelProperty(value = "角色状态（0禁用；1启用）")
    private Integer role_status;

    @ApiModelProperty(value = "授权用户统计数")
    private Integer countUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Integer getRole_status() {
        return role_status;
    }

    public void setRole_status(Integer role_status) {
        this.role_status = role_status;
    }

    public Integer getCountUser() {
        return countUser;
    }

    public void setCountUser(Integer countUser) {
        this.countUser = countUser;
    }
}
