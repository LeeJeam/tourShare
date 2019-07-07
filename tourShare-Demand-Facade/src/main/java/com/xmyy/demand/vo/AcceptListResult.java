package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "获取接单人列表")
public class AcceptListResult implements Serializable {

    @ApiModelProperty( value = "买手/背包客ID")
    private Long id;

    @ApiModelProperty( value = "接单人是否背包客（0否，1是）")
    private Integer isPacker;

    @ApiModelProperty( value = "接单人UUID")
    private String uuid;

    @ApiModelProperty( value = "昵称")
    private String nickName;

    @ApiModelProperty( value = "头像")
    private String avatarRsurl;

    @ApiModelProperty( value = "信任值分数")
    private Integer trustValue;

    @ApiModelProperty( value = "常去地")
    private String oftenPlace;

    @ApiModelProperty( value = "行程编号")
    private Long tourId;

    @ApiModelProperty( value = "行程信息")
    private TourInfoResult tourInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarRsurl() {
        return avatarRsurl;
    }

    public void setAvatarRsurl(String avatarRsurl) {
        this.avatarRsurl = avatarRsurl;
    }

    public Integer getTrustValue() {
        return trustValue;
    }

    public void setTrustValue(Integer trustValue) {
        this.trustValue = trustValue;
    }

    public String getOftenPlace() {
        return oftenPlace;
    }

    public void setOftenPlace(String oftenPlace) {
        this.oftenPlace = oftenPlace;
    }

    public TourInfoResult getTourInfo() {
        return tourInfo;
    }

    public void setTourInfo(TourInfoResult tourInfo) {
        this.tourInfo = tourInfo;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
