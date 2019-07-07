package com.xmyy.circle.vo;

import com.xmyy.circle.model.UcDynamicCircle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "动态置顶返回对象")
public class CircleTopPageResult extends UcDynamicCircle implements Serializable{

	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "状态")
	private String stateLabel;

	@ApiModelProperty(value = "类型")
	private String typeLabel;

	@ApiModelProperty(value = "置顶操作人")
	private String toperName;

	public String getToperName() {
		return toperName;
	}

	public void setToperName(String toperName) {
		this.toperName = toperName;
	}

	public String getTypeLabel() {
		return typeLabel;
	}

	public void setTypeLabel(String typeLabel) {
		this.typeLabel = typeLabel;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getStateLabel() {
		return stateLabel;
	}

	public void setStateLabel(String stateLabel) {
		this.stateLabel = stateLabel;
	}
}
