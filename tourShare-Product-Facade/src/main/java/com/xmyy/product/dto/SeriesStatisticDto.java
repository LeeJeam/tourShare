package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Simon on 2018/7/9.
 */
@ApiModel(value = "系列统计DTO")
public class SeriesStatisticDto implements Serializable {

    @ApiModelProperty(value = "一级类目ID")
    private Long categoryId;

    @ApiModelProperty(value = "数量")
    private Integer num;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

}
