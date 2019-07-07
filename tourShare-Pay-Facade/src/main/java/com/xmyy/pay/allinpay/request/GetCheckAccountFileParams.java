package com.xmyy.pay.allinpay.request;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class GetCheckAccountFileParams extends YunParams implements Serializable {

    private String date;

    private Long fileType;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getFileType() {
        return fileType;
    }

    public void setFileType(Long fileType) {
        this.fileType = fileType;
    }

    public GetCheckAccountFileParams() {
    }

    public GetCheckAccountFileParams(String date, Long fileType) {
        this.date = date;
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "GetCheckAccountFileParams{" +
                "date='" + date + '\'' +
                ", fileType=" + fileType +
                '}';
    }
}
