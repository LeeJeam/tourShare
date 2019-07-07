package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Simon on 2018/6/25.
 */
@ApiModel(value = "skuID实体")
public class ApiSkuIdDto implements Serializable {

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "颜色属性ID")
    private Long colorAttrId;

    @ApiModelProperty(value = "颜色属性名称")
    private String colorAttrName;

    @ApiModelProperty(value = "属性列表(MAP的key为属性类型ID,MAP的value为属性类型ID和属性值ID)")
    private Map<Long,ApiAttrItemDto> attrItem;

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

    public String getColorAttrName() {
        return colorAttrName;
    }

    public void setColorAttrName(String colorAttrName) {
        this.colorAttrName = colorAttrName;
    }

    public void setColorAttrId(Long colorAttrId) {
        this.colorAttrId = colorAttrId;
    }

    public Map<Long, ApiAttrItemDto> getAttrItem() {
        return attrItem;
    }

    public void setAttrItem(Map<Long, ApiAttrItemDto> attrItem) {
        this.attrItem = attrItem;
    }
}
