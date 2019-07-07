package com.xmyy.cert.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


@ApiModel(value = "用户认证入参")
public class MemberCertAddParam implements Serializable {

	@NotBlank(message = "姓名不能为空")
	@ApiModelProperty(value = "姓名", required = true)
	private String memberName;

	@NotBlank(message = "手机号不能为空")
	@Pattern(regexp = "^(1\\d{10})$", message = "无效的手机号码")
	@ApiModelProperty(value = "手机号码", required = true)
	private String mobile;

	@NotBlank(message = "身份证图片url不能为空")
	@ApiModelProperty(value = "身份证图片url（正反面两张，逗号分隔）", required = true)
	private String identityRsurl;

	@NotBlank(message = "身份证号不能为空")
	@ApiModelProperty(value = "身份证号", required = true)
	private String idCardNumber;

	@NotBlank(message = "护照号码不能为空")
	@ApiModelProperty(value = "护照号码", required = true, example = "G12345678")
	private String passportNumber;

	@NotBlank(message = "护照图片url不能为空")
	@ApiModelProperty(value = "护照图片url（正反面两张，逗号分隔）", required = true)
	private String passRsurl;

	@NotNull
	@Range(min = 1, max = 2, message = "参数不正确")
	@ApiModelProperty(value = "用户类型（1买手，2背包客）")
	private Integer memberType;

	@ApiModelProperty(value = "芝麻分", example = "650")
	private Integer zhimaScore;

	@ApiModelProperty(value = "分类标签")
	private String classifyTags;

	@NotNull
	@Range(min = 0, max = 1, message = "参数不正确")
	@ApiModelProperty(value = "是否定居海外（1是，0否）")
	private Integer isOverseas;

	@NotBlank
	@ApiModelProperty(value = "居住地国家编码")
	private String liveCountryShortCode;

	public String getClassifyTags() {
		return classifyTags;
	}

	public void setClassifyTags(String classifyTags) {
		this.classifyTags = classifyTags;
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

	public Integer getZhimaScore() {
		return zhimaScore;
	}

	public void setZhimaScore(Integer zhimaScore) {
		this.zhimaScore = zhimaScore;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getPassRsurl() {
		return passRsurl;
	}

	public void setPassRsurl(String passRsurl) {
		this.passRsurl = passRsurl;
	}

	public String getIdentityRsurl() {
		return identityRsurl;
	}

	public void setIdentityRsurl(String identityRsurl) {
		this.identityRsurl = identityRsurl;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Integer getIsOverseas() {
		return isOverseas;
	}

	public void setIsOverseas(Integer isOverseas) {
		this.isOverseas = isOverseas;
	}

	public String getLiveCountryShortCode() {
		return liveCountryShortCode;
	}

	public void setLiveCountryShortCode(String liveCountryShortCode) {
		this.liveCountryShortCode = liveCountryShortCode;
	}
}