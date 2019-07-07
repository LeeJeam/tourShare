package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by yeyu on 2018/6/19.
 * 描述：
 */
@ApiModel(value = "商品系列列表附加属性",description = "商品系列列表附加属性")
public class PtSeriesAttrDto implements Serializable{
    @ApiModelProperty(value = "属性类型ID")
    private Long attrTypeId;
    @ApiModelProperty(value = "属性类型名称")
    private String attrTypeName;
    @ApiModelProperty(value = "属性值ID串")
    private String attrValueIdStr;
    @ApiModelProperty(value = "属性值名称串")
    private String attrValueNameStr;
    @ApiModelProperty(value = "系列ID")
    private Long ptSeriesId;

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

    public Long getPtSeriesId() {
        return ptSeriesId;
    }

    public void setPtSeriesId(Long ptSeriesId) {
        this.ptSeriesId = ptSeriesId;
    }

    @Override
    public String toString() {
        return "PtSeriesAttrDto{" +
                "attrTypeId=" + attrTypeId +
                ", attrTypeName='" + attrTypeName + '\'' +
                ", attrValueIdStr='" + attrValueIdStr + '\'' +
                ", attrValueNameStr='" + attrValueNameStr + '\'' +
                ", ptSeriesId=" + ptSeriesId +
                '}';
    }
}
