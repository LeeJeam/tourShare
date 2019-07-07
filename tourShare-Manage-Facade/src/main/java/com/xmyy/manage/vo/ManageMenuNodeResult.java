package com.xmyy.manage.vo;

import com.xmyy.manage.model.AdminMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel("菜单节点信息")
public class ManageMenuNodeResult extends AdminMenu implements Serializable {

    @ApiModelProperty("下一级菜单")
    private List<ManageMenuNodeResult> children;

    public List<ManageMenuNodeResult> getChildren() {
        return children;
    }

    public void setChildren(List<ManageMenuNodeResult> children) {
        this.children = children;
    }

}
