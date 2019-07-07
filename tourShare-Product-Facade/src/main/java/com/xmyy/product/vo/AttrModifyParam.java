package com.xmyy.product.vo;

import com.xmyy.product.dto.AttrAddDto;
import com.xmyy.product.dto.AttrDeleteDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "类目属性修改参数")
public class AttrModifyParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "一级类目ID")
    private Long categoryId;

    @NotNull
    @ApiModelProperty(value = "二级类目ID")
    private Long categoryId2;

    @NotNull
    @ApiModelProperty(value = "品牌ID")
    private Long brandId;

    @ApiModelProperty(value = "新增属性")
    private List<AttrAddDto> addItem;

    @ApiModelProperty(value = "删除属性")
    private List<AttrDeleteDto> delItem;


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

    public List<AttrAddDto> getAddItem() {
        return addItem;
    }

    public void setAddItem(List<AttrAddDto> addItem) {
        this.addItem = addItem;
    }

    public List<AttrDeleteDto> getDelItem() {
        return delItem;
    }

    public void setDelItem(List<AttrDeleteDto> delItem) {
        this.delItem = delItem;
    }
}
