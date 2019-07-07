package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "后台商品管理列表入参")
public class ManageProductListParam implements Serializable {

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "最小预定数")
    private Integer orderCountMin;

    @ApiModelProperty(value = "最大预定数")
    private Integer orderCountMax;

    @ApiModelProperty(value = "有效时间(传时间戳)")
    private Date expiresTime;

    @ApiModelProperty(value = "发布人昵称")
    private String nickName;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码，默认为1")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小，默认为10")
    private Integer size = 10;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrderCountMin() {
        return orderCountMin;
    }

    public void setOrderCountMin(Integer orderCountMin) {
        this.orderCountMin = orderCountMin;
    }

    public Integer getOrderCountMax() {
        return orderCountMax;
    }

    public void setOrderCountMax(Integer orderCountMax) {
        this.orderCountMax = orderCountMax;
    }

    public Date getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(Date expiresTime) {
        this.expiresTime = expiresTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
