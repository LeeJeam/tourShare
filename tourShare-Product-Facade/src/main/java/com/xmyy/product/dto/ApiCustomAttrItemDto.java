package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Simon on 2018/7/4.
 */
@ApiModel(value = "自定义属性入参")
public class ApiCustomAttrItemDto implements Serializable {

    @ApiModelProperty(value = "属性类型ID")
    private Long attrTypeId;

    @ApiModelProperty(value = "属性值名称")
    private String attrValueName;

    public Long getAttrTypeId() {
        return attrTypeId;
    }

    public void setAttrTypeId(Long attrTypeId) {
        this.attrTypeId = attrTypeId;
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
        if(!(obj instanceof ApiCustomAttrItemDto))
            return false;
        ApiCustomAttrItemDto dto = (ApiCustomAttrItemDto)obj;
        return attrTypeId.equals(dto.getAttrTypeId()) && attrValueName.equals(dto.getAttrValueName());
    }

    @Override
    public int hashCode() {
        int result = attrTypeId.hashCode();
        result = 31 * result + attrValueName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ApiCustomAttrItemDto{" +
                "attrTypeId=" + attrTypeId +
                ", attrValueName='" + attrValueName + '\'' +
                '}';
    }
}