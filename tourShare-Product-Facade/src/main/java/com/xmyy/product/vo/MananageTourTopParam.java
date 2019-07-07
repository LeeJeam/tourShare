package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel("行程置顶入参")
public class MananageTourTopParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "行程id", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "有效时长，分钟为单位", required = true)
    private Integer times;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}