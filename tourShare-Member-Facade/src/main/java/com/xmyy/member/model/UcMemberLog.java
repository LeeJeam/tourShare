package com.xmyy.member.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户修改日志
 * </p>
 *
 * @author admin
 * @since 2018-05-23
 */
@ApiModel("用户修改日志")
@TableName("uc_member_log")
@SuppressWarnings("serial")
public class UcMemberLog extends BaseModel {

    @ApiModelProperty(value = "日志事件")
	private String action;
    @ApiModelProperty(value = "日志内容")
	private String content;
    @ApiModelProperty(value = "买家/买手")
	@TableField("member_id")
	private Long memberId;
    @ApiModelProperty(value = "用户类型(1,买手，2背包客)")
	@TableField("member_type")
	private Integer memberType;


	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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