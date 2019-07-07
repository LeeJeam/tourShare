package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Simon on 2018/6/4.
 */
@ApiModel(value = "商品详情DTO")
public class ApiProductDetailDto implements Serializable {

    @ApiModelProperty(value = "商品ID")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "图片列表")
    private List<String> images;

    @ApiModelProperty(value = "商品描述")
    private String productDesc;

    @ApiModelProperty(value = "购买地")
    private String buyRegion;

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "失效时间")
    private Date expiresTime;

    @ApiModelProperty(value = "预订数")
    private Integer orderCount;

    @ApiModelProperty(value = "是否收藏（0：否，1：是）")
    private Integer isFavorites;

    @ApiModelProperty(value = "购买地图标")
    private String buyRegionLogo;

    @ApiModelProperty(value = "购买地币种")
    private String buyRegionCurrency;

    @ApiModelProperty(value = "购买地币种编码")
    private String buyRegionCurrencyCode;

    @ApiModelProperty(value = "当前币种")
    private String currency;

    @ApiModelProperty(value = "当前币种码")
    private String currencyCode;

    @ApiModelProperty(value = "上下架状态(0下架，1上架)")
    private Integer isSale;

    @ApiModelProperty(value = "物流方式（1国内配送，2鉴定配送）")
    private Integer expressType;

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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getBuyRegion() {
        return buyRegion;
    }

    public void setBuyRegion(String buyRegion) {
        this.buyRegion = buyRegion;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getIsFavorites() {
        return isFavorites;
    }

    public void setIsFavorites(Integer isFavorites) {
        this.isFavorites = isFavorites;
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

    public Integer getIsSale() {
        return isSale;
    }

    public void setIsSale(Integer isSale) {
        this.isSale = isSale;
    }

    public Integer getExpressType() {
        return expressType;
    }

    public void setExpressType(Integer expressType) {
        this.expressType = expressType;
    }
}
