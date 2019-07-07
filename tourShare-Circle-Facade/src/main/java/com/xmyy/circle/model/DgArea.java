package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 地区
 * </p>
 *
 * @author wangzejun
 * @since 2018-06-27
 */
@ApiModel("地区")
@TableName("dg_area")
@SuppressWarnings("serial")
public class DgArea extends BaseModel {

    @ApiModelProperty(value = "区域名称")
	@TableField("area_name")
	private String areaName;
    @ApiModelProperty(value = "区域编码")
	@TableField("area_code")
	private String areaCode;
    @ApiModelProperty(value = "父亲地区编码")
	@TableField("parent_code")
	private String parentCode;
    @ApiModelProperty(value = "地区简称")
	private String shortname;
    @ApiModelProperty(value = "经度")
	private Double longitude;
    @ApiModelProperty(value = "纬度")
	private Double latitude;
    @ApiModelProperty(value = "层级")
	private Integer level;
    @ApiModelProperty(value = "前端位置")
	private String position;
    @ApiModelProperty(value = "显示顺序")
	private Integer sort;


	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}