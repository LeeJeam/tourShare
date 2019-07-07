package com.xmyy.livevideo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "后台管理查询直播参数")
public class LiveVideoManagePageParam implements Serializable {

    @ApiModelProperty(value = "直播时间", example = "50")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

    @ApiModelProperty(value = "直播状态", example = "50")
    private Integer state;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页号（默认1）")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小（默认10）")
    private Integer size = 10;

    @ApiModelProperty(value = "买手ID")
    private Long sellerId;

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
