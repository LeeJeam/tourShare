package com.xmyy.product.vo;

import com.xmyy.product.dto.ApiTourSiteDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("今日行程消息")
public class ApiTourTodayResult implements Serializable {

    @ApiModelProperty(value = "行程id")
    private Long id;

    @ApiModelProperty(value = "首站名称")
    private String firstRegionName;

    @ApiModelProperty(value = "终点站名称")
    private String destRegionName;

    @ApiModelProperty(value = "首站时间")
    private Date firstTime;

    @ApiModelProperty(value = "返程时间")
    private Date returnTime;

    @ApiModelProperty(value = "即将前往的地区")
    private String nextRegionName;

    @ApiModelProperty(value = "会员的uuid 这里的会员指 买手 买家（背包客）")
    private String creatorUuid;

    @ApiModelProperty(value = "会员的登录设备")
    private Integer loginSource;

    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    @ApiModelProperty(value = "是不是背包客")
    private Integer isPacker;

    @ApiModelProperty(value = "行程站点", hidden = true)
    private List<ApiTourSiteDto> tourSites;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstRegionName() {
        return firstRegionName;
    }

    public void setFirstRegionName(String firstRegionName) {
        this.firstRegionName = firstRegionName;
    }

    public String getDestRegionName() {
        return destRegionName;
    }

    public void setDestRegionName(String destRegionName) {
        this.destRegionName = destRegionName;
    }

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getNextRegionName() {
        return nextRegionName;
    }

    public void setNextRegionName(String nextRegionName) {
        this.nextRegionName = nextRegionName;
    }

    public String getCreatorUuid() {
        return creatorUuid;
    }

    public void setCreatorUuid(String creatorUuid) {
        this.creatorUuid = creatorUuid;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }

    public Integer getLoginSource() {
        return loginSource;
    }

    public void setLoginSource(Integer loginSource) {
        this.loginSource = loginSource;
    }

    public List<ApiTourSiteDto> getTourSites() {
        return tourSites;
    }

    public void setTourSites(List<ApiTourSiteDto> tourSites) {
        this.tourSites = tourSites;
    }
}