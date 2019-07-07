package com.xmyy.livevideo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel(value = "买手端解散群组")
public class LiveVideoCloseGroupParam implements Serializable {

    @JsonProperty(value = "GroupId")
    private String GroupId = "";

    @JsonIgnore
    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

}
