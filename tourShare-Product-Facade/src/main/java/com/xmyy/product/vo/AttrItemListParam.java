package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "查询属性列表参数")
public class AttrItemListParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "一级类目ID")
    private Long categoryId;

    @NotNull
    @ApiModelProperty(value = "二级类目ID")
    private Long categoryId2;

    @ApiModelProperty(value = "品牌ID")
    private Long brandId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCategoryId2() {
        return categoryId2;
    }

    public void setCategoryId2(Long categoryId2) {
        this.categoryId2 = categoryId2;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
