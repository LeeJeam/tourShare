package com.xmyy.circle.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "生活圈置顶对象")
public class CircleDownParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "id", required = true)
    private Long id;

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
}
