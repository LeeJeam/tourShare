package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel("买家端首页轮播行程入参")
public class ApiTourInBuyerIndexParam implements Serializable {

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "分页页号，默认1")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小，默认5")
    private Integer size = 5;

    @ApiModelProperty(value = "匹配地区", hidden = true)
    private String matchRegion;

    @ApiModelProperty(value = "买家标签，逗号分隔")
    private String tags;

    @ApiModelProperty(value = "标签数组，前端不需要传", hidden = true)
    private List<String> tagsGroup;

    @ApiModelProperty(value = "是否背包客（0否，1是）", hidden = true)
    private Integer isPacker;

    @ApiModelProperty(value = "行程卡片展示位置：0-买家首页轮播", hidden = true)
    private Integer cardType = 0;

    @ApiModelProperty(value = "皮肤类型（0-城市风，1-插画风），默认0")
    private Integer skinType = 0;

    @ApiModelProperty(value = "行程卡片分辨率（S:drawable-hdpi; X:drawable-xhdpi，IOS-1X；XX:drawable-xxhdpi，IOS-2X；XXX:drawable-xxxhdpi），默认XX")
    private String cardSize = "XX";

    @ApiModelProperty(value = "查看设备（1-安卓，2-IOS，4-PC）")
    private Integer deviceType;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getMatchRegion() {
        return matchRegion;
    }

    public void setMatchRegion(String matchRegion) {
        this.matchRegion = matchRegion;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<String> getTagsGroup() {
        return tagsGroup;
    }

    public void setTagsGroup(List<String> tagsGroup) {
        this.tagsGroup = tagsGroup;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
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