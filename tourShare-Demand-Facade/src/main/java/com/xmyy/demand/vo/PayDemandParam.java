package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "支付需求入参")
public class PayDemandParam implements Serializable {

    @NotNull
    @ApiModelProperty( value = "需求id", required = true)
    private Long id;

    @ApiModelProperty(value = "支付日志ID")
    private String payLogId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayLogId() {
        return payLogId;
    }

    public void setPayLogId(String payLogId) {
        this.payLogId = payLogId;
    }
}
