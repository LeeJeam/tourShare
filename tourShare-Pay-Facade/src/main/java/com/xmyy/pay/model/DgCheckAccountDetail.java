package com.xmyy.pay.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 明细对账表
 * </p>
 *
 * @author zlp
 * @since 2018-09-02
 */
@ApiModel("明细对账表")
@TableName("dg_check_account_detail")
@SuppressWarnings("serial")
public class DgCheckAccountDetail extends BaseModel {

    @ApiModelProperty(value = "通联订单号orderNo，可查询订单和退款原订单号")
	@TableField("order_no")
	private String orderNo;
    @ApiModelProperty(value = "订单类型（1充值，2消费，3提现，4平台转账，5托收，6托付，7退款）")
	@TableField("order_type")
	private Integer orderType;
    @ApiModelProperty(value = "交易金额(单位:分)")
	private Long amount;
    @ApiModelProperty(value = "手续费(单位:分)")
	private Long fee;
    @ApiModelProperty(value = "交易时间（yyyy-MM-dd HH:mm:ss）")
	private String time;
    @ApiModelProperty(value = "商户订单号（支付订单）")
	@TableField("biz_order_no")
	private String bizOrderNo;
    @ApiModelProperty(value = "渠道金额(单位:分)")
	@TableField("channel_amount")
	private Long channelAmount;
    @ApiModelProperty(value = "渠道流水号")
	@TableField("channel_no")
	private String channelNo;
    @ApiModelProperty(value = "渠道手续费(单位:分)")
	@TableField("channel_fee")
	private Long channelFee;
    @ApiModelProperty(value = "云账户手续费(单位:分)")
	@TableField("cloud_account_fee")
	private Long cloudAccountFee;


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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getBizOrderNo() {
		return bizOrderNo;
	}

	public void setBizOrderNo(String bizOrderNo) {
		this.bizOrderNo = bizOrderNo;
	}

	public Long getChannelAmount() {
		return channelAmount;
	}

	public void setChannelAmount(Long channelAmount) {
		this.channelAmount = channelAmount;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public Long getChannelFee() {
		return channelFee;
	}

	public void setChannelFee(Long channelFee) {
		this.channelFee = channelFee;
	}

	public Long getCloudAccountFee() {
		return cloudAccountFee;
	}

	public void setCloudAccountFee(Long cloudAccountFee) {
		this.cloudAccountFee = cloudAccountFee;
	}

}