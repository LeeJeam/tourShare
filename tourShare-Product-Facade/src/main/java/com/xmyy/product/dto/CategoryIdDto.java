package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Simon on 2018/6/1.
 */
@ApiModel(value = "类目ID列表")
public class CategoryIdDto implements Serializable {

    @NotNull
    @ApiModelProperty(value = "一级类目ID")
    private Long categoryId;

    @NotNull
    @ApiModelProperty(value = "二级类目ID")
    private Long categoryId2;

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
}
