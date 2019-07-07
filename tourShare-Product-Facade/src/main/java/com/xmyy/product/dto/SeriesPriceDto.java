package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Simon on 2018/5/26.
 */
@ApiModel(value = "价格项")
public class SeriesPriceDto implements Serializable {

    @ApiModelProperty(value = "国家地区")
    private String region;

    @ApiModelProperty(value = "价格单位")
    private String unit;

    @ApiModelProperty(value = "店铺列表")
    private List<Shop> shops;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    public class Shop implements Serializable{

        @ApiModelProperty(value = "店铺")
        private String shop;

        @ApiModelProperty(value = "价格")
        private BigDecimal price;

        public String getShop() {
            return shop;
        }

        public void setShop(String shop) {
            this.shop = shop;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

    }
}
