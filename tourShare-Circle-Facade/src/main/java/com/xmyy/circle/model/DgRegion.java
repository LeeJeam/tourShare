package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * DG_REGION 地区表
 * </p>
 *
 * @author wangzejun
 * @since 2018-06-04
 */
@ApiModel("DG_REGION 地区表")
@TableName("dg_region")
@SuppressWarnings("serial")
public class DgRegion extends BaseModel {

    @ApiModelProperty(value = "地区代码")
	private String ccode;
    @ApiModelProperty(value = "地区名称")
	private String cname;
    @ApiModelProperty(value = "语言版本")
	private String language;
    @ApiModelProperty(value = "父地区代码")
	private String parentcode;
    @ApiModelProperty(value = "纬度")
	private String latitude;
    @ApiModelProperty(value = "经度")
	private String longitude;
    @ApiModelProperty(value = "时区")
	private String timezone;
    @ApiModelProperty(value = "IDXCODE")
	private String idxcode;
    @ApiModelProperty(value = "SHORTCODE")
	private String shortcode;
    @ApiModelProperty(value = "CHILDRENCOUNT")
	private Integer childrencount;
    @ApiModelProperty(value = "全名")
	private String fullname;
    @ApiModelProperty(value = "邮政编码")
	private String postcode;
    @ApiModelProperty(value = "面积单位")
	private String areacode;
    @ApiModelProperty(value = "SECIDXCODE")
	private String secidxcode;
    @ApiModelProperty(value = "GRADE")
	private Integer grade;
    @ApiModelProperty(value = "是不是热门地区")
	@TableField("is_hot")
	private Integer isHot;
    @ApiModelProperty(value = "国家（或地区）码")
	@TableField("prefix_phone")
	private String prefixPhone;
	@ApiModelProperty(value = "国旗")
	@TableField("national_flag")
	private String nationalFlag;


	public String getCcode() {
		return ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getParentcode() {
		return parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getIdxcode() {
		return idxcode;
	}

	public void setIdxcode(String idxcode) {
		this.idxcode = idxcode;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	public Integer getChildrencount() {
		return childrencount;
	}

	public void setChildrencount(Integer childrencount) {
		this.childrencount = childrencount;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getSecidxcode() {
		return secidxcode;
	}

	public void setSecidxcode(String secidxcode) {
		this.secidxcode = secidxcode;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public String getPrefixPhone() {
		return prefixPhone;
	}

	public void setPrefixPhone(String prefixPhone) {
		this.prefixPhone = prefixPhone;
	}

	public String getNationalFlag() {
		return nationalFlag;
	}

	public void setNationalFlag(String nationalFlag) {
		this.nationalFlag = nationalFlag;
	}
}