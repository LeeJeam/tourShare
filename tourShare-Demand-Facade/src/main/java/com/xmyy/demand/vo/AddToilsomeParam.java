package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "添加辛苦费入参")
public class AddToilsomeParam implements Serializable {

    @NotNull
    @ApiModelProperty( value = "需求ID", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty( value = "辛苦费金额", required = true)
    private BigDecimal payPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

}
