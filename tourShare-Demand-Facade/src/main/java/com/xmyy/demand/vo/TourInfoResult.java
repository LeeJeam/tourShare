package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "行程信息")
public class TourInfoResult implements Serializable {

    @ApiModelProperty(value = "行程ID")
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "行程编号")
    private String tourNo;

    @ApiModelProperty(value = "站点List信息")
    private List<TourSiteInfoResult> tourSites;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTourNo() {
        return tourNo;
    }

    public void setTourNo(String tourNo) {
        this.tourNo = tourNo;
    }

    public List<TourSiteInfoResult> getTourSites() {
        return tourSites;
    }

    public void setTourSites(List<TourSiteInfoResult> tourSites) {
        this.tourSites = tourSites;
    }
}
