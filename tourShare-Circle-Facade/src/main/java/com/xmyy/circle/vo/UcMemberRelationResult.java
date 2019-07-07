package com.xmyy.circle.vo;

import com.xmyy.circle.dto.PtProductDto;
import com.xmyy.circle.model.UcMemberRelation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "关注列表")
public class UcMemberRelationResult extends UcMemberRelation implements Serializable {

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户头像")
    private String avatarRsurl;

    @ApiModelProperty(value = "居住国家")
    private String liveCountry;

    @ApiModelProperty(value = "分类标签")
    private String classifyTags;

    @ApiModelProperty(value = "推荐商品集合")
    private List<PtProductDto> ptProductList;

    @ApiModelProperty(value = "买手UUID")
    private String toMemberUUID;

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

    public String getLiveCountry() {
        return liveCountry;
    }

    public void setLiveCountry(String liveCountry) {
        this.liveCountry = liveCountry;
    }

    public List<PtProductDto> getPtProductList() {
        return ptProductList;
    }

    public void setPtProductList(List<PtProductDto> ptProductList) {
        this.ptProductList = ptProductList;
    }

    public String getClassifyTags() {
        return classifyTags;
    }

    public void setClassifyTags(String classifyTags) {
        this.classifyTags = classifyTags;
    }

    public String getToMemberUUID() {
        return toMemberUUID;
    }

    public void setToMemberUUID(String toMemberUUID) {
        this.toMemberUUID = toMemberUUID;
    }
}
