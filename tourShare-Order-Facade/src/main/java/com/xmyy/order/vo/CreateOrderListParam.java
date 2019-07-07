package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "订单结算时的页面所有参数")
public class CreateOrderListParam implements Serializable {

    @NotEmpty
    @ApiModelProperty(value = "订单列表", required = true)
    private List<CreateOrderParam> orderList;

    @NotBlank(message = "收货人姓名不能为空")
    @ApiModelProperty(value = "收货人姓名", required = true)
    private String consigneeName;

    @NotBlank(message = "收货人电话不能为空")
    @ApiModelProperty(value = "收货人电话", required = true)
    private String consigneePhone;

    @NotBlank(message = "收货人地址不能为空")
    @ApiModelProperty(value = "收货人地址", required = true)
    private String consigneeAddress;

    @NotNull(message = "金额不能为空")
    @ApiModelProperty(value = "结算页合计金额（后台校验）", required = true)
    private BigDecimal payMoney;

    public List<CreateOrderParam> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<CreateOrderParam> orderList) {
        this.orderList = orderList;
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

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }
}
