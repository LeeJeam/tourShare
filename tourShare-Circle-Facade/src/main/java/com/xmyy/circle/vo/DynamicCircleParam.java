package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "笔记/视频发布")
public class DynamicCircleParam implements Serializable {

    @ApiModelProperty(value = "买手ID")
    private Long sellerId;

    @ApiModelProperty(value = "标题")
    private String title;

    @NotNull
    @ApiModelProperty(value = "类型（1笔记，2视频）", required = true)
    private Integer typeId;

    @ApiModelProperty(value = "笔记内容")
    private String content;

    @ApiModelProperty(value = "视频路径")
    private String videoRsurl;

    @ApiModelProperty(value = "封面地址(多个以英文逗号隔开)")
    private String coverRsurl;

    @NotBlank
    @ApiModelProperty(value = "分类标签(多个以英文逗号隔开)", required = true)
    private String classifyTags;

    @ApiModelProperty(value = "视频时长")
    private Integer videoTimes;

    public Integer getVideoTimes() {
        return videoTimes;
    }

    public void setVideoTimes(Integer videoTimes) {
        this.videoTimes = videoTimes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideoRsurl() {
        return videoRsurl;
    }

    public void setVideoRsurl(String videoRsurl) {
        this.videoRsurl = videoRsurl;
    }

    public String getCoverRsurl() {
        return coverRsurl;
    }

    public void setCoverRsurl(String coverRsurl) {
        this.coverRsurl = coverRsurl;
    }

    public String getClassifyTags() {
        return classifyTags;
    }

    public void setClassifyTags(String classifyTags) {
        this.classifyTags = classifyTags;
    }
}
