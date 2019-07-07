package com.xmyy.search.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "ES搜索买手+背包客列表入参")
public class SellerPageParam implements Serializable {

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码，默认1")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小，默认10")
    private Integer size = 10;

    @NotBlank
    @ApiModelProperty(value = "搜索框内容")
    private String keyword;

    @ApiModelProperty(value = "定居地国际码")
    private String liveCountryShortCode;

    @ApiModelProperty(value="大洲编码，当查询各大洲下的‘全部’时，传此参数，传此参数时不传liveRegionShortCode")
    private String continentCode;

    @ApiModelProperty(value = "搜索列表的类型（0-大众买手；1-资深买手；2-背包客；不传-全部买手）")
    private Integer isSelf;

    @ApiModelProperty(value = "搜索列表的排序（1-行程时间；2-信誉分；3-交易量；不传-智能排序）")
    private Integer orderby;
    @JsonIgnore
    private String clientIp; // 非查询接口参数
    @JsonIgnore
    private Long buyerId;

    @ApiModelProperty(value = "传洲码时内部使用",hidden = true)
    private List<String> shortCodes;

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getLiveCountryShortCode() {
        return liveCountryShortCode;
    }

    public void setLiveCountryShortCode(String liveCountryShortCode) {
        this.liveCountryShortCode = liveCountryShortCode;
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

    public String getContinentCode() {
        return continentCode;
    }

    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }

    public List<String> getShortCodes() {
        return shortCodes;
    }

    public void setShortCodes(List<String> shortCodes) {
        this.shortCodes = shortCodes;
    }
}
