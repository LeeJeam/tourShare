package com.xmyy.livevideo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "买手端关闭房间参数")
@SuppressWarnings("serial")
public class LiveVideoParam implements Serializable {

	@ApiModelProperty(value = "聊天室")
	private String liveRoomId;

    @ApiModelProperty(value = "买家/买手")
	private Long memberId;

    @ApiModelProperty(value = "封面URL")
	private String pageUrl;

    @ApiModelProperty(value = "标题")
	private String roomTitle;

    @ApiModelProperty(value = "描述")
	private String roomDesc;

    @ApiModelProperty(value = "城市地址")
	private String cityAddr;

    @ApiModelProperty(value = "状态（0初始化，1直播中，2已结束）")
	private Integer status;

    @ApiModelProperty(value = "用户类型(1买手，2买家)")
	private Integer memberType;

	public String getLiveRoomId() {
		return liveRoomId;
	}

	public void setLiveRoomId(String liveRoomId) {
		this.liveRoomId = liveRoomId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getRoomTitle() {
		return roomTitle;
	}

	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}

	public String getRoomDesc() {
		return roomDesc;
	}

	public void setRoomDesc(String roomDesc) {
		this.roomDesc = roomDesc;
	}

	public String getCityAddr() {
		return cityAddr;
	}

	public void setCityAddr(String cityAddr) {
		this.cityAddr = cityAddr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
}