package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 每日广播
 * </p>
 *
 * @author yeyu
 * @since 2018-07-03
 */
@ApiModel("每日广播")
@TableName("dg_everyday_radio")
@SuppressWarnings("serial")
public class DgEverydayRadio extends BaseModel {

    @ApiModelProperty(value = "类型：0：其他，1：买手接收需求，2：买家付款成功，3：买家提醒发货，4：买家申请售后")
	private Integer type;
    @ApiModelProperty(value = "推送内容")
	private String content;
    @ApiModelProperty(value = "推送给买手或背包客")
	@TableField("to_member_id")
	private Long toMemberId;
    @ApiModelProperty(value = "用户类型：1：买手，3:背包客")
	@TableField("to_member_type")
	private Integer toMemberType;
    @ApiModelProperty(value = "状态：是否有效，1：有效，0：失效")
	private Integer status;


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getToMemberId() {
		return toMemberId;
	}

	public void setToMemberId(Long toMemberId) {
		this.toMemberId = toMemberId;
	}

	public Integer getToMemberType() {
		return toMemberType;
	}

	public void setToMemberType(Integer toMemberType) {
		this.toMemberType = toMemberType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}