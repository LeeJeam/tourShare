package com.xmyy.common.vo;

import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 后台用户
 * </p>
 *
 * @author zlp
 * @since 2018-05-30
 */

public class AdminUserInfo extends BaseModel {

	private String account;
	private String password;
	private String userName;
	private Integer gender;
	private String staffNo;
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