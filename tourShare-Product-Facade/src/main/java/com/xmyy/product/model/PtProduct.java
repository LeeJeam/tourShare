package com.xmyy.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品表SPU
 * </p>
 *
 * @author simon
 * @since 2018-05-18
 */
@ApiModel("商品表")
@TableName("pt_product")
@SuppressWarnings("serial")
public class PtProduct extends BaseModel {

    @ApiModelProperty(value = "行程表_id")
	@TableField("dg_tour_id")
	private Long dgTourId;
    @ApiModelProperty(value = "品牌_id")
	@TableField("pt_brand_id")
	private Long ptBrandId;
    @ApiModelProperty(value = "商品模版_id")
	@TableField("pt_series_id")
	private Long ptSeriesId;
    @ApiModelProperty(value = "一级类目_id")
	@TableField("pt_category_id")
	private Long ptCategoryId;
    @ApiModelProperty(value = "二级类目_id")
	@TableField("pt_category_id2")
	private Long ptCategoryId2;
    @ApiModelProperty(value = "名称")
	private String name;
    @ApiModelProperty(value = "商品编号")
	@TableField("product_no")
	private String productNo;
    @ApiModelProperty(value = "市场价")
	@TableField("market_price")
	private BigDecimal marketPrice;
    @ApiModelProperty(value = "最小价格")
	@TableField("min_price")
	private BigDecimal minPrice;
	@ApiModelProperty(value = "最大价格")
	@TableField("max_price")
	private BigDecimal maxPrice;
    @ApiModelProperty(value = "状态(0：下架，1：上架)")
	@TableField("is_sale")
	private Integer isSale;
    @ApiModelProperty(value = "是否热门商品（0:否，1:是）")
	@TableField("is_hot")
	private Integer isHot;
    @ApiModelProperty(value = "是否推荐（0否，1是）")
	@TableField("is_recommend")
	private Integer isRecommend;
    @ApiModelProperty(value = "商品描述")
	@TableField("product_desc")
	private String productDesc;
    @ApiModelProperty(value = "封面图")
	private String cover;
    @ApiModelProperty(value = "商品图片（多张以分号隔开）")
	private String images;
    @ApiModelProperty(value = "物流方式（1国内配送，2鉴定配送）")
	@TableField("express_type")
	private Integer expressType;
    @ApiModelProperty(value = "失效时间")
	@TableField("expires_time")
	private Date expiresTime;
    @ApiModelProperty(value = "购买地")
	@TableField("buy_region")
	private String buyRegion;
    @ApiModelProperty(value = "购买店铺名称")
	@TableField("shop_name")
	private String shopName;
    @ApiModelProperty(value = "标题")
	private String title;
    @ApiModelProperty(value = "标签")
	private String tags;
    @ApiModelProperty(value = "服务说明")
	@TableField("service_desc")
	private String serviceDesc;
	@ApiModelProperty(value = "商品类型(1:预售，2现货)")
	@TableField("product_type")
	private Integer productType;

	@ApiModelProperty(value = "订单量")
	@TableField("order_count")
	private Integer orderCount;
	@ApiModelProperty(value = "收藏量")
	@TableField("collect_count")
	private Integer collectCount;
	@ApiModelProperty(value = "退货量")
	@TableField("refund_count")
	private Integer refundCount;
	@ApiModelProperty(value = "是否置顶（0：否，1：是）")
	@TableField("is_top")
	private Integer isTop;
	@ApiModelProperty(value = "置顶开始时间")
	@TableField("top_begin_time")
	private Date topBeginTime;
	@ApiModelProperty(value = "置顶结束时间")
	@TableField("top_end_time")
	private Date topEndTime;

	@ApiModelProperty(value = "商品点击数")
	@TableField("click_count")
	private Integer clickCount;
	@ApiModelProperty(value = "直播商品点击数")
	@TableField("video_click_count")
	private Integer videoClickCount;
	@ApiModelProperty(value = "好评度")
	@TableField("favorable")
	private Float favorable;

	@ApiModelProperty(value = "购买地图标")
	@TableField("buy_region_logo")
	private String buyRegionLogo;
	@ApiModelProperty(value = "购买地币种")
	@TableField("buy_region_currency")
	private String buyRegionCurrency;
	@ApiModelProperty(value = "购买地币种编码")
	@TableField("buy_region_currency_code")
	private String buyRegionCurrencyCode;
	@ApiModelProperty(value = "当前币种")
	@TableField("currency")
	private String currency;
	@ApiModelProperty(value = "当前币种图标")
	@TableField("currency_code")
	private String currencyCode;
	@ApiModelProperty(value = "购买地国际码")
	@TableField("buy_region_short_code")
	private String buyRegionShortCode;

	@ApiModelProperty(value = "设置TOP操作人ID")
	@TableField("top_op_id")
	private Long TopOpId;
	@ApiModelProperty(value = "设置TOP时间")
	@TableField("top_op_time")
	private Date TopOpTime;

	public Long getDgTourId() {
		return dgTourId;
	}

	public void setDgTourId(Long dgTourId) {
		this.dgTourId = dgTourId;
	}

	public Long getPtBrandId() {
		return ptBrandId;
	}

	public void setPtBrandId(Long ptBrandId) {
		this.ptBrandId = ptBrandId;
	}

	public Long getPtSeriesId() {
		return ptSeriesId;
	}

	public void setPtSeriesId(Long ptSeriesId) {
		this.ptSeriesId = ptSeriesId;
	}

	public Long getPtCategoryId() {
		return ptCategoryId;
	}

	public void setPtCategoryId(Long ptCategoryId) {
		this.ptCategoryId = ptCategoryId;
	}

	public Long getPtCategoryId2() {
		return ptCategoryId2;
	}

	public void setPtCategoryId2(Long ptCategoryId2) {
		this.ptCategoryId2 = ptCategoryId2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Integer getIsSale() {
		return isSale;
	}

	public void setIsSale(Integer isSale) {
		this.isSale = isSale;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Date getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(Date expiresTime) {
		this.expiresTime = expiresTime;
	}

	public String getBuyRegion() {
		return buyRegion;
	}

	public void setBuyRegion(String buyRegion) {
		this.buyRegion = buyRegion;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Integer getExpressType() {
		return expressType;
	}

	public void setExpressType(Integer expressType) {
		this.expressType = expressType;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Integer getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
	}

	public Integer getRefundCount() {
		return refundCount;
	}

	public void setRefundCount(Integer refundCount) {
		this.refundCount = refundCount;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Date getTopBeginTime() {
		return topBeginTime;
	}

	public void setTopBeginTime(Date topBeginTime) {
		this.topBeginTime = topBeginTime;
	}

	public Date getTopEndTime() {
		return topEndTime;
	}

	public void setTopEndTime(Date topEndTime) {
		this.topEndTime = topEndTime;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getVideoClickCount() {
		return videoClickCount;
	}

	public void setVideoClickCount(Integer videoClickCount) {
		this.videoClickCount = videoClickCount;
	}

	public Float getFavorable() {
		return favorable;
	}

	public void setFavorable(Float favorable) {
		this.favorable = favorable;
	}

	public String getBuyRegionLogo() {
		return buyRegionLogo;
	}

	public void setBuyRegionLogo(String buyRegionLogo) {
		this.buyRegionLogo = buyRegionLogo;
	}

	public String getBuyRegionCurrency() {
		return buyRegionCurrency;
	}

	public void setBuyRegionCurrency(String buyRegionCurrency) {
		this.buyRegionCurrency = buyRegionCurrency;
	}

	public String getBuyRegionCurrencyCode() {
		return buyRegionCurrencyCode;
	}

	public void setBuyRegionCurrencyCode(String buyRegionCurrencyCode) {
		this.buyRegionCurrencyCode = buyRegionCurrencyCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getTopOpId() {
		return TopOpId;
	}

	public void setTopOpId(Long topOpId) {
		TopOpId = topOpId;
	}

	public Date getTopOpTime() {
		return TopOpTime;
	}

	public void setTopOpTime(Date topOpTime) {
		TopOpTime = topOpTime;
	}

	public String getBuyRegionShortCode() {
		return buyRegionShortCode;
	}

	public void setBuyRegionShortCode(String buyRegionShortCode) {
		this.buyRegionShortCode = buyRegionShortCode;
	}
}