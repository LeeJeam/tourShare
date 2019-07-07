package com.xmyy.product.vo;

import com.xmyy.product.dto.ApiAttrTypeDto;
import com.xmyy.product.dto.ApiColorDto;
import com.xmyy.product.dto.ApiPriceDto;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "系列/商品属性列表")
public class ApiAttrListResult implements Serializable {

    private List<ApiAttrTypeDto> attr;

    private List<ApiColorDto> colorAttr;

    private List<ApiPriceDto> priceAttr;

    public List<ApiAttrTypeDto> getAttr() {
        return attr;
    }

    public void setAttr(List<ApiAttrTypeDto> attr) {
        this.attr = attr;
    }

    public List<ApiColorDto> getColorAttr() {
        return colorAttr;
    }

    public void setColorAttr(List<ApiColorDto> colorAttr) {
        this.colorAttr = colorAttr;
    }

    public List<ApiPriceDto> getPriceAttr() {
        return priceAttr;
    }

    public void setPriceAttr(List<ApiPriceDto> priceAttr) {
        this.priceAttr = priceAttr;
    }
}
