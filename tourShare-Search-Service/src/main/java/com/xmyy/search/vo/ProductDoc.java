package com.xmyy.search.vo;

import com.xmyy.search.core.SearchUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Simon on 2018/7/20.
 */
@Document(indexName = "#{esConfig.productIndexName}",type = SearchUtils.TYPE_PRODUCT)
public class ProductDoc {

    @Id
    private Long id;

    private Long dgTourId;

    private Long ptBrandId;

    private Long ptSeriesId;

    private Long ptCategoryId;

    private Long ptCategoryId2;

    private String name;

    private String productNo;

    private BigDecimal marketPrice;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Integer isSale;

    private Integer isHot;

    private Integer isRecommend;

    private String productDesc;

    private String cover;

    private String images;

    private Integer expressType;

    private Date expiresTime;

    @Field(type = FieldType.text)
    private String buyRegion;

    @Field(type = FieldType.text)
    private String shopName;

    @Field(type = FieldType.text)
    private String title;

    private String tags;

    private String serviceDesc;

    private Integer productType;

    private Integer orderCount;

    private Integer collectCount;

    private Integer refundCount;

    private Integer isTop;

    private Date topBeginTime;

    private Date topEndTime;

    private Integer clickCount;

    private Integer videoClickCount;

    private Float favorable;

    private String buyRegionLogo;

    private String buyRegionCurrency;

    private String buyRegionCurrencyCode;

    private String currency;

    private String currencyCode;

    private Long TopOpId;

    private Date TopOpTime;

    //扩展字段
    @Field(type = FieldType.text)
    private String categoryName;

    @Field(type = FieldType.text)
    private String categoryName2;

    @Field(type = FieldType.text)
    private String brandName;

    private Long sellerId;

    private String nickName;

    private String avatarRsurl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDgTourId() {
        return dgTourId;
    }

    public void setDgTourId(Long dgTourId) {
        this.dgTourId = dgTourId;
    }

    public Long getPtBrandId() {
        return ptBrandId;
    }

    public void setPtBrandId(Long ptBrandId) {
        this.ptBrandId = ptBrandId;
    }

    public Long getPtSeriesId() {
        return ptSeriesId;
    }

    public void setPtSeriesId(Long ptSeriesId) {
        this.ptSeriesId = ptSeriesId;
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

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
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

    public Integer getIsSale() {
        return isSale;
    }

    public void setIsSale(Integer isSale) {
        this.isSale = isSale;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getExpressType() {
        return expressType;
    }

    public void setExpressType(Integer expressType) {
        this.expressType = expressType;
    }

    public Date getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(Date expiresTime) {
        this.expiresTime = expiresTime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(Integer refundCount) {
        this.refundCount = refundCount;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Date getTopBeginTime() {
        return topBeginTime;
    }

    public void setTopBeginTime(Date topBeginTime) {
        this.topBeginTime = topBeginTime;
    }

    public Date getTopEndTime() {
        return topEndTime;
    }

    public void setTopEndTime(Date topEndTime) {
        this.topEndTime = topEndTime;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Integer getVideoClickCount() {
        return videoClickCount;
    }

    public void setVideoClickCount(Integer videoClickCount) {
        this.videoClickCount = videoClickCount;
    }

    public Float getFavorable() {
        return favorable;
    }

    public void setFavorable(Float favorable) {
        this.favorable = favorable;
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

    public Long getTopOpId() {
        return TopOpId;
    }

    public void setTopOpId(Long topOpId) {
        TopOpId = topOpId;
    }

    public Date getTopOpTime() {
        return TopOpTime;
    }

    public void setTopOpTime(Date topOpTime) {
        TopOpTime = topOpTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName2() {
        return categoryName2;
    }

    public void setCategoryName2(String categoryName2) {
        this.categoryName2 = categoryName2;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
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

}
