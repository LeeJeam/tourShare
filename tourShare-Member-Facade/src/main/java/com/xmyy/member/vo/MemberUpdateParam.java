package com.xmyy.member.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "修改个人信息入参，各参数信息不为空就修改")
public class MemberUpdateParam implements Serializable {

    @JsonIgnore
    private Long id;

    @ApiModelProperty(value = "用户个性签名")
    private String personalizedSignature;

    @ApiModelProperty(value = "常去的地方；多个以英文逗号隔开")
    private String oftenPlace;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像路径")
    private String avatarRsurl;

    @ApiModelProperty(value = "分类标签；多个以英文逗号隔开")
    private String classifyTags;

    @ApiModelProperty(value = "居住国家")
    private String liveCountry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonalizedSignature() {
        return personalizedSignature;
    }

    public void setPersonalizedSignature(String personalizedSignature) {
        this.personalizedSignature = personalizedSignature;
    }

    public String getOftenPlace() {
        return oftenPlace;
    }

    public void setOftenPlace(String oftenPlace) {
        this.oftenPlace = oftenPlace;
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

    public String getClassifyTags() {
        return classifyTags;
    }

    public void setClassifyTags(String classifyTags) {
        this.classifyTags = classifyTags;
    }

    public String getLiveCountry() {
        return liveCountry;
    }

    public void setLiveCountry(String liveCountry) {
        this.liveCountry = liveCountry;
    }
}
