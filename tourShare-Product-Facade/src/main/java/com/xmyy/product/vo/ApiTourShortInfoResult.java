package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("程简略信息")
public class ApiTourShortInfoResult implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "行程编号")
    private String tourNo;

    @ApiModelProperty(value="标签")
    private String tags;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value ="行程的状态（0-已下架（失效状态）；1-未进行；2-进行中；3-已完成）")
    private Integer status;

    @ApiModelProperty(value="审核状态（0-待审核（默认），1-审核中，2-审核通过，3-审核失败，4-审核不通过）")
    private Integer auditStatus;

    @ApiModelProperty(value="首站名称")
    private String firstRegion;

    @ApiModelProperty(value="终点站")
    private String destRegion;

    @ApiModelProperty(value="行程站点数")
    private Integer siteCount;

    @ApiModelProperty(value="当前到达地区")
    private String curRegion;

    @ApiModelProperty(value="当前到达地区logo")
    private String curImgLogo;

    @ApiModelProperty(value="当前到达地区卡片")
    private String curImgUrl;

    @ApiModelProperty(value="首站时间")
    private Date firstTime;

    @ApiModelProperty(value="返程时间")
    private Date returnTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTourNo() {
        return tourNo;
    }

    public void setTourNo(String tourNo) {
        this.tourNo = tourNo;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getFirstRegion() {
        return firstRegion;
    }

    public void setFirstRegion(String firstRegion) {
        this.firstRegion = firstRegion;
    }

    public String getDestRegion() {
        return destRegion;
    }

    public void setDestRegion(String destRegion) {
        this.destRegion = destRegion;
    }

    public Integer getSiteCount() {
        return siteCount;
    }

    public void setSiteCount(Integer siteCount) {
        this.siteCount = siteCount;
    }

    public String getCurRegion() {
        return curRegion;
    }

    public void setCurRegion(String curRegion) {
        this.curRegion = curRegion;
    }

    public String getCurImgLogo() {
        return curImgLogo;
    }

    public void setCurImgLogo(String curImgLogo) {
        this.curImgLogo = curImgLogo;
    }

    public String getCurImgUrl() {
        return curImgUrl;
    }

    public void setCurImgUrl(String curImgUrl) {
        this.curImgUrl = curImgUrl;
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
}