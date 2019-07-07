package com.xmyy.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author simon
 * @since 2018-06-02
 */
@ApiModel("商品属性")
@TableName("pt_attr")
@SuppressWarnings("serial")
public class PtAttr extends BaseModel {

    @ApiModelProperty(value = "商品表_id")
	@TableField("pt_product_id")
	private Long ptProductId;
    @ApiModelProperty(value = "商品SKU_id")
	@TableField("pt_sku_id")
	private Long ptSkuId;
    @ApiModelProperty(value = "属性值_id")
	@TableField("pt_attr_value_id")
	private Long ptAttrValueId;
    @ApiModelProperty(value = "属性类型_id")
	@TableField("pt_attr_type_id")
	private Long ptAttrTypeId;
    @ApiModelProperty(value = "属性类型名称")
	@TableField("pt_attr_type_name")
	private String ptAttrTypeName;
    @ApiModelProperty(value = "属性值名称")
	@TableField("pt_attr_value_name")
	private String ptAttrValueName;

	public Long getPtProductId() {
		return ptProductId;
	}

	public void setPtProductId(Long ptProductId) {
		this.ptProductId = ptProductId;
	}

	public Long getPtSkuId() {
		return ptSkuId;
	}

	public void setPtSkuId(Long ptSkuId) {
		this.ptSkuId = ptSkuId;
	}

	public Long getPtAttrValueId() {
		return ptAttrValueId;
	}

	public void setPtAttrValueId(Long ptAttrValueId) {
		this.ptAttrValueId = ptAttrValueId;
	}

	public Long getPtAttrTypeId() {
		return ptAttrTypeId;
	}

	public void setPtAttrTypeId(Long ptAttrTypeId) {
		this.ptAttrTypeId = ptAttrTypeId;
	}

	public String getPtAttrTypeName() {
		return ptAttrTypeName;
	}

	public void setPtAttrTypeName(String ptAttrTypeName) {
		this.ptAttrTypeName = ptAttrTypeName;
	}

	public String getPtAttrValueName() {
		return ptAttrValueName;
	}

	public void setPtAttrValueName(String ptAttrValueName) {
		this.ptAttrValueName = ptAttrValueName;
	}

	@Override
	public String toString() {
		return "PtAttr{" +
				"ptProductId=" + ptProductId +
				", ptSkuId=" + ptSkuId +
				", ptAttrValueId=" + ptAttrValueId +
				", ptAttrTypeId=" + ptAttrTypeId +
				", ptAttrTypeName='" + ptAttrTypeName + '\'' +
				", ptAttrValueName='" + ptAttrValueName + '\'' +
				'}';
	}
}