package com.xmyy.livevideo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "进群统计状态")
public class LiveVideoCountParam implements Serializable {

    @ApiModelProperty(value = "聊天室ID")
    private String roomId;

    @ApiModelProperty(value = "类型（1进入，0退出，2统计）")
    private Integer  type;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
