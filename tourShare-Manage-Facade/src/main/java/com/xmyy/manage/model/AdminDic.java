package com.xmyy.manage.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 数据字典明细表
 * </p>
 *
 * @author zlp
 * @since 2018-07-02
 */
@ApiModel("数据字典明细表")
@TableName("admin_dic")
@SuppressWarnings("serial")
public class AdminDic extends BaseModel {

	@ApiModelProperty(value = "类型。搜索引擎热词：SEARCH_HOTS；搜索引擎买手同步：SELLER_LAST_INDEX_TIME",required = true)
	@TableField("type_")
	private String type;
	@ApiModelProperty(value = "编号。买手或者背包客搜索引擎热词：MEMBER；买手或者背包客搜索引擎热词：PRODUCT；买手或者背包客搜索引擎热词：CRICLE；搜索引擎买手同步：SELLER_LAST_INDEX_TIME",required = true)
	@TableField("code_")
	private String code;

	@ApiModelProperty(value = "字典内容",required = true)
	@TableField("code_text")
	private String codeText;

	@TableField("parent_type")
	private String parentType;
	@TableField("parent_code")
	private String parentCode;
	@TableField("sort_no")
	private Integer sortNo;

	@ApiModelProperty(value = "是否可编辑，1是；0否",required = true)
	@TableField("editable_")
	private Integer editable;


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeText() {
		return codeText;
	}

	public void setCodeText(String codeText) {
		this.codeText = codeText;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Integer getEditable() {
		return editable;
	}

	public void setEditable(Integer editable) {
		this.editable = editable;
	}

}