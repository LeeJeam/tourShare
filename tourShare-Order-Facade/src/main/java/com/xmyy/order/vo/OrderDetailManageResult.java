package com.xmyy.order.vo;

import com.xmyy.common.vo.ExpressItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "后台管理订单详情")
public class OrderDetailManageResult implements Serializable {

    //=============收货信息==================
    @ApiModelProperty(value = "收货地址")
    private String consigneeAddress;

    @ApiModelProperty(value = "收货人姓名")
    private String consigneeName;

    @ApiModelProperty(value = "收货人电话")
    private String consigneePhone;

    //=============卖家信息==================
    @ApiModelProperty(value = "卖家真实名字")
    private String realName;

    @ApiModelProperty(value = "卖家昵称")
    private String nickName;

    @ApiModelProperty(value = "联系电话")
    private String mobile;

    @ApiModelProperty(value = "是否自营（1是 自营（签约）买手，0否 大众买手）")
    private Integer isSelf;

    @ApiModelProperty(value = "认证状态（0提交认证，50认证审核通过，-50认证审核不通过）")
    private Integer realState;

    //=============订单信息==================
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单创建时间字符串")
    private String orderTime;

    @ApiModelProperty(value = "行程编号")
    private String tourNo;

    @ApiModelProperty(value = "交易编号")
    private String outTradeNo;

    @ApiModelProperty(value = "支付时间字符串")
    private String payTime;

    @ApiModelProperty(value = "支付方式")
    private String payMethod;

    @ApiModelProperty(value = "订单状态历史")
    private List<OrderHistoryVo> orderHistoryList;

    //=============物品详情==================
    @ApiModelProperty(value = "物品名称")
    private String productName;

    @ApiModelProperty(value = "物品种类")
    private String category;

    @ApiModelProperty(value = "物品规格")
    private String specParams;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    //=============物流信息==================
    @ApiModelProperty(value = "物流信息")
    private List<ExpressItem> expressInfo;

    //=============客服记录==================


    //=============评价信息==================
    @ApiModelProperty(value = "评价时间字符串")
    private String evaluateTime;

    @ApiModelProperty(value = "评价分数")
    private Integer productLevel;

    @ApiModelProperty(value = "订单下商品评价列表")
    private List<ProductEvaluateVo> productevaluatelist;

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }

    public Integer getRealState() {
        return realState;
    }

    public void setRealState(Integer realState) {
        this.realState = realState;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTourNo() {
        return tourNo;
    }

    public void setTourNo(String tourNo) {
        this.tourNo = tourNo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public List<OrderHistoryVo> getOrderHistoryList() {
        return orderHistoryList;
    }

    public void setOrderHistoryList(List<OrderHistoryVo> orderHistoryList) {
        this.orderHistoryList = orderHistoryList;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSpecParams() {
        return specParams;
    }

    public void setSpecParams(String specParams) {
        this.specParams = specParams;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ExpressItem> getExpressInfo() {
        return expressInfo;
    }

    public void setExpressInfo(List<ExpressItem> expressInfo) {
        this.expressInfo = expressInfo;
    }

    public String getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(String evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public Integer getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(Integer productLevel) {
        this.productLevel = productLevel;
    }

    public List<ProductEvaluateVo> getProductevaluatelist() {
        return productevaluatelist;
    }

    public void setProductevaluatelist(List<ProductEvaluateVo> productevaluatelist) {
        this.productevaluatelist = productevaluatelist;
    }
}
