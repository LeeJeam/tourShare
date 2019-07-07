package com.xmyy.demand.model;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 需求单表
 *
 * @author AnCheng
 */
@ApiModel("需求单表")
@TableName("dg_demand_order")
@SuppressWarnings("serial")
public class DgDemandOrder extends BaseModel {

    @ApiModelProperty(value = "订单ID")
	@TableField("order_id_")
	private Long orderId;
    @ApiModelProperty(value = "需求单号")
	@TableField("demand_no")
	private String demandNo;
    @ApiModelProperty(value = "买家ID")
	@TableField("buyer_id")
	private Long buyerId;
    @ApiModelProperty(value = "需求发布时间")
	@TableField("demand_time")
	private Date demandTime;
    @ApiModelProperty(value = "需求发布地点")
	@TableField("demand_position")
	private String demandPosition;
    @ApiModelProperty(value = "购买国家地区ID")
	@TableField("country_id")
	private Integer countryId;
    @ApiModelProperty(value = "购买国家名称")
	@TableField("buy_country")
	private String buyCountry;
    @ApiModelProperty(value = "买手/背包客ID")
	@TableField("seller_id")
	private Long sellerId;
    @ApiModelProperty(value = "是否背包客:0否，1是")
	@TableField("is_packer")
	private Integer isPacker;
    @ApiModelProperty(value = "需求图片，可以多张")
	@TableField("demand_images")
	private String demandImages;
    @ApiModelProperty(value = "需求封面图")
	@TableField("demand_cover")
	private String demandCover;
    @ApiModelProperty(value = "需求描述")
	@TableField("demand_desc")
	private String demandDesc;
    @ApiModelProperty(value = "最迟收货时间")
	@TableField("delivery_time")
	private Date deliveryTime;
    @ApiModelProperty(value = "预算价格")
	@TableField("budget_price")
	private BigDecimal budgetPrice;
    @ApiModelProperty(value = "鉴定费")
	@TableField("appraisal_price")
	private BigDecimal appraisalPrice;
    @ApiModelProperty(value = "0未支付 1已支付")
	@TableField("is_toilsome")
	private Integer isToilsome;
    @ApiModelProperty(value = "辛苦费")
	@TableField("toilsome_price")
	private BigDecimal toilsomePrice;
    @ApiModelProperty(value = "需求状态（1待接单，2待指定，3代发货，4已发货，5已完成，6已失效，7已删除，8待支付）")
	@TableField("budget_status")
	private Integer budgetStatus;
    @ApiModelProperty(value = "标签类型")
	@TableField("product_type")
	private String productType;
    @ApiModelProperty(value = "关联行程")
	private Long tour;
    @ApiModelProperty(value = "行程单编号")
	@TableField("tour_no")
	private String tourNo;
    @ApiModelProperty(value = "收货人姓名")
	@TableField("consignee_name")
	private String consigneeName;
    @ApiModelProperty(value = "收货人电话")
	@TableField("buyer_phone")
	private String buyerPhone;
    @ApiModelProperty(value = "收货人地址")
	@TableField("consignee_address")
	private String consigneeAddress;
    @ApiModelProperty(value = "0国内寄送，1鉴定寄送")
	@TableField("is_appraisal")
	private Integer isAppraisal;
    @ApiModelProperty(value = "一个需求单最多10个买手接需求，超过不允许接单")
	@TableField("accepted_number")
	private Integer acceptedNumber;
    @ApiModelProperty(value = "取消需求单的原因")
	@TableField("cancel_reason")
	private Integer cancelReason;
    @ApiModelProperty(value = "买手/背包客删除标识：0正常，1删除")
	@TableField("seller_del")
	private Integer sellerDel;
    @ApiModelProperty(value = "买家删除标记:0正常，1删除")
	@TableField("buyer_del")
	private Integer buyerDel;
    @ApiModelProperty(value = "取消原因文本")
	@TableField("cancel_text")
	private String cancelText;
    @ApiModelProperty(value = "发布需求是选择指派的买手")
	@TableField("release_seller_ids")
	private String releaseSellerIds;
    @ApiModelProperty(value = "购买国家编码")
	@TableField("short_code")
	private String shortCode;


	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getDemandNo() {
		return demandNo;
	}

	public void setDemandNo(String demandNo) {
		this.demandNo = demandNo;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Date getDemandTime() {
		return demandTime;
	}

	public void setDemandTime(Date demandTime) {
		this.demandTime = demandTime;
	}

	public String getDemandPosition() {
		return demandPosition;
	}

	public void setDemandPosition(String demandPosition) {
		this.demandPosition = demandPosition;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getBuyCountry() {
		return buyCountry;
	}

	public void setBuyCountry(String buyCountry) {
		this.buyCountry = buyCountry;
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

	public String getDemandImages() {
		return demandImages;
	}

	public void setDemandImages(String demandImages) {
		this.demandImages = demandImages;
	}

	public String getDemandCover() {
		return demandCover;
	}

	public void setDemandCover(String demandCover) {
		this.demandCover = demandCover;
	}

	public String getDemandDesc() {
		return demandDesc;
	}

	public void setDemandDesc(String demandDesc) {
		this.demandDesc = demandDesc;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public BigDecimal getBudgetPrice() {
		return budgetPrice;
	}

	public void setBudgetPrice(BigDecimal budgetPrice) {
		this.budgetPrice = budgetPrice;
	}

	public BigDecimal getAppraisalPrice() {
		return appraisalPrice;
	}

	public void setAppraisalPrice(BigDecimal appraisalPrice) {
		this.appraisalPrice = appraisalPrice;
	}

	public Integer getIsToilsome() {
		return isToilsome;
	}

	public void setIsToilsome(Integer isToilsome) {
		this.isToilsome = isToilsome;
	}

	public BigDecimal getToilsomePrice() {
		return toilsomePrice;
	}

	public void setToilsomePrice(BigDecimal toilsomePrice) {
		this.toilsomePrice = toilsomePrice;
	}

	public Integer getBudgetStatus() {
		return budgetStatus;
	}

	public void setBudgetStatus(Integer budgetStatus) {
		this.budgetStatus = budgetStatus;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Long getTour() {
		return tour;
	}

	public void setTour(Long tour) {
		this.tour = tour;
	}

	public String getTourNo() {
		return tourNo;
	}

	public void setTourNo(String tourNo) {
		this.tourNo = tourNo;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public Integer getIsAppraisal() {
		return isAppraisal;
	}

	public void setIsAppraisal(Integer isAppraisal) {
		this.isAppraisal = isAppraisal;
	}

	public Integer getAcceptedNumber() {
		return acceptedNumber;
	}

	public void setAcceptedNumber(Integer acceptedNumber) {
		this.acceptedNumber = acceptedNumber;
	}

	public Integer getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(Integer cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Integer getSellerDel() {
		return sellerDel;
	}

	public void setSellerDel(Integer sellerDel) {
		this.sellerDel = sellerDel;
	}

	public Integer getBuyerDel() {
		return buyerDel;
	}

	public void setBuyerDel(Integer buyerDel) {
		this.buyerDel = buyerDel;
	}

	public String getCancelText() {
		return cancelText;
	}

	public void setCancelText(String cancelText) {
		this.cancelText = cancelText;
	}

	public String getReleaseSellerIds() {
		return releaseSellerIds;
	}

	public void setReleaseSellerIds(String releaseSellerIds) {
		this.releaseSellerIds = releaseSellerIds;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

}