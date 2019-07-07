package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "发布需求入参")
public class AddDemandParam implements Serializable {

    @ApiModelProperty(value = "指定买手ID,多个逗号隔开")
    private String sellerIds;

    @NotBlank(message = "购买地名称不能为空")
    @ApiModelProperty(value = "购买地名称", required = true)
    private String buyCountry;

    @NotBlank(message = "国际码不能为空")
    @ApiModelProperty(value = "购买地国际码", required = true)
    private String shortCode;

    @NotBlank(message = "需求图片不能为空")
    @ApiModelProperty(value = "需求图片，多张逗号分割", required = true)
    private String demandImages;

    @NotBlank(message = "需求描述不能为空")
    @ApiModelProperty(value = "需求描述", required = true)
    private String demandDesc;

    @NotNull(message = "收货时限不能为空")
    @ApiModelProperty(value = "最迟收货时间", required = true)
    private Date deliveryTime;

    @NotNull(message = "预算价格不能为空")
    @ApiModelProperty(value = "预算价格", required = true)
    private BigDecimal budgetPrice;

    @NotBlank(message = "类型不能为空")
    @ApiModelProperty(value = "标签类型", required = true)
    private String productType;

    @NotBlank(message = "收货人姓名不能为空")
    @ApiModelProperty(value = "收货人姓名", required = true)
    private String consigneeName;

    @NotBlank(message = "联系电话不能为空")
    @ApiModelProperty(value = "联系电话", required = true)
    private String buyerPhone;

    @NotBlank(message = "收货地址不能为空")
    @ApiModelProperty(value = "收货地址", required = true)
    private String consigneeAddress;

    @ApiModelProperty(value = "发布地点")
    private String releaseAddress;

    @ApiModelProperty(value = "是否鉴定（0不鉴定，1鉴定）", required = true)
    private Integer isAppraisal;

    public String getSellerIds() {
        return sellerIds;
    }

    public void setSellerIds(String sellerIds) {
        this.sellerIds = sellerIds;
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

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
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

    public String getReleaseAddress() {
        return releaseAddress;
    }

    public void setReleaseAddress(String releaseAddress) {
        this.releaseAddress = releaseAddress;
    }
}
