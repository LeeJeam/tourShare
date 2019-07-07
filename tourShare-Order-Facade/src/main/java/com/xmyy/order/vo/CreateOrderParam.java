package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "订单结算时一张订单的参数")
public class CreateOrderParam implements Serializable {

    @ApiModelProperty(value = "订单结算商品列表", required = true)
    private List<ProductParam> productList;

    @NotNull(message = "买手ID不能为空")
    @ApiModelProperty(value = "买手ID", required = true)
    private Long sellerId;

    @NotNull(message = "配送方式不能为空")
    @ApiModelProperty(value = "配送方式（0国内寄送，1鉴定寄送）", required = true)
    private Integer isAppraisal;

    @NotNull(message = "鉴定费不能为空")
    @ApiModelProperty(value = "鉴定费", required = true)
    private BigDecimal appraisalPrice;

    @ApiModelProperty(value = "买家留言")
    private String buyerMessage;

    @NotNull(message = "商品总数不能为空")
    @ApiModelProperty(value = "订单商品总数", required = true)
    private Integer num;

    @NotNull(message = "订单实付款不能为空")
    @ApiModelProperty(value = "订单实付款（后台校验）", required = true)
    private BigDecimal payMoney;

    public List<ProductParam> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductParam> productList) {
        this.productList = productList;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getIsAppraisal() {
        return isAppraisal;
    }

    public void setIsAppraisal(Integer isAppraisal) {
        this.isAppraisal = isAppraisal;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getAppraisalPrice() {
        return appraisalPrice;
    }

    public void setAppraisalPrice(BigDecimal appraisalPrice) {
        this.appraisalPrice = appraisalPrice;
    }
}
