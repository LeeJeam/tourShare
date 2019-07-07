package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@ApiModel(value = "后台需求单详情")
public class DemBackDetailResult implements Serializable {

    @ApiModelProperty(value = "需求单ID")
    private Long id;

    @ApiModelProperty(value = "需求品牌-标签")
    private String demandBrand;

    @ApiModelProperty(value = "需求描述")
    private String demandDesc;

    @ApiModelProperty(value = "需求图片")
    private String demandImages;

    @ApiModelProperty(value = "需求创建时间")
    private Date demandCreateTime;

    @ApiModelProperty(value = "需求价格")
    private BigDecimal budgetPrice;

    @ApiModelProperty(value = "需求单号")
    private String demandNo;

    @ApiModelProperty(value = "订单创建时间")
    private Date orderCreateTime;

    @ApiModelProperty(value = "订单有效时间")
    private Date deliveryTime;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "支付方式")
    private String payType;

    @ApiModelProperty(value = "受理人")
    private String acceptorName;

    @ApiModelProperty(value = "受理人ID")
    private Long acceptorId;

    @ApiModelProperty(value = "受理时间")
    private Date acceptTime;

    @ApiModelProperty(value = "确认时间")
    private Date confirmTime;

    @ApiModelProperty(value = "需求历史状态")
    private List<HashMap<String,Object>> demandHistoryLists;

    @ApiModelProperty(value = "行程单号")
    private String tourNo;

    @ApiModelProperty(value = "行程验证人")
    private String tourVerifier;

    @ApiModelProperty(value = "行程验证时间")
    private Date tourVeriTime;

    @ApiModelProperty(value = "启程时间")
    private Date tourStartTime;

    @ApiModelProperty(value = "返程时间")
    private Date tourEndTime;

    @ApiModelProperty(value = "登机牌")
    private String boardCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDemandBrand() {
        return demandBrand;
    }

    public void setDemandBrand(String demandBrand) {
        this.demandBrand = demandBrand;
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

    public Date getDemandCreateTime() {
        return demandCreateTime;
    }

    public void setDemandCreateTime(Date demandCreateTime) {
        this.demandCreateTime = demandCreateTime;
    }

    public BigDecimal getBudgetPrice() {
        return budgetPrice;
    }

    public void setBudgetPrice(BigDecimal budgetPrice) {
        this.budgetPrice = budgetPrice;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAcceptorName() {
        return acceptorName;
    }

    public void setAcceptorName(String acceptorName) {
        this.acceptorName = acceptorName;
    }

    public Long getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(Long acceptorId) {
        this.acceptorId = acceptorId;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public List<HashMap<String, Object>> getDemandHistoryLists() {
        return demandHistoryLists;
    }

    public void setDemandHistoryLists(List<HashMap<String, Object>> demandHistoryLists) {
        this.demandHistoryLists = demandHistoryLists;
    }

    public String getTourNo() {
        return tourNo;
    }

    public void setTourNo(String tourNo) {
        this.tourNo = tourNo;
    }

    public String getTourVerifier() {
        return tourVerifier;
    }

    public void setTourVerifier(String tourVerifier) {
        this.tourVerifier = tourVerifier;
    }

    public Date getTourVeriTime() {
        return tourVeriTime;
    }

    public void setTourVeriTime(Date tourVeriTime) {
        this.tourVeriTime = tourVeriTime;
    }

    public Date getTourStartTime() {
        return tourStartTime;
    }

    public void setTourStartTime(Date tourStartTime) {
        this.tourStartTime = tourStartTime;
    }

    public Date getTourEndTime() {
        return tourEndTime;
    }

    public void setTourEndTime(Date tourEndTime) {
        this.tourEndTime = tourEndTime;
    }

    public String getBoardCard() {
        return boardCard;
    }

    public void setBoardCard(String boardCard) {
        this.boardCard = boardCard;
    }
}
