package com.xmyy.member.vo;

import java.io.Serializable;
import java.util.List;

public class PhoneBelongResult implements Serializable {

    private String ret;

    private String mobile;

    private List<String> data;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }


}
