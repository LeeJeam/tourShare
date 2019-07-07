package com.xmyy.search.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@ApiModel(value = "商品搜索入参")
public class ProductSearchParam implements Serializable {

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码，默认1")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小，默认10")
    private Integer size = 10;

    @ApiModelProperty(value = "搜索框内容")
    private String keyword;

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
