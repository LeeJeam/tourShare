package com.xmyy.manage.vo;

import com.xmyy.manage.model.AdminMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel("菜单节点选中信息")
public class ManageMenuNodeCheckResult extends AdminMenu implements Serializable {

    @ApiModelProperty("前端显示名称")
    private String title;

    @ApiModelProperty("下一级菜单")
    private List<ManageMenuNodeCheckResult> children;

    @ApiModelProperty("是否有权限")
    private Boolean permission;

    @ApiModelProperty("是否勾选(如果勾选，子节点也会全部勾选)")
    private Boolean checked;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ManageMenuNodeCheckResult> getChildren() {
        return children;
    }

    public void setChildren(List<ManageMenuNodeCheckResult> children) {
        this.children = children;
    }

    public Boolean getPermission() {
        return permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
