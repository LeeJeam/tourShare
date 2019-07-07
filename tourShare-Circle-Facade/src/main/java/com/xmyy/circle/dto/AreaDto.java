package com.xmyy.circle.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 地区信息<br>
 *
 * @author wangzejun
 * @time 2018年 06月27日 15:56:19
 * @since 1.0.0
 */
@ApiModel("地区信息")
public class AreaDto implements Serializable {

    private static final long serialVersionUID = 1039975995996859224L;

    @ApiModelProperty(value = "区域名称")
    private String areaName;

    @ApiModelProperty(value = "区域编码")
    private String areaCode;

    @ApiModelProperty(value="父亲区域编码")
    private String parentCode;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}