package com.xmyy.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

import java.math.BigDecimal;

/**
 * <p>
 * 参考价格
 * </p>
 *
 * @author simon
 * @since 2018-06-07
 */
@ApiModel("参考价格")
@TableName("pt_series_price")
@SuppressWarnings("serial")
public class PtSeriesPrice extends BaseModel {

    @ApiModelProperty(value = "商品模版_id")
	@TableField("pt_series_id")
	private Long ptSeriesId;
    @ApiModelProperty(value = "国家")
	private String region;
    @ApiModelProperty(value = "店铺")
	private String shop;
    @ApiModelProperty(value = "当地价格")
	private BigDecimal price;
    @ApiModelProperty(value = "单位")
	private String unit;
    @ApiModelProperty(value = "描述")
	private String descript;
    @ApiModelProperty(value = "单位符号")
	private String symbol;


	public Long getPtSeriesId() {
		return ptSeriesId;
	}

	public void setPtSeriesId(Long ptSeriesId) {
		this.ptSeriesId = ptSeriesId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}