package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "买手发货入参")
public class DeliverGoodsParam implements Serializable {

    @NotNull(message = "订单ID不能为空")
    @ApiModelProperty(value = "订单ID", required = true)
    private Long orderId;

    @NotBlank(message = "收货人姓名不能为空")
    @ApiModelProperty(value = "收货人姓名", required = true)
    private String consigneeName;

    @NotBlank(message = "收货人电话不能为空")
    @ApiModelProperty(value = "收货人电话", required = true)
    private String consigneePhone;

    @NotBlank(message = "收货人地址不能为空")
    @ApiModelProperty(value = "收货人地址", required = true)
    private String consigneeAddress;

    @NotBlank(message = "物流单号不能为空")
    @ApiModelProperty(value = "物流单号", required = true)
    private String wayBillNo;

    @NotBlank(message = "物流公司代码不能为空")
    @ApiModelProperty(value = "物流公司代码", required = true)
    private String comCode;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getWayBillNo() {
        return wayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        this.wayBillNo = wayBillNo;
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

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }
}
