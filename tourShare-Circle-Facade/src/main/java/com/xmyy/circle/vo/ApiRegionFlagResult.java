package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "获取国家国旗出参")
public class ApiRegionFlagResult implements Serializable {

    @ApiModelProperty(value="国家名称")
    private String cname;

    @ApiModelProperty(value="国家旗子")
    private String nationalFlag;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getNationalFlag() {
        return nationalFlag;
    }

    public void setNationalFlag(String nationalFlag) {
        this.nationalFlag = nationalFlag;
    }
}