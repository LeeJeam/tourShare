package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "动态统计返回对象")
public class CircleCountResult implements Serializable {

    @ApiModelProperty(value = "笔记总数")
    private Integer noteConut;

    @ApiModelProperty(value = "视频总数")
    private Integer videoCount;

    public Integer getNoteConut() {
        return noteConut;
    }

    public void setNoteConut(Integer noteConut) {
        this.noteConut = noteConut;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }
}
