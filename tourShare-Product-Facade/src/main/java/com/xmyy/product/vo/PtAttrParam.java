package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "商品属性请求参数",description = "商品属性请求参数")
public class PtAttrParam implements Serializable{

    @ApiModelProperty(value = "属性值ID")
    private Integer ptAttrValueId;

    @ApiModelProperty(value = "属性类型ID")
    private Integer ptAttrTypeId;

    public Integer getPtAttrValueId() {
        return ptAttrValueId;
    }

    public void setPtAttrValueId(Integer ptAttrValueId) {
        this.ptAttrValueId = ptAttrValueId;
    }

    public Integer getPtAttrTypeId() {
        return ptAttrTypeId;
    }

    public void setPtAttrTypeId(Integer ptAttrTypeId) {
        this.ptAttrTypeId = ptAttrTypeId;
    }

}
