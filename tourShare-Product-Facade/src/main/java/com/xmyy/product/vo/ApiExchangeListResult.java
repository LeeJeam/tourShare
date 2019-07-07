package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "外汇汇率列表结果")
public class ApiExchangeListResult implements Serializable {

    @ApiModelProperty(value = "外币货币码")
    private String foreignCurrencyCode;

    @ApiModelProperty(value = "汇率")
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

}
