package com.xmyy.product.dto;

/**
 * Created by Simon on 2018/6/13.
 */
public class ProductStatisticDto {

    private Long categoryId;

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
