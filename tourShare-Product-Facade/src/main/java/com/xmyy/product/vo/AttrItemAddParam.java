package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@ApiModel(value = "类目属性新增参数")
public class AttrItemAddParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "一级类目ID")
    private Long categoryId;

    @NotNull
    @ApiModelProperty(value = "二级类目ID")
    private Long categoryId2;

    @ApiModelProperty(value = "品牌ID")
    private Long brandId;

    @NotBlank
    @ApiModelProperty(value = "属性类型")
    private String AttrType;

    @NotEmpty
    @ApiModelProperty(value = "属性值")
    private Set<String> AttrValue;

    @ApiModelProperty(value = "展示方式（0复选，1下拉）")
    private Integer showType;

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

    public String getAttrType() {
        return AttrType;
    }

    public void setAttrType(String attrType) {
        AttrType = attrType;
    }

    public Set<String> getAttrValue() {
        return AttrValue;
    }

    public void setAttrValue(Set<String> attrValue) {
        AttrValue = attrValue;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }
}
