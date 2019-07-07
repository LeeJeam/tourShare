package com.xmyy.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

import java.math.BigDecimal;

/**
 * <p>
 * 商品SKU
 * </p>
 *
 * @author simon
 * @since 2018-06-02
 */
@ApiModel("商品SKU")
@TableName("pt_sku")
@SuppressWarnings("serial")
public class PtSku extends BaseModel {

    @ApiModelProperty(value = "商品表_id")
	@TableField("pt_product_id")
	private Long ptProductId;
	@ApiModelProperty(value = "颜色属性_id")
	@TableField("pt_series_color_id")
	private Long ptSeriesColorId;
    @ApiModelProperty(value = "价格")
	private BigDecimal price;
    @ApiModelProperty(value = "库存")
	private Integer stock;

	public Long getPtProductId() {
		return ptProductId;
	}

	public void setPtProductId(Long ptProductId) {
		this.ptProductId = ptProductId;
	}

	public Long getPtSeriesColorId() {
		return ptSeriesColorId;
	}

	public void setPtSeriesColorId(Long ptSeriesColorId) {
		this.ptSeriesColorId = ptSeriesColorId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

}