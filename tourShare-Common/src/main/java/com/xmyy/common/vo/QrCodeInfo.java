package com.xmyy.common.vo;

import java.io.Serializable;

public class QrCodeInfo implements Serializable {

    private int type;       //1，买手，2，背包客
    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
