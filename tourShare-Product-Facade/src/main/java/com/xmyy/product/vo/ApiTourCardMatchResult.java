package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("行程卡片查询结果")
public class ApiTourCardMatchResult implements Serializable {

    @ApiModelProperty(value = "卡片编码")
    private String cardCode;

    @ApiModelProperty(value = "图片地址")
    private String imgUrl;

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}