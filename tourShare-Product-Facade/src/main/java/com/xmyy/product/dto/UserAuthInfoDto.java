package com.xmyy.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户认证信息
 *
 * @author wangzejun
 */
@ApiModel("用户认证信息")
public class UserAuthInfoDto implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "真实名字")
    private String realName;

    @ApiModelProperty(value = "用户头像")
    private String avatarRsurl;

    @ApiModelProperty(value = "手机号码")
    private String mobilePhone;

    @ApiModelProperty(value = "常去的地方")
    private String usualRegion;

    @ApiModelProperty(value = "是否身份实名认证（1是，0否）")
    private Integer isAuthRealName;

    @ApiModelProperty(value = "是否芝麻认证（1是，0否）")
    private Integer isAuthesameCredit;

    @ApiModelProperty(value = "是否护照实名认证（1是，0否）")
    private Integer isAuthPassport;

    @ApiModelProperty(value = "认证结果（0-没有认证 1-认证通过）")
    private Integer authResult = 1;

    @ApiModelProperty(value = "认证信息")
    private String authMsg;

    @ApiModelProperty(value = "是否背包客")
    private Integer isPacker;

    @ApiModelProperty(value = "用户uuid")
    private String uuid;

    @ApiModelProperty(value = "是否自营买手（0-大众买手，1-自营买手）")
    private Integer isSelf;

    @ApiModelProperty(value = "认证状态（0-提交认证，50-认证审核通过，-50-认证审核不通过）")
    private Integer realState;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getUsualRegion() {
        return usualRegion;
    }

    public void setUsualRegion(String usualRegion) {
        this.usualRegion = usualRegion;
    }

    public Integer getIsAuthRealName() {
        return isAuthRealName;
    }

    public void setIsAuthRealName(Integer isAuthRealName) {
        this.isAuthRealName = isAuthRealName;
    }

    public Integer getIsAuthesameCredit() {
        return isAuthesameCredit;
    }

    public void setIsAuthesameCredit(Integer isAuthesameCredit) {
        this.isAuthesameCredit = isAuthesameCredit;
    }

    public Integer getIsAuthPassport() {
        return isAuthPassport;
    }

    public void setIsAuthPassport(Integer isAuthPassport) {
        this.isAuthPassport = isAuthPassport;
    }

    public Integer getAuthResult() {
        return authResult;
    }

    public void setAuthResult(Integer authResult) {
        this.authResult = authResult;
    }

    public String getAuthMsg() {
        return authMsg;
    }

    public void setAuthMsg(String authMsg) {
        this.authMsg = authMsg;
    }

    public String getAvatarRsurl() {
        return avatarRsurl;
    }

    public void setAvatarRsurl(String avatarRsurl) {
        this.avatarRsurl = avatarRsurl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Integer getRealState() {
        return realState;
    }

    public void setRealState(Integer realState) {
        this.realState = realState;
    }
}