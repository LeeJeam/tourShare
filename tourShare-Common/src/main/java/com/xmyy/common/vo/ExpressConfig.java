package com.xmyy.common.vo;

import com.xmyy.common.util.PropertiesUtils;
import java.io.IOException;
import java.util.Map;

/**
 * 快递100参数配置
 *
 * @author wangzejun
 */
public class ExpressConfig {

    // 配置文件是express-100.properties
    private String freeKey;
    private String firmKey;
    private String customer;
    private String freeUrl;
    private String firmUrl;
    private String autoUrl;
    private String freeSwitch;

    /**
     * 单例模式
     * 读取配置文件，初始化配置类
     */
    private static ExpressConfig expressConfig = new ExpressConfig();

    private ExpressConfig() {
        try {
            Map<String, String> configMap = PropertiesUtils.getMapByPath("config/express-100.properties");
            this.freeKey=configMap.get("freeKey");
            this.freeUrl=configMap.get("freeUrl");
            this.firmKey=configMap.get("firmKey");
            this.firmUrl=configMap.get("firmUrl");
            this.autoUrl=configMap.get("autoUrl");
            this.customer= configMap.get("customer");
            this.freeSwitch= configMap.get("freeSwitch");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ExpressConfig getInstance() {
        return expressConfig;
    }

    public String getFreeKey() {
        return freeKey;
    }

    public void setFreeKey(String freeKey) {
        this.freeKey = freeKey;
    }

    public String getFirmKey() {
        return firmKey;
    }

    public void setFirmKey(String firmKey) {
        this.firmKey = firmKey;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getFreeUrl() {
        return freeUrl;
    }

    public void setFreeUrl(String freeUrl) {
        this.freeUrl = freeUrl;
    }

    public String getFirmUrl() {
        return firmUrl;
    }

    public void setFirmUrl(String firmUrl) {
        this.firmUrl = firmUrl;
    }

    public String getAutoUrl() {
        return autoUrl;
    }

    public void setAutoUrl(String autoUrl) {
        this.autoUrl = autoUrl;
    }

    public String getFreeSwitch() {
        return freeSwitch;
    }

    public void setFreeSwitch(String freeSwitch) {
        this.freeSwitch = freeSwitch;
    }
}