package com.xmyy.circle.vo;

import com.xmyy.circle.model.UcDynamicCircle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "笔记/视频列表")
public class CirclePageResult extends UcDynamicCircle implements Serializable {

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatarRsurl;

    @ApiModelProperty(value = "买手类型（1资深买手；0大众买手）")
    private Integer isSelf;

    @ApiModelProperty(value = "买手类型（1资深买手；0大众买手）")
    private String isSelfStr;

    @ApiModelProperty(value = "创建时间,比如：58分钟前")
    private String createTimeStr;

    @ApiModelProperty(value = "是否点赞（1是，0否）")
    private Integer isPraised;

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }

    public String getIsSelfStr() {
        return isSelfStr;
    }

    public void setIsSelfStr(String isSelfStr) {
        this.isSelfStr = isSelfStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
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
