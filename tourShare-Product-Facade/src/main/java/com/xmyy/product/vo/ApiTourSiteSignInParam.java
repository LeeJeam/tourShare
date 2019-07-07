package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("当前位置信息")
public class ApiTourSiteSignInParam implements Serializable {

    @ApiModelProperty(value="行程站点id")
    private Long id;

    @ApiModelProperty(value="行程ID")
    private Long tourId;

    @ApiModelProperty(value="国家编码")
    private String countryCode;

    @ApiModelProperty(value="自定义国家编码", hidden = true)
    private String customCountryCode;

    @ApiModelProperty(value="国家名称")
    private String countryName;

    @ApiModelProperty(value="省份编码")
    private String provinceCode;

    @ApiModelProperty(value="省份名称")
    private String provinceName;

    @ApiModelProperty(value="城市编码")
    private String cityCode;

    @ApiModelProperty(value="城市名称")
    private String cityName;

    @ApiModelProperty(value="当前经度")
    private Double longitude;

    @ApiModelProperty(value="当前纬度")
    private Double Latitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public String getCustomCountryCode() {
        return customCountryCode;
    }

    public void setCustomCountryCode(String customCountryCode) {
        this.customCountryCode = customCountryCode;
    }
}