package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "订单结算中商品的参数")
public class ProductParam implements Serializable {

    @NotNull(message = "商品ID不能为空")
    @ApiModelProperty(value = "商品ID", required = true)
    private Long productId;

    @NotNull(message = "商品SKU ID不能为空")
    @ApiModelProperty(value = "商品SKU ID", required = true)
    private Long skuId;

    @NotBlank(message = "商品规格属性不能为空")
    @ApiModelProperty(value = "商品规格属性", required = true)
    private String specParams;

    @NotBlank(message = "购买商品数量不能为空")
    @ApiModelProperty(value = "购买商品数量", required = true)
    private Integer productNum;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSpecParams() {
        return specParams;
    }

    public void setSpecParams(String specParams) {
        this.specParams = specParams;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

}
