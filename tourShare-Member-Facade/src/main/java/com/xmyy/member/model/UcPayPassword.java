package com.xmyy.member.model;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 用户支付密码
 * </p>
 *
 * @author simon
 * @since 2018-09-12
 */
@ApiModel("用户支付密码")
@TableName("uc_pay_password")
@SuppressWarnings("serial")
public class UcPayPassword extends BaseModel {

    @ApiModelProperty(value = "支付密码")
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}