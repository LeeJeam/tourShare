package com.xmyy.livevideo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class LiveVideoImHistoryParam implements Serializable {

    @JsonProperty(value = "ChatType")
    private String ChatType;

    @JsonProperty(value = "MsgTime")
    private String MsgTime ;

    public String getChatType() {
        return ChatType;
    }

    public void setChatType(String chatType) {
        ChatType = chatType;
    }

    public String getMsgTime() {
        return MsgTime;
    }

    public void setMsgTime(String msgTime) {
        MsgTime = msgTime;
    }
}
