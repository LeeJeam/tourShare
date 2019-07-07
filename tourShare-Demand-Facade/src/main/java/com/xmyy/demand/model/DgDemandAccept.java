package com.xmyy.demand.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.ibase4j.core.base.BaseModel;

import java.math.BigDecimal;

/**
 * <p>
 * 需求接单表
 * </p>
 *
 * @author wangmd
 * @since 2018-06-07
 */
@ApiModel("需求接单表")
@TableName("dg_demand_accept")
@Data
@SuppressWarnings("serial")
public class DgDemandAccept extends BaseModel {

    @ApiModelProperty(value = "保险支付日志ID")
	@TableField("benefits_log_id_")
	private Long benefitsLogId;
    @ApiModelProperty(value = "需求表ID")
	@TableField("demand_order_id_")
	private Long demandOrderId;
    @ApiModelProperty(value = "买手ID")
	@TableField("seller_id")
	private Long sellerId;
    @ApiModelProperty(value = "是否背包客")
	@TableField("is_packer")
	private Integer isPacker;
    @ApiModelProperty(value = "买手信任值")
	@TableField("trust_dgree")
	private Integer trustDgree;
    @ApiModelProperty(value = "常去地点")
	@TableField("often_tour")
	private String oftenTour;
    @ApiModelProperty(value = "接单指定行程ID")
	@TableField("tour_id")
	private Long tourId;
    @ApiModelProperty(value = "指定买手时，买手展示的行程地点")
	@TableField("tour_list")
	private String tourList;
    @ApiModelProperty(value = "支付保险状态（0不支付 1已支付 2已退款）")
	@TableField("benefit_status")
	private Integer benefitStatus;
    @ApiModelProperty(value = "保险金额")
	private BigDecimal benefits;
    @ApiModelProperty(value = "保险公司")
	@TableField("benefits_name")
	private String benefitsName;
    @ApiModelProperty(value = "需求接单状态（1主动接单待指定；2主动接单已被指定；3已接单未被指定，4被动接单待买手受理，5买手主动取消）")
	@TableField("budget_status")
	private Integer budgetStatus;
    @ApiModelProperty(value = "需求原因")
	@TableField("cancel_reason")
	private Integer cancelReason;
    @ApiModelProperty(value = "取消原因文本")
	@TableField("cancel_text")
	private String cancelText;


	public Long getBenefitsLogId() {
		return benefitsLogId;
	}

	public void setBenefitsLogId(Long benefitsLogId) {
		this.benefitsLogId = benefitsLogId;
	}

	public Long getDemandOrderId() {
		return demandOrderId;
	}

	public void setDemandOrderId(Long demandOrderId) {
		this.demandOrderId = demandOrderId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getIsPacker() {
		return isPacker;
	}

	public void setIsPacker(Integer isPacker) {
		this.isPacker = isPacker;
	}

	public Integer getTrustDgree() {
		return trustDgree;
	}

	public void setTrustDgree(Integer trustDgree) {
		this.trustDgree = trustDgree;
	}

	public String getOftenTour() {
		return oftenTour;
	}

	public void setOftenTour(String oftenTour) {
		this.oftenTour = oftenTour;
	}

	public Long getTourId() {
		return tourId;
	}

	public void setTourId(Long tourId) {
		this.tourId = tourId;
	}

	public String getTourList() {
		return tourList;
	}

	public void setTourList(String tourList) {
		this.tourList = tourList;
	}

	public Integer getBenefitStatus() {
		return benefitStatus;
	}

	public void setBenefitStatus(Integer benefitStatus) {
		this.benefitStatus = benefitStatus;
	}

	public BigDecimal getBenefits() {
		return benefits;
	}

	public void setBenefits(BigDecimal benefits) {
		this.benefits = benefits;
	}

	public String getBenefitsName() {
		return benefitsName;
	}

	public void setBenefitsName(String benefitsName) {
		this.benefitsName = benefitsName;
	}

	public Integer getBudgetStatus() {
		return budgetStatus;
	}

	public void setBudgetStatus(Integer budgetStatus) {
		this.budgetStatus = budgetStatus;
	}

	public Integer getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(Integer cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCancelText() {
		return cancelText;
	}

	public void setCancelText(String cancelText) {
		this.cancelText = cancelText;
	}

}