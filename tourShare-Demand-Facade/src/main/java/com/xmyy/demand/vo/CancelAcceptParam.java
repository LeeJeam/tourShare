package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "买手取消接单入参")
public class CancelAcceptParam implements Serializable {

    @NotNull
    @ApiModelProperty( value = "需求单id", required = true)
    private Long id;

    @NotNull
    @Range(min = 1, max = 2)
    @ApiModelProperty( value = "是否背包客（0否，1是）", required = true)
    private Integer isPacker;

    @NotNull
    @ApiModelProperty( value = "取消原因", required = true)
    private Integer cancelReason;

    @NotBlank
    @ApiModelProperty( value = "取消原因文本", required = true)
    private String cancelText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
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
