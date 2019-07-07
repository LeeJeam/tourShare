package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 广告位
 * </p>
 *
 * @author zlp
 * @since 2018-06-04
 */
@ApiModel("广告位")
@TableName("ws_advert_position")
@SuppressWarnings("serial")
public class WsAdvertPosition extends BaseModel {

    @ApiModelProperty(value = "广告位名称")
	private String name;
    @ApiModelProperty(value = "广告位代码")
	private String code;
    @ApiModelProperty(value = "类型1代码，2文字，3图片，4FLASH")
	private Integer type;
    @ApiModelProperty(value = "播放类型1单个，2轮播")
	@TableField("play_type")
	private Integer playType;
    @ApiModelProperty(value = "轮播时间间隔单位，秒")
	@TableField("play_timer")
	private Integer playTimer;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPlayType() {
		return playType;
	}

	public void setPlayType(Integer playType) {
		this.playType = playType;
	}

	public Integer getPlayTimer() {
		return playTimer;
	}

	public void setPlayTimer(Integer playTimer) {
		this.playTimer = playTimer;
	}

}