package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel("背包客个人主页进行中的行程")
public class ApiTourInfoInPackerIndexResult implements Serializable {

    @ApiModelProperty(value = "行程数统计显示文字")
    private Integer count;

    @ApiModelProperty(value = "最新进行中行程")
    private ApiTourInfoResult tour;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ApiTourInfoResult getTour() {
        return tour;
    }

    public void setTour(ApiTourInfoResult tour) {
        this.tour = tour;
    }
}