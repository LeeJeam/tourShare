package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("行程站点信息出参")
public class ApiTourSiteDto implements Serializable {

    @ApiModelProperty(value="行程站点ID")
    private Long id;

    @ApiModelProperty(value="行程ID")
    private Long tourId;

    @ApiModelProperty(value="地区编号")
    private String regionCode;

    @ApiModelProperty(value="站点名称")
    private String regionName;

    @ApiModelProperty(value="站点图片")
    private String regionImgUrl;

    @ApiModelProperty(value="行程站点国旗url")
    private String regionLogo;

    @ApiModelProperty(value="是否已签到（0-未签到，1-已签到）")
    private Integer isSignIn;

    @ApiModelProperty(value="计划出发时间")
    private Date planBeginTime;

    @ApiModelProperty(value = "站点类型（0-起点站，1-首站，2-中途站，3-返程站）")
    private Integer siteType;

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

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionImgUrl() {
        return regionImgUrl;
    }

    public void setRegionImgUrl(String regionImgUrl) {
        this.regionImgUrl = regionImgUrl;
    }

    public Integer getIsSignIn() {
        return isSignIn;
    }

    public void setIsSignIn(Integer isSignIn) {
        this.isSignIn = isSignIn;
    }

    public Integer getSiteType() {
        return siteType;
    }

    public void setSiteType(Integer siteType) {
        this.siteType = siteType;
    }

    public String getRegionLogo() {
        return regionLogo;
    }

    public void setRegionLogo(String regionLogo) {
        this.regionLogo = regionLogo;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }
}