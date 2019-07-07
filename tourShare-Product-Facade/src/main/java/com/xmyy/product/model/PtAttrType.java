package com.xmyy.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 属性类型
 * </p>
 *
 * @author simon
 * @since 2018-06-07
 */
@ApiModel("属性类型")
@TableName("pt_attr_type")
@SuppressWarnings("serial")
public class PtAttrType extends BaseModel {

    @ApiModelProperty(value = "品牌_id")
	@TableField("pt_brand_id")
	private Long ptBrandId;
    @ApiModelProperty(value = "类目_id")
	@TableField("pt_category_id")
	private Long ptCategoryId;
    @ApiModelProperty(value = "类目_id2")
	@TableField("pt_category_id2")
	private Long ptCategoryId2;
    @ApiModelProperty(value = "名称")
	private String name;
    @ApiModelProperty(value = "展示方式（0：复选，1：下拉）")
	@TableField("show_type")
	private Integer showType;


	public Long getPtBrandId() {
		return ptBrandId;
	}

	public void setPtBrandId(Long ptBrandId) {
		this.ptBrandId = ptBrandId;
	}

	public Long getPtCategoryId() {
		return ptCategoryId;
	}

	public void setPtCategoryId(Long ptCategoryId) {
		this.ptCategoryId = ptCategoryId;
	}

	public Long getPtCategoryId2() {
		return ptCategoryId2;
	}

	public void setPtCategoryId2(Long ptCategoryId2) {
		this.ptCategoryId2 = ptCategoryId2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

}