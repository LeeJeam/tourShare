package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 关注
 * </p>
 *
 * @author yeyu
 * @since 2018-06-11
 */
@ApiModel("关注")
@TableName("uc_member_relation")
@SuppressWarnings("serial")
public class UcMemberRelation extends BaseModel {

    @ApiModelProperty(value = "买家用户")
	@TableField("member_id")
	private Long memberId;
    @ApiModelProperty(value = "目标id")
	@TableField("to_member_id")
	private Long toMemberId;
    @ApiModelProperty(value = "用户类型(1,买手，2买家/背包客)")
	@TableField("to_member_type")
	private Integer toMemberType;


	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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

	@Override
	public String toString() {
		return "UcMemberRelation{" +
				"memberId=" + memberId +
				", toMemberId=" + toMemberId +
				", toMemberType=" + toMemberType +
				'}';
	}
}