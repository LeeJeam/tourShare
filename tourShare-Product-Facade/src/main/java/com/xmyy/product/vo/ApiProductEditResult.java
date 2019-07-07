package com.xmyy.product.vo;

import com.xmyy.product.dto.ApiSkuDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "商品详情")
public class ApiProductEditResult implements Serializable {

    @ApiModelProperty(value = "商品ID")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "一级类目ID")
    private Long categoryId;

    @ApiModelProperty(value = "一级类目名称")
    private String categoryName;

    @ApiModelProperty(value = "二级类目ID")
    private Long categoryId2;

    @ApiModelProperty(value = "二级类目名称")
    private String categoryName2;

    @ApiModelProperty(value = "品牌ID")
    private Long brandId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "系列ID")
    private Long seriesId;

    @ApiModelProperty(value = "系列名称")
    private String seriesName;

    @ApiModelProperty(value = "商品类型(1:预售，2现货)")
    private Integer productType;

    @ApiModelProperty(value = "购买国家")
    private String buyRegion;

    @ApiModelProperty(value = "购买商店名称")
    private String shopName;

    @ApiModelProperty(value = "商品详情")
    private String productDesc;

    @ApiModelProperty(value = "物流方式（1:国内配送，2：鉴定配送）")
    private Integer expressType;

    @ApiModelProperty(value = "失效时间")
    private Date expiresTime;

    @ApiModelProperty(value = "图片")
    private List<String> images;

    @ApiModelProperty(value = "行程ID")
    private Long tourId;

    @ApiModelProperty(value = "商品型号参数列表")
    private List<ApiSkuDto> sku;

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCategoryId2() {
        return categoryId2;
    }

    public void setCategoryId2(Long categoryId2) {
        this.categoryId2 = categoryId2;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public List<ApiSkuDto> getSku() {
        return sku;
    }

    public void setSku(List<ApiSkuDto> sku) {
        this.sku = sku;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

}
