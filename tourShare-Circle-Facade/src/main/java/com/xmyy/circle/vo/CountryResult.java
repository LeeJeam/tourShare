package com.xmyy.circle.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "国家数据")
public class CountryResult implements Serializable {

    @ApiModelProperty("首字母")
    private String initial;

    @ApiModelProperty("国家编码")
    @JsonIgnore
    private String code;

    @ApiModelProperty("国际码")
    private String shortCode;

    @ApiModelProperty("国家中文名称")
    private String name;

    @ApiModelProperty(value="国家英文名称")
    private String enName;

    @ApiModelProperty(value="所在洲")
    private String continent;

    @ApiModelProperty(value="国家国旗")
    private String logo;

    @ApiModelProperty(value="国家货币")
    private String currency;

    @ApiModelProperty(value="是否全部（0-部分，1-全部）")
    private Integer isAll = 0;

    @ApiModelProperty(value="国家货币编码")
    private String currencyCode;

    @ApiModelProperty(value="手机号码前戳")
    private String prefixPhone;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPrefixPhone() {
        return prefixPhone;
    }

    public void setPrefixPhone(String prefixPhone) {
        this.prefixPhone = prefixPhone;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Integer getIsAll() {
        return isAll;
    }

    public void setIsAll(Integer isAll) {
        this.isAll = isAll;
    }
}