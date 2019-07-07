package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "订单状态历史")
public class OrderHistoryVo implements Serializable {

    @ApiModelProperty(value = "操作时间字符串")
    private String updateTime;

    @ApiModelProperty(value = "操作内容")
    private String optContext;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getOptContext() {
        return optContext;
    }

    public void setOptContext(String optContext) {
        this.optContext = optContext;
    }
}
