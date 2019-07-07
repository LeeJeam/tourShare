package com.xmyy.common.push;

import java.io.Serializable;

/**
 * 信鸽推送，消息接收者实体
 */
public class PushUser implements Serializable {

    //接受者UUID
    private String uuid;
    //当前用户登录设备类型：android，IOS
    private Integer deviceType;

    public PushUser(String uuid, Integer deviceType) {
        this.uuid = uuid;
        this.deviceType = deviceType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

}
