package com.xmyy.product.vo;

import com.xmyy.product.dto.AttrValueDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "属性与属性值")
public class AttrTypeAndValueListResult implements Serializable {

    @ApiModelProperty(value = "属性类型ID")
    private Long id;

    @ApiModelProperty(value = "属性类型名称")
    private String name;

    @ApiModelProperty(value = "展示方式（0复选，1下拉）")
    private Integer showType;

    @ApiModelProperty(value = "属性值")
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

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public List<AttrValueDto> getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(List<AttrValueDto> attrValue) {
        this.attrValue = attrValue;
    }
}
