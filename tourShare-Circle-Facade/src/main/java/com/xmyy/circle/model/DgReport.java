package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 举报
 * </p>
 *
 * @author simon
 * @since 2018-06-11
 */
@ApiModel("举报")
@TableName("dg_report")
@SuppressWarnings("serial")
public class DgReport extends BaseModel {

    @ApiModelProperty(value = "举报类型（1.广告内容 2.不友善内容 3.垃圾内容 4.违法违规内容 5.其他）")
	@TableField("report_type")
	private Integer reportType;
    @ApiModelProperty(value = "举报内容")
	private String content;
    @ApiModelProperty(value = "目标ID")
	@TableField("target_id")
	private Long targetId;
    @ApiModelProperty(value = "类型（1，笔记，2，视频）")
	@TableField("target_type")
	private Integer targetType;
    @ApiModelProperty(value = "举报人ID")
	@TableField("member_id")
	private Long memberId;
    @ApiModelProperty(value = "举报人类型（1买手，2买家，3背包客）")
	@TableField("member_type")
	private Integer memberType;
    @ApiModelProperty(value = "举报图片")
    @TableField("images")
    private String images;

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
}