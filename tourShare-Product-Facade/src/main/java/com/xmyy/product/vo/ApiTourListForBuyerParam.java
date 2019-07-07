package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel("买家端更多行程入参")
public class ApiTourListForBuyerParam implements Serializable {

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "分页页号，默认1")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小，默认10")
    private Integer size = 10;

    @ApiModelProperty(value = "买家标签，多个逗号分开")
    private String tags;

    @ApiModelProperty(value = "买家标签List", hidden = true)
    private List<String> tagsGroup;

    @ApiModelProperty(value = "目的地国际码")
    private String destRegionCode;

    @ApiModelProperty(value = "目的地国际码List", hidden = true)
    private List<String> destRegionCodes;

    @ApiModelProperty(value = "大洲编码，当查询各大洲下的‘全部’时，传此参数，传此参数时不传destRegionCode")
    private String continentCode;

    @ApiModelProperty(value = "是否背包客（0否，1是）", hidden = true)
    private Integer isPacker;

    @ApiModelProperty(value = "排序类型（0-智能排序；1-行程时间；2-买手的评分，信用值；3-买手的交易量）")
    private Integer sortType = 0;

    @ApiModelProperty(value = "买手类型（0-全部买手，默认；1-自营买手；2-大众买手；3-背包客")
    private Integer sellerType = 0;

    @ApiModelProperty(value = "是否自营买手", hidden = true)
    private Integer isSelf;

    @ApiModelProperty(value = "行程卡片展示位置：更多行程", hidden = true)
    private Integer cardType = 1;

    @ApiModelProperty(value = "皮肤类型（0-城市风；1-插画风），默认0")
    private Integer skinType = 0;

    @ApiModelProperty(value = "行程卡片分辨率（S:drawable-hdpi; X:drawable-xhdpi，IOS-1X；XX:drawable-xxhdpi，IOS-2X；XXX:drawable-xxxhdpi)，默认XX")
    private String cardSize = "XX";

    @NotNull
    @ApiModelProperty(value = "查看设备（1-安卓，2-IOS，4-PC）", required = true)
    private Integer deviceType;

    @ApiModelProperty(value = "行程查询方式（0-只查行程表，1-查行程表关联买手表）", hidden = true)
    private Integer queryType = 0;

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

    public String getDestRegionCode() {
        return destRegionCode;
    }

    public void setDestRegionCode(String destRegionCode) {
        this.destRegionCode = destRegionCode;
    }

    public List<String> getDestRegionCodes() {
        return destRegionCodes;
    }

    public void setDestRegionCodes(List<String> destRegionCodes) {
        this.destRegionCodes = destRegionCodes;
    }

    public String getContinentCode() {
        return continentCode;
    }

    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public Integer getSellerType() {
        return sellerType;
    }

    public void setSellerType(Integer sellerType) {
        this.sellerType = sellerType;
    }

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
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

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }
}