package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "商品置顶对象")
public class ManageProductTopParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "商品id", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "有效时长，分钟为单位", required = true)
    private Integer times;

    @ApiModelProperty(value = "置顶人ID", hidden = true)
    private Long updateBy;

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

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
