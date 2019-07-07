package com.xmyy.search.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "热词搜索入参")
public class HotSearchParam implements Serializable {

	@ApiModelProperty(value = "热词总数（默认5）")
	private Integer hotCount = 5;

	private String currentCity;

	@ApiModelProperty(value = "搜索框内容")
	private String keyword;

	@ApiModelProperty(value = "多个以逗号隔开，MEMBER,PRODUCT,CRICLE")
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getHotCount() {
		return hotCount;
	}

	public void setHotCount(Integer hotCount) {
		this.hotCount = hotCount;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
