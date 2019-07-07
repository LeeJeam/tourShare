package com.xmyy.livevideo.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 直播回调日志表
 * </p>
 *
 * @author simon
 * @since 2018-08-07
 */
@ApiModel("直播回调日志表")
@TableName("vd_live_callback_log")
@SuppressWarnings("serial")
public class VdLiveCallbackLog extends BaseModel {

    @ApiModelProperty(value = "事件类型")
	@TableField("event_type")
	private Integer eventType;
    @ApiModelProperty(value = "流ID")
	@TableField("stream_id")
	private String streamId;
    @ApiModelProperty(value = "视频ID")
	@TableField("video_id")
	private String videoId;
    @ApiModelProperty(value = "视频地址")
	@TableField("video_url")
	private String videoUrl;
    @ApiModelProperty(value = "图片地址")
	@TableField("pic_full_url")
	private String picFullUrl;
    @ApiModelProperty(value = "请求JSON")
	@TableField("json_body")
	private String jsonBody;


	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getPicFullUrl() {
		return picFullUrl;
	}

	public void setPicFullUrl(String picFullUrl) {
		this.picFullUrl = picFullUrl;
	}

	public String getJsonBody() {
		return jsonBody;
	}

	public void setJsonBody(String jsonBody) {
		this.jsonBody = jsonBody;
	}

}