package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Simon on 2018/6/8.
 */
@ApiModel(value = "图片属性")
public class ApiColorDto implements Serializable {

    //对应图片属性表的id
    @ApiModelProperty(value = "颜色属性ID")
    private Long attrValueId;

    //对应图片属性表的name
    @ApiModelProperty(value = "颜色属性名称")
    private String attrValueName;

    @ApiModelProperty(value = "图片")
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
