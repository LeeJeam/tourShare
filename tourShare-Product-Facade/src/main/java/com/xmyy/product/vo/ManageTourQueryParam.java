package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;
import java.io.Serializable;

@ApiModel("管理端-行程查询入参")
public class ManageTourQueryParam implements Serializable {

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty("分页页码，默认1")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty("页大小，默认10")
    private Integer size = 10;

    @ApiModelProperty("行程编号")
    private String tourNo;

    @ApiModelProperty("行程的状态（0-已下架，1-未进行，2-进行中，3-已完成）")
    private Integer status;

    @ApiModelProperty("行程开始时间")
    private Long timingStartTime;

    @ApiModelProperty("行程结束时间")
    private Long timingEndTime;

    @ApiModelProperty("最小订单量")
    private Integer minOrderCount;

    @ApiModelProperty("最大订单量")
    private Integer maxOrderCount;

    @ApiModelProperty("是否直播(0-未直播；1-直播中)")
    private Integer isLive;

    @ApiModelProperty("目的地")
    private String destRegion;

    @ApiModelProperty("审核状态（0-无证件（默认），1-未审核，2-审核通过，3-审核不通过）")
    private Integer auditStatus;

    @ApiModelProperty("标签")
    private String tags;

    @ApiModelProperty("用户名真实姓名")
    private String userName;

    @ApiModelProperty("注册手机号")
    private String mobile;

    @ApiModelProperty("审核人姓名")
    private String auditUserName;

    @ApiModelProperty("最小需求数")
    private Integer minDemandCount;

    @ApiModelProperty("最大需求数")
    private Integer maxDemandCount;

    @ApiModelProperty("是否验证（0：否；1：是）")
    private Integer isValidate;

    @ApiModelProperty("是否背包客（0-买手；1-背包客）")
    private Integer isPacker;

    @ApiModelProperty("买手ID")
    private Long buyerId;

    public Integer getCurrent() {
        return current;
    }

    public Integer getSize() {
        return size;
    }

    public String getTourNo() {
        return tourNo;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getTimingStartTime() {
        return timingStartTime;
    }

    public Long getTimingEndTime() {
        return timingEndTime;
    }

    public Integer getMinOrderCount() {
        return minOrderCount;
    }

    public Integer getMaxOrderCount() {
        return maxOrderCount;
    }

    public Integer getIsLive() {
        return isLive;
    }

    public String getDestRegion() {
        return destRegion;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public String getTags() {
        return tags;
    }

    public String getUserName() {
        return userName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public Integer getMinDemandCount() {
        return minDemandCount;
    }

    public Integer getMaxDemandCount() {
        return maxDemandCount;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setTourNo(String tourNo) {
        this.tourNo = tourNo;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setTimingStartTime(Long timingStartTime) {
        this.timingStartTime = timingStartTime;
    }

    public void setTimingEndTime(Long timingEndTime) {
        this.timingEndTime = timingEndTime;
    }

    public void setMinOrderCount(Integer minOrderCount) {
        this.minOrderCount = minOrderCount;
    }

    public void setMaxOrderCount(Integer maxOrderCount) {
        this.maxOrderCount = maxOrderCount;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
    }

    public void setDestRegion(String destRegion) {
        this.destRegion = destRegion;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public void setMinDemandCount(Integer minDemandCount) {
        this.minDemandCount = minDemandCount;
    }

    public void setMaxDemandCount(Integer maxDemandCount) {
        this.maxDemandCount = maxDemandCount;
    }

    public Integer getIsValidate() {
        return isValidate;
    }

    public void setIsValidate(Integer isValidate) {
        this.isValidate = isValidate;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }
}
