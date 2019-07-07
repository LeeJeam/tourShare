package com.xmyy.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 类目
 * </p>
 *
 * @author simon
 * @since 2018-05-18
 */
@ApiModel("类目")
@TableName("pt_category")
@SuppressWarnings("serial")
public class PtCategory extends BaseModel {

    @ApiModelProperty(value = "名称")
	private String name;
    @ApiModelProperty(value = "父类目")
	@TableField("parent_id")
	private Long parentId;
    @ApiModelProperty(value = "排序序号")
	private Integer sort;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}