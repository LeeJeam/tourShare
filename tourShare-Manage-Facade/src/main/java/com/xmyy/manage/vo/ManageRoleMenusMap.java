package com.xmyy.manage.vo;

import java.io.Serializable;

public class ManageRoleMenusMap implements Serializable {

    private Long roleId;

    private String menuIds; //多个逗号分割

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
}
