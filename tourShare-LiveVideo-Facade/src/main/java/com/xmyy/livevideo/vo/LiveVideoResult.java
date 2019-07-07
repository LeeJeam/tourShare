package com.xmyy.livevideo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "创建房间返回")
public class LiveVideoResult implements Serializable {

	@ApiModelProperty(value = "买手")
	private Long memberId;

    @ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "头像")
	private String imagUrl;

    @ApiModelProperty(value = "聊天室")
	private String liveRoomId;

    @ApiModelProperty(value = "行程ID")
	private Long strokeId;

    @ApiModelProperty(value = "点赞数")
	private Integer pointNumber;

    @ApiModelProperty(value = "观看数")
	private Integer lookNumber;

    @ApiModelProperty(value = "系统观看数")
	private Integer sysLookNumber;

    @ApiModelProperty(value = "推流地址")
	private String pushUrl;

    @ApiModelProperty(value = "录播URL地址")
	private String videoUrl;

    @ApiModelProperty(value = "状态（0初始化，1直播中，2已结束）")
	private Integer status;

	@ApiModelProperty(value = "群组ID")
	private String groupId;

	@ApiModelProperty(value = "状态名称（0初始化，1直播中，2已结束）")
	private String stateLabel;

	@ApiModelProperty(value = "直播创建时间")
	private Date beginTime;

	@ApiModelProperty(value = "标题")
	private String roomTitle;

	@ApiModelProperty(value = "城市地址")
	private String cityAddr;

	@ApiModelProperty(value = "RTMP拉流地址")
	private String pullRtmpUrl;

	@ApiModelProperty(value = "封面URL")
	private String pageUrl;

	public String getImagUrl() {
		return imagUrl;
	}

	public void setImagUrl(String imagUrl) {
		this.imagUrl = imagUrl;
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

	public String getPullRtmpUrl() {
		return pullRtmpUrl;
	}

	public void setPullRtmpUrl(String pullRtmpUrl) {
		this.pullRtmpUrl = pullRtmpUrl;
	}

	public String getRoomTitle() {
		return roomTitle;
	}

	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}

	public String getCityAddr() {
		return cityAddr;
	}

	public void setCityAddr(String cityAddr) {
		this.cityAddr = cityAddr;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public String getStateLabel() {
		return stateLabel;
	}

	public void setStateLabel(String stateLabel) {
		this.stateLabel = stateLabel;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLiveRoomId() {
		return liveRoomId;
	}

	public void setLiveRoomId(String liveRoomId) {
		this.liveRoomId = liveRoomId;
	}

	public Long getStrokeId() {
		return strokeId;
	}

	public void setStrokeId(Long strokeId) {
		this.strokeId = strokeId;
	}

	public Integer getPointNumber() {
		return pointNumber;
	}

	public void setPointNumber(Integer pointNumber) {
		this.pointNumber = pointNumber;
	}

	public Integer getLookNumber() {
		return lookNumber;
	}

	public void setLookNumber(Integer lookNumber) {
		this.lookNumber = lookNumber;
	}

	public Integer getSysLookNumber() {
		return sysLookNumber;
	}

	public void setSysLookNumber(Integer sysLookNumber) {
		this.sysLookNumber = sysLookNumber;
	}

	public String getPushUrl() {
		return pushUrl;
	}

	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}