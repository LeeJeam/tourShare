package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "会员个人基本信息")
public class MemberInfoResult implements Serializable {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "用户状态（1有效；3审核失败；5已锁；7待审核）")
    private Long userState;

    @ApiModelProperty(value = "会员类型（2企业会员；3个人会员）")
    private Long memberType;

    @ApiModelProperty(value = "云商通用户id")
    private String userId;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "县市")
    private String area;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "身份证号码，RSA 加密")
    private String identityCardNo;

    @ApiModelProperty(value = "是否绑定手机")
    private Boolean isPhoneChecked;

    @ApiModelProperty(value = "创建时间 yyyy-MM-dd HH:mm:ss")
    private String registerTime;

    @ApiModelProperty(value = "是否进行实名认证")
    private Boolean isIdentityChecked;

    @ApiModelProperty(value = "实名认证时间 yyyy-MM-dd HH:mm:ss")
    private String realNameTime;

    @ApiModelProperty(value = "访问终端类型（1-Mobile；2-PC）")
    private Long source;

    @ApiModelProperty(value = "商户系统用户标识，商户系统中唯一编号（UUID）")
    private String bizUserId;

    @ApiModelProperty(value = "是否已设置支付密码")
    private Boolean isSetPayPwd;

    @ApiModelProperty(value = "是否已签电子协议")
    private Boolean isSignContract;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserState() {
        return userState;
    }

    public void setUserState(Long userState) {
        this.userState = userState;
    }

    public Long getMemberType() {
        return memberType;
    }

    public void setMemberType(Long memberType) {
        this.memberType = memberType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentityCardNo() {
        return identityCardNo;
    }

    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo = identityCardNo;
    }

    public Boolean getPhoneChecked() {
        return isPhoneChecked;
    }

    public void setPhoneChecked(Boolean phoneChecked) {
        isPhoneChecked = phoneChecked;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public Boolean getIdentityChecked() {
        return isIdentityChecked;
    }

    public void setIdentityChecked(Boolean identityChecked) {
        isIdentityChecked = identityChecked;
    }

    public String getRealNameTime() {
        return realNameTime;
    }

    public void setRealNameTime(String realNameTime) {
        this.realNameTime = realNameTime;
    }

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(String bizUserId) {
        this.bizUserId = bizUserId;
    }

    public Boolean getSetPayPwd() {
        return isSetPayPwd;
    }

    public void setSetPayPwd(Boolean setPayPwd) {
        isSetPayPwd = setPayPwd;
    }

    public Boolean getSignContract() {
        return isSignContract;
    }

    public void setSignContract(Boolean signContract) {
        isSignContract = signContract;
    }
}
