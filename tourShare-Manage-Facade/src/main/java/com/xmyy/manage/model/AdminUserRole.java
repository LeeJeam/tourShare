package com.xmyy.manage.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 用户角色关联关系
 * </p>
 *
 * @author LinBo
 * @since 2018-06-29
 */
@ApiModel("用户角色关联关系")
@TableName("admin_user_role")
@SuppressWarnings("serial")
public class AdminUserRole extends BaseModel {

    @ApiModelProperty(value = "用户id_ID")
	@TableField("user_id_")
	private Long userId;

    @ApiModelProperty(value = "用户id_ID")
	@TableField("role_id_")
	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}