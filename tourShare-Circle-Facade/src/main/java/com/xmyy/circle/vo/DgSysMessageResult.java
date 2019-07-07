package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "系统消息")
public class DgSysMessageResult implements Serializable {

    @ApiModelProperty(value = "系统消息ID")
    private Long messageId;

    @ApiModelProperty(value = "系统消息标题")
    private String title;

    @ApiModelProperty(value = "系统消息内容")
    private String content;

    @ApiModelProperty(value = "系统消息缩略图")
    private String cover;

    @ApiModelProperty(value = "图片集合（多张图片用逗号隔开）")
    private String images;

    @ApiModelProperty(value = "接收时间")
    private Date createTime;

    @ApiModelProperty(value = "是否已读（-1未读；1已读）")
    private Integer read;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }
}
