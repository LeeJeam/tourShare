package com.xmyy.member.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 邀请码
 * </p>
 *
 * @author admin
 * @since 2018-05-23
 */
@ApiModel("邀请码")
@TableName("uc_invite_code")
@SuppressWarnings("serial")
public class UcInviteCode extends BaseModel {

    @ApiModelProperty(value = "邀请码号")
	@TableField("invite_no")
	private String inviteNo;
    @ApiModelProperty(value = "手机号码")
	private String mobile;
    @ApiModelProperty(value = "接受人email")
	private String email;
    @ApiModelProperty(value = "责任人")
	@TableField("occupy_man")
	private String occupyMan;
    @ApiModelProperty(value = "补充说明")
	@TableField("explain_info")
	private String explainInfo;
    @ApiModelProperty(value = "渠道(1运营推广，2线下推广，3用户邀请)")
	@TableField("channel_type")
	private Integer channelType;
    @ApiModelProperty(value = "时效")
	private Integer days;
    @ApiModelProperty(value = "发送时间")
	@TableField("send_time")
	private Date sendTime;
    @ApiModelProperty(value = "状态（0待使用，50已使用，-1已失效）")
	private Integer state;


	public String getInviteNo() {
		return inviteNo;
	}

	public void setInviteNo(String inviteNo) {
		this.inviteNo = inviteNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOccupyMan() {
		return occupyMan;
	}

	public void setOccupyMan(String occupyMan) {
		this.occupyMan = occupyMan;
	}

	public String getExplainInfo() {
		return explainInfo;
	}

	public void setExplainInfo(String explainInfo) {
		this.explainInfo = explainInfo;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}