package com.xmyy.manage.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 后台用户
 * </p>
 *
 * @author zlp
 * @since 2018-05-30
 */
@ApiModel("后台用户")
@TableName("admin_user")
@SuppressWarnings("serial")
public class AdminUser extends BaseModel {

    @ApiModelProperty(value = "登陆帐户")
	private String account;
    @ApiModelProperty(value = "密码")
	private String password;
    @ApiModelProperty(value = "姓名")
	@TableField("user_name")
	private String userName;
    @ApiModelProperty(value = "性别(1:男;2:女)")
	private Integer gender;
    @ApiModelProperty(value = "工号")
	@TableField("staff_no")
	private String staffNo;
    @ApiModelProperty(value = "手机")
	private String mobile;


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}