package com.xmyy.pay.allinpay.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/6.
 */
public class CallBackBean implements Serializable {

    private String sysid;

    private String sign;

    private String timestamp;

    private String v;

    private JSONObject rps;

    public String getSysid() {
        return sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public JSONObject getRps() {
        return rps;
    }

    public void setRps(JSONObject rps) {
        this.rps = rps;
    }
}
