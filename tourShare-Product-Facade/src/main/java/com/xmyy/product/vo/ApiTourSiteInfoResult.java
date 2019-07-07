package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("行程站点信息")
public class ApiTourSiteInfoResult implements Serializable {

    @ApiModelProperty(value="地区名称")
    private String regionName;

    @ApiModelProperty(value="是否签到（0-未签到；1-已签到）")
    private Integer isSignIn;

    @ApiModelProperty(value="计划出发时间")
    private Date planBeginTime;

    @ApiModelProperty(value="站点国旗url")
    private String regionLogo;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getIsSignIn() {
        return isSignIn;
    }

    public void setIsSignIn(Integer isSignIn) {
        this.isSignIn = isSignIn;
    }

    public Date getPlanBeginTime() {
        return planBeginTime;
    }

    public void setPlanBeginTime(Date planBeginTime) {
        this.planBeginTime = planBeginTime;
    }

    public String getRegionLogo() {
        return regionLogo;
    }

    public void setRegionLogo(String regionLogo) {
        this.regionLogo = regionLogo;
    }
}