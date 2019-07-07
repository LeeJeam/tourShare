package com.xmyy.product.vo;

import com.xmyy.product.dto.ApiProductDetailDto;
import com.xmyy.product.dto.ApiProductSellerDto;
import com.xmyy.product.dto.ApiProductSimpleDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "商品详情实体")
public class ApiProductDetailResult implements Serializable {

    @ApiModelProperty(value = "商品")
    private ApiProductDetailDto product;

    @ApiModelProperty(value = "买手信息")
    private ApiProductSellerDto seller;

    @ApiModelProperty(value = "行程信息")
    private ApiTourShortInfoResult tour;

    private List<ApiProductSimpleDto> sellerProducts;

    public ApiProductDetailDto getProduct() {
        return product;
    }

    public void setProduct(ApiProductDetailDto product) {
        this.product = product;
    }

    public ApiProductSellerDto getSeller() {
        return seller;
    }

    public void setSeller(ApiProductSellerDto seller) {
        this.seller = seller;
    }

    public ApiTourShortInfoResult getTour() {
        return tour;
    }

    public void setTour(ApiTourShortInfoResult tour) {
        this.tour = tour;
    }

    public List<ApiProductSimpleDto> getSellerProducts() {
        return sellerProducts;
    }

    public void setSellerProducts(List<ApiProductSimpleDto> sellerProducts) {
        this.sellerProducts = sellerProducts;
    }
}
