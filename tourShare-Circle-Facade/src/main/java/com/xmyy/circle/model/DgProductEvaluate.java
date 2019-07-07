package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

import java.util.Date;

/**
 * <p>
 * 14订单商品评价
 * </p>
 *
 * @author simon
 * @since 2018-06-05
 */
@ApiModel("14订单商品评价")
@TableName("dg_product_evaluate")
@SuppressWarnings("serial")
public class DgProductEvaluate extends BaseModel {

    @ApiModelProperty(value = "订单商品_ID")
	@TableField("product_id_")
	private Long productId;
    @ApiModelProperty(value = "买家_ID")
	@TableField("buyer_id_")
	private Long buyerId;
    @ApiModelProperty(value = "评价内容")
	private String content;
    @ApiModelProperty(value = "订单id")
	@TableField("order_id")
	private Long orderId;
    @ApiModelProperty(value = "点赞数")
	@TableField("praise_count")
	private Integer praiseCount;
    @ApiModelProperty(value = "图片(多跟以逗号隔开)")
	@TableField("pic_rsurl")
	private String picRsurl;
    @ApiModelProperty(value = "追评")
	@TableField("review_content")
	private String reviewContent;
    @ApiModelProperty(value = "回复")
	@TableField("reply_content")
	private String replyContent;
    @ApiModelProperty(value = "追评时间")
	@TableField("review_time")
	private Date reviewTime;
    @ApiModelProperty(value = "回复时间")
	@TableField("reply_time")
	private Date replyTime;
    @ApiModelProperty(value = "追评图片(多跟以逗号隔开)")
	@TableField("review_pic_rsurl")
	private String reviewPicRsurl;
	@ApiModelProperty(value = "买手_ID")
	@TableField("seller_id_")
	private Long sellerId;
	@ApiModelProperty(value = "是否背包客 0否；1是")
	@TableField("is_packer")
	private Integer isPacker;
	@ApiModelProperty(value = "订单类型(1：预售；2：现货；3：需求)")
	@TableField("order_type")
	private Integer orderType;
	@ApiModelProperty(value = "商品快照ID")
	@TableField("product_order_id")
	private Long productOrderId;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public String getPicRsurl() {
		return picRsurl;
	}

	public void setPicRsurl(String picRsurl) {
		this.picRsurl = picRsurl;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public String getReviewPicRsurl() {
		return reviewPicRsurl;
	}

	public void setReviewPicRsurl(String reviewPicRsurl) {
		this.reviewPicRsurl = reviewPicRsurl;
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

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Long getProductOrderId() {
		return productOrderId;
	}

	public void setProductOrderId(Long productOrderId) {
		this.productOrderId = productOrderId;
	}
}