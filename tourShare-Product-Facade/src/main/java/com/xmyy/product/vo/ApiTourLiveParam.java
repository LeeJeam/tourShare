package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("行程直播入参")
public class ApiTourLiveParam implements Serializable {

    @ApiModelProperty("行程ID")
    private Long tourId;

    @ApiModelProperty("直播状态（0-未直播，1-直播中）")
    private Integer isLive;

    @ApiModelProperty("行程创建者ID")
    private Long createBy;

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public Integer getIsLive() {
        return isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
}