package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@ApiModel(value = "系统消息入参对象")
public class DgSysMessageParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "消息类型（1：系统消息，2：公告消息），默认1")
    private Integer messageType;

    @NotNull
    @ApiModelProperty(value = "消息标题")
    private String title;

    @NotNull
    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "缩略图")
    private String cover;

    @ApiModelProperty(value = "图片集合（多张图片用逗号隔开）")
    private String images;

    @NotNull
    @ApiModelProperty(value = "接收者用户类型（1买手，2买家，3背包客）")
    private Integer memberType;

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

}
