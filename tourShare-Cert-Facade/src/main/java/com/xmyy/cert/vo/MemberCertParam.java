package com.xmyy.cert.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "查询用户认证入参")
@SuppressWarnings("serial")
public class MemberCertParam implements Serializable {

	@ApiModelProperty(value = "姓名")
	private String memberName;

	@ApiModelProperty(value = "手机号码")
	private String mobile;

    @ApiModelProperty(value = "认证审核人id")
	private Long realReviewUserId;

    @ApiModelProperty(value = "认证审核人姓名")
	private String realReviewUserName;

    @ApiModelProperty(value = "认证审核时间(yyyy-MM-dd)")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date realReviewTime;

	@ApiModelProperty(value = "认证状态（0提交认证，50认证审核通过，-50认证审核不通过）")
	private Integer state;

	@Range(min = 1, message = "分页参数不正确")
	@ApiModelProperty(value = "页大小（默认10）")
	private Integer size = 10;

	@Range(min = 1, message = "分页参数不正确")
	@ApiModelProperty(value = "页码（默认1）")
	private Integer current = 1;

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

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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
}