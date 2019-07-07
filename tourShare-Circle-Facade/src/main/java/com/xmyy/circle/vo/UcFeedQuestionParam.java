package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "反馈问题答疑参数")
public class UcFeedQuestionParam implements Serializable{

    @ApiModelProperty(value = "主键ID")
    private Long Id;

    @NotBlank
    @ApiModelProperty(value = "问题")
    private String question;

    @NotBlank
    @ApiModelProperty(value = "答案")
    private String answer;

    @NotNull
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

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

}
