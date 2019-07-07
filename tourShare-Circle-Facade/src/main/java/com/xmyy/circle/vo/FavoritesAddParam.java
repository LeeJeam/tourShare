package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@ApiModel("收藏/取消收藏入参")
@SuppressWarnings("serial")
public class FavoritesAddParam implements Serializable {

	@ApiModelProperty(value = "登陆设备（1安卓，2IOS，3小程序，4PC）")
	private Integer appId;

	@NotNull
	@ApiModelProperty(value = "收藏类型（1笔记；2视频；3商品）", required = true)
	private Integer entityTypeId;

	@NotNull
	@ApiModelProperty(value = "收藏id", required = true)
	private Long entityId;

    @ApiModelProperty(value = "当前登陆的买家/买手，可不赋值")
	private Long memberId;

	@NotNull(message = "当前登陆的用户类型不能为空")
    @ApiModelProperty(value = "当前登陆的用户类型(1,买手，2买家),可理解为买家端，还是买手端", required = true)
	private Integer memberType;

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
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