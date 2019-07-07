package com.xmyy.circle.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 国家信息<br>
 *
 * @author wangzejun
 * @time 2018年 06月26日 11:04:33
 * @since 1.0.0
 */
public class CountryDto implements Serializable {

    private static final long serialVersionUID = 8451235848889505375L;

    @ApiModelProperty("首字母")
    private String initial;

    /**
     * 国际代码
     */
    @ApiModelProperty("国家码（国际码）")
    private String code;

    /**
     * 国家中文名称
     */
    @ApiModelProperty("国家中文名称")
    private String name;

    /**
     * 国家英文名称
     */
    @ApiModelProperty(value="国家英文名称")
    private String enName;

    /**
     * 国家国旗
     */
    @ApiModelProperty(value="国家国旗")
    private String logo;

    /**
     * 手机号码前戳
     */
    @ApiModelProperty(value="手机号码前戳")
    private String prefixPhone;

    /**
     * 国家货币编码
     */
    @ApiModelProperty(value="国家货币编码")
    private String currencyCode;

    /**
     * 国家货币
     */
    @ApiModelProperty(value="国家货币")
    private String currency;


    /**
     * 是不是热门国家
     * @return
     */
    @ApiModelProperty(value="是不是热门")
    private Integer isHot;

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
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

    public String getPrefixPhone() {
        return prefixPhone;
    }

    public void setPrefixPhone(String prefixPhone) {
        this.prefixPhone = prefixPhone;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}