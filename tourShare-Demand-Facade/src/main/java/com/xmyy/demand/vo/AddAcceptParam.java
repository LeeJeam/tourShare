package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "接需求参数")
public class AddAcceptParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "需求ID", required = true)
    private Long id;

    @NotNull
    @Range(min = 0, max = 1)
    @ApiModelProperty(value = "是否背包客（0否，1是）", required = true)
    private Integer isPacker;

    @NotNull
    @ApiModelProperty(value = "行程ID", required = true)
    private Long tourId;

    @ApiModelProperty(value = "保险金额")
    private BigDecimal policyPrice;

    @ApiModelProperty(value = "保险公司")
    private String benefitsName;

    @ApiModelProperty(value = "保险支付日志")
    private Long benefitsLogId;

    @ApiModelProperty(value = "保险支付状态（0不支付，1已支付，2已退款）")
    private Integer benefitStatus;

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }

    public BigDecimal getPolicyPrice() {
        return policyPrice;
    }

    public void setPolicyPrice(BigDecimal policyPrice) {
        this.policyPrice = policyPrice;
    }

    public String getBenefitsName() {
        return benefitsName;
    }

    public void setBenefitsName(String benefitsName) {
        this.benefitsName = benefitsName;
    }

    public Long getBenefitsLogId() {
        return benefitsLogId;
    }

    public void setBenefitsLogId(Long benefitsLogId) {
        this.benefitsLogId = benefitsLogId;
    }

    public Integer getBenefitStatus() {
        return benefitStatus;
    }

    public void setBenefitStatus(Integer benefitStatus) {
        this.benefitStatus = benefitStatus;
    }

}
