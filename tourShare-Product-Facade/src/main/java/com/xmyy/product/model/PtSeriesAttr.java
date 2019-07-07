package com.xmyy.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 模版属性
 * </p>
 *
 * @author simon
 * @since 2018-06-07
 */
@ApiModel("模版属性")
@TableName("pt_series_attr")
@SuppressWarnings("serial")
public class PtSeriesAttr extends BaseModel {

    @ApiModelProperty(value = "商品模版_id")
	@TableField("pt_series_id")
	private Long ptSeriesId;
    @ApiModelProperty(value = "属性值_id")
	@TableField("pt_attr_value_id")
	private Long ptAttrValueId;
    @ApiModelProperty(value = "属性类型_id")
	@TableField("pt_attr_type_id")
	private Long ptAttrTypeId;


	public Long getPtSeriesId() {
		return ptSeriesId;
	}

	public void setPtSeriesId(Long ptSeriesId) {
		this.ptSeriesId = ptSeriesId;
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
}