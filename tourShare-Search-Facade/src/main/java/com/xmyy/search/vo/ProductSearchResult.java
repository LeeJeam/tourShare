package com.xmyy.search.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "商品搜索结果")
public class ProductSearchResult implements Serializable {

    @ApiModelProperty(value = "商品ID")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "最低价格")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "最高价格")
    private BigDecimal maxPrice;

    @ApiModelProperty(value = "购买地")
    private String buyRegion;

    @ApiModelProperty(value = "购买商店")
    private String shopName;

    @ApiModelProperty(value = "商品类型")
    private Integer productType;

    @ApiModelProperty(value = "预售商品过期时间")
    private Date expiresTime;

    @ApiModelProperty(value = "订单数/预定数")
    private Integer orderCount;

    @ApiModelProperty(value = "行程ID")
    private Long dgTourId;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatarRsurl;

    @ApiModelProperty(value = "购买地国旗")
    private String buyRegionLogo;

    @ApiModelProperty(value = "购买地货币")
    private String buyRegionCurrency;

    @ApiModelProperty(value = "购买地货币码")
    private String buyRegionCurrencyCode;

    @ApiModelProperty(value = "当前币种")
    private String currency;

    @ApiModelProperty(value = "当前币种码")
    private String currencyCode;

    @ApiModelProperty(value = "价格（当前设定与minPrice相等）")
    private BigDecimal price;

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

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
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

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
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

    public Long getDgTourId() {
        return dgTourId;
    }

    public void setDgTourId(Long dgTourId) {
        this.dgTourId = dgTourId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarRsurl() {
        return avatarRsurl;
    }

    public void setAvatarRsurl(String avatarRsurl) {
        this.avatarRsurl = avatarRsurl;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
