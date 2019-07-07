package com.xmyy.cert.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class MemberCertUploadResult implements Serializable {

    @ApiModelProperty(value = "图片上次路径")
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
