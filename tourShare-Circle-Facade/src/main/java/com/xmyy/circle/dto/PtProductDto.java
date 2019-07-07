package com.xmyy.circle.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

import java.io.Serializable;
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
public class PtProductDto implements Serializable{

	private Long id;
    @ApiModelProperty(value = "名称")
	private String name;
    @ApiModelProperty(value = "商品编号")
	private String productNo;
    @ApiModelProperty(value = "商品描述")
	private String productDesc;
    @ApiModelProperty(value = "封面图")
	private String cover;
    @ApiModelProperty(value = "商品图片（多张以分号隔开）")
	private String images;
    @ApiModelProperty(value = "购买地")
	private String buyRegion;
    @ApiModelProperty(value = "购买店铺名称")
	private String shopName;
    @ApiModelProperty(value = "标题")
	private String title;
    @ApiModelProperty(value = "标签")
	private String tags;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "PtProductDto{" +
				"id=" + id +
				", name='" + name + '\'' +
				", productNo='" + productNo + '\'' +
				", productDesc='" + productDesc + '\'' +
				", cover='" + cover + '\'' +
				", images='" + images + '\'' +
				", buyRegion='" + buyRegion + '\'' +
				", shopName='" + shopName + '\'' +
				", title='" + title + '\'' +
				", tags='" + tags + '\'' +
				'}';
	}
}