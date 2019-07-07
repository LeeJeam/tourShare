package com.xmyy.product.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ApiTourCardDto implements Serializable {

    @ApiModelProperty(value="卡片ID")
    private Long id;

    @ApiModelProperty(value = "卡片名称")
    private String cardName;

    @ApiModelProperty(value = "卡片编码")
    private String cardCode;

    @ApiModelProperty(value = "皮肤类型（0-城市风；1-插画风）")
    private Integer skinType;

    @ApiModelProperty(value = "行程卡片展示位置（0-买家首页轮播325x160；1-买家首页更多行程335x165；2-行程详情288x110）")
    private Integer cardType;

    @ApiModelProperty(value = "图片分辨率（S:drawable-hdpi; X:drawable-xhdpi，IOS-1X； XX:drawable-xxhdpi，IOS-2X； XXX:drawable-xxxhdpi）")
    private String cardSize;

    @ApiModelProperty(value = "查看设备（1-安卓，2-IOS，4-PC）")
    private Integer deviceType;

    @ApiModelProperty(value = "图片地址")
    private String imgUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
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

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getCardSize() {
        return cardSize;
    }

    public void setCardSize(String cardSize) {
        this.cardSize = cardSize;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}