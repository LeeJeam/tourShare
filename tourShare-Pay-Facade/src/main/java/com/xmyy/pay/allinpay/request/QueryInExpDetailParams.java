package com.xmyy.pay.allinpay.request;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class QueryInExpDetailParams extends YunParams implements Serializable {

    private String bizUserId;

    private String accountSetNo;

    private String dateStart;

    private String dateEnd;

    private Long startPosition;

    private Long queryNum;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public String getAccountSetNo() {
        return accountSetNo;
    }

    public void setAccountSetNo(String accountSetNo) {
        this.accountSetNo = accountSetNo;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Long getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Long startPosition) {
        this.startPosition = startPosition;
    }

    public Long getQueryNum() {
        return queryNum;
    }

    public void setQueryNum(Long queryNum) {
        this.queryNum = queryNum;
    }

    public QueryInExpDetailParams() {
    }

    public QueryInExpDetailParams(String bizUserId, String accountSetNo, String dateStart, String dateEnd, Long startPosition, Long queryNum) {
        this.bizUserId = bizUserId;
        this.accountSetNo = accountSetNo;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.startPosition = startPosition;
        this.queryNum = queryNum;
    }

    @Override
    public String toString() {
        return "QueryInExpDetailParams{" +
                "bizUserId='" + bizUserId + '\'' +
                ", accountSetNo='" + accountSetNo + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", startPosition=" + startPosition +
                ", queryNum=" + queryNum +
                '}';
    }
}
