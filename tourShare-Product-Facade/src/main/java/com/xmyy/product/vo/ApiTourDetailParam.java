package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "行程详情入参")
public class ApiTourDetailParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "行程ID", required = true)
    private Long id;

    @ApiModelProperty(value = "皮肤类型（0-城市风；1-插画风），默认0")
    private Integer skinType = 0;

    @ApiModelProperty(value = "行程卡片展示位置（0-买家首页轮播325x160；1-买家首页更多行程335x165；2-行程详情288x110）", hidden = true)
    private Integer cardType = 2;

    @ApiModelProperty(value = "行程卡片分辨率（S:drawable-hdpi; X:drawable-xhdpi，IOS-1X； XX:drawable-xxhdpi，IOS-2X； XXX:drawable-xxxhdpi），默认XX")
    private String cardSize = "XX";

    @NotNull
    @ApiModelProperty(value = "查看设备（1-安卓，2-IOS，4-PC）", required = true)
    private Integer deviceType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}