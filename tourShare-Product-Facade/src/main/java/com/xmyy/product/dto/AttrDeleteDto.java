package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Simon on 2018/6/9.
 */
@ApiModel(value = "属性删除项实体")
public class AttrDeleteDto implements Serializable {

    @ApiModelProperty(value = "属性类型ID")
    private Long attrTypeId;

    @ApiModelProperty(value = "属性值ID列表")
    private List<Long> attrValueIds;

    public Long getAttrTypeId() {
        return attrTypeId;
    }

    public void setAttrTypeId(Long attrTypeId) {
        this.attrTypeId = attrTypeId;
    }

    public List<Long> getAttrValueIds() {
        return attrValueIds;
    }

    public void setAttrValueIds(List<Long> attrValueIds) {
        this.attrValueIds = attrValueIds;
    }
}
