package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "行程卡片添加")
public class ApiTourCardAddParam implements Serializable {

    @ApiModelProperty(value = "卡片名称")
    private String cardName;

    @ApiModelProperty(value = "卡片编码")
    private String cardCode;

    @ApiModelProperty(value = "皮肤类型")
    private Integer skinType;

    @ApiModelProperty(value = "行程卡片展示位置（0-买家首页行程轮播；1-买家首页更多行程；2-行程详情）")
    private Integer cardType = 2;

    @ApiModelProperty(value = "图片分辨率（S:drawable-hdpi; X:drawable-xhdpi，IOS-1X； XX:drawable-xxhdpi，IOS-2X； XXX:drawable-xxxhdpi），默认XX")
    private String cardSize = "XX";

    @ApiModelProperty(value = "查看设备（1-安卓，2-IOS，4-PC）", required = true)
    private Integer deviceType;

    @ApiModelProperty(value = "图片地址")
    private String imgUrl;

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