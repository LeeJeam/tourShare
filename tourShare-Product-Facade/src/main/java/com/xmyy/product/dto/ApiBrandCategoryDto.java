package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Simon on 2018/6/5.
 */
@ApiModel(value = "品牌所属类目")
public class ApiBrandCategoryDto implements Serializable {

    @ApiModelProperty(value = "二级类目ID")
    private Long categoryId2;

    @ApiModelProperty(value = "二级类目名称")
    private String categoryName2;

    @ApiModelProperty(value = "商品数量")
    private Integer productCount;

    public Long getCategoryId2() {
        return categoryId2;
    }

    public void setCategoryId2(Long categoryId2) {
        this.categoryId2 = categoryId2;
    }

    public String getCategoryName2() {
        return categoryName2;
    }

    public void setCategoryName2(String categoryName2) {
        this.categoryName2 = categoryName2;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    @Override
    public String toString() {
        return "ApiBrandCategoryDto{" +
                "categoryId2=" + categoryId2 +
                ", categoryName2='" + categoryName2 + '\'' +
                ", productCount=" + productCount +
                '}';
    }
}
