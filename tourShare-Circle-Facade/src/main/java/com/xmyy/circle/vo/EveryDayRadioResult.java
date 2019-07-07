package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;


@ApiModel(value ="今日提醒出参对象")
public class EveryDayRadioResult implements Serializable{

    @ApiModelProperty(value = "推送内容")
    private String content;

    @ApiModelProperty(value = "今日提醒消息ID")
    private Long radioId;

    @ApiModelProperty(value = "推送时间")
    private Date createTime;

    @ApiModelProperty(value = "用户ID")
    private Long memberId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getRadioId() {
        return radioId;
    }

    public void setRadioId(Long radioId) {
        this.radioId = radioId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

}
