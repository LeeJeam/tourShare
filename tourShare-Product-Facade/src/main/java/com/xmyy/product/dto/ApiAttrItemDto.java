package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Simon on 2018/5/30.
 */
@ApiModel(value = "属性对实体")
public class ApiAttrItemDto implements Serializable {

    @ApiModelProperty(value = "属性类型ID")
    private Long attrTypeId;

    @ApiModelProperty(value = "属性类型名称")
    private String attrTypeName;

    @ApiModelProperty(value = "属性值ID")
    private Long attrValueId;

    @ApiModelProperty(value = "属性值名称")
    private String attrValueName;

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

    public Long getAttrValueId() {
        return attrValueId;
    }

    public void setAttrValueId(Long attrValueId) {
        this.attrValueId = attrValueId;
    }

    public String getAttrValueName() {
        return attrValueName;
    }

    public void setAttrValueName(String attrValueName) {
        this.attrValueName = attrValueName;
    }
}
