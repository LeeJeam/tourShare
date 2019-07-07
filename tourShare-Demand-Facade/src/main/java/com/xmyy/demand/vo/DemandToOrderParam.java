package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "指定买手生成订单参数")
public class DemandToOrderParam implements Serializable {

    @NotNull
    @ApiModelProperty( value = "需求ID", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty( value = "买手/背包客ID", required = true)
    private Long sellerId;

    @NotNull
    @ApiModelProperty( value = "是否背包客（0否，1是）", required = true)
    private Integer isPacker;

    @NotNull
    @ApiModelProperty( value = "行程ID", required = true)
    private Long tourId;

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }
}
