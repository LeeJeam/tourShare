package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 国家信息
 * </p>
 *
 * @author wangzejun
 * @since 2018-06-25
 */
@ApiModel("国家信息")
@TableName("dg_country")
@SuppressWarnings("serial")
public class DgCountry extends BaseModel {

	@ApiModelProperty("国家编码(系统自己定义)")
	private String code;
	@ApiModelProperty("国家码")
	private String shortCode;
    @ApiModelProperty(value = "国家名称")
	private String name;
    @ApiModelProperty(value = "国家英文名")
	@TableField("en_name")
	private String enName;
    @ApiModelProperty(value = "国家图标")
	private String logo;
    @ApiModelProperty(value = "国家货币")
	private String currency;
    @ApiModelProperty(value = "国家货币编码")
	@TableField("currency_code")
	private String currencyCode;
    @ApiModelProperty(value = "国家首字母")
	private String initial;
    @ApiModelProperty(value = "电话区号")
	@TableField("prefix_phone")
	private String prefixPhone;
    @ApiModelProperty(value="所属洲编码")
	@TableField("continent_code")
	private String continentCode;
	@ApiModelProperty(value="所属洲名称")
	@TableField("continent_Name")
	private String continentName;
    @ApiModelProperty(value="是不是热门国家")
    @TableField("is_hot")
	private Integer isHot;
    @ApiModelProperty(value="序号")
	private Integer sortNo;

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public String getPrefixPhone() {
		return prefixPhone;
	}

	public void setPrefixPhone(String prefixPhone) {
		this.prefixPhone = prefixPhone;
	}

	public String getContinentCode() {
		return continentCode;
	}

	public void setContinentCode(String continentCode) {
		this.continentCode = continentCode;
	}

	public String getContinentName() {
		return continentName;
	}

	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}