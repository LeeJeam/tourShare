package com.xmyy.pay.allinpay.request;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class ConsumeApplyParams extends YunParams implements Serializable {

    private String payerId;

    private String recieverId;

    private String bizOrderNo;

    private Long amount;

    private Long fee;

    private Long validateType;

    private JSONArray splitRule;

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

    private String extendInfo;

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
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

    public JSONArray getSplitRule() {
        return splitRule;
    }

    public void setSplitRule(JSONArray splitRule) {
        this.splitRule = splitRule;
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

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public ConsumeApplyParams() {
    }

    public ConsumeApplyParams(String payerId, String recieverId, String bizOrderNo, Long amount, Long fee, Long validateType, JSONArray splitRule, String frontUrl, String backUrl, String ordErexpireDatetime, JSONObject payMethod, String goodsName, String goodsDesc, String industryCode, String industryName, Long source, String summary, String extendInfo) {
        this.payerId = payerId;
        this.recieverId = recieverId;
        this.bizOrderNo = bizOrderNo;
        this.amount = amount;
        this.fee = fee;
        this.validateType = validateType;
        this.splitRule = splitRule;
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
        this.extendInfo = extendInfo;
    }

    @Override
    public String toString() {
        return "ConsumeApplyParams{" +
                "payerId='" + payerId + '\'' +
                ", recieverId='" + recieverId + '\'' +
                ", bizOrderNo='" + bizOrderNo + '\'' +
                ", amount=" + amount +
                ", fee=" + fee +
                ", validateType=" + validateType +
                ", splitRule=" + splitRule +
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
                ", extendInfo='" + extendInfo + '\'' +
                '}';
    }
}
