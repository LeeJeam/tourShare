package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Simon on 2018/5/26.
 */
@ApiModel(value = "属性")
public class SeriesAttrDto implements Serializable {

    @ApiModelProperty(value = "属性类型ID")
    private Long id;

    @ApiModelProperty(value = "属性类型名称")
    private String name;

    @ApiModelProperty(value = "属性值列表")
    private List<AttrValueDto> attrValue;

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

    public List<AttrValueDto> getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(List<AttrValueDto> attrValue) {
        this.attrValue = attrValue;
    }
}
