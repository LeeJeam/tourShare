package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "订单下的商品信息")
public class GoodsResult implements Serializable {

    @ApiModelProperty(value = "商品类型（1预售；2现货；3需求）")
    private Integer productType;

    @ApiModelProperty(value = "商品ID（如果为预售和现货id为商品id，如果为需求为需求单id）")
    private Long productId;

    @ApiModelProperty(value = "商品订单ID（预售、现货为商品订单ID，需求为需求单ID）")
    private Long productOrderId;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "商品封面图")
    private String productCover;

    @ApiModelProperty(value = "商品名称/需求描述")
    private String title;

    @ApiModelProperty(value = "规格属性")
    private String specParams;

    @ApiModelProperty(value = "需求收货时限")
    private Date deliveryTime;

    @ApiModelProperty(value = "需求收货时限字符串")
    private String deliveryTimeString;

    @ApiModelProperty(value = "购买数量")
    private Integer productNum;

    @ApiModelProperty(value = "小计金额")
    private BigDecimal subtotal;

    @ApiModelProperty(value = "售后ID，没有申请售后则为null")
    private Long orderReturnId;

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductCover() {
        return productCover;
    }

    public void setProductCover(String productCover) {
        this.productCover = productCover;
    }

    public String getSpecParams() {
        return specParams;
    }

    public void setSpecParams(String specParams) {
        this.specParams = specParams;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getDeliveryTimeString() {
        return deliveryTimeString;
    }

    public void setDeliveryTimeString(String deliveryTimeString) {
        this.deliveryTimeString = deliveryTimeString;
    }

    public Long getOrderReturnId() {
        return orderReturnId;
    }

    public void setOrderReturnId(Long orderReturnId) {
        this.orderReturnId = orderReturnId;
    }

    public Long getProductOrderId() {
        return productOrderId;
    }

    public void setProductOrderId(Long productOrderId) {
        this.productOrderId = productOrderId;
    }
}
