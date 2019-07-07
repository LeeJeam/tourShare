package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@ApiModel(value = "反馈接口参数")
public class UcFeedbackParam implements Serializable {

    @ApiModelProperty(value = "ID")
    private Long Id;

    @ApiModelProperty(value = "反馈问题类型（1:功能异常，2：用的不爽，建议开发的功能）,默认1")
    private Integer questionType = 1;

    @NotEmpty
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

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

}
