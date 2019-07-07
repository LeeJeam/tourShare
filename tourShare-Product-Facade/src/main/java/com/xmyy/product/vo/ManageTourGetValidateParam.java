package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.Date;

@ApiModel("管理端-行程查询入参")
public class ManageTourGetValidateParam implements Serializable {

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty("分页页码，默认1")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty("页大小，默认10")
    private Integer size = 10;

    @ApiModelProperty("行程编号")
    private String tourNo;

    @ApiModelProperty("审核人姓名")
    private String auditUserName;

    @ApiModelProperty("审核状态（0：无证件（默认），1：未审核，2：审核通过，3：审核不通过）")
    private Integer auditStatus;

    @ApiModelProperty("注册手机号")
    private String mobile;

    @ApiModelProperty("审核开始时间")
    private Date startAuditTime;

    @ApiModelProperty("审核结束时间")
    private Date endAuditTime;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getTourNo() {
        return tourNo;
    }

    public void setTourNo(String tourNo) {
        this.tourNo = tourNo;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getStartAuditTime() {
        return startAuditTime;
    }

    public void setStartAuditTime(Date startAuditTime) {
        this.startAuditTime = startAuditTime;
    }

    public Date getEndAuditTime() {
        return endAuditTime;
    }

    public void setEndAuditTime(Date endAuditTime) {
        this.endAuditTime = endAuditTime;
    }

}
