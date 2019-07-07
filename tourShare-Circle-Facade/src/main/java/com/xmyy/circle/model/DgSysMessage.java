package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 12 系统消息
 * </p>
 *
 * @author yeyu
 * @since 2018-06-22
 */
@ApiModel("12 系统消息")
@TableName("dg_sys_message")
@SuppressWarnings("serial")
public class DgSysMessage extends BaseModel {

    @ApiModelProperty(value = "消息类型（1：系统消息，2：公告消息）")
	@TableField("message_type")
	private Integer messageType;
    @ApiModelProperty(value = "标题")
	private String title;
    @ApiModelProperty(value = "内容")
	private String content;
    @ApiModelProperty(value = "缩略图")
	private String cover;
    @ApiModelProperty(value = "图片集合（多张图片用逗号隔开）")
	private String images;
    @ApiModelProperty(value = "接收者类型（1:买手，2：买家）")
	@TableField("member_type")
	private Integer memberType;


	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

}