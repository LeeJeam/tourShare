package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "买家首页入参")
public class BuyerIndexParam implements Serializable {

    @ApiModelProperty(value = "国家码，查询全部全部不传")
    private String countryShortCode;

    @ApiModelProperty(value = "买家ID", hidden = true)
    private Long buyerId;

    @ApiModelProperty(value = "皮肤类型（0-城市风，1-插画风），默认0")
    private Integer skinType = 0;

    @ApiModelProperty(value = "行程卡片分辨率（S:drawable-hdpi; X:drawable-xhdpi，IOS-1X；XX:drawable-xxhdpi，IOS-2X；XXX:drawable-xxxhdpi），默认XX")
    private String cardSize = "XX";

    @NotNull(message = "deviceType不能为空")
    @ApiModelProperty(value = "查看设备（1-安卓，2-IOS，4-PC）", required = true)
    private Integer deviceType;

    public String getCountryShortCode() {
        return countryShortCode;
    }

    public void setCountryShortCode(String countryShortCode) {
        this.countryShortCode = countryShortCode;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
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

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }
}