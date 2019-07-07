package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.Date;

@ApiModel("行程匹配入参")
public class ApiTourMatchParam implements Serializable {

    @ApiModelProperty(value = "匹配最小时间（开直播传当前时间）")
    private Date matchMinTime;

    @ApiModelProperty(value = "匹配最大时间（接需求传最迟收货时间、发预售传预售过期时间）")
    private Date matchMaxTime;

    @ApiModelProperty(value = "国家码")
    private String matchRegionCode;

    @ApiModelProperty(value = "城市地区代码，自定义国际码", hidden = true)
    private String customRegionCode;

    @ApiModelProperty(value = "是否背包客（0否，1是）")
    private Integer isPacker;

    @ApiModelProperty(value = "行程创建人ID", hidden = true)
    private Long createBy;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "分页页号，默认1", hidden = true)
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小，默认10", hidden = true)
    private Integer size = 10;

    public Date getMatchMinTime() {
        return matchMinTime;
    }

    public void setMatchMinTime(Date matchMinTime) {
        this.matchMinTime = matchMinTime;
    }

    public Date getMatchMaxTime() {
        return matchMaxTime;
    }

    public void setMatchMaxTime(Date matchMaxTime) {
        this.matchMaxTime = matchMaxTime;
    }

    public String getMatchRegionCode() {
        return matchRegionCode;
    }

    public void setMatchRegionCode(String matchRegionCode) {
        this.matchRegionCode = matchRegionCode;
    }

    public String getCustomRegionCode() {
        return customRegionCode;
    }

    public void setCustomRegionCode(String customRegionCode) {
        this.customRegionCode = customRegionCode;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
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