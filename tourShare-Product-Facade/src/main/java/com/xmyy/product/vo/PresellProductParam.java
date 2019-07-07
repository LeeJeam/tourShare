package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@ApiModel(value = "首页最新预售参数")
public class PresellProductParam implements Serializable {

    @ApiModelProperty(value = "购买地国家码")
    private String BuyRegionShortCode;

    @ApiModelProperty(value = "买家ID")
    private Long buyerId;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码，默认为1")
    private Integer current;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小，默认为10")
    private Integer size;

    public String getBuyRegionShortCode() {
        return BuyRegionShortCode;
    }

    public void setBuyRegionShortCode(String buyRegionShortCode) {
        BuyRegionShortCode = buyRegionShortCode;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }
}
