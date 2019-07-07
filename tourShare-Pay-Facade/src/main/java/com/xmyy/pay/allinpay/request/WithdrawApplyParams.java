package com.xmyy.pay.allinpay.request;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class WithdrawApplyParams extends YunParams implements Serializable {

    private String bizOrderNo;

    private String bizUserId;

    private String accountSetNo;

    private Long amount;

    private Long fee;

    private Long validateType;

    private String backUrl;

    private String ordErexpireDatetime;

    private JSONObject payMethod;

    private String bankCardNo;

    private Long bankCardPro;

    private String withdrawType;

    private String industryCode;

    private String industryName;

    private Long source;

    private String summary;

    private String extendInfo;

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public void setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
    }

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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Long getValidateType() {
        return validateType;
    }

    public void setValidateType(Long validateType) {
        this.validateType = validateType;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getOrdErexpireDatetime() {
        return ordErexpireDatetime;
    }

    public void setOrdErexpireDatetime(String ordErexpireDatetime) {
        this.ordErexpireDatetime = ordErexpireDatetime;
    }

    public JSONObject getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(JSONObject payMethod) {
        this.payMethod = payMethod;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public Long getBankCardPro() {
        return bankCardPro;
    }

    public void setBankCardPro(Long bankCardPro) {
        this.bankCardPro = bankCardPro;
    }

    public String getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(String withdrawType) {
        this.withdrawType = withdrawType;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public WithdrawApplyParams() {
    }

    public WithdrawApplyParams(String bizOrderNo, String bizUserId, String accountSetNo, Long amount, Long fee, Long validateType, String backUrl, String ordErexpireDatetime, JSONObject payMethod, String bankCardNo, Long bankCardPro, String withdrawType, String industryCode, String industryName, Long source, String summary, String extendInfo) {
        this.bizOrderNo = bizOrderNo;
        this.bizUserId = bizUserId;
        this.accountSetNo = accountSetNo;
        this.amount = amount;
        this.fee = fee;
        this.validateType = validateType;
        this.backUrl = backUrl;
        this.ordErexpireDatetime = ordErexpireDatetime;
        this.payMethod = payMethod;
        this.bankCardNo = bankCardNo;
        this.bankCardPro = bankCardPro;
        this.withdrawType = withdrawType;
        this.industryCode = industryCode;
        this.industryName = industryName;
        this.source = source;
        this.summary = summary;
        this.extendInfo = extendInfo;
    }

    @Override
    public String toString() {
        return "WithdrawApplyParams{" +
                "bizOrderNo='" + bizOrderNo + '\'' +
                ", bizUserId='" + bizUserId + '\'' +
                ", accountSetNo='" + accountSetNo + '\'' +
                ", amount=" + amount +
                ", fee=" + fee +
                ", validateType=" + validateType +
                ", backUrl='" + backUrl + '\'' +
                ", ordErexpireDatetime='" + ordErexpireDatetime + '\'' +
                ", payMethod=" + payMethod +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", bankCardPro=" + bankCardPro +
                ", withdrawType='" + withdrawType + '\'' +
                ", industryCode='" + industryCode + '\'' +
                ", industryName='" + industryName + '\'' +
                ", source=" + source +
                ", summary='" + summary + '\'' +
                ", extendInfo='" + extendInfo + '\'' +
                '}';
    }
}
