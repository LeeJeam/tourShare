package com.xmyy.member.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "买手置顶对象")
public class SellerTopParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "有效时长（单位：min）", required = true)
    private Integer times;

    @JsonIgnore
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
