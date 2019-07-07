package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 系统消息阅读记录
 * </p>
 *
 * @author yeyu
 * @since 2018-06-22
 */
@ApiModel("系统消息阅读记录")
@TableName("dg_sys_message_read")
@SuppressWarnings("serial")
public class DgSysMessageRead extends BaseModel {

    @ApiModelProperty(value = "系统消息_ID")
	@TableField("message_id_")
	private Long messageId;
    @ApiModelProperty(value = "用户")
	@TableField("member_id")
	private Long memberId;
    @ApiModelProperty(value = "用户类型（1:买手，2：买家）")
	@TableField("member_type")
	private Integer memberType;


	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
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

}