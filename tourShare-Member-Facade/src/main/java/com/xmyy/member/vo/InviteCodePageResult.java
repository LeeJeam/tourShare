package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "邀请码查询条件对象")
public class InviteCodePageResult implements Serializable{

	private Long id;

	@ApiModelProperty(value = "邀请码号")
	private String inviteNo;

	@ApiModelProperty(value = "手机号码")
	private String mobile;


	@ApiModelProperty(value = "发送时间")
	private Date sendTime;

	@ApiModelProperty(value = "渠道(1运营推广，2线下推广，3用户邀请)")
	private Integer channelType;

	@ApiModelProperty(value = "渠道(1运营推广，2线下推广，3用户邀请)")
	private Integer channelTypeLabel;

	@ApiModelProperty(value = "状态（0待使用，50已使用，-1已失效）")
	private Integer state;

	@ApiModelProperty(value = "状态")
	private String stateLabel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public Integer getChannelTypeLabel() {
		return channelTypeLabel;
	}

	public void setChannelTypeLabel(Integer channelTypeLabel) {
		this.channelTypeLabel = channelTypeLabel;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStateLabel() {
		return stateLabel;
	}

	public void setStateLabel(String stateLabel) {
		this.stateLabel = stateLabel;
	}
}
