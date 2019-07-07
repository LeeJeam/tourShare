package com.xmyy.member.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 18短信发送记录
 * </p>
 *
 * @author admin
 * @since 2018-05-23
 */
@ApiModel("18短信发送记录")
@TableName("dg_sms_record")
@SuppressWarnings("serial")
public class DgSmsRecord extends BaseModel {

    @ApiModelProperty(value = "平台编号")
	@TableField("biz_id")
	private String bizId;
    @ApiModelProperty(value = "类型")
	private Integer type;
    @ApiModelProperty(value = "接收短信号码")
	private String mobile;
    @ApiModelProperty(value = "状态(0待发送；50发送成功；-50发送失败)")
	private Integer state;
    @ApiModelProperty(value = "短信内容")
	private String content;


	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}