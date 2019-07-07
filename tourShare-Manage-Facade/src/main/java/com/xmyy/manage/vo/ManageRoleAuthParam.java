package com.xmyy.manage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel("角色对应授权菜单信息")
public class ManageRoleAuthParam implements Serializable {

    @ApiModelProperty("角色ID")
    @NotNull(message = "角色ID为空")
    private Long roleId;

    @ApiModelProperty("有权限菜单ID数组")
    private List<Long> menuIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }
}
