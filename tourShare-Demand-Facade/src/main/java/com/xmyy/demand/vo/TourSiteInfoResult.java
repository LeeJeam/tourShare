package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "行程站点信息")
public class TourSiteInfoResult implements Serializable {

    @ApiModelProperty(value = "行程站点ID")
    private Long id;

    @ApiModelProperty(value = "出发时间")
    private Date planBeginTime;

    @ApiModelProperty(value = "站点名称")
    private String regionName;

    @ApiModelProperty(value = "站点国旗")
    private String regionLogo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPlanBeginTime() {
        return planBeginTime;
    }

    public void setPlanBeginTime(Date planBeginTime) {
        this.planBeginTime = planBeginTime;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionLogo() {
        return regionLogo;
    }

    public void setRegionLogo(String regionLogo) {
        this.regionLogo = regionLogo;
    }
}
