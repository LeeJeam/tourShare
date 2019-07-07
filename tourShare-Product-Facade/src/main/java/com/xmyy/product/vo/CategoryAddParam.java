package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@ApiModel(value = "类目添加参数")
public class CategoryAddParam implements Serializable {

    @NotBlank
    @ApiModelProperty(value = "类目名称")
    private String name;

    @ApiModelProperty(value = "父类目ID，空值或0表示一级类目")
    private Long parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
