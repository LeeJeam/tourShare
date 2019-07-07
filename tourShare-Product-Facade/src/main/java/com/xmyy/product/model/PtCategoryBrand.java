package com.xmyy.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 类目品牌关联
 * </p>
 *
 * @author simon
 * @since 2018-05-18
 */
@ApiModel("类目品牌关联")
@TableName("pt_category_brand")
@SuppressWarnings("serial")
public class PtCategoryBrand extends BaseModel {

    @ApiModelProperty(value = "品牌_id")
	@TableField("pt_brand_id")
	private Long ptBrandId;
    @ApiModelProperty(value = "类目_id")
	@TableField("pt_category_id")
	private Long ptCategoryId;
	@ApiModelProperty(value = "类目二_id")
	@TableField("pt_category_id2")
	private Long ptCategoryId2;
	@ApiModelProperty(value = "商品数量")
	@TableField("product_count")
	private Integer productCount;

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

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
}