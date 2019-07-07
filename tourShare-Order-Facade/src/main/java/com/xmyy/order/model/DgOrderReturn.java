package com.xmyy.order.model;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 订单退货记录
 * </p>
 *
 * @author admin
 * @since 2018-06-19
 */
@ApiModel("订单退货记录")
@TableName("dg_order_return")
@SuppressWarnings("serial")
public class DgOrderReturn extends BaseModel {

    @ApiModelProperty(value = "预售订单表_ID")
	@TableField("product_order_id_")
	private Long productOrderId;
    @ApiModelProperty(value = "订单ID")
	@TableField("order_id_")
	private Long orderId;
    @ApiModelProperty(value = "退货单编号")
	@TableField("return_no")
	private String returnNo;
    @ApiModelProperty(value = "1：预售；2：现货；3：需求")
	@TableField("order_type")
	private Integer orderType;
    @ApiModelProperty(value = "商品ID（预售现货为商品ID，需求为需求单ID）")
	@TableField("product_id")
	private Long productId;
    @ApiModelProperty(value = "商品标题（预售现货为商品名称，需求为需求描述）")
	@TableField("product_title")
	private String productTitle;
    @ApiModelProperty(value = "商品封面图")
	@TableField("product_cover")
	private String productCover;
    @ApiModelProperty(value = "商品价格")
	@TableField("product_price")
	private BigDecimal productPrice;
    @ApiModelProperty(value = "购买数量")
	@TableField("product_num")
	private Integer productNum;
    @ApiModelProperty(value = "预售现货商品规格参数，黑白/36")
	@TableField("spec_params")
	private String specParams;
    @ApiModelProperty(value = "需求过期时间")
	@TableField("delivery_time")
	private Date deliveryTime;
    @ApiModelProperty(value = "退款金额")
	@TableField("return_money")
	private BigDecimal returnMoney;
    @ApiModelProperty(value = "退货原因文字")
	@TableField("reason_text")
	private String reasonText;
    @ApiModelProperty(value = "退货原因")
	@TableField("return_reason")
	private Integer returnReason;
    @ApiModelProperty(value = "退货申请时间")
	@TableField("return_submit_time")
	private Date returnSubmitTime;
    @ApiModelProperty(value = "买家ID")
	@TableField("buyer_id")
	private Long buyerId;
    @ApiModelProperty(value = "是否背包客 0否；1是")
	@TableField("is_packer")
	private Integer isPacker;
    @ApiModelProperty(value = "买手ID")
	@TableField("seller_id")
	private Long sellerId;
    @ApiModelProperty(value = "退货物流单号")
	@TableField("return_waybill")
	private String returnWaybill;
    @ApiModelProperty(value = "0售后处理中 1退款成功")
	@TableField("return_status")
	private Integer returnStatus;


	public Long getProductOrderId() {
		return productOrderId;
	}

	public void setProductOrderId(Long productOrderId) {
		this.productOrderId = productOrderId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getProductCover() {
		return productCover;
	}

	public void setProductCover(String productCover) {
		this.productCover = productCover;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public String getSpecParams() {
		return specParams;
	}

	public void setSpecParams(String specParams) {
		this.specParams = specParams;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public BigDecimal getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(BigDecimal returnMoney) {
		this.returnMoney = returnMoney;
	}

	public String getReasonText() {
		return reasonText;
	}

	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}

	public Integer getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(Integer returnReason) {
		this.returnReason = returnReason;
	}

	public Date getReturnSubmitTime() {
		return returnSubmitTime;
	}

	public void setReturnSubmitTime(Date returnSubmitTime) {
		this.returnSubmitTime = returnSubmitTime;
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

	public String getReturnWaybill() {
		return returnWaybill;
	}

	public void setReturnWaybill(String returnWaybill) {
		this.returnWaybill = returnWaybill;
	}

	public Integer getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(Integer returnStatus) {
		this.returnStatus = returnStatus;
	}

}