package com.xmyy.product.vo;

import com.xmyy.product.dto.PtAttrDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "商品管理返回参数",description = "商品管理返回参数")
public class ProductManagerResult  implements Serializable{

    @ApiModelProperty(value = "商品ID")
    private Long productId;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "商品编号")
    private String productNo;

    @ApiModelProperty(value = "种类")
    private String cateName;

    @ApiModelProperty(value = "类目")
    private String cateName2;

    @ApiModelProperty(value = "购买国家")
    private String buyRegion;

    private List<PtAttrDto> ptAttrDtoList;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCateName2() {
        return cateName2;
    }

    public void setCateName2(String cateName2) {
        this.cateName2 = cateName2;
    }

    public String getBuyRegion() {
        return buyRegion;
    }

    public void setBuyRegion(String buyRegion) {
        this.buyRegion = buyRegion;
    }

    public List<PtAttrDto> getPtAttrDtoList() {
        return ptAttrDtoList;
    }

    public void setPtAttrDtoList(List<PtAttrDto> ptAttrDtoList) {
        this.ptAttrDtoList = ptAttrDtoList;
    }

}
