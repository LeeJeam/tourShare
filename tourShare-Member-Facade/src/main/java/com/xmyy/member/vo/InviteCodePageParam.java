package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "邀请码查询条件")
public class InviteCodePageParam implements Serializable{

	@ApiModelProperty(value = "邀请码号")
	private String inviteNo;

	@ApiModelProperty(value = "发送开始时间，格式：yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sendTimeBegin;

	@ApiModelProperty(value = "发送结束时间，格式：yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sendTimeEnd;

	@ApiModelProperty(value = "渠道(1运营推广，2线下推广，3用户邀请)")
	private Integer channelType;

	@ApiModelProperty(value = "状态（0待使用，50已使用，-1已失效）")
	private Integer state;

	@ApiModelProperty(value = "手机号码")
	private String mobile;

	@ApiModelProperty(value = "页码，默认为1")
	private Integer current = 1;

	@ApiModelProperty(value = "页大小，默认为10")
	private Integer size = 10;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getInviteNo() {
		return inviteNo;
	}

	public void setInviteNo(String inviteNo) {
		this.inviteNo = inviteNo;
	}

	public Date getSendTimeBegin() {
		return sendTimeBegin;
	}

	public void setSendTimeBegin(Date sendTimeBegin) {
		this.sendTimeBegin = sendTimeBegin;
	}

	public Date getSendTimeEnd() {
		return sendTimeEnd;
	}

	public void setSendTimeEnd(Date sendTimeEnd) {
		this.sendTimeEnd = sendTimeEnd;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
