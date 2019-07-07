package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Simon on 2018/6/2.
 */
@ApiModel(value = "商品SKU")
public class ApiSkuDto implements Serializable {

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "颜色属性ID")
    private Long colorAttrId;

    @ApiModelProperty(value = "属性列表")
    private List<ApiAttrItemDto> attrItem;

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

    public Long getColorAttrId() {
        return colorAttrId;
    }

    public void setColorAttrId(Long colorAttrId) {
        this.colorAttrId = colorAttrId;
    }

    public List<ApiAttrItemDto> getAttrItem() {
        return attrItem;
    }

    public void setAttrItem(List<ApiAttrItemDto> attrItem) {
        this.attrItem = attrItem;
    }
}
