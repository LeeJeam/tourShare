package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "商品列表请求参数")
public class ApiProductListParam implements Serializable {

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码，默认为1")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小，默认为10")
    private Integer size = 10;

    @ApiModelProperty(value = "一级类目ID")
    private Long categoryId;

    @ApiModelProperty(value = "购买地")
    private String buyRegion;

    @ApiModelProperty(value = "购买地国际码")
    private String buyRegionShortCode;

    @ApiModelProperty(value="大洲编码，当查询各大洲下的‘全部’时，传此参数，传此参数时不传buyRegionShortCode")
    private String continentCode;

    @Range(min = 0, max = 1)
    @ApiModelProperty(value = "上下架状态(空值表示全部，0下架，1上架)")
    private Integer isSale;

    @Range(min = 0, max = 6)
    @ApiModelProperty(value = "排序（0：不排序，1时间降，2时间升，3销量降，4销量升，5价格升，6价格降）")
    private Integer sort;

    @ApiModelProperty(value = "买手ID")
    private Long sellerId;

    @ApiModelProperty(value = "空值表示全部，1预售，2现货")
    private Integer productType;

    @ApiModelProperty(hidden = true)
    private List<String> shortCodes;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getIsSale() {
        return isSale;
    }

    public void setIsSale(Integer isSale) {
        this.isSale = isSale;
    }

    public String getBuyRegion() {
        return buyRegion;
    }

    public void setBuyRegion(String buyRegion) {
        this.buyRegion = buyRegion;
    }

    public String getBuyRegionShortCode() {
        return buyRegionShortCode;
    }

    public void setBuyRegionShortCode(String buyRegionShortCode) {
        this.buyRegionShortCode = buyRegionShortCode;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getContinentCode() {
        return continentCode;
    }

    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }

    public List<String> getShortCodes() {
        return shortCodes;
    }

    public void setShortCodes(List<String> shortCodes) {
        this.shortCodes = shortCodes;
    }
}
