package com.xmyy.pay.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 平台每日汇总对账表
 *
 * @author AnCheng
 */
@ApiModel("平台每日汇总对账表")
@TableName("dg_check_account_summary")
@SuppressWarnings("serial")
public class DgCheckAccountSummary extends BaseModel {

    @ApiModelProperty(value = "订单笔数")
	@TableField("order_total")
	private Integer orderTotal;
    @ApiModelProperty(value = "订单金额")
	@TableField("order_amount")
	private Long orderAmount;
    @ApiModelProperty(value = "充值订单笔数")
	@TableField("deposit_total")
	private Integer depositTotal;
    @ApiModelProperty(value = "充值订单金额")
	@TableField("deposit_amount")
	private Long depositAmount;
    @ApiModelProperty(value = "消费订单笔数")
	@TableField("consume_total")
	private Integer consumeTotal;
    @ApiModelProperty(value = "消费订单金额")
	@TableField("consume_amount")
	private Long consumeAmount;
    @ApiModelProperty(value = "协议消费订单笔数")
	@TableField("protocol_consume_total")
	private Integer protocolConsumeTotal;
    @ApiModelProperty(value = "协议消费订单金额")
	@TableField("protocol_consume_amount")
	private Long protocolConsumeAmount;
    @ApiModelProperty(value = "提现订单笔数")
	@TableField("withdraw_total")
	private Integer withdrawTotal;
    @ApiModelProperty(value = "提现订单金额")
	@TableField("withdraw_amount")
	private Long withdrawAmount;
    @ApiModelProperty(value = "平台转账订单笔数")
	@TableField("trans_total")
	private Integer transTotal;
    @ApiModelProperty(value = "平台转账订单金额")
	@TableField("trans_amount")
	private Long transAmount;
    @ApiModelProperty(value = "托收订单笔数")
	@TableField("agent_collect_total")
	private Integer agentCollectTotal;
    @ApiModelProperty(value = "托收订单金额")
	@TableField("agent_collect_amount")
	private Long agentCollectAmount;
    @ApiModelProperty(value = "协议托收订单笔数")
	@TableField("protocol_agent_collect_total")
	private Integer protocolAgentCollectTotal;
    @ApiModelProperty(value = "协议托收订单金额")
	@TableField("protocol_agent_collect_amount")
	private Long protocolAgentCollectAmount;
    @ApiModelProperty(value = "单笔托付笔数")
	@TableField("signal_agent_pay_total")
	private Integer signalAgentPayTotal;
    @ApiModelProperty(value = "单笔托付金额")
	@TableField("signal_agent_pay_amount")
	private Long signalAgentPayAmount;
    @ApiModelProperty(value = "批量托付笔数")
	@TableField("batch_agent_pay_total")
	private Integer batchAgentPayTotal;
    @ApiModelProperty(value = "批量托付金额")
	@TableField("batch_agent_pay_amount")
	private Long batchAgentPayAmount;
    @ApiModelProperty(value = "跨应用转账笔数")
	@TableField("cross_app_trans_total")
	private Integer crossAppTransTotal;
    @ApiModelProperty(value = "跨应用转账金额")
	@TableField("cross_app_trans_amount")
	private Long crossAppTransAmount;
    @ApiModelProperty(value = "债权转让笔数")
	@TableField("trans_creditor_right_total")
	private Integer transCreditorRightTotal;
    @ApiModelProperty(value = "债券转让金额")
	@TableField("trans_creditor_right_amount")
	private Long transCreditorRightAmount;
    @ApiModelProperty(value = "退款订单笔数")
	@TableField("refund_total")
	private Integer refundTotal;
    @ApiModelProperty(value = "退款订单金额")
	@TableField("refund_amount")
	private Long refundAmount;
    @ApiModelProperty(value = "流标退款笔数")
	@TableField("no_bidders_refund_total")
	private Integer noBiddersRefundTotal;
    @ApiModelProperty(value = "流标退款金额")
	@TableField("no_bidders_refund_amount")
	private Long noBiddersRefundAmount;
    @ApiModelProperty(value = "首笔订单号")
	@TableField("start_order_no")
	private String startOrderNo;
    @ApiModelProperty(value = "末尾订单号")
	@TableField("end_order_no")
	private String endOrderNo;


	public Integer getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Integer orderTotal) {
		this.orderTotal = orderTotal;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getDepositTotal() {
		return depositTotal;
	}

	public void setDepositTotal(Integer depositTotal) {
		this.depositTotal = depositTotal;
	}

	public Long getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Long depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Integer getConsumeTotal() {
		return consumeTotal;
	}

	public void setConsumeTotal(Integer consumeTotal) {
		this.consumeTotal = consumeTotal;
	}

	public Long getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(Long consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public Integer getProtocolConsumeTotal() {
		return protocolConsumeTotal;
	}

	public void setProtocolConsumeTotal(Integer protocolConsumeTotal) {
		this.protocolConsumeTotal = protocolConsumeTotal;
	}

	public Long getProtocolConsumeAmount() {
		return protocolConsumeAmount;
	}

	public void setProtocolConsumeAmount(Long protocolConsumeAmount) {
		this.protocolConsumeAmount = protocolConsumeAmount;
	}

	public Integer getWithdrawTotal() {
		return withdrawTotal;
	}

	public void setWithdrawTotal(Integer withdrawTotal) {
		this.withdrawTotal = withdrawTotal;
	}

	public Long getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(Long withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public Integer getTransTotal() {
		return transTotal;
	}

	public void setTransTotal(Integer transTotal) {
		this.transTotal = transTotal;
	}

	public Long getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Long transAmount) {
		this.transAmount = transAmount;
	}

	public Integer getAgentCollectTotal() {
		return agentCollectTotal;
	}

	public void setAgentCollectTotal(Integer agentCollectTotal) {
		this.agentCollectTotal = agentCollectTotal;
	}

	public Long getAgentCollectAmount() {
		return agentCollectAmount;
	}

	public void setAgentCollectAmount(Long agentCollectAmount) {
		this.agentCollectAmount = agentCollectAmount;
	}

	public Integer getProtocolAgentCollectTotal() {
		return protocolAgentCollectTotal;
	}

	public void setProtocolAgentCollectTotal(Integer protocolAgentCollectTotal) {
		this.protocolAgentCollectTotal = protocolAgentCollectTotal;
	}

	public Long getProtocolAgentCollectAmount() {
		return protocolAgentCollectAmount;
	}

	public void setProtocolAgentCollectAmount(Long protocolAgentCollectAmount) {
		this.protocolAgentCollectAmount = protocolAgentCollectAmount;
	}

	public Integer getSignalAgentPayTotal() {
		return signalAgentPayTotal;
	}

	public void setSignalAgentPayTotal(Integer signalAgentPayTotal) {
		this.signalAgentPayTotal = signalAgentPayTotal;
	}

	public Long getSignalAgentPayAmount() {
		return signalAgentPayAmount;
	}

	public void setSignalAgentPayAmount(Long signalAgentPayAmount) {
		this.signalAgentPayAmount = signalAgentPayAmount;
	}

	public Integer getBatchAgentPayTotal() {
		return batchAgentPayTotal;
	}

	public void setBatchAgentPayTotal(Integer batchAgentPayTotal) {
		this.batchAgentPayTotal = batchAgentPayTotal;
	}

	public Long getBatchAgentPayAmount() {
		return batchAgentPayAmount;
	}

	public void setBatchAgentPayAmount(Long batchAgentPayAmount) {
		this.batchAgentPayAmount = batchAgentPayAmount;
	}

	public Integer getCrossAppTransTotal() {
		return crossAppTransTotal;
	}

	public void setCrossAppTransTotal(Integer crossAppTransTotal) {
		this.crossAppTransTotal = crossAppTransTotal;
	}

	public Long getCrossAppTransAmount() {
		return crossAppTransAmount;
	}

	public void setCrossAppTransAmount(Long crossAppTransAmount) {
		this.crossAppTransAmount = crossAppTransAmount;
	}

	public Integer getTransCreditorRightTotal() {
		return transCreditorRightTotal;
	}

	public void setTransCreditorRightTotal(Integer transCreditorRightTotal) {
		this.transCreditorRightTotal = transCreditorRightTotal;
	}

	public Long getTransCreditorRightAmount() {
		return transCreditorRightAmount;
	}

	public void setTransCreditorRightAmount(Long transCreditorRightAmount) {
		this.transCreditorRightAmount = transCreditorRightAmount;
	}

	public Integer getRefundTotal() {
		return refundTotal;
	}

	public void setRefundTotal(Integer refundTotal) {
		this.refundTotal = refundTotal;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Integer getNoBiddersRefundTotal() {
		return noBiddersRefundTotal;
	}

	public void setNoBiddersRefundTotal(Integer noBiddersRefundTotal) {
		this.noBiddersRefundTotal = noBiddersRefundTotal;
	}

	public Long getNoBiddersRefundAmount() {
		return noBiddersRefundAmount;
	}

	public void setNoBiddersRefundAmount(Long noBiddersRefundAmount) {
		this.noBiddersRefundAmount = noBiddersRefundAmount;
	}

	public String getStartOrderNo() {
		return startOrderNo;
	}

	public void setStartOrderNo(String startOrderNo) {
		this.startOrderNo = startOrderNo;
	}

	public String getEndOrderNo() {
		return endOrderNo;
	}

	public void setEndOrderNo(String endOrderNo) {
		this.endOrderNo = endOrderNo;
	}

}