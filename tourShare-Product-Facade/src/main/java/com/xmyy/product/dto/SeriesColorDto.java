package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Simon on 2018/5/26.
 */
@ApiModel(value = "颜色项")
public class SeriesColorDto implements Serializable {

    @ApiModelProperty(value = "颜色名称")
    private String name;

    @ApiModelProperty(value = "颜色图片")
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
