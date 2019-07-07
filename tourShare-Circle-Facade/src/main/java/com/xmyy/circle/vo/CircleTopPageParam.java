package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "动态置顶查询条件对象")
public class CircleTopPageParam implements Serializable{

	@ApiModelProperty(value = "关键字")
	private String keywords;

	@ApiModelProperty(value = "用户名")
	private String memberName;

	@ApiModelProperty(value = "置顶开始时间，格式：yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date topTimeBegin;

	@ApiModelProperty(value = "置顶结束时间，格式：yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date topTimeEnd;

	@ApiModelProperty(value = "类型（1，笔记，2，视频）")
	private Integer typeId;

	@ApiModelProperty(value = "状态（0发布中，50发布成功，-50发布失败）")
	private Integer state;

	@ApiModelProperty(value = "分类标签(多个以英文逗号隔开)")
	private String classifyTags;

	@Range(min = 1, message = "分页参数不正确")
	@ApiModelProperty(value = "页码，默认为1")
	private Integer current = 1;

	@Range(min = 1, message = "分页参数不正确")
	@ApiModelProperty(value = "页大小，默认为10")
	private Integer size = 10;

	@ApiModelProperty(value = "买手id")
	private Long sellerId;

	@ApiModelProperty(value = "是否置顶（0否，1是）")
	private Integer isTop;

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Date getTopTimeBegin() {
		return topTimeBegin;
	}

	public void setTopTimeBegin(Date topTimeBegin) {
		this.topTimeBegin = topTimeBegin;
	}

	public Date getTopTimeEnd() {
		return topTimeEnd;
	}

	public void setTopTimeEnd(Date topTimeEnd) {
		this.topTimeEnd = topTimeEnd;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getClassifyTags() {
		return classifyTags;
	}

	public void setClassifyTags(String classifyTags) {
		this.classifyTags = classifyTags;
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
}
