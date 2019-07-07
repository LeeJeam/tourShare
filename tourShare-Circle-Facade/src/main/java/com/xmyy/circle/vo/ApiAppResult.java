package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "基于App预先加载基础信息对象")
public class ApiAppResult implements Serializable {

    @ApiModelProperty(value = "首页广告图")
    private List<Adverts> ads;

    @ApiModelProperty(value = "热门国家列表")
    private List<Country> countrys;

    @ApiModel(value = "热门国家")
    public static class Country implements Serializable {
        @ApiModelProperty(value = "国家码")
        private String countryShortCode;

        @ApiModelProperty(value = "国家名称")
        private String countryName;

        public String getCountryShortCode() {
            return countryShortCode;
        }

        public void setCountryShortCode(String countryShortCode) {
            this.countryShortCode = countryShortCode;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }
    }

    @ApiModelProperty(value = "热门标签")
    private List<String> tags;

    @ApiModelProperty(value = "发现广告轮播图")
    private List<Adverts> findAds;

    @ApiModelProperty(value = "直播广告轮播图")
    private List<Adverts> liveAds;

    @ApiModelProperty(value = "背包客广告图")
    private List<Adverts> packAds;

    @ApiModel(value = "广告内容")
    public static class Adverts implements Serializable {
        @ApiModelProperty(value = "资源URL")
        private String fileRsurl;

        @ApiModelProperty(value = "点击链接")
        private String clickUrl;

        public String getFileRsurl() {
            return fileRsurl;
        }

        public void setFileRsurl(String fileRsurl) {
            this.fileRsurl = fileRsurl;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }
    }

    public List<Adverts> getAds() {
        return ads;
    }

    public void setAds(List<Adverts> ads) {
        this.ads = ads;
    }

    public List<Country> getCountrys() {
        return countrys;
    }

    public void setCountrys(List<Country> countrys) {
        this.countrys = countrys;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Adverts> getFindAds() {
        return findAds;
    }

    public void setFindAds(List<Adverts> findAds) {
        this.findAds = findAds;
    }

    public List<Adverts> getLiveAds() {
        return liveAds;
    }

    public void setLiveAds(List<Adverts> liveAds) {
        this.liveAds = liveAds;
    }

    public List<Adverts> getPackAds() {
        return packAds;
    }

    public void setPackAds(List<Adverts> packAds) {
        this.packAds = packAds;
    }
}
