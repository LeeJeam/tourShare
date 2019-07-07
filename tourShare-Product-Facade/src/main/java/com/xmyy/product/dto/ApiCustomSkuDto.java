package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Simon on 2018/7/4.
 */
@ApiModel(value = "自定义SKU入参")
public class ApiCustomSkuDto implements Serializable {

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "属性列表")
    private List<ApiCustomAttrItemDto> attrItem;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public List<ApiCustomAttrItemDto> getAttrItem() {
        return attrItem;
    }

    public void setAttrItem(List<ApiCustomAttrItemDto> attrItem) {
        this.attrItem = attrItem;
    }
}