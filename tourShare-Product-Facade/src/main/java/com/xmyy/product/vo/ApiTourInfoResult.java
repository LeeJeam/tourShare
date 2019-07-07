package com.xmyy.product.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ApiTourInfoResult implements Serializable {

    @ApiModelProperty(value="行程id")
    private Long id;

    @ApiModelProperty(value="行程编号", hidden = true)
    private String tourNo;

    @ApiModelProperty(value="行程的创建时间")
    private Date createTime;

    @ApiModelProperty(value ="行程的状态（0-已下架，1-未进行，2-进行中，3-已完成）")
    private Integer status;

    @ApiModelProperty(value="行程创建人ID")
    private Long createBy;

    @ApiModelProperty(value="是否历史行程（0-否，1-是")
    private Integer isHistory;

    @ApiModelProperty(value="行程站点信息")
    private List<ApiTourSiteInfoResult> tourSites;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public List<ApiTourSiteInfoResult> getTourSites() {
        return tourSites;
    }

    public void setTourSites(List<ApiTourSiteInfoResult> tourSites) {
        this.tourSites = tourSites;
    }

    public Integer getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(Integer isHistory) {
        this.isHistory = isHistory;
    }

    public String getTourNo() {
        return tourNo;
    }

    public void setTourNo(String tourNo) {
        this.tourNo = tourNo;
    }
}