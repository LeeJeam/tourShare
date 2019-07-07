package com.xmyy.pay.allinpay.request;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class SignalAgentPayParams extends YunParams implements Serializable {

    private String bizOrderNo;

    private JSONArray collectPayList;

    private String bizUserId;

    private String accountSetNo;

    private String backUrl;

    private JSONObject payToBankCardInfo;

    private Long amount;

    private Long fee;

    private JSONArray splitRuleList;

    private Long goodsType;

    private String goodsNo;

    private String tradeCode;

    private String summary;

    private String extendInfo;

    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public void setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
    }

    public JSONArray getCollectPayList() {
        return collectPayList;
    }

    public void setCollectPayList(JSONArray collectPayList) {
        this.collectPayList = collectPayList;
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

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public JSONObject getPayToBankCardInfo() {
        return payToBankCardInfo;
    }

    public void setPayToBankCardInfo(JSONObject payToBankCardInfo) {
        this.payToBankCardInfo = payToBankCardInfo;
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

    public JSONArray getSplitRuleList() {
        return splitRuleList;
    }

    public void setSplitRuleList(JSONArray splitRuleList) {
        this.splitRuleList = splitRuleList;
    }

    public Long getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Long goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
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

    public SignalAgentPayParams() {
    }

    public SignalAgentPayParams(String bizOrderNo, JSONArray collectPayList, String bizUserId, String accountSetNo, String backUrl, JSONObject payToBankCardInfo, Long amount, Long fee, JSONArray splitRuleList, Long goodsType, String goodsNo, String tradeCode, String summary, String extendInfo) {
        this.bizOrderNo = bizOrderNo;
        this.collectPayList = collectPayList;
        this.bizUserId = bizUserId;
        this.accountSetNo = accountSetNo;
        this.backUrl = backUrl;
        this.payToBankCardInfo = payToBankCardInfo;
        this.amount = amount;
        this.fee = fee;
        this.splitRuleList = splitRuleList;
        this.goodsType = goodsType;
        this.goodsNo = goodsNo;
        this.tradeCode = tradeCode;
        this.summary = summary;
        this.extendInfo = extendInfo;
    }

    @Override
    public String toString() {
        return "SignalAgentPay{" +
                "bizOrderNo='" + bizOrderNo + '\'' +
                ", collectPayList=" + collectPayList +
                ", bizUserId='" + bizUserId + '\'' +
                ", accountSetNo='" + accountSetNo + '\'' +
                ", backUrl='" + backUrl + '\'' +
                ", payToBankCardInfo=" + payToBankCardInfo +
                ", amount=" + amount +
                ", fee=" + fee +
                ", splitRuleList=" + splitRuleList +
                ", goodsType=" + goodsType +
                ", goodsNo='" + goodsNo + '\'' +
                ", tradeCode='" + tradeCode + '\'' +
                ", summary='" + summary + '\'' +
                ", extendInfo='" + extendInfo + '\'' +
                '}';
    }
}
