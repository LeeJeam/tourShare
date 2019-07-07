package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("行程验证参数")
public class ManageTourValidateParam implements Serializable {

    @ApiModelProperty("行程ID")
    private Long id;

    @ApiModelProperty("行程审核结果（2：审核通过，3：审核不通过）")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
