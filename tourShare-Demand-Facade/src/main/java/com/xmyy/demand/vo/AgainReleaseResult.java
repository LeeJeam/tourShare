package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "重新发布需求")
public class AgainReleaseResult implements Serializable {

    @ApiModelProperty(value = "买手ID,多个是逗号隔开")
    private String sellerIds;

    @ApiModelProperty(value = "购买国家地区")
    private Integer countryId;

    @ApiModelProperty(value = "购买国家名称")
    private String buyCountry;

    @ApiModelProperty(value = "需求图片，可以多张")
    private String demandImages;

    @ApiModelProperty(value = "需求描述")
    private String demandDesc;

    @ApiModelProperty(value = "最迟收货时间")
    private Date deliveryTime;

    @ApiModelProperty(value = "预算价格")
    private BigDecimal budgetPrice;

    @ApiModelProperty(value = "标签类型")
    private String productType;

    @ApiModelProperty(value = "收货人姓名")
    private String consigneeName;

    @ApiModelProperty(value = "联系电话快照")
    private String buyerPhone;

    @ApiModelProperty(value = "收货地址")
    private String consigneeAddress;

    @ApiModelProperty(value = "是否鉴定：0不鉴定，1鉴定")
    private Integer isAppraisal;

    public String getSellerIds() {
        return sellerIds;
    }

    public void setSellerIds(String sellerIds) {
        this.sellerIds = sellerIds;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getBuyCountry() {
        return buyCountry;
    }

    public void setBuyCountry(String buyCountry) {
        this.buyCountry = buyCountry;
    }

    public String getDemandImages() {
        return demandImages;
    }

    public void setDemandImages(String demandImages) {
        this.demandImages = demandImages;
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

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public Integer getIsAppraisal() {
        return isAppraisal;
    }

    public void setIsAppraisal(Integer isAppraisal) {
        this.isAppraisal = isAppraisal;
    }
}
