package com.xmyy.cert.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

import java.util.Date;

@ApiModel("uc_member_cert 用户认证")
@TableName("uc_member_cert")
@SuppressWarnings("serial")
public class UcMemberCert extends BaseModel {

    @ApiModelProperty(value = "是否身份实名认证（1是，0否）")
	@TableField("is_pass_identity")
	private Integer isPassIdentity;
    @ApiModelProperty(value = "身份图片，存储格式：正面图,反面图")
	@TableField("identity_rsurl")
	private String identityRsurl;
    @ApiModelProperty(value = "是否护照实名认证（1是，0否）")
	@TableField("is_pass_passport")
	private Integer isPassPassport;
    @ApiModelProperty(value = "护照图片，存储格式：正面图,反面图")
	@TableField("pass_rsurl")
	private String passRsurl;
    @ApiModelProperty(value = "是否芝麻认证（1是，0否）")
	@TableField("is_pass_zhima")
	private Integer isPassZhima;
    @ApiModelProperty(value = "身份证号码")
	@TableField("id_card_number")
	private String idCardNumber;
    @ApiModelProperty(value = "芝麻信用openid")
	@TableField("zhima_openid")
	private String zhimaOpenid;
    @ApiModelProperty(value = "芝麻信用分")
	@TableField("zhima_score")
	private Integer zhimaScore;
    @ApiModelProperty(value = "认证状态（0提交认证，50认证审核通过，-50认证审核不通过）")
	private Integer state;
    @ApiModelProperty(value = "提交认证时间")
	@TableField("real_create_time")
	private Date realCreateTime;
    @ApiModelProperty(value = "认证审核人id")
	@TableField("real_review_user_id")
	private Long realReviewUserId;
    @ApiModelProperty(value = "认证审核人姓名")
	@TableField("real_review_user_name")
	private String realReviewUserName;
    @ApiModelProperty(value = "认证审核时间")
	@TableField("real_review_time")
	private Date realReviewTime;
    @ApiModelProperty(value = "认证备注")
	@TableField("real_remark")
	private String realRemark;
    @ApiModelProperty(value = "是否定居海外（1是，0否）；")
	@TableField("is_overseas")
	private Integer isOverseas;
    @ApiModelProperty(value = "护照号码")
	@TableField("passport_number")
	private String passportNumber;
    @ApiModelProperty(value = "买家/买手")
	@TableField("member_id")
	private Long memberId;
    @ApiModelProperty(value = "用户类型(1,买手，2背包客)")
	@TableField("member_type")
	private Integer memberType;
	@ApiModelProperty(value = "用户名")
	@TableField("member_name")
	private String memberName;
	@ApiModelProperty(value = "手机号码")
	@TableField("mobile")
	private String mobile;
	@ApiModelProperty(value = "居住地国家名称")
	@TableField("live_country")
	private String liveCountry;
	@ApiModelProperty(value = "居住地国家编码")
	@TableField("live_country_short_code")
	private String liveCountryShortCode;

	public Integer getIsPassIdentity() {
		return isPassIdentity;
	}

	public void setIsPassIdentity(Integer isPassIdentity) {
		this.isPassIdentity = isPassIdentity;
	}

	public String getIdentityRsurl() {
		return identityRsurl;
	}

	public void setIdentityRsurl(String identityRsurl) {
		this.identityRsurl = identityRsurl;
	}

	public Integer getIsPassPassport() {
		return isPassPassport;
	}

	public void setIsPassPassport(Integer isPassPassport) {
		this.isPassPassport = isPassPassport;
	}

	public String getPassRsurl() {
		return passRsurl;
	}

	public void setPassRsurl(String passRsurl) {
		this.passRsurl = passRsurl;
	}

	public Integer getIsPassZhima() {
		return isPassZhima;
	}

	public void setIsPassZhima(Integer isPassZhima) {
		this.isPassZhima = isPassZhima;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getZhimaOpenid() {
		return zhimaOpenid;
	}

	public void setZhimaOpenid(String zhimaOpenid) {
		this.zhimaOpenid = zhimaOpenid;
	}

	public Integer getZhimaScore() {
		return zhimaScore;
	}

	public void setZhimaScore(Integer zhimaScore) {
		this.zhimaScore = zhimaScore;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getRealCreateTime() {
		return realCreateTime;
	}

	public void setRealCreateTime(Date realCreateTime) {
		this.realCreateTime = realCreateTime;
	}

	public Long getRealReviewUserId() {
		return realReviewUserId;
	}

	public void setRealReviewUserId(Long realReviewUserId) {
		this.realReviewUserId = realReviewUserId;
	}

	public String getRealReviewUserName() {
		return realReviewUserName;
	}

	public void setRealReviewUserName(String realReviewUserName) {
		this.realReviewUserName = realReviewUserName;
	}

	public Date getRealReviewTime() {
		return realReviewTime;
	}

	public void setRealReviewTime(Date realReviewTime) {
		this.realReviewTime = realReviewTime;
	}

	public String getRealRemark() {
		return realRemark;
	}

	public void setRealRemark(String realRemark) {
		this.realRemark = realRemark;
	}

	public Integer getIsOverseas() {
		return isOverseas;
	}

	public void setIsOverseas(Integer isOverseas) {
		this.isOverseas = isOverseas;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLiveCountry() {
		return liveCountry;
	}

	public void setLiveCountry(String liveCountry) {
		this.liveCountry = liveCountry;
	}

	public String getLiveCountryShortCode() {
		return liveCountryShortCode;
	}

	public void setLiveCountryShortCode(String liveCountryShortCode) {
		this.liveCountryShortCode = liveCountryShortCode;
	}
}