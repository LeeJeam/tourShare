package com.xmyy.pay.allinpay;


import com.xmyy.pay.allinpay.request.ServerHandler;

import java.io.Serializable;

public class YunRequest<T> implements Serializable {

    private String sysid;

    private String sign;

    private String timestamp;

    private String v;

    private String service;

    private String method;

    private T param;

    private String paramJson;

    private ServerHandler serverHandler;

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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    public ServerHandler getServerHandler() {
        return serverHandler;
    }

    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    public YunRequest(ServerHandler serverHandler,T param) {
        this.serverHandler = serverHandler;
        this.param=param;
    }

    @Override
    public String toString() {
        return "YunRequest{" +
                "sysid='" + sysid + '\'' +
                ", sign='" + sign + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", v='" + v + '\'' +
                ", service='" + service + '\'' +
                ", method='" + method + '\'' +
                ", param=" + param +
                '}';
    }
}
