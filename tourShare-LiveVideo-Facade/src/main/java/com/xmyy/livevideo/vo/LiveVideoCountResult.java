package com.xmyy.livevideo.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class LiveVideoCountResult implements Serializable {

    @ApiModelProperty(value = "观看数")
    private Integer lookNumber;

    public Integer getLookNumber() {
        return lookNumber;
    }

    public void setLookNumber(Integer lookNumber) {
        this.lookNumber = lookNumber;
    }
}
