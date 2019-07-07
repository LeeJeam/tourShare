package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "首页最新预售结果")
public class PresellProductResult implements Serializable {

    @ApiModelProperty(value = "商品ID")
    private Long id;

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "商品封面")
    private String cover;

    @ApiModelProperty(value = "购买国家")
    private String buyRegion;

    @ApiModelProperty(value = "预定数")
    private Integer orderCount;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "失效时间")
    private Date expiresTime;

    @ApiModelProperty(value = "行程ID")
    private Long tourId;

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

    public String getBuyRegion() {
        return buyRegion;
    }

    public void setBuyRegion(String buyRegion) {
        this.buyRegion = buyRegion;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(Date expiresTime) {
        this.expiresTime = expiresTime;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
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
