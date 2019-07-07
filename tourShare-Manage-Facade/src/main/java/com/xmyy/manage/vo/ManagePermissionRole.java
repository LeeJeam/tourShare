package com.xmyy.manage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("角色权限")
public class ManagePermissionRole implements Serializable {

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("是否有权限（0没有；1有）")
    private Boolean isPermission;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Boolean getPermission() {
        return isPermission;
    }

    public void setPermission(Boolean permission) {
        isPermission = permission;
    }
}
