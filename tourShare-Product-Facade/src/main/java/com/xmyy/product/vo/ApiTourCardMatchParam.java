package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel("行程卡片查询参数")
public class ApiTourCardMatchParam implements Serializable {

    @ApiModelProperty(value = "卡片编码集合")
    private List<String> cardCodes = new ArrayList<>();

    @ApiModelProperty(value = "皮肤类型（0-城市风；1-插画风）")
    private Integer skinType;

    @ApiModelProperty(value = "行程卡片展示位置（0-买家首页轮播325x160；1-买家首页更多行程335x165；2-行程详情288x110）")
    private Integer cardType;

    @ApiModelProperty(value = "图片分辨率（S:drawable-hdpi; X:drawable-xhdpi，IOS-1X； XX:drawable-xxhdpi，IOS-2X； XXX:drawable-xxxhdpi）")
    private String cardSize;

    @ApiModelProperty(value = "查看设备（1-安卓，2-IOS，4-PC）")
    private Integer deviceType;

    public List<String> getCardCodes() {
        return cardCodes;
    }

    public void setCardCodes(List<String> cardCodes) {
        this.cardCodes = cardCodes;
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



