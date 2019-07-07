package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(value = "买家注册入参")
@SuppressWarnings("serial")
public class BuyerAddParam extends MemberCommon implements Serializable {

	@ApiModelProperty(value = "手机号码", required = true)
	@Pattern(regexp = "^(1\\d{10})$", message = "无效的手机号码")
	private String mobile;

	@ApiModelProperty(value = "注册设备端（1安卓，2苹果，3小程序，4PC）", required = true)
	@NotNull(message = "注册设备端不能为空")
	private Integer source;

	@ApiModelProperty(value = "登陆地")
	private String loginCountry;


	public String getLoginCountry() {
		return loginCountry;
	}

	public void setLoginCountry(String loginCountry) {
		this.loginCountry = loginCountry;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
