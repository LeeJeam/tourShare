package com.xmyy.member.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 登录日志
 * </p>
 *
 * @author admin
 * @since 2018-05-23
 */
@ApiModel("登录日志")
@TableName("uc_member_login")
@SuppressWarnings("serial")
public class UcMemberLogin extends BaseModel {

    @ApiModelProperty(value = "令牌")
	private String token;
    @ApiModelProperty(value = "登录号账号、手机号、邮箱")
	@TableField("login_code")
	private String loginCode;
    @ApiModelProperty(value = "手机号码")
	private String mobile;
    @ApiModelProperty(value = "登录时间")
	@TableField("login_time")
	private Date loginTime;
    @ApiModelProperty(value = "是否注销0否，1是，-1超时)")
	@TableField("is_logout")
	private Integer isLogout;
    @ApiModelProperty(value = "注销时间")
	@TableField("logout_time")
	private Date logoutTime;
    @ApiModelProperty(value = "最后活跃时间")
	@TableField("last_active_time")
	private Date lastActiveTime;
    @ApiModelProperty(value = "状态（0：临时，50：正常，-1失效）")
	private Integer state;
    @ApiModelProperty(value = "登陆地")
	@TableField("login_country")
	private String loginCountry;
    @ApiModelProperty(value = "登录类型（1，密码登录2，动态码登录,3，第三方）")
	@TableField("login_type")
	private Integer loginType;
    @ApiModelProperty(value = "登记来源端（1，安卓，2苹果，3小程序，4 pc）")
	@TableField("login_source")
	private Integer loginSource;
    @ApiModelProperty(value = "ip")
	private String ip;
    @ApiModelProperty(value = "买家/买手")
	@TableField("member_id")
	private Long memberId;
    @ApiModelProperty(value = "用户类型(1,买手，2背包客)")
	@TableField("member_type")
	private Integer memberType;


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Integer getIsLogout() {
		return isLogout;
	}

	public void setIsLogout(Integer isLogout) {
		this.isLogout = isLogout;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public Date getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(Date lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getLoginCountry() {
		return loginCountry;
	}

	public void setLoginCountry(String loginCountry) {
		this.loginCountry = loginCountry;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public Integer getLoginSource() {
		return loginSource;
	}

	public void setLoginSource(Integer loginSource) {
		this.loginSource = loginSource;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

}