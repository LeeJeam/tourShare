package com.xmyy.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 品牌
 * </p>
 *
 * @author simon
 * @since 2018-05-18
 */
@ApiModel("品牌")
@TableName("pt_brand")
@SuppressWarnings("serial")
public class PtBrand extends BaseModel {

    @ApiModelProperty(value = "名称")
	private String name;
    @ApiModelProperty(value = "首字母")
	private String initial;
    @ApiModelProperty(value = "图标")
	private String logo;
	@ApiModelProperty(value = "品牌编号")
	@TableField("brand_no")
	private String brandNo;
	@ApiModelProperty(value = "英文名称")
	@TableField("en_name")
	private String enName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}
}