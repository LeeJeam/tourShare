package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Simon on 2018/5/25.
 */
@ApiModel(value = "属性值")
public class AttrValueDto implements Serializable {

    @ApiModelProperty(value = "属性值ID")
    private Long id;

    @ApiModelProperty(value = "属性值名称")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
