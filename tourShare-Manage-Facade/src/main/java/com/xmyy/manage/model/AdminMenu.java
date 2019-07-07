package com.xmyy.manage.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author LinBo
 * @since 2018-06-29
 */
@ApiModel("菜单")
@TableName("admin_menu")
@SuppressWarnings("serial")
public class AdminMenu extends BaseModel {

    @ApiModelProperty(value = "菜单名称")
    @TableField("menu_name")
    private String menuName;
    @ApiModelProperty(value = "操作名称（R：查询；C：新增；U：更新；D：删除）")
    private String operation;
    @ApiModelProperty(value = "上级ID")
    @TableField("p_id")
    private Long pId;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "页面地址")
    @TableField("html_url")
    private String htmlUrl;
    @ApiModelProperty("控件编码")
    @TableField("component_code")
    private String componentCode;
    @ApiModelProperty(value = "同级排序")
    @TableField("sort_no")
    private Integer sortNo;

    public String getComponentCode() {
        return componentCode;
    }

    public void setComponentCode(String componentCode) {
        this.componentCode = componentCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

}