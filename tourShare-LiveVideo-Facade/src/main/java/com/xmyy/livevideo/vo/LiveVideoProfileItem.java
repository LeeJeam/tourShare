package com.xmyy.livevideo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class LiveVideoProfileItem implements Serializable {

    private String Tag = "";
    private String Value = "";
    @JSONField(name = "Tag")
    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }
    @JSONField(name = "Value")
    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
