package com.xmyy.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 颜色属性
 * </p>
 *
 * @author simon
 * @since 2018-06-07
 */
@ApiModel("颜色属性")
@TableName("pt_series_color")
@SuppressWarnings("serial")
public class PtSeriesColor extends BaseModel {

    @ApiModelProperty(value = "商品模版_id")
	@TableField("pt_series_id")
	private Long ptSeriesId;
    @ApiModelProperty(value = "名称")
	private String name;
    @ApiModelProperty(value = "图片")
	private String image;


	public Long getPtSeriesId() {
		return ptSeriesId;
	}

	public void setPtSeriesId(Long ptSeriesId) {
		this.ptSeriesId = ptSeriesId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}