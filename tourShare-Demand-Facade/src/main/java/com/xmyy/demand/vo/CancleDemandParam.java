package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "买家取消需求参数")
public class CancleDemandParam implements Serializable {

    @NotNull
    @ApiModelProperty( value = "需求单id", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty( value = "取消原因", required = true)
    private Integer cancelReason;

    @ApiModelProperty( value = "取消原因文本", required = true)
    private String cancelText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(Integer cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getCancelText() {
        return cancelText;
    }

    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
    }
}
