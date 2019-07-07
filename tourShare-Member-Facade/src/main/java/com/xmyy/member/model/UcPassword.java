package com.xmyy.member.model;

import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户登录密码
 * </p>
 *
 * @author admin
 * @since 2018-05-23
 */
@ApiModel("用户登录密码")
@TableName("uc_password")
@SuppressWarnings("serial")
public class UcPassword extends BaseModel {

    @ApiModelProperty(value = "登录密码")
	private String password;


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}