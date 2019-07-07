package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Simon on 2018/6/13.
 */
@ApiModel(value = "买手背包商品实体")
public class ApiProductSimpleDto implements Serializable {

    @ApiModelProperty(value = "商品ID")
    private Long id;

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "商品封面")
    private String cover;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "购买地")
    private String buyRegion;

    @ApiModelProperty(value = "购买地图标")
    private String buyRegionLogo;

    @ApiModelProperty(value = "购买地币种")
    private String buyRegionCurrency;

    @ApiModelProperty(value = "购买地币种编码")
    private String buyRegionCurrencyCode;

    @ApiModelProperty(value = "当前币种")
    private String currency;

    @ApiModelProperty(value = "当前币种图标")
    private String currencyCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBuyRegion() {
        return buyRegion;
    }

    public void setBuyRegion(String buyRegion) {
        this.buyRegion = buyRegion;
    }

    public String getBuyRegionLogo() {
        return buyRegionLogo;
    }

    public void setBuyRegionLogo(String buyRegionLogo) {
        this.buyRegionLogo = buyRegionLogo;
    }

    public String getBuyRegionCurrency() {
        return buyRegionCurrency;
    }

    public void setBuyRegionCurrency(String buyRegionCurrency) {
        this.buyRegionCurrency = buyRegionCurrency;
    }

    public String getBuyRegionCurrencyCode() {
        return buyRegionCurrencyCode;
    }

    public void setBuyRegionCurrencyCode(String buyRegionCurrencyCode) {
        this.buyRegionCurrencyCode = buyRegionCurrencyCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
