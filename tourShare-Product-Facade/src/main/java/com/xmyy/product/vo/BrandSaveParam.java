package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "添加品牌参数")
public class BrandSaveParam implements Serializable {

    @ApiModelProperty(value = "品牌ID(新增时传空值或0)")
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "品牌名称")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "首字母")
    private String initial;

    @NotBlank
    @ApiModelProperty(value = "图标")
    private String logo;

    @NotBlank
    @ApiModelProperty(value = "英文名称")
    private String enName;

    @ApiModelProperty(value = "二级类目ID")
    private List<Long> categoryList2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public List<Long> getCategoryList2() {
        return categoryList2;
    }

    public void setCategoryList2(List<Long> categoryList2) {
        this.categoryList2 = categoryList2;
    }
}
