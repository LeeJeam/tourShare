package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "根据区县编码获取街道信息入参")
public class ApiAreaJieDaoParam implements Serializable {

    @ApiModelProperty("区县编码")
    private String quxianCode;

    @ApiModelProperty("层级")
    private Integer level = 4;

    public String getQuxianCode() {
        return quxianCode;
    }

    public void setQuxianCode(String quxianCode) {
        this.quxianCode = quxianCode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}