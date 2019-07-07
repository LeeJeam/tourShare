package com.xmyy.pay.vo;

import java.io.Serializable;

public class InExpDetailDto implements Serializable {

    private String tradeNo;

    private String accountSetName;

    private String changeTime;

    private Long curAmount;

    private Long oriAmount;

    private Long chgAmount;

    private Long curFreezenAmount;

    private String bizOrderNo;

    private String remark;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getAccountSetName() {
        return accountSetName;
    }

    public void setAccountSetName(String accountSetName) {
        this.accountSetName = accountSetName;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public Long getCurAmount() {
        return curAmount;
    }

    public void setCurAmount(Long curAmount) {
        this.curAmount = curAmount;
    }

    public Long getOriAmount() {
        return oriAmount;
    }

    public void setOriAmount(Long oriAmount) {
        this.oriAmount = oriAmount;
    }

    public Long getChgAmount() {
        return chgAmount;
    }

    public void setChgAmount(Long chgAmount) {
        this.chgAmount = chgAmount;
    }

    public Long getCurFreezenAmount() {
        return curFreezenAmount;
    }

    public void setCurFreezenAmount(Long curFreezenAmount) {
        this.curFreezenAmount = curFreezenAmount;
    }

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public void setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
