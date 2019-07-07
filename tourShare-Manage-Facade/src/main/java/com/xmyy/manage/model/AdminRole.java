package com.xmyy.manage.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author LinBo
 * @since 2018-06-29
 */
@ApiModel("角色")
@TableName("admin_role")
@SuppressWarnings("serial")
public class AdminRole extends BaseModel {

    @ApiModelProperty(value = "角色名称")
	@TableField("role_name")
	private String roleName;

    @ApiModelProperty(value = "角色代码")
	@TableField("role_code")
	private String roleCode;

    @ApiModelProperty(value = "角色状态（0：禁用；1：启用）")
    @TableField("role_status")
    private Integer roleStatus;

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

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }
}