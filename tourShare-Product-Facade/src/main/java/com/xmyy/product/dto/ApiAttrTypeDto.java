package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Simon on 2018/6/8.
 */
@ApiModel(value = "属性实体")
public class ApiAttrTypeDto implements Serializable {

    @ApiModelProperty(value = "属性类型ID")
    private Long attrTypeId;

    @ApiModelProperty(value = "属性类型名称")
    private String attrTypeName;

    @ApiModelProperty(value = "属性值")
    private List<ApiAttrValueDto> attrValue;

    public Long getAttrTypeId() {
        return attrTypeId;
    }

    public void setAttrTypeId(Long attrTypeId) {
        this.attrTypeId = attrTypeId;
    }

    public String getAttrTypeName() {
        return attrTypeName;
    }

    public void setAttrTypeName(String attrTypeName) {
        this.attrTypeName = attrTypeName;
    }

    public List<ApiAttrValueDto> getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(List<ApiAttrValueDto> attrValue) {
        this.attrValue = attrValue;
    }
}
