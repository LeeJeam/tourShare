package com.xmyy.order.model;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单表
 *
 * @author AnCheng
 */
@ApiModel("订单表")
@TableName("dg_order")
@SuppressWarnings("serial")
public class DgOrder extends BaseModel {

    @ApiModelProperty(value = "代收支付记录ID")
	@TableField("apply_paylog_id")
	private Long applyPaylogId;
    @ApiModelProperty(value = "代付支付记录ID")
	@TableField("pay_paylog_id")
	private Long payPaylogId;
    @ApiModelProperty(value = "退款支付记录ID")
	@TableField("refund_paylog_id")
	private Long refundPaylogId;
    @ApiModelProperty(value = "订单号")
	@TableField("order_no")
	private String orderNo;
    @ApiModelProperty(value = "1：预售；2：现货；3：需求")
	@TableField("order_type")
	private Integer orderType;
    @ApiModelProperty(value = "需求ID")
	@TableField("demand_id")
	private Long demandId;
    @ApiModelProperty(value = "商品名称（需求描述）字符串，用户后台查询的冗余字段")
	@TableField("product_name_str")
	private String productNameStr;
    @ApiModelProperty(value = "订单状态")
	@TableField("order_status")
	private Integer orderStatus;
    @ApiModelProperty(value = "订单关联的行程id")
	@TableField("tour_id")
	private Long tourId;
    @ApiModelProperty(value = "行程编号")
	@TableField("tour_no")
	private String tourNo;
    @ApiModelProperty(value = "0国内寄送，1鉴定寄送")
	@TableField("is_appraisal")
	private Integer isAppraisal;
    @ApiModelProperty(value = "发货状态（后台展示使用，未发货0，已发货1）")
	@TableField("send_status")
	private Integer sendStatus;
    @ApiModelProperty(value = "鉴定费")
	@TableField("appraisal_price")
	private BigDecimal appraisalPrice;
    @ApiModelProperty(value = "鉴定视频地址")
	@TableField("appraisal_url")
	private String appraisalUrl;
    @ApiModelProperty(value = "买家ID")
	@TableField("buyer_id")
	private Long buyerId;
    @ApiModelProperty(value = "是否背包客 0否；1是")
	@TableField("is_packer")
	private Integer isPacker;
    @ApiModelProperty(value = "买手/背包客ID")
	@TableField("seller_id")
	private Long sellerId;
    @ApiModelProperty(value = "订单商品总数")
	@TableField("total_num")
	private Integer totalNum;
    @ApiModelProperty(value = "订单总实付金额")
	@TableField("pay_money")
	private BigDecimal payMoney;
    @ApiModelProperty(value = "快递单号")
	@TableField("waybill_no")
	private String waybillNo;
    @ApiModelProperty(value = "物流公司编码(快递100)")
	@TableField("logistics_no")
	private String logisticsNo;
    @ApiModelProperty(value = "物流公司名称")
	@TableField("logistics_company")
	private String logisticsCompany;
    @ApiModelProperty(value = "收货人地址")
	@TableField("consignee_address")
	private String consigneeAddress;
    @ApiModelProperty(value = "收货人姓名")
	@TableField("consignee_name")
	private String consigneeName;
    @ApiModelProperty(value = "收货人电话")
	@TableField("consignee_phone")
	private String consigneePhone;
    @ApiModelProperty(value = "买家留言")
	@TableField("buyer_message")
	private String buyerMessage;
    @ApiModelProperty(value = "取消原因文字")
	@TableField("cancel_text")
	private String cancelText;
    @ApiModelProperty(value = "取消订单时选择的原因")
	@TableField("cancel_reason")
	private Integer cancelReason;
    @ApiModelProperty(value = "下单时间")
	@TableField("order_time")
	private Date orderTime;
    @ApiModelProperty(value = "需求形成的订单-收货时限；商品订单-订单待支付时限")
	@TableField("delivery_time")
	private Date deliveryTime;
    @ApiModelProperty(value = "0未评价 1已评价 2已追评")
	@TableField("evaluate_status")
	private Integer evaluateStatus;
    @ApiModelProperty(value = "买家删除订单状态，0未删除，1已删除")
	@TableField("buyer_del")
	private Integer buyerDel;
    @ApiModelProperty(value = "买手删除订单状态，0未删除，1已删除")
	@TableField("seller_del")
	private Integer sellerDel;


	public Long getApplyPaylogId() {
		return applyPaylogId;
	}

	public void setApplyPaylogId(Long applyPaylogId) {
		this.applyPaylogId = applyPaylogId;
	}

	public Long getPayPaylogId() {
		return payPaylogId;
	}

	public void setPayPaylogId(Long payPaylogId) {
		this.payPaylogId = payPaylogId;
	}

	public Long getRefundPaylogId() {
		return refundPaylogId;
	}

	public void setRefundPaylogId(Long refundPaylogId) {
		this.refundPaylogId = refundPaylogId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Long getDemandId() {
		return demandId;
	}

	public void setDemandId(Long demandId) {
		this.demandId = demandId;
	}

	public String getProductNameStr() {
		return productNameStr;
	}

	public void setProductNameStr(String productNameStr) {
		this.productNameStr = productNameStr;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getTourId() {
		return tourId;
	}

	public void setTourId(Long tourId) {
		this.tourId = tourId;
	}

	public String getTourNo() {
		return tourNo;
	}

	public void setTourNo(String tourNo) {
		this.tourNo = tourNo;
	}

	public Integer getIsAppraisal() {
		return isAppraisal;
	}

	public void setIsAppraisal(Integer isAppraisal) {
		this.isAppraisal = isAppraisal;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public BigDecimal getAppraisalPrice() {
		return appraisalPrice;
	}

	public void setAppraisalPrice(BigDecimal appraisalPrice) {
		this.appraisalPrice = appraisalPrice;
	}

	public String getAppraisalUrl() {
		return appraisalUrl;
	}

	public void setAppraisalUrl(String appraisalUrl) {
		this.appraisalUrl = appraisalUrl;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getIsPacker() {
		return isPacker;
	}

	public void setIsPacker(Integer isPacker) {
		this.isPacker = isPacker;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public String getCancelText() {
		return cancelText;
	}

	public void setCancelText(String cancelText) {
		this.cancelText = cancelText;
	}

	public Integer getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(Integer cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getEvaluateStatus() {
		return evaluateStatus;
	}

	public void setEvaluateStatus(Integer evaluateStatus) {
		this.evaluateStatus = evaluateStatus;
	}

	public Integer getBuyerDel() {
		return buyerDel;
	}

	public void setBuyerDel(Integer buyerDel) {
		this.buyerDel = buyerDel;
	}

	public Integer getSellerDel() {
		return sellerDel;
	}

	public void setSellerDel(Integer sellerDel) {
		this.sellerDel = sellerDel;
	}

}