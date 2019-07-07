package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "取消订单入参")
public class CancelOrderParam implements Serializable {

    @NotNull(message = "订单ID不能为空")
    @ApiModelProperty(value = "订单ID", required = true)
    private Long orderId;

    @NotNull(message = "取消原因不能为空")
    @ApiModelProperty(value = "取消原因（1我不想买了；2信息填写错误，重新拍；3买手缺货；4其他）", required = true)
    private Integer cancelReason;

    public CancelOrderParam() {
    }

    public CancelOrderParam(Long orderId, Integer cancelReason) {
        this.orderId = orderId;
        this.cancelReason = cancelReason;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(Integer cancelReason) {
        this.cancelReason = cancelReason;
    }
}
