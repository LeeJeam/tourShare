package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableName;
import org.hibernate.validator.constraints.NotEmpty;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 反馈问题答疑
 * </p>
 *
 * @author yeyu
 * @since 2018-06-12
 */
@ApiModel("反馈问题答疑")
@TableName("uc_feed_question")
@SuppressWarnings("serial")
public class UcFeedQuestion extends BaseModel {
    @ApiModelProperty(value = "问题")
	private String question;
    @ApiModelProperty(value = "答案")
	private String answer;
    @ApiModelProperty(value = "排序")
	private Integer sort;


	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}