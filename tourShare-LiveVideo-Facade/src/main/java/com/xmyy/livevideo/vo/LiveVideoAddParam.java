package com.xmyy.livevideo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "买手端创建房间参数")
@SuppressWarnings("serial")
public class LiveVideoAddParam implements Serializable {

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

    @ApiModelProperty(value = "用户类型(1,买手，2买家)")
	private Integer memberType;

	@ApiModelProperty(value = "行程ID")
	private Long strokeId;

	@ApiModelProperty(value = "国际编码")
	private String countryCode;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Long getStrokeId() {
		return strokeId;
	}

	public void setStrokeId(Long strokeId) {
		this.strokeId = strokeId;
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
	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
}