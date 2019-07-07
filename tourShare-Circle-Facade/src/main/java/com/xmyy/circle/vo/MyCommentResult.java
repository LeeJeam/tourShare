package com.xmyy.circle.vo;

import com.xmyy.circle.model.DgComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "我的评论")
public class MyCommentResult extends DgComment implements Serializable {

    @ApiModelProperty(value = "动态内容")
    private String circleContent;

    @ApiModelProperty(value = "动态视频路径")
    private String videoRsurl;

    @ApiModelProperty(value = "动态封面地址(多个以英文逗号隔开)")
    private String coverRsurl;

    @ApiModelProperty(value = "动态标题")
    private String title;

    @ApiModelProperty(value = "创建时间,比如：58分钟前")
    private String createTimeStr;

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getCircleContent() {
        return circleContent;
    }

    public void setCircleContent(String circleContent) {
        this.circleContent = circleContent;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
