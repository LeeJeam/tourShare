package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "后台需求单列表")
public class DemandListManageResult implements Serializable {

    @ApiModelProperty(value = "需求单ID")
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "需求图片，可以多张")
    private String demandImages;

    @ApiModelProperty(value = "需求描述")
    private String demandDesc;

    @ApiModelProperty(value = "需求数量")
    private Integer demandCount = 1;

    @ApiModelProperty(value = "预算价格")
    private BigDecimal budgetPrice;

    @ApiModelProperty(value = "完成时间")
    private Date deliveryTime;

    @ApiModelProperty(value = "支付状态")
    private Integer payStatus = 1;//   默认支付成功，支付不成功不生成需求单

    @ApiModelProperty(value = "支付状态")
    private String payStatusText = "支付成功";//   默认支付成功，支付不成功不生成需求单

    @ApiModelProperty(value = "需求单状态：1待接单，2待指定，3待发货，4待收货，5已完成，6下架失效")
    private Integer demandStatus;

    @ApiModelProperty(value = "需求单状态文本：1待接单，2待指定，3待发货，4待收货，5已完成，6下架失效")
    private String demandStatusText;

    @ApiModelProperty(value = "需求单号")
    private String demandNo;

    @ApiModelProperty(value = "行程单号")
    private String tourNo;

    @ApiModelProperty(value = "二次确认")
    private String secendConfirm;

    @ApiModelProperty(value = "鉴定寄送：0否，1是")
    private Integer isAppraisal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getDemandCount() {
        return demandCount;
    }

    public void setDemandCount(Integer demandCount) {
        this.demandCount = demandCount;
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

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getDemandStatus() {
        return demandStatus;
    }

    public void setDemandStatus(Integer demandStatus) {
        this.demandStatus = demandStatus;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
    }

    public String getTourNo() {
        return tourNo;
    }

    public void setTourNo(String tourNo) {
        this.tourNo = tourNo;
    }

    public Integer getIsAppraisal() {
        return isAppraisal;
    }

    public void setIsAppraisal(Integer isAppraisal) {
        this.isAppraisal = isAppraisal;
    }

    public String getDemandStatusText() {
        return demandStatusText;
    }
    public void setDemandStatusText(String demandStatusText) {
        this.demandStatusText = demandStatusText;
    }

    public String getSecendConfirm() {
        return secendConfirm;
    }

    public void setSecendConfirm(String secendConfirm) {
        this.secendConfirm = secendConfirm;
    }

    public String getPayStatusText() {
        return payStatusText;
    }

    public void setPayStatusText(String payStatusText) {
        this.payStatusText = payStatusText;
    }
}
