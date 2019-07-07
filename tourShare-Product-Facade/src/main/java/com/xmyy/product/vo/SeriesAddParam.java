package com.xmyy.product.vo;

import com.xmyy.product.dto.SeriesAttrDto;
import com.xmyy.product.dto.SeriesColorDto;
import com.xmyy.product.dto.SeriesPriceDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "商品库商品添加参数")
public class SeriesAddParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "一级类目ID")
    private Long categoryId;

    @NotNull
    @ApiModelProperty(value = "二级类目ID")
    private Long categoryId2;

    @ApiModelProperty(value = "品牌ID")
    private Long brandId;

    @NotBlank
    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "别名")
    private String alias;

    @ApiModelProperty(value = "属性列表")
    private List<SeriesAttrDto> attrItem;

    @ApiModelProperty(value = "颜色列表")
    private List<SeriesColorDto> colorItem;

    @ApiModelProperty(value = "价格列表")
    private List<SeriesPriceDto> priceItem;

    private List<String> images;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCategoryId2() {
        return categoryId2;
    }

    public void setCategoryId2(Long categoryId2) {
        this.categoryId2 = categoryId2;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<SeriesAttrDto> getAttrItem() {
        return attrItem;
    }

    public void setAttrItem(List<SeriesAttrDto> attrItem) {
        this.attrItem = attrItem;
    }

    public List<SeriesColorDto> getColorItem() {
        return colorItem;
    }

    public void setColorItem(List<SeriesColorDto> colorItem) {
        this.colorItem = colorItem;
    }

    public List<SeriesPriceDto> getPriceItem() {
        return priceItem;
    }

    public void setPriceItem(List<SeriesPriceDto> priceItem) {
        this.priceItem = priceItem;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
