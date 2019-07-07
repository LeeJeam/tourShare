package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@ApiModel(value = "商品详情里的评价请求参数")
public class EvaluateByProductIdParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "商品ID")
    private Long productId;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码，默认1")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小，默认10")
    private Integer size = 10;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
