package com.xmyy.common.vo;

import java.io.Serializable;

public class MemberInfo implements Serializable {

    private Long id;
    private String uuid;
    private Long time;
    private String realName;
    private String nickName;
    private String avatarRsurl;
    private String oftenPlace;
    private Integer realState;
    private String mobile;
    private Integer isPacker;
    private Integer isSelf;
    private Integer trustValue;
    private Integer isPassIdentity;
    private Integer isPassPassport;
    private Integer isPassZhima;
    private String isPassIdentityLabel;
    private String isPassPassportLabel;
    private String isPassZhimaLabel;
    private String idCardNumber;
    private Integer loginSource;
    private String bindPhone;
    private Long payPasswordId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarRsurl() {
        return avatarRsurl;
    }

    public void setAvatarRsurl(String avatarRsurl) {
        this.avatarRsurl = avatarRsurl;
    }

    public String getOftenPlace() {
        return oftenPlace;
    }

    public void setOftenPlace(String oftenPlace) {
        this.oftenPlace = oftenPlace;
    }

    public Integer getRealState() {
        return realState;
    }

    public void setRealState(Integer realState) {
        this.realState = realState;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }

    public Integer getTrustValue() {
        return trustValue;
    }

    public void setTrustValue(Integer trustValue) {
        this.trustValue = trustValue;
    }

    public Integer getIsPassIdentity() {
        return isPassIdentity;
    }

    public void setIsPassIdentity(Integer isPassIdentity) {
        this.isPassIdentity = isPassIdentity;
    }

    public Integer getIsPassPassport() {
        return isPassPassport;
    }

    public void setIsPassPassport(Integer isPassPassport) {
        this.isPassPassport = isPassPassport;
    }

    public Integer getIsPassZhima() {
        return isPassZhima;
    }

    public void setIsPassZhima(Integer isPassZhima) {
        this.isPassZhima = isPassZhima;
    }

    public String getIsPassIdentityLabel() {
        return isPassIdentityLabel;
    }

    public void setIsPassIdentityLabel(String isPassIdentityLabel) {
        this.isPassIdentityLabel = isPassIdentityLabel;
    }

    public String getIsPassPassportLabel() {
        return isPassPassportLabel;
    }

    public void setIsPassPassportLabel(String isPassPassportLabel) {
        this.isPassPassportLabel = isPassPassportLabel;
    }

    public String getIsPassZhimaLabel() {
        return isPassZhimaLabel;
    }

    public void setIsPassZhimaLabel(String isPassZhimaLabel) {
        this.isPassZhimaLabel = isPassZhimaLabel;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public Integer getLoginSource() {
        return loginSource;
    }

    public void setLoginSource(Integer loginSource) {
        this.loginSource = loginSource;
    }

    public String getBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(String bindPhone) {
        this.bindPhone = bindPhone;
    }

    public Long getPayPasswordId() {
        return payPasswordId;
    }

    public void setPayPasswordId(Long payPasswordId) {
        this.payPasswordId = payPasswordId;
    }
}
