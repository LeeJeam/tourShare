package com.xmyy.livevideo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "修改房间状态字段")
public class LiveVideoStatusParam implements Serializable {

    @ApiModelProperty(value = "买家/买手ID")
    private Long memberId;

    @ApiModelProperty(value = "用户类型(1买手，2买家)")
    private Integer memberType;

    @ApiModelProperty(value = "聊天室ID")
    private String liveRoomId;

    @ApiModelProperty(value = "状态（0初始化，1直播中，2已结束）")
    private Integer status;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(String liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
