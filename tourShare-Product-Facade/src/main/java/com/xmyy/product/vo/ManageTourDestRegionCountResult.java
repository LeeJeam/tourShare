package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("行程目的地统计信息")
public class ManageTourDestRegionCountResult implements Serializable {

    @ApiModelProperty("目的地名称")
    private String destRegionName;

    @ApiModelProperty("目的地编码")
    private String destRegionCode;

    @ApiModelProperty("统计数")
    private Integer count;

    public String getDestRegionName() {
        return destRegionName;
    }

    public void setDestRegionName(String destRegionName) {
        this.destRegionName = destRegionName;
    }

    public String getDestRegionCode() {
        return destRegionCode;
    }

    public void setDestRegionCode(String destRegionCode) {
        this.destRegionCode = destRegionCode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
