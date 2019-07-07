package com.xmyy.product.vo;

import com.xmyy.product.dto.ApiSkuIdDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "商品SKUS")
public class ApiSkuListResult extends ApiSkuIdDto {

    @ApiModelProperty(value = "SKU_ID")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
