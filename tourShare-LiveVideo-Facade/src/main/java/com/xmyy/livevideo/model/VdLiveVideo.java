package com.xmyy.livevideo.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 直播
 * </p>
 *
 * @author wangmd
 * @since 2018-06-04
 */
@ApiModel("直播")
@TableName("vd_live_video")
@SuppressWarnings("serial")
public class VdLiveVideo extends BaseModel {

    @ApiModelProperty(value = "买家/买手")
	@TableField("member_id")
	private Long memberId;
    @ApiModelProperty(value = "聊天室")
	@TableField("live_room_id")
	private String liveRoomId;
    @ApiModelProperty(value = "行程ID")
	@TableField("stroke_id")
	private Long strokeId;
    @ApiModelProperty(value = "封面URL")
	@TableField("page_url")
	private String pageUrl;
    @ApiModelProperty(value = "标题")
	@TableField("room_title")
	private String roomTitle;
    @ApiModelProperty(value = "描述")
	@TableField("room_desc")
	private String roomDesc;
    @ApiModelProperty(value = "城市地址")
	@TableField("city_addr")
	private String cityAddr;
    @ApiModelProperty(value = "点赞数")
	@TableField("point_number")
	private Integer pointNumber;
    @ApiModelProperty(value = "观看数")
	@TableField("look_number")
	private Integer lookNumber;
    @ApiModelProperty(value = "系统观看数")
	@TableField("sys_look_number")
	private Integer sysLookNumber;
    @ApiModelProperty(value = "推流地址")
	@TableField("push_url")
	private String pushUrl;
    @ApiModelProperty(value = "拉流地址")
	@TableField("pull_url")
	private String pullUrl;
    @ApiModelProperty(value = "RTMP拉流地址")
	@TableField("pull_rtmp_url")
	private String pullRtmpUrl;
    @ApiModelProperty(value = "录播URL地址")
	@TableField("video_url")
	private String videoUrl;
    @ApiModelProperty(value = "状态（0-初始化、1-直播中、2-已结束）")
	private Integer status;
    @ApiModelProperty(value = "用户类型(1,买手，2背包客)")
	@TableField("member_type")
	private Integer memberType;
	@ApiModelProperty(value = "groupId")
	@TableField("group_id")
	private String groupId;
	@ApiModelProperty(value = "直播创建时间")
	@TableField("begin_time")
	private Date beginTime;
	@ApiModelProperty(value = "直播下线时间")
	@TableField("end_time")
	private Date endTime;
	@ApiModelProperty(value = "直播有效时间")
	@TableField("effect_Time")
	private Date effectTime;
	@ApiModelProperty(value = "监管人员")
	@TableField("supervise")
	private String supervise;
	@ApiModelProperty(value = "是否违规(1,违规，0不违规)")
	@TableField("is_violate")
	private Integer isViolate;

	@ApiModelProperty(value = "是否置顶（0：否，1：是）")
	@TableField("is_top")
	private Integer isTop;
	@ApiModelProperty(value = "置顶开始时间")
	@TableField("top_begin_time")
	private Date topBeginTime;
	@ApiModelProperty(value = "置顶结束时间")
	@TableField("top_end_time")
	private Date topEndTime;
	@ApiModelProperty(value = "国际编码")
	@TableField("country_code")
	private String countryCode;
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Date getTopBeginTime() {
		return topBeginTime;
	}

	public void setTopBeginTime(Date topBeginTime) {
		this.topBeginTime = topBeginTime;
	}

	public Date getTopEndTime() {
		return topEndTime;
	}

	public void setTopEndTime(Date topEndTime) {
		this.topEndTime = topEndTime;
	}

	public String getSupervise() {
		return supervise;
	}

	public void setSupervise(String supervise) {
		this.supervise = supervise;
	}

	public Integer getIsViolate() {
		return isViolate;
	}

	public void setIsViolate(Integer isViolate) {
		this.isViolate = isViolate;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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

	public String getPullUrl() {
		return pullUrl;
	}

	public void setPullUrl(String pullUrl) {
		this.pullUrl = pullUrl;
	}

	public String getPullRtmpUrl() {
		return pullRtmpUrl;
	}

	public void setPullRtmpUrl(String pullRtmpUrl) {
		this.pullRtmpUrl = pullRtmpUrl;
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

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}