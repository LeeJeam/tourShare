package com.xmyy.product.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 行程站点
 *
 * @author wzj
 */
@ApiModel("行程站点")
@TableName("dg_tour_site")
@SuppressWarnings("serial")
public class DgTourSite extends BaseModel {

	@ApiModelProperty(value = "行程ID")
	@TableField("tour_id")
	private Long tourId;
    @ApiModelProperty(value = "地区编码")
	@TableField("region_code")
	private String regionCode;
    @ApiModelProperty(value = "地区名称")
	@TableField("region_name")
	private String regionName;
    @ApiModelProperty(value = "站点类型（0-起点站，1-中途站，2-终点站，3-返程站）")
	@TableField("site_type")
	private Integer siteType;
    @ApiModelProperty(value = "地区logo")
	@TableField("region_logo")
	private String regionLogo;
    @ApiModelProperty(value = "站点图片")
	@TableField("region_img_url")
	private String regionImgUrl;
    @ApiModelProperty(value = "出发时间")
	@TableField("plan_begin_time")
	private Date planBeginTime;
    @ApiModelProperty(value = "是不是已签到 （0-未签到，默认；1-已签到）")
	@TableField("is_sign_in")
	private Integer isSignIn;
    @ApiModelProperty(value = "是否背包客（0-否；1-是）")
	@TableField("is_packer")
	private Integer isPacker;
    @ApiModelProperty(value = "站点序号")
	private Integer sort;


	public Long getTourId() {
		return tourId;
	}

	public void setTourId(Long tourId) {
		this.tourId = tourId;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Integer getSiteType() {
		return siteType;
	}

	public void setSiteType(Integer siteType) {
		this.siteType = siteType;
	}

	public String getRegionLogo() {
		return regionLogo;
	}

	public void setRegionLogo(String regionLogo) {
		this.regionLogo = regionLogo;
	}

	public String getRegionImgUrl() {
		return regionImgUrl;
	}

	public void setRegionImgUrl(String regionImgUrl) {
		this.regionImgUrl = regionImgUrl;
	}

	public Date getPlanBeginTime() {
		return planBeginTime;
	}

	public void setPlanBeginTime(Date planBeginTime) {
		this.planBeginTime = planBeginTime;
	}

	public Integer getIsSignIn() {
		return isSignIn;
	}

	public void setIsSignIn(Integer isSignIn) {
		this.isSignIn = isSignIn;
	}

	public Integer getIsPacker() {
		return isPacker;
	}

	public void setIsPacker(Integer isPacker) {
		this.isPacker = isPacker;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}