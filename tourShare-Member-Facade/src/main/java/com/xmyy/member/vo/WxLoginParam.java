package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(value = "微信登陆入参")
public class WxLoginParam extends MemberCommon implements Serializable {

    @ApiModelProperty(value = "微信用户唯一标识", required = true)
    @NotBlank(message = "微信用户标识不能为空")
    private String  openId;

    @ApiModelProperty(value = "接口调用凭证")
    private String accessToken;

    @ApiModelProperty(value = "头像")
    private String headimgurl;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别(1男，2女)")
    private Integer gender;

    @ApiModelProperty(value = "手机号码", required = true)
    @Pattern(regexp = "^(1\\d{10})$", message = "无效的手机号码")
    private String mobile;

    @ApiModelProperty(value = "登陆地")
    private String loginCountry;

    @ApiModelProperty(value = "登记来源端（1安卓，2苹果，3小程序，4PC）", required = true)
    @NotNull(message = "登陆来源端不能为空")
    private Integer loginSource;

    public Integer getLoginSource() {
        return loginSource;
    }

    public void setLoginSource(Integer loginSource) {
        this.loginSource = loginSource;
    }

    public String getLoginCountry() {
        return loginCountry;
    }

    public void setLoginCountry(String loginCountry) {
        this.loginCountry = loginCountry;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
