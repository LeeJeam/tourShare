package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("行程验证列表数据")
public class ManageTourValidateDetailResult implements Serializable {

    @ApiModelProperty("行程ID")
    private Long id;

    @ApiModelProperty("行程编号")
    private String tourNo;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("行程开始时间")
    private Date timingStartTime;

    @ApiModelProperty("行程结束时间")
    private Date timingEndTime;

    @ApiModelProperty("审核人姓名")
    private String auditUserName;

    @ApiModelProperty("审核时间")
    private Date auditTime;

    @ApiModelProperty("审核状态（0：无证件（默认），1：未审核，2：审核通过，3：审核不通过）")
    private Integer auditStatus;

    @ApiModelProperty("登机牌图片URL")
    private String checkPic;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("目的地")
    private String destRegion;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getCheckPic() {
        return checkPic;
    }

    public void setCheckPic(String checkPic) {
        this.checkPic = checkPic;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDestRegion() {
        return destRegion;
    }

    public void setDestRegion(String destRegion) {
        this.destRegion = destRegion;
    }
}
