package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.hibernate.validator.constraints.NotEmpty;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 意见反馈
 * </p>
 *
 * @author yeyu
 * @since 2018-06-12
 */
@ApiModel("意见反馈")
@TableName("uc_feedback")
@SuppressWarnings("serial")
public class UcFeedback extends BaseModel {
    @ApiModelProperty(value = "反馈问题类型（1:功能异常，2：用的不爽，建议开发的功能）")
	@TableField("question_type")
	private Integer questionType;
    @ApiModelProperty(value = "反馈内容")
	private String content;


	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}