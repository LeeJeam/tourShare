package com.xmyy.pay.allinpay.request;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class ServerHandler extends YunParams implements Serializable {

    private String service;

    private String method;

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

    public ServerHandler(String service, String method) {
        this.service = service;
        this.method = method;
    }

    @Override
    public String toString() {
        return "ServiceHandler{" +
                "service='" + service + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
