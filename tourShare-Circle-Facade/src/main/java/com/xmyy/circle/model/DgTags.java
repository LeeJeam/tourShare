package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 对象标签表
 * </p>
 *
 * @author zlp
 * @since 2018-06-01
 */
@ApiModel("对象标签表")
@TableName("dg_tags")
@SuppressWarnings("serial")
public class DgTags extends BaseModel {

    @ApiModelProperty(value = "标签名字")
	@TableField("tag_name")
	private String tagName;
    @ApiModelProperty(value = "是否推荐（1：是，0：否）")
	@TableField("is_recommend")
	private Integer isRecommend;
    @ApiModelProperty(value = "标签类型")
	@TableField("tag_type")
	private Integer tagType;
    @ApiModelProperty(value = "权重值")
	private Integer sort;
    @ApiModelProperty(value = "状态（50：上架，-50：下架）")
	private Integer state;


	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Integer getTagType() {
		return tagType;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}