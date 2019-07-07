package com.xmyy.circle.vo;

import com.xmyy.circle.model.UcDynamicCircle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "动态圈")
public class CircleDetailResult extends UcDynamicCircle implements Serializable {

    @ApiModelProperty(value = "常去的地方")
    private String oftenPlace;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatarRsurl;

    @ApiModelProperty(value = "是否收藏（1是，0否）")
    private Integer isCollected;

    @ApiModelProperty(value = "是否点赞（1是，0否）")
    private Integer isPraised;

    @ApiModelProperty(value = "是否自营（1资深买手，0大众买手）")
    private Integer isSelf;

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }

    public Integer getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(Integer isCollected) {
        this.isCollected = isCollected;
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

    public Integer getIsPraised() {
        return isPraised;
    }

    public void setIsPraised(Integer isPraised) {
        this.isPraised = isPraised;
    }
}
