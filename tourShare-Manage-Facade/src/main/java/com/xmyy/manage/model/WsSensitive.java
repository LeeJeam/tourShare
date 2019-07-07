package com.xmyy.manage.model;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 敏感词
 * </p>
 *
 * @author zlp
 * @since 2018-06-27
 */
@ApiModel("敏感词")
@TableName("ws_sensitive")
@SuppressWarnings("serial")
public class WsSensitive extends BaseModel {

    @ApiModelProperty(value = "敏感词")
	private String content;


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}