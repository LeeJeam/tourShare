package com.xmyy.pay.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 支付记录
 *
 * @author AnCheng
 */
@ApiModel("支付记录")
@TableName("dg_pay_log")
@SuppressWarnings("serial")
public class DgPayLog extends BaseModel {

    @ApiModelProperty(value = "订单ID，多个用逗号分隔")
	@TableField("order_ids")
	private String orderIds;
    @ApiModelProperty(value = "支付单号")
	@TableField("biz_order_no")
	private String bizOrderNo;
    @ApiModelProperty(value = "收付款用户编号（UUID）")
	@TableField("biz_user_id")
	private String bizUserId;
    @ApiModelProperty(value = "1订单支付；2确认收货；3确认退款；4申请提现")
	@TableField("service_type")
	private Integer serviceType;
    @ApiModelProperty(value = "支付状态（1未支付；3交易失败；4交易成功；5退款成功；6关闭；7退款受理；99进行中）")
	private Integer status;
    @ApiModelProperty(value = "订单金额（单位：分）")
	private Long amount;
    @ApiModelProperty(value = "手续费（单位：分）")
	private Long fee;
    @ApiModelProperty(value = "支付方式（1支付宝；2微信；3银行卡支付）")
	@TableField("pay_method")
	private Integer payMethod;
    @ApiModelProperty(value = "收款列表")
	@TableField("reciever_list")
	private String recieverList;
    @ApiModelProperty(value = "提现银行卡号")
	@TableField("bank_card_no")
	private String bankCardNo;
    @ApiModelProperty(value = "快捷支付交易编号")
	@TableField("trade_no")
	private String tradeNo;
    @ApiModelProperty(value = "支付宝扫码支付payInfo")
	@TableField("pay_info")
	private String payInfo;


	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public String getBizOrderNo() {
		return bizOrderNo;
	}

	public void setBizOrderNo(String bizOrderNo) {
		this.bizOrderNo = bizOrderNo;
	}

	public String getBizUserId() {
		return bizUserId;
	}

	public void setBizUserId(String bizUserId) {
		this.bizUserId = bizUserId;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

	public String getRecieverList() {
		return recieverList;
	}

	public void setRecieverList(String recieverList) {
		this.recieverList = recieverList;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

}