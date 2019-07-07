package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.support.Pagination;

import java.io.Serializable;

@ApiModel("行程验证列表数据")
public class ManageTourGetValidateResult implements Serializable {

    @ApiModelProperty("分页数据")
    private Pagination<ManageTourValidateDetailResult> pageData;

    @ApiModelProperty("审核统计")
    private ManageTourValidateCountResult countResult;

    public Pagination<ManageTourValidateDetailResult> getPageData() {
        return pageData;
    }

    public void setPageData(Pagination<ManageTourValidateDetailResult> pageData) {
        this.pageData = pageData;
    }

    public ManageTourValidateCountResult getCountResult() {
        return countResult;
    }

    public void setCountResult(ManageTourValidateCountResult countResult) {
        this.countResult = countResult;
    }
}
