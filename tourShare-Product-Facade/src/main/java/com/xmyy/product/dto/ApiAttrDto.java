package com.xmyy.product.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Simon on 2018/5/30.
 */
public class ApiAttrDto implements Serializable {

    @ApiModelProperty(value = "属性类型ID")
    private Long attrTypeId;

    @ApiModelProperty(value = "属性类型名称")
    private String attrTypeName;

    @ApiModelProperty(value = "属性值ID列表，多个ID以逗号分隔")
    private String attrValueIdStr;

    @ApiModelProperty(value = "属性值名称列表，多个名称以逗号分隔")
    private String attrValueNameStr;

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

    public String getAttrValueIdStr() {
        return attrValueIdStr;
    }

    public void setAttrValueIdStr(String attrValueIdStr) {
        this.attrValueIdStr = attrValueIdStr;
    }

    public String getAttrValueNameStr() {
        return attrValueNameStr;
    }

    public void setAttrValueNameStr(String attrValueNameStr) {
        this.attrValueNameStr = attrValueNameStr;
    }
}
