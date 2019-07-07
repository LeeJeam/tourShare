package com.xmyy.order.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 订单状态历史
 * </p>
 *
 * @author admin
 * @since 2018-06-05
 */
@ApiModel("订单状态历史")
@TableName("dg_order_history")
@SuppressWarnings("serial")
public class DgOrderHistory extends BaseModel {

    @ApiModelProperty(value = "订单ID")
	@TableField("order_id_")
	private Long orderId;
    @ApiModelProperty(value = "操作内容")
	@TableField("opt_context")
	private String optContext;
    @ApiModelProperty(value = "操作的具体描述")
	@TableField("opt_desc")
	private String optDesc;


	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOptContext() {
		return optContext;
	}

	public void setOptContext(String optContext) {
		this.optContext = optContext;
	}

	public String getOptDesc() {
		return optDesc;
	}

	public void setOptDesc(String optDesc) {
		this.optDesc = optDesc;
	}

}