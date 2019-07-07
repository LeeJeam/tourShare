package com.xmyy.pay.allinpay.request;

import java.io.Serializable;

/**
 * Created by Simon on 2018/9/5.
 */
public class ApplyBindBankCardParams extends YunParams implements Serializable {

    private String bizUserId;

    private String cardNo;

    private String phone;

    private String name;

    private Long cardCheck;

    private Long identityType;

    private String identityNo;

    private String validate;

    private String cvv2;

    private Boolean isSafeCard;

    private String unionBank;

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCardCheck() {
        return cardCheck;
    }

    public void setCardCheck(Long cardCheck) {
        this.cardCheck = cardCheck;
    }

    public Long getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Long identityType) {
        this.identityType = identityType;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public Boolean getSafeCard() {
        return isSafeCard;
    }

    public void setSafeCard(Boolean safeCard) {
        isSafeCard = safeCard;
    }

    public String getUnionBank() {
        return unionBank;
    }

    public void setUnionBank(String unionBank) {
        this.unionBank = unionBank;
    }

    public ApplyBindBankCardParams() {
    }

    public ApplyBindBankCardParams(String bizUserId, String cardNo, String phone, String name, Long cardCheck, Long identityType, String identityNo, String validate, String cvv2, Boolean isSafeCard, String unionBank) {
        this.bizUserId = bizUserId;
        this.cardNo = cardNo;
        this.phone = phone;
        this.name = name;
        this.cardCheck = cardCheck;
        this.identityType = identityType;
        this.identityNo = identityNo;
        this.validate = validate;
        this.cvv2 = cvv2;
        this.isSafeCard = isSafeCard;
        this.unionBank = unionBank;
    }

    @Override
    public String toString() {
        return "ApplyBindBankCardParams{" +
                "bizUserId='" + bizUserId + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", cardCheck=" + cardCheck +
                ", identityType=" + identityType +
                ", identityNo='" + identityNo + '\'' +
                ", validate='" + validate + '\'' +
                ", cvv2='" + cvv2 + '\'' +
                ", isSafeCard=" + isSafeCard +
                ", unionBank='" + unionBank + '\'' +
                '}';
    }
}
