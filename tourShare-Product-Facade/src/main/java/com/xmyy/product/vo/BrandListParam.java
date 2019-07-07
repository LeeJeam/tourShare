package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@ApiModel(value = "品牌列表查询请求参数")
public class BrandListParam implements Serializable {

    @ApiModelProperty(value = "品牌名称")
    private String name;

    @ApiModelProperty(value = "首字母")
    private String initial;

    @ApiModelProperty(value = "二级类目ID")
    private Long categoryId2;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码，默认为1")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小，默认为10")
    private Integer size = 10;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public Long getCategoryId2() {
        return categoryId2;
    }

    public void setCategoryId2(Long categoryId2) {
        this.categoryId2 = categoryId2;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
