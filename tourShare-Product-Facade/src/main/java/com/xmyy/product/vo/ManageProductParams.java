package com.xmyy.product.vo;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class ManageProductParams implements Serializable{

    @ApiModelProperty(value = "商品ID")
    private String productIds;

    @ApiModelProperty(value = "一级目录ID")
    private Integer categoryId;

    @ApiModelProperty(value = "二级目录ID")
    private Integer categoryId2;

    @ApiModelProperty(value = "品牌ID")
    private Integer brandId;

    @ApiModelProperty(value = "属性集合")
    private List<PtAttrParam> ptAttrParams;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码，默认为1")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小，默认为10")
    private Integer size = 10;

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

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId2() {
        return categoryId2;
    }

    public void setCategoryId2(Integer categoryId2) {
        this.categoryId2 = categoryId2;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public List<PtAttrParam> getPtAttrParams() {
        return ptAttrParams;
    }

    public void setPtAttrParams(List<PtAttrParam> ptAttrParams) {
        this.ptAttrParams = ptAttrParams;
    }
}
