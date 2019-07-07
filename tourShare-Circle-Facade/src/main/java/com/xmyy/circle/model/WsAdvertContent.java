package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

import java.util.Date;

/**
 * <p>
 * 广告内容
 * </p>
 *
 * @author zlp
 * @since 2018-06-04
 */
@ApiModel("广告内容")
@TableName("ws_advert_content")
@SuppressWarnings("serial")
public class WsAdvertContent extends BaseModel {

    @ApiModelProperty(value = "广告位id_ID")
	@TableField("position_id_")
	private Long positionId;
    @ApiModelProperty(value = "资源URL")
	@TableField("file_rsurl")
	private String fileRsurl;
    @ApiModelProperty(value = "状态-50删除,1待发布，50发布")
	private Integer state;
    @ApiModelProperty(value = "开始时间")
	@TableField("start_time")
	private Date startTime;
    @ApiModelProperty(value = "结束时间")
	@TableField("end_time")
	private Date endTime;
    @ApiModelProperty(value = "文字内容")
	private String content;
    @ApiModelProperty(value = "单幅广告描述")
	@TableField("describe_")
	private String describe;
    @ApiModelProperty(value = "排序（越大越前）")
	private Integer sort;
    @ApiModelProperty(value = "点击链接")
	@TableField("click_url")
	private String clickUrl;


	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getFileRsurl() {
		return fileRsurl;
	}

	public void setFileRsurl(String fileRsurl) {
		this.fileRsurl = fileRsurl;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getClickUrl() {
		return clickUrl;
	}

	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}

}