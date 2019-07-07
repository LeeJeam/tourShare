package com.xmyy.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 商品模版
 * </p>
 *
 * @author simon
 * @since 2018-06-07
 */
@ApiModel("商品模版")
@TableName("pt_series")
@SuppressWarnings("serial")
public class PtSeries extends BaseModel {

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
    @ApiModelProperty(value = "别名")
	private String alias;
    @ApiModelProperty(value = "模版编号")
	@TableField("series_no")
	private String SeriesNo;
    @ApiModelProperty(value = "图片")
	private String images;


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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSeriesNo() {
		return SeriesNo;
	}

	public void setSeriesNo(String seriesNo) {
		SeriesNo = seriesNo;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

}