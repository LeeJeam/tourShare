package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 收藏表
 * </p>
 *
 * @author zlp
 * @since 2018-06-09
 */
@ApiModel("收藏表")
@TableName("dg_favorites")
@SuppressWarnings("serial")
public class DgFavorites extends BaseModel {

    @ApiModelProperty(value = "平台id")
	@TableField("app_id")
	private Integer appId;
    @ApiModelProperty(value = "平台名称")
	@TableField("app_name")
	private String appName;
    @ApiModelProperty(value = "依赖实体类型（1，笔记；2，视频；3，商品）")
	@TableField("entity_type_id")
	private Integer entityTypeId;
    @ApiModelProperty(value = "依赖实体id")
	@TableField("entity_id")
	private Long entityId;
    @ApiModelProperty(value = "买家/买手")
	@TableField("member_id")
	private Long memberId;
    @ApiModelProperty(value = "用户类型(1,买手，2背包客)")
	@TableField("member_type")
	private Integer memberType;


	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
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