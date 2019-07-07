package com.xmyy.member.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 我的收货地址
 * </p>
 *
 * @author admin
 * @since 2018-05-23
 */
@ApiModel("我的收货地址")
@TableName("uc_member_address")
@SuppressWarnings("serial")
public class UcMemberAddress extends BaseModel {

    @ApiModelProperty(value = "买家_ID")
	@TableField("buyer_id_")
	private Long buyerId;
    @ApiModelProperty(value = "姓名")
	private String name;
    @ApiModelProperty(value = "地区code")
	@TableField("area_code")
	private String areaCode;
    @ApiModelProperty(value = "地区名称")
	@TableField("area_name")
	private String areaName;
    @ApiModelProperty(value = "地址")
	private String address;
    @ApiModelProperty(value = "手机号码")
	private String mobile;
    @ApiModelProperty(value = "是否默认(1是；0否)")
	@TableField("is_default")
	private Integer isDefault;
    @ApiModelProperty(value = "邮政编码")
	@TableField("zip_code")
	private String zipCode;
    @ApiModelProperty(value = "街道")
	@TableField("street_name")
	private String streetName;


	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

}