package com.xmyy.pay.allinpay.bean;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/6.
 */
public class OrderPayBean implements Serializable {

    private String orderNo;

    private String bizOrderNo;

    private Long amount;

    private String payDatetime;

    private String buyerBizUserId;

    private Long refundWhereabouts;

    private Long payWhereabouts;

    private Long remainAmount;

    private String acct;

    private String accttype;

    private String termno;

    private String cusid;

    private String extendInfo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public void setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPayDatetime() {
        return payDatetime;
    }

    public void setPayDatetime(String payDatetime) {
        this.payDatetime = payDatetime;
    }

    public String getBuyerBizUserId() {
        return buyerBizUserId;
    }

    public void setBuyerBizUserId(String buyerBizUserId) {
        this.buyerBizUserId = buyerBizUserId;
    }

    public Long getRefundWhereabouts() {
        return refundWhereabouts;
    }

    public void setRefundWhereabouts(Long refundWhereabouts) {
        this.refundWhereabouts = refundWhereabouts;
    }

    public Long getPayWhereabouts() {
        return payWhereabouts;
    }

    public void setPayWhereabouts(Long payWhereabouts) {
        this.payWhereabouts = payWhereabouts;
    }

    public Long getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Long remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }

    public String getAccttype() {
        return accttype;
    }

    public void setAccttype(String accttype) {
        this.accttype = accttype;
    }

    public String getTermno() {
        return termno;
    }

    public void setTermno(String termno) {
        this.termno = termno;
    }

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }
}
