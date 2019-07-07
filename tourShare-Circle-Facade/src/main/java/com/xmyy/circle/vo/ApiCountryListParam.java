package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "国家列表显示参数")
public class ApiCountryListParam implements Serializable {

    @ApiModelProperty(value = "是否包含热门国家")
    private Integer isContainHot;

    @ApiModelProperty(value="展示格式（0-字母下面是国家列表，1-国家列表）")
    private Integer showStyle;

    public Integer getIsContainHot() {
        return isContainHot;
    }

    public void setIsContainHot(Integer isContainHot) {
        this.isContainHot = isContainHot;
    }

    public Integer getShowStyle() {
        return showStyle;
    }

    public void setShowStyle(Integer showStyle) {
        this.showStyle = showStyle;
    }
}