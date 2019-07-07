package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "订单下商品评价")
public class ProductEvaluateVo implements Serializable {

    @ApiModelProperty(value = "商品评价")
    private String productEvaluate;

    @ApiModelProperty(value = "商品评价图片")
    private String productEvaluateImages;

    public String getProductEvaluate() {
        return productEvaluate;
    }

    public void setProductEvaluate(String productEvaluate) {
        this.productEvaluate = productEvaluate;
    }

    public String getProductEvaluateImages() {
        return productEvaluateImages;
    }

    public void setProductEvaluateImages(String productEvaluateImages) {
        this.productEvaluateImages = productEvaluateImages;
    }
}
