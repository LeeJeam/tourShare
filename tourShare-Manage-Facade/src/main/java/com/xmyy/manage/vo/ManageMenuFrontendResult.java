package com.xmyy.manage.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel("菜单信息-前端使用")
public class ManageMenuFrontendResult implements Serializable {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("上级ID")
    private Long pId;

    @ApiModelProperty(value = "同级排序")
    private Integer sortNo;

    @ApiModelProperty("前端路由")
    private String path;

    @ApiModelProperty("前端图标样式")
    private String icon;

    @ApiModelProperty("控件编码")
    private String name;

    @ApiModelProperty("资源显示名称")
    private String title;

    @ApiModelProperty("下一级菜单")
    private List<ManageMenuFrontendResult> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ManageMenuFrontendResult> getChildren() {
        return children;
    }

    public void setChildren(List<ManageMenuFrontendResult> children) {
        this.children = children;
    }
}
