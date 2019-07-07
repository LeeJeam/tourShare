package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.support.Pagination;

import java.io.Serializable;

@ApiModel("行程查询结果")
public class ManageTourQueryResult implements Serializable {

    @ApiModelProperty("行程信息")
    private Pagination<ManageTourDetailResult> pageination;

    @ApiModelProperty("当前分页总交易额")
    private Long amountCount;

    public Pagination<ManageTourDetailResult> getPageination() {
        return pageination;
    }

    public void setPageination(Pagination<ManageTourDetailResult> pageination) {
        this.pageination = pageination;
    }

    public Long getAmountCount() {
        return amountCount;
    }

    public void setAmountCount(Long amountCount) {
        this.amountCount = amountCount;
    }
}
