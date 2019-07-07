package com.xmyy.manage.model;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 接口
 * </p>
 *
 * @author LinBo
 * @since 2018-07-11
 */
@ApiModel("接口")
@TableName("admin_interface")
@SuppressWarnings("serial")
public class AdminInterface extends BaseModel {

    @ApiModelProperty(value = "请求地址")
	private String url;
    @ApiModelProperty(value = "请求方法（GET、POST、PUT、DELETE）")
	private String method;
    @ApiModelProperty(value = "描述")
	private String describe;


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}