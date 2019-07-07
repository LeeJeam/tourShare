package com.xmyy.manage.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 角色菜单
 * </p>
 *
 * @author LinBo
 * @since 2018-06-29
 */
@ApiModel("角色菜单关联关系")
@TableName("admin_role_menu")
@SuppressWarnings("serial")
public class AdminRoleMenu extends BaseModel {

    @ApiModelProperty(value = "菜单ID_ID")
	@TableField("menu_id_")
	private Long menuId;
    @ApiModelProperty(value = "角色ID_ID")
	@TableField("role_id_")
	private Long roleId;


	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}