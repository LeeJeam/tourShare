package com.xmyy.member.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "查询买手入参")
public class SellerPageParam implements Serializable {

    @ApiModelProperty(value = "页码（默认1）")
    private Integer current = 1;

    @ApiModelProperty(value = "页大小（默认10）")
    private Integer size = 10;

    @ApiModelProperty(value = "搜索框文本")
    private String keyword;

    @ApiModelProperty(value = "搜索列表的居住国家")
    private String liveCountry;

    @ApiModelProperty(value = "搜索列表的类型（0大众买手；1资深买手；2背包客）")
    private Integer isSelf;

    @ApiModelProperty(value = "搜索列表的排序（1行程时间；2信誉分；3交易量）")
    private Integer orderby;
    @JsonIgnore
    private String clientIp; // 非查询接口参数
    @JsonIgnore
    private Long buyerId;

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getLiveCountry() {
        return liveCountry;
    }

    public void setLiveCountry(String liveCountry) {
        this.liveCountry = liveCountry;
    }

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }
}
