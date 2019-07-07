package com.xmyy.order.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单商品表，商品形成的订单商品信息
 *
 * @author AnCheng
 */
@ApiModel("订单商品表，商品形成的订单商品信息")
@TableName("dg_product_order")
@SuppressWarnings("serial")
public class DgProductOrder extends BaseModel {

    @ApiModelProperty(value = "订单ID")
	@TableField("order_id_")
	private Long orderId;
    @ApiModelProperty(value = "商品ID")
	@TableField("product_id")
	private Long productId;
    @ApiModelProperty(value = "关联行程ID")
	@TableField("tour_id")
	private Long tourId;
    @ApiModelProperty(value = "商品SKU ID")
	@TableField("sku_id")
	private Long skuId;
    @ApiModelProperty(value = "预售形成的商品名称")
	@TableField("product_name")
	private String productName;
    @ApiModelProperty(value = "商品类型(1:预售，2现货)")
	@TableField("product_type")
	private Integer productType;
    @ApiModelProperty(value = "商品价格")
	@TableField("product_price")
	private BigDecimal productPrice;
    @ApiModelProperty(value = "商品封面图")
	@TableField("product_cover")
	private String productCover;
    @ApiModelProperty(value = "商品图片")
	@TableField("product_images")
	private String productImages;
    @ApiModelProperty(value = "预售商品，规格")
	@TableField("product_spec")
	private String productSpec;
    @ApiModelProperty(value = "规格下的属性值")
	@TableField("spec_params")
	private String specParams;
    @ApiModelProperty(value = "每种商品购买数量小计")
	@TableField("product_num")
	private Integer productNum;
    @ApiModelProperty(value = "小计金额")
	private BigDecimal subtotal;
    @ApiModelProperty(value = "是否申请售后(1是，0否)")
	@TableField("is_refund")
	private Integer isRefund;


	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getTourId() {
		return tourId;
	}

	public void setTourId(Long tourId) {
		this.tourId = tourId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductCover() {
		return productCover;
	}

	public void setProductCover(String productCover) {
		this.productCover = productCover;
	}

	public String getProductImages() {
		return productImages;
	}

	public void setProductImages(String productImages) {
		this.productImages = productImages;
	}

	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	public String getSpecParams() {
		return specParams;
	}

	public void setSpecParams(String specParams) {
		this.specParams = specParams;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public Integer getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Integer isRefund) {
		this.isRefund = isRefund;
	}

}