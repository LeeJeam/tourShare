package com.xmyy.member.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 买手
 * </p>
 *
 * @author AnCheng
 * @since 2018-09-13
 */
@ApiModel("买手")
@TableName("uc_seller")
@SuppressWarnings("serial")
public class UcSeller extends BaseModel {

    @ApiModelProperty(value = "密码_ID")
	@TableField("password_id_")
	private Long passwordId;
    @ApiModelProperty(value = "uuid")
	private String uuid;
    @ApiModelProperty(value = "状态(50正常，-50停用)")
	private Integer state;
    @ApiModelProperty(value = "手机号码")
	private String mobile;
    @ApiModelProperty(value = "用户个性签名")
	@TableField("personalized_signature")
	private String personalizedSignature;
    @ApiModelProperty(value = "常去的地方")
	@TableField("often_place")
	private String oftenPlace;
    @ApiModelProperty(value = "真实名字")
	@TableField("real_name")
	private String realName;
    @ApiModelProperty(value = "昵称")
	@TableField("nick_name")
	private String nickName;
    @ApiModelProperty(value = "头像")
	@TableField("avatar_rsurl")
	private String avatarRsurl;
    @ApiModelProperty(value = "信任值")
	@TableField("trust_value")
	private Integer trustValue;
    @ApiModelProperty(value = "是否身份实名认证（1是，0否）")
	@TableField("is_pass_identity")
	private Integer isPassIdentity;
    @ApiModelProperty(value = "是否护照实名认证（1是，0否）")
	@TableField("is_pass_passport")
	private Integer isPassPassport;
    @ApiModelProperty(value = "是否芝麻认证（1是，0否）")
	@TableField("is_pass_zhima")
	private Integer isPassZhima;
    @ApiModelProperty(value = "邀请码")
	@TableField("invitation_code")
	private String invitationCode;
    @ApiModelProperty(value = "是否自营（1是，0否）")
	@TableField("is_self")
	private Integer isSelf;
    @ApiModelProperty(value = "认证状态（0提交认证，50认证审核通过，-50认证审核不通过）")
	@TableField("real_state")
	private Integer realState;
    @ApiModelProperty(value = "居住国家")
	@TableField("live_country")
	private String liveCountry;
    @ApiModelProperty(value = "生日")
	private Date birthday;
    @ApiModelProperty(value = "性别(1男，2女)")
	private Integer gender;
    @ApiModelProperty(value = "注册来源端（1，安卓，2苹果，3小程序，4 pc）")
	private Integer source;
    @ApiModelProperty(value = "是否置顶（0否，1是）")
	@TableField("is_top")
	private Integer isTop;
    @ApiModelProperty(value = "置顶操作时间")
	@TableField("top_time")
	private Date topTime;
    @ApiModelProperty(value = "置顶有效时间")
	@TableField("top_end_time")
	private Date topEndTime;
    @ApiModelProperty(value = "分类标签")
	@TableField("classify_tags")
	private String classifyTags;
    @ApiModelProperty(value = "成交量")
	@TableField("trade_count")
	private Integer tradeCount;
    @ApiModelProperty(value = "行程最新时间")
	@TableField("tour_time")
	private Date tourTime;
    @ApiModelProperty(value = "行程数")
	@TableField("tour_count")
	private Integer tourCount;
    @ApiModelProperty(value = "买手编号")
	@TableField("seller_no")
	private String sellerNo;
    @ApiModelProperty(value = "置顶操作人")
	@TableField("top_op_id")
	private Long topOpId;
    @ApiModelProperty(value = "身份证号码")
	@TableField("id_card_number")
	private String idCardNumber;
    @ApiModelProperty(value = "当前登录来源端（1，安卓，2苹果，3小程序，4 pc）")
	@TableField("login_source")
	private Integer loginSource;
    @ApiModelProperty(value = "居住国家国际码")
	@TableField("live_country_short_code")
	private String liveCountryShortCode;
    @ApiModelProperty(value = "通联绑定手机号")
	@TableField("bind_phone")
	private String bindPhone;
    @ApiModelProperty(value = "平台支付密码")
	@TableField("pay_password_id")
	private Long payPasswordId;


	public Long getPasswordId() {
		return passwordId;
	}

	public void setPasswordId(Long passwordId) {
		this.passwordId = passwordId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPersonalizedSignature() {
		return personalizedSignature;
	}

	public void setPersonalizedSignature(String personalizedSignature) {
		this.personalizedSignature = personalizedSignature;
	}

	public String getOftenPlace() {
		return oftenPlace;
	}

	public void setOftenPlace(String oftenPlace) {
		this.oftenPlace = oftenPlace;
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

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
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

	public String getLiveCountry() {
		return liveCountry;
	}

	public void setLiveCountry(String liveCountry) {
		this.liveCountry = liveCountry;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Date getTopTime() {
		return topTime;
	}

	public void setTopTime(Date topTime) {
		this.topTime = topTime;
	}

	public Date getTopEndTime() {
		return topEndTime;
	}

	public void setTopEndTime(Date topEndTime) {
		this.topEndTime = topEndTime;
	}

	public String getClassifyTags() {
		return classifyTags;
	}

	public void setClassifyTags(String classifyTags) {
		this.classifyTags = classifyTags;
	}

	public Integer getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(Integer tradeCount) {
		this.tradeCount = tradeCount;
	}

	public Date getTourTime() {
		return tourTime;
	}

	public void setTourTime(Date tourTime) {
		this.tourTime = tourTime;
	}

	public Integer getTourCount() {
		return tourCount;
	}

	public void setTourCount(Integer tourCount) {
		this.tourCount = tourCount;
	}

	public String getSellerNo() {
		return sellerNo;
	}

	public void setSellerNo(String sellerNo) {
		this.sellerNo = sellerNo;
	}

	public Long getTopOpId() {
		return topOpId;
	}

	public void setTopOpId(Long topOpId) {
		this.topOpId = topOpId;
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

	public String getLiveCountryShortCode() {
		return liveCountryShortCode;
	}

	public void setLiveCountryShortCode(String liveCountryShortCode) {
		this.liveCountryShortCode = liveCountryShortCode;
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