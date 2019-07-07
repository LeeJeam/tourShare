package com.xmyy.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 属性值
 * </p>
 *
 * @author simon
 * @since 2018-06-07
 */
@ApiModel("属性值")
@TableName("pt_attr_value")
@SuppressWarnings("serial")
public class PtAttrValue extends BaseModel {

    @ApiModelProperty(value = "属性类型_id")
	@TableField("pt_attr_type_id")
	private Long ptAttrTypeId;
    @ApiModelProperty(value = "名称")
	private String name;

    @ApiModelProperty(value = "是否自定义（0否，1是）")
    @TableField("is_custom")
    private Integer isCustom;
    @ApiModelProperty(value = "自定义人ID")
	@TableField("custom_by")
    private Long customBy;
    @ApiModelProperty(value = "图片")
    private String images;

	public Long getPtAttrTypeId() {
		return ptAttrTypeId;
	}

	public void setPtAttrTypeId(Long ptAttrTypeId) {
		this.ptAttrTypeId = ptAttrTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsCustom() {
		return isCustom;
	}

	public void setIsCustom(Integer isCustom) {
		this.isCustom = isCustom;
	}

	public Long getCustomBy() {
		return customBy;
	}

	public void setCustomBy(Long customBy) {
		this.customBy = customBy;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
}