package com.xmyy.livevideo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "后台直播当前置顶查询参数")
public class LiveVideoCurrentTopParam implements Serializable {

    @ApiModelProperty(value = "置顶标识", example = "50")
    private Integer isTop;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页号（默认1）")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小（默认5）")
    private Integer size = 5;

    public Integer getIsTop() {
        return isTop;
    }
    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
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
