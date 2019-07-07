package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value = "需求详情")
public class DemandDetailResult implements Serializable {

    @ApiModelProperty(value = "需求单id")
    private Long id;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "需求单编号")
    private String demandNo;

    @ApiModelProperty(value = "买家ID")
    private Long buyerId;

    @ApiModelProperty(value = "买家昵称")
    private String buyerNickname;

    @ApiModelProperty(value = "买家头像")
    private String buyerImage;

    @ApiModelProperty(value = "发布时间")
    private Date demandTime;

    @ApiModelProperty(value = "发布时间字符串")
    private String demandTimeText;

    @ApiModelProperty(value = "发布地点")
    private String demandPosition;

    @ApiModelProperty(value = "购买国家名称")
    private String buyCountry;

    @ApiModelProperty(value = "需求图片，可以多张")
    private String demandImages;

    @ApiModelProperty(value = "需求描述")
    private String demandDesc;

    @ApiModelProperty(value = "最迟收货时间")
    private Date deliveryTime;

    @ApiModelProperty(value = "最迟收货时间字符串")
    private String deliveryTimeText;

    @ApiModelProperty(value = "预算价格")
    private BigDecimal budgetPrice;

    @ApiModelProperty(value = "是否添加辛苦费（0否；1是）")
    private Integer isToilsome;

    @ApiModelProperty(value = "辛苦费")
    private BigDecimal toilsomePrice;

    @ApiModelProperty(value = "需求单状态")
    private Integer budgetStatus;

    @ApiModelProperty(value = "需求单状态文字")
    private String budgetStatusText;

    @ApiModelProperty(value = "一个需求单最多10个买手接需求，超过不允许接单")
    private Integer acceptedNumber;

    @ApiModelProperty(value = "已接单人头像")
    private List<String> sellerImage;

    @ApiModelProperty(value = "买家UUID")
    private String buyerUUID;

    @ApiModelProperty(value = "买手UUID")
    private String sellerUUID;

    @ApiModelProperty(value = "收货人")
    private String consigneeName;

    @ApiModelProperty(value = "收货电话")
    private String buyerPhone;

    @ApiModelProperty(value = "收货地址")
    private String consigneeAddress;

    @ApiModelProperty(value = "是否背包客（0否，1是）")
    private Integer isPacker;

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

    public String getBuyerNickname() {
        return buyerNickname;
    }

    public void setBuyerNickname(String buyerNickname) {
        this.buyerNickname = buyerNickname;
    }

    public String getBuyerImage() {
        return buyerImage;
    }

    public void setBuyerImage(String buyerImage) {
        this.buyerImage = buyerImage;
    }

    public Date getDemandTime() {
        return demandTime;
    }

    public void setDemandTime(Date demandTime) {
        this.demandTime = demandTime;
    }

    public String getDemandPosition() {
        return demandPosition;
    }

    public void setDemandPosition(String demandPosition) {
        this.demandPosition = demandPosition;
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

    public Integer getIsToilsome() {
        return isToilsome;
    }

    public void setIsToilsome(Integer isToilsome) {
        this.isToilsome = isToilsome;
    }

    public BigDecimal getToilsomePrice() {
        return toilsomePrice;
    }

    public void setToilsomePrice(BigDecimal toilsomePrice) {
        this.toilsomePrice = toilsomePrice;
    }

    public Integer getBudgetStatus() {
        return budgetStatus;
    }

    public void setBudgetStatus(Integer budgetStatus) {
        this.budgetStatus = budgetStatus;
    }

    public Integer getAcceptedNumber() {
        return acceptedNumber;
    }

    public void setAcceptedNumber(Integer acceptedNumber) {
        this.acceptedNumber = acceptedNumber;
    }

    public List<String> getSellerImage() {
        return sellerImage;
    }

    public void setSellerImage(List<String> sellerImage) {
        this.sellerImage = sellerImage;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
    }

    public String getBudgetStatusText() {
        return budgetStatusText;
    }

    public void setBudgetStatusText(String budgetStatusText) {
        this.budgetStatusText = budgetStatusText;
    }

    public String getDemandTimeText() {
        return demandTimeText;
    }

    public void setDemandTimeText(String demandTimeText) {
        this.demandTimeText = demandTimeText;
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

    public String getSellerUUID() {
        return sellerUUID;
    }

    public void setSellerUUID(String sellerUUID) {
        this.sellerUUID = sellerUUID;
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

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }
}
