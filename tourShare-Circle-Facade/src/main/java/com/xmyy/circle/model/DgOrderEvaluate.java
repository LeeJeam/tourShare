package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 14订单买手评价
 * </p>
 *
 * @author simon
 * @since 2018-06-05
 */
@ApiModel("14订单买手评价")
@TableName("dg_order_evaluate")
@SuppressWarnings("serial")
public class DgOrderEvaluate extends BaseModel {

    @ApiModelProperty(value = "订单_ID")
	@TableField("order_id_")
	private Long orderId;
    @ApiModelProperty(value = "买家_ID")
	@TableField("buyer_id_")
	private Long buyerId;
    @ApiModelProperty(value = "评价内容")
	private String content;
    @ApiModelProperty(value = "买手服务星级")
	@TableField("buy_service")
	private Integer buyService;
    @ApiModelProperty(value = "商品质量星级")
	@TableField("product_level")
	private Integer productLevel;
    @ApiModelProperty(value = "点赞数")
	@TableField("praise_count")
	private Integer praiseCount;
	@ApiModelProperty(value = "买手_ID")
	@TableField("seller_id_")
	private Long sellerId;
	@ApiModelProperty(value = "是否背包客 0否；1是")
	@TableField("is_packer")
	private Integer isPacker;


	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getBuyService() {
		return buyService;
	}

	public void setBuyService(Integer buyService) {
		this.buyService = buyService;
	}

	public Integer getProductLevel() {
		return productLevel;
	}

	public void setProductLevel(Integer productLevel) {
		this.productLevel = productLevel;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
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
}