package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "举报增加请求参数")
public class ReportAddParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "目标ID")
    private Long targetId;

    @NotNull
    @Range(min = 1, max = 2, message = "参数错误")
    @ApiModelProperty(value = "目标类型（1笔记，2视频）")
    private Integer targetType;

    @NotNull
    @Range(min = 1, max = 3)
    @ApiModelProperty(value = "举报者的用户类型（1买手，2买家，3背包客）")
    private Integer memberType;

    @NotNull
    @Range(min = 1, max = 5)
    @ApiModelProperty(value = "举报类型（1-广告内容，2-不友善内容，3-垃圾内容，4-违法违规内容，5-其他）")
    private Integer reportType;

    @ApiModelProperty(value = "举报内容")
    private String content;

    @ApiModelProperty(value = "举报图片")
    private List<String> images;

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
