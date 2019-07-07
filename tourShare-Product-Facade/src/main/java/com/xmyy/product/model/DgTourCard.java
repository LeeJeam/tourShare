package com.xmyy.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 行程卡片
 *
 * @author wzj
 */
@ApiModel("行程卡片")
@TableName("dg_tour_card")
@SuppressWarnings("serial")
public class DgTourCard extends BaseModel {

    @ApiModelProperty(value = "卡片中文名称")
	@TableField("card_name")
	private String cardName;
    @ApiModelProperty(value = "卡片英文名称")
	@TableField("card_eng_name")
	private String cardEngName;
    @ApiModelProperty(value = "卡片编码")
	@TableField("card_code")
	private String cardCode;
    @ApiModelProperty(value = "皮肤类型（0-城市风；1-插画风）")
	@TableField("skin_type")
	private Integer skinType;
    @ApiModelProperty(value = "卡片分辨率（S:drawable-hdpi;    X:drawable-xhdpi，IOS-1X；  XX:drawable-xxhdpi，IOS-2X；  XXX-drawable-xxxhdpi)")
	@TableField("card_size")
	private String cardSize;
    @ApiModelProperty(value = "卡片展示位置（0-买家首页行程轮播；1-买家首页更多行程；2-行程详情）")
	@TableField("card_type")
	private Integer cardType;
    @ApiModelProperty(value = "图片地址")
	@TableField("img_url")
	private String imgUrl;
    @ApiModelProperty(value = "查看设备 （1-安卓，2-IOS，4- PC）")
	@TableField("device_type")
	private Integer deviceType;

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardEngName() {
		return cardEngName;
	}

	public void setCardEngName(String cardEngName) {
		this.cardEngName = cardEngName;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public Integer getSkinType() {
		return skinType;
	}

	public void setSkinType(Integer skinType) {
		this.skinType = skinType;
	}

	public String getCardSize() {
		return cardSize;
	}

	public void setCardSize(String cardSize) {
		this.cardSize = cardSize;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

}