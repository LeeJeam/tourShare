package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("置顶行程信息")
public class ManageTourTopResult implements Serializable {

    @ApiModelProperty("行程ID")
    private Long id;

    @ApiModelProperty("行程编号（T开头+9位纯数字）")
    private String tourNo;

    @ApiModelProperty("首站地区")
    private String firstRegion;

    @ApiModelProperty("目的地")
    private String destRegion;

    @ApiModelProperty("返程地区")
    private String returnRegion;

    @ApiModelProperty("首站时间")
    private Date firstTime;

    @ApiModelProperty("返程时间")
    private Date returnTime;

    @ApiModelProperty("置顶人名字")
    private String toperName;

    @ApiModelProperty("置顶生效时间")
    private Date topBeginTime;

    @ApiModelProperty("置顶失效时间")
    private Date topEndTime;

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

    public String getDestRegion() {
        return destRegion;
    }

    public void setDestRegion(String destRegion) {
        this.destRegion = destRegion;
    }

    public String getReturnRegion() {
        return returnRegion;
    }

    public void setReturnRegion(String returnRegion) {
        this.returnRegion = returnRegion;
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

    public Date getTopBeginTime() {
        return topBeginTime;
    }

    public void setTopBeginTime(Date topBeginTime) {
        this.topBeginTime = topBeginTime;
    }

    public Date getTopEndTime() {
        return topEndTime;
    }

    public void setTopEndTime(Date topEndTime) {
        this.topEndTime = topEndTime;
    }

    public String getFirstRegion() {
        return firstRegion;
    }

    public void setFirstRegion(String firstRegion) {
        this.firstRegion = firstRegion;
    }

    public String getToperName() {
        return toperName;
    }

    public void setToperName(String toperName) {
        this.toperName = toperName;
    }
}
