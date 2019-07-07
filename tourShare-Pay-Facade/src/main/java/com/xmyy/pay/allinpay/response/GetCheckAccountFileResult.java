package com.xmyy.pay.allinpay.response;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class GetCheckAccountFileResult implements Serializable {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "GetCheckAccountFileResult{" +
                "url='" + url + '\'' +
                '}';
    }
}
