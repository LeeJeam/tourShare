package com.xmyy.product.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xmyy.product.dto.UserAuthInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("买家端首页，轮播行程、更多行程列表")
public class ApiTourInBuyerIndexResult implements Serializable {

    @ApiModelProperty(value = "行程ID")
    private Long id;

    @ApiModelProperty(value = "行程编号")
    private String tourNo;

    @ApiModelProperty(value = "标签，多个逗号隔开")
    private String tags;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "审核状态（0-未上传，默认；1-未审核；2-审核通过；3-审核不通过）")
    private Integer auditStatus;

    @ApiModelProperty(value = "行程的状态（0-已下架，1-未进行，2-进行中，3-已完成）")
    private Integer status;

    @ApiModelProperty(value = "首站名称")
    private String firstRegionName;

    @ApiModelProperty(value = "目的站名称")
    private String destRegionName;

    @ApiModelProperty(value = "是否正在直播（0否，1是）")
    private Integer isLive;

    @ApiModelProperty(value = "首站时间")
    private Date firstTime;

    @ApiModelProperty(value = "返程时间")
    private Date returnTime;

    @ApiModelProperty(value = "行程站点数")
    private Integer siteCount;

    @ApiModelProperty(value = "当前到达地区名称")
    private String curRegionName;

    @ApiModelProperty(value = "当前到达地区编码", hidden = true)
    @JsonIgnore
    private String curRegionCode;

    @ApiModelProperty(value = "当前到达站点卡片url")
    private String curImgUrl;

    @ApiModelProperty(value = "是否背包客（0否，1是）")
    private Integer isPacker;

    @ApiModelProperty(value = "行程创建者id")
    private Long createBy;

    @ApiModelProperty(value = "用户认证信息")
    private UserAuthInfoDto userAuthInfo;

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

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
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

    public String getCurRegionName() {
        return curRegionName;
    }

    public void setCurRegionName(String curRegionName) {
        this.curRegionName = curRegionName;
    }

    public Integer getIsLive() {
        return isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
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

    public Integer getSiteCount() {
        return siteCount;
    }

    public void setSiteCount(Integer siteCount) {
        this.siteCount = siteCount;
    }

    public String getCurRegionCode() {
        return curRegionCode;
    }

    public void setCurRegionCode(String curRegionCode) {
        this.curRegionCode = curRegionCode;
    }

    public String getCurImgUrl() {
        return curImgUrl;
    }

    public void setCurImgUrl(String curImgUrl) {
        this.curImgUrl = curImgUrl;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public UserAuthInfoDto getUserAuthInfo() {
        return userAuthInfo;
    }

    public void setUserAuthInfo(UserAuthInfoDto userAuthInfo) {
        this.userAuthInfo = userAuthInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}