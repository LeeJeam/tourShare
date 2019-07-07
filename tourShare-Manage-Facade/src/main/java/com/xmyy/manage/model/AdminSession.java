package com.xmyy.manage.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

import java.util.Date;

/**
 * <p>
 * 10后台登录日志
 * </p>
 *
 * @author zlp
 * @since 2018-05-30
 */
@ApiModel("10后台登录日志")
@TableName("admin_session")
@SuppressWarnings("serial")
public class AdminSession extends BaseModel {

    @ApiModelProperty(value = "后台用户id_ID")
	@TableField("user_id_")
	private Long userId;
    @ApiModelProperty(value = "会员id")
	@TableField("session_id")
	private String sessionId;
    @ApiModelProperty(value = "帐号")
	private String account;
    @ApiModelProperty(value = "ip")
	private String ip;
    @ApiModelProperty(value = "登录时间")
	@TableField("start_time")
	private Date startTime;


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}