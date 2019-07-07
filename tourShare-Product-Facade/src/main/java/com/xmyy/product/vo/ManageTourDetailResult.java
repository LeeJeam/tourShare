package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("管理端-行程信息出参")
public class ManageTourDetailResult implements Serializable {

    @ApiModelProperty("行程ID")
    private Long id;

    @ApiModelProperty("行程编号")
    private String tourNo;

    @ApiModelProperty("目的地")
    private String destRegion;

    @ApiModelProperty("审核状态（0：无证件（默认），1：未审核，2：审核通过，3：审核不通过）")
    private Integer auditStatus;

    @ApiModelProperty("需求数")
    private Integer demandCount;

    @ApiModelProperty("成交数")
    private Integer orderCount;

    @ApiModelProperty("成交额")
    private Integer amount;

    @ApiModelProperty("行程状态（0：失效，1：有效，2：完成）")
    private Integer status;

    @ApiModelProperty("行程开始时间")
    private Date timingStartTime;

    @ApiModelProperty("行程结束时间")
    private Date timingEndTime;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("登机牌图片URL")
    private String checkPic;

    @ApiModelProperty("审核人姓名")
    private String auditUserName;

    @ApiModelProperty("审核时间")
    private Date auditTime;

    @ApiModelProperty("是否置顶（0：否，1：是）")
    private Integer isTop;

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

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getDemandCount() {
        return demandCount;
    }

    public void setDemandCount(Integer demandCount) {
        this.demandCount = demandCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTimingStartTime() {
        return timingStartTime;
    }

    public void setTimingStartTime(Date timingStartTime) {
        this.timingStartTime = timingStartTime;
    }

    public Date getTimingEndTime() {
        return timingEndTime;
    }

    public void setTimingEndTime(Date timingEndTime) {
        this.timingEndTime = timingEndTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCheckPic() {
        return checkPic;
    }

    public void setCheckPic(String checkPic) {
        this.checkPic = checkPic;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }
}
