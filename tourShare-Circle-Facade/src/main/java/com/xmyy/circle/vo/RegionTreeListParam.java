package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("查询区域入参")
public class RegionTreeListParam implements Serializable {

    @ApiModelProperty(value="父亲地区代码")
    private String parentcode;

    @ApiModelProperty(value="地区代码")
    private String ccode;

    @ApiModelProperty(value="地区名称")
    private String cname;

    @ApiModelProperty(value="最小区域层级")
    private Integer minGrade = 0;

    @ApiModelProperty(value="菜单的叶子的展开状态")
    private String status;

    @ApiModelProperty(value="最大区域层级")
    private Integer maxGrade;

    @ApiModelProperty(value="语言")
    private String language="zh";

    @ApiModelProperty(value="区域层级")
    private Integer grade;

    @ApiModelProperty(value="是不是热门，0-不热门，1-热门")
    private Integer isHot;

    @ApiModelProperty(value="显示的风格0：列表风格 1：洲-国-市 2：字母-国-市 3：省-市-区/县")
    private Integer showStyle = 0;

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Integer getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(Integer minGrade) {
        this.minGrade = minGrade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(Integer maxGrade) {
        this.maxGrade = maxGrade;
    }

    public Integer getShowStyle() {
        return showStyle;
    }

    public void setShowStyle(Integer showStyle) {
        this.showStyle = showStyle;
    }

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

    public String getParentcode() {
        return parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }
}