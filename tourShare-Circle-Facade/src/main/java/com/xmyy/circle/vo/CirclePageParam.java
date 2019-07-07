package com.xmyy.circle.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@ApiModel("笔记/视频（首页/发现列表/买家端买手详情页动态）列表")
public class CirclePageParam implements Serializable {

    @ApiModelProperty(value = "1发现列表（默认值，视频笔记都展示）；2首页；3买家端买手详情页动态（视频笔记都展示）；4搜索列表（默认typeId=1）")
    private Integer type = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码，默认1")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小，默认10")
    private Integer size = 10;

    @ApiModelProperty(value = "分类标签")
    private String classifyTags;

    @ApiModelProperty(value = "买家端买手详情页；买手id")
    private Long sellerId;

    @ApiModelProperty(value = "搜索框内容")
    private String keywords;

    @ApiModelProperty(value = "1笔记；2视频")
    private Integer typeId;

    @JsonIgnore
    private Long buyerId;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getClassifyTags() {
        return classifyTags;
    }

    public void setClassifyTags(String classifyTags) {
        this.classifyTags = classifyTags;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
