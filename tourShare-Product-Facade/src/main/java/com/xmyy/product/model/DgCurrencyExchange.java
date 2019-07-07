package com.xmyy.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author simon
 * @since 2018-07-01
 */
@ApiModel("")
@TableName("dg_currency_exchange")
@SuppressWarnings("serial")
public class DgCurrencyExchange extends BaseModel {

    @ApiModelProperty(value = "外币码")
	@TableField("foreign_currency_code")
	private String foreignCurrencyCode;
    @ApiModelProperty(value = "转换比例")
	private Double rate;


	public String getForeignCurrencyCode() {
		return foreignCurrencyCode;
	}

	public void setForeignCurrencyCode(String foreignCurrencyCode) {
		this.foreignCurrencyCode = foreignCurrencyCode;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "DgCurrencyExchange{" +
				"foreignCurrencyCode='" + foreignCurrencyCode + '\'' +
				", rate=" + rate +
				'}';
	}
}