package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "获取国家国旗入参")
public class ApiRegionFlagListParam implements Serializable {

    @ApiModelProperty(value="语言")
    private String language = "zh";

    @ApiModelProperty(value="区域层级")
    private Integer grade = 1;

    @ApiModelProperty(value="是否热门（0非热门，1热门）")
    private Integer isHot;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }
}