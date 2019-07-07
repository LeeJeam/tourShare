package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "行程查商品参数")
public class ApiProductListByTourIdParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "行程ID")
    private Long tourId;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码，默认为1")
    private Integer current=1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小，默认为10")
    private Integer size=10;

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
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
}
