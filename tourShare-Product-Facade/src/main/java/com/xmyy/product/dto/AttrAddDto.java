package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Simon on 2018/6/9.
 */
@ApiModel(value = "属性增加项实体")
public class AttrAddDto implements Serializable {

    @ApiModelProperty(value = "属性类型ID")
    private Long attrTypeId;

    @ApiModelProperty(value = "属性名称列表")
    private List<String> attrValueNames;

    public Long getAttrTypeId() {
        return attrTypeId;
    }

    public void setAttrTypeId(Long attrTypeId) {
        this.attrTypeId = attrTypeId;
    }

    public List<String> getAttrValueNames() {
        return attrValueNames;
    }

    public void setAttrValueNames(List<String> attrValueNames) {
        this.attrValueNames = attrValueNames;
    }
}
