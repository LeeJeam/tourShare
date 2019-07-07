package com.xmyy.product.vo;

import com.xmyy.product.dto.ApiBrandCategoryDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "品牌表格列表结果")
public class BrandListResult implements Serializable {

    @ApiModelProperty(value = "品牌ID")
    private Long id;

    @ApiModelProperty(value = "品牌编号")
    private String BrandNo;

    @ApiModelProperty(value = "品牌名称")
    private String name;

    @ApiModelProperty(value = "英文名称")
    private String enName;

    @ApiModelProperty(value = "首字母")
    private String initial;

    @ApiModelProperty(value = "图标")
    private String logo;

    private List<ApiBrandCategoryDto> brandCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandNo() {
        return BrandNo;
    }

    public void setBrandNo(String brandNo) {
        BrandNo = brandNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public List<ApiBrandCategoryDto> getBrandCategory() {
        return brandCategory;
    }

    public void setBrandCategory(List<ApiBrandCategoryDto> brandCategory) {
        this.brandCategory = brandCategory;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "BrandListResult{" +
                "id=" + id +
                ", BrandNo='" + BrandNo + '\'' +
                ", name='" + name + '\'' +
                ", enName='" + enName + '\'' +
                ", initial='" + initial + '\'' +
                ", logo='" + logo + '\'' +
                ", brandCategory=" + brandCategory +
                '}';
    }
}
