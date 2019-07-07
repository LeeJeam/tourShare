package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "买手通过标签搜索需求")
public class DemandListByTagsResult implements Serializable {

    @ApiModelProperty(value = "需求ID")
    private Long id;

    @ApiModelProperty(value = "买家ID")
    private Long buyerId;

    @ApiModelProperty(value = "需求图片，可以多张")
    private String demandImages;

    @ApiModelProperty(value = "需求封面图")
    private String demandCover;

    @ApiModelProperty(value = "需求描述")
    private String demandDesc;

    @ApiModelProperty(value = "最迟收货时间")
    private Date deliveryTime;

    @ApiModelProperty(value = "最迟收货时间字符串")
    private String deliveryTimeText;

    @ApiModelProperty(value = "预算价格")
    private BigDecimal budgetPrice;

    @ApiModelProperty(value = "标签类型")
    private String productType;

    @ApiModelProperty(value = "买家UUID")
    private String buyerUUID;

    @ApiModelProperty(value = "购买地")
    private String buyCountry;

    @ApiModelProperty(value = "购买地国旗")
    private String countryIcon;

    public String getCountryIcon() {
        return countryIcon;
    }

    public void setCountryIcon(String countryIcon) {
        this.countryIcon = countryIcon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getDemandImages() {
        return demandImages;
    }

    public void setDemandImages(String demandImages) {
        this.demandImages = demandImages;
    }

    public String getDemandCover() {
        return demandCover;
    }

    public void setDemandCover(String demandCover) {
        this.demandCover = demandCover;
    }

    public String getDemandDesc() {
        return demandDesc;
    }

    public void setDemandDesc(String demandDesc) {
        this.demandDesc = demandDesc;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public BigDecimal getBudgetPrice() {
        return budgetPrice;
    }

    public void setBudgetPrice(BigDecimal budgetPrice) {
        this.budgetPrice = budgetPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getDeliveryTimeText() {
        return deliveryTimeText;
    }

    public void setDeliveryTimeText(String deliveryTimeText) {
        this.deliveryTimeText = deliveryTimeText;
    }

    public String getBuyerUUID() {
        return buyerUUID;
    }

    public void setBuyerUUID(String buyerUUID) {
        this.buyerUUID = buyerUUID;
    }

    public String getBuyCountry() {
        return buyCountry;
    }

    public void setBuyCountry(String buyCountry) {
        this.buyCountry = buyCountry;
    }
}
