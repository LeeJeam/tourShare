package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "需求列表")
public class DemandListResult implements Serializable {

    @ApiModelProperty( value = "需求单id")
    private Long id;

    @ApiModelProperty( value = "买家id")
    private Long buyerId;

    @ApiModelProperty( value = "需求单状态:1待接单，2待指定，3待发货，4待收货，5已完成，6下架失效")
    private Integer budgetStatus;

    @ApiModelProperty( value = "需求单状态:1待接单，2待指定，3待发货，4待收货，5已完成，6下架失效")
    private String budgetStatusText;

    @ApiModelProperty( value = "需求描述")
    private String demandDesc;

    @ApiModelProperty( value = "需求图片，可以多张")
    private String demandImages;

    @ApiModelProperty( value = "需求封面图")
    private String demandCover;

    @ApiModelProperty( value = "预算价格")
    private BigDecimal budgetPrice;

    @ApiModelProperty( value = "收货时间")
    private Date deliveryTime;

    @ApiModelProperty( value = "收货时间字符串")
    private String deliveryTimeText;

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

    public Integer getBudgetStatus() {
        return budgetStatus;
    }

    public void setBudgetStatus(Integer budgetStatus) {
        this.budgetStatus = budgetStatus;
    }

    public String getDemandDesc() {
        return demandDesc;
    }

    public void setDemandDesc(String demandDesc) {
        this.demandDesc = demandDesc;
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

    public BigDecimal getBudgetPrice() {
        return budgetPrice;
    }

    public void setBudgetPrice(BigDecimal budgetPrice) {
        this.budgetPrice = budgetPrice;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getBudgetStatusText() {
        return budgetStatusText;
    }

    public void setBudgetStatusText(String budgetStatusText) {
        this.budgetStatusText = budgetStatusText;
    }

    public String getDeliveryTimeText() {
        return deliveryTimeText;
    }

    public void setDeliveryTimeText(String deliveryTimeText) {
        this.deliveryTimeText = deliveryTimeText;
    }
}
