package com.xmyy.pay.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 支付明细，记录每笔支付记录的组成
 * </p>
 *
 * @author AnCheng
 * @since 2018-09-10
 */
@ApiModel("支付明细，记录每笔支付记录的组成")
@TableName("dg_pay_detail")
@SuppressWarnings("serial")
public class DgPayDetail extends BaseModel {

	@TableField("pay_log_id_")
	private Long payLogId;
    @ApiModelProperty(value = "商户订单号（支付订单）")
	@TableField("biz_order_no")
	private String bizOrderNo;
    @ApiModelProperty(value = "该项分账类型")
	private Integer type;
    @ApiModelProperty(value = "付款用户编号（UUID）")
	@TableField("biz_user_id")
	private String bizUserId;
    @ApiModelProperty(value = "账户集编号")
	@TableField("account_set_no")
	private String accountSetNo;
    @ApiModelProperty(value = "分账金额（单位：分）")
	private Long amount;
    @ApiModelProperty(value = "手续费（单位：分）")
	private Long fee;


	public Long getPayLogId() {
		return payLogId;
	}

	public void setPayLogId(Long payLogId) {
		this.payLogId = payLogId;
	}

	public String getBizOrderNo() {
		return bizOrderNo;
	}

	public void setBizOrderNo(String bizOrderNo) {
		this.bizOrderNo = bizOrderNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBizUserId() {
		return bizUserId;
	}

	public void setBizUserId(String bizUserId) {
		this.bizUserId = bizUserId;
	}

	public String getAccountSetNo() {
		return accountSetNo;
	}

	public void setAccountSetNo(String accountSetNo) {
		this.accountSetNo = accountSetNo;
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

}