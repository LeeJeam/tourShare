package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Simon on 2018/6/8.
 */
@ApiModel(value = "属性值")
public class ApiAttrValueDto implements Serializable {

    @ApiModelProperty(value = "属性值ID")
    private Long attrValueId;

    @ApiModelProperty(value = "属性值名称")
    private String attrValueName;

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

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof ApiAttrValueDto))
            return false;
        ApiAttrValueDto dto = (ApiAttrValueDto)obj;
        return attrValueId.equals(dto.getAttrValueId()) && attrValueName.equals(dto.getAttrValueName());
    }

    @Override
    public int hashCode() {
        int result = attrValueId.hashCode();
        result = 31 * result + attrValueName.hashCode();
        return result;
    }
}
