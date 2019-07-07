package com.xmyy.pay.allinpay.request;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class DepositApplyParams extends YunParams implements Serializable {

    private String bizOrderNo;

    private String bizUserId;

    private String accountSetNo;

    private Long amount;

    private Long fee;

    private Long validateType;

    private String frontUrl;

    private String backUrl;

    private String ordErexpireDatetime;

    private JSONObject payMethod;

    private String goodsName;

    private String goodsDesc;

    private String industryCode;

    private String industryName;

    private Long source;

    private String summary;

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

    public String getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
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

    public DepositApplyParams() {
    }

    public DepositApplyParams(String bizOrderNo, String bizUserId, String accountSetNo, Long amount, Long fee, Long validateType, String frontUrl, String backUrl, String ordErexpireDatetime, JSONObject payMethod, String goodsName, String goodsDesc, String industryCode, String industryName, Long source, String summary) {
        this.bizOrderNo = bizOrderNo;
        this.bizUserId = bizUserId;
        this.accountSetNo = accountSetNo;
        this.amount = amount;
        this.fee = fee;
        this.validateType = validateType;
        this.frontUrl = frontUrl;
        this.backUrl = backUrl;
        this.ordErexpireDatetime = ordErexpireDatetime;
        this.payMethod = payMethod;
        this.goodsName = goodsName;
        this.goodsDesc = goodsDesc;
        this.industryCode = industryCode;
        this.industryName = industryName;
        this.source = source;
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "DepositApplyParams{" +
                "bizOrderNo='" + bizOrderNo + '\'' +
                ", bizUserId='" + bizUserId + '\'' +
                ", accountSetNo='" + accountSetNo + '\'' +
                ", amount=" + amount +
                ", fee=" + fee +
                ", validateType=" + validateType +
                ", frontUrl='" + frontUrl + '\'' +
                ", backUrl='" + backUrl + '\'' +
                ", ordErexpireDatetime='" + ordErexpireDatetime + '\'' +
                ", payMethod=" + payMethod +
                ", goodsName='" + goodsName + '\'' +
                ", goodsDesc='" + goodsDesc + '\'' +
                ", industryCode='" + industryCode + '\'' +
                ", industryName='" + industryName + '\'' +
                ", source=" + source +
                ", summary='" + summary + '\'' +
                '}';
    }
}
