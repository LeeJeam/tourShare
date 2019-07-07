package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "后台需求单列表参数")
public class DemandListManageParam implements Serializable {

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小（默认10）")
    private Integer size = 10;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码（默认1）")
    private Integer current = 1;

    @ApiModelProperty(value = "物品名称")
    private String demandDesc;

    @ApiModelProperty(value = "鉴定寄送（0否，1是）")
    private String isAppraisal;

    @ApiModelProperty(value = "需求单状态：1待接单，2待指定，3待发货，4待收货，5已完成，6下架失效")
    private String demandStatus;

    @ApiModelProperty(value = "需求单号")
    private String demandNo;

    @ApiModelProperty(value = "开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "买家ID")
    private Long buyerId;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public String getDemandDesc() {
        return demandDesc;
    }

    public void setDemandDesc(String demandDesc) {
        this.demandDesc = demandDesc;
    }

    public String getIsAppraisal() {
        return isAppraisal;
    }

    public void setIsAppraisal(String isAppraisal) {
        this.isAppraisal = isAppraisal;
    }

    public String getDemandStatus() {
        return demandStatus;
    }

    public void setDemandStatus(String demandStatus) {
        this.demandStatus = demandStatus;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }
}
