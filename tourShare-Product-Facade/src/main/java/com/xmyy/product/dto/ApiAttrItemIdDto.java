package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Simon on 2018/6/25.
 */
@ApiModel(value = "属性ID对")
public class ApiAttrItemIdDto implements Serializable {

    @ApiModelProperty(value = "属性类型ID")
    private Long attrTypeId;

    @ApiModelProperty(value = "属性值ID")
    private Long attrValueId;

    public Long getAttrTypeId() {
        return attrTypeId;
    }

    public void setAttrTypeId(Long attrTypeId) {
        this.attrTypeId = attrTypeId;
    }

    public Long getAttrValueId() {
        return attrValueId;
    }

    public void setAttrValueId(Long attrValueId) {
        this.attrValueId = attrValueId;
    }
}
