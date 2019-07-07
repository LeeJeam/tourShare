package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "买手推荐/搜索列表")
public class SellerPageResult implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "用户UUID")
    private String uuid;

    @ApiModelProperty(value = "用户个性签名")
    private String personalizedSignature;

    @ApiModelProperty(value = "常去的地方")
    private String oftenPlace;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatarRsurl;

    @ApiModelProperty(value = "买手类型（0大众买手；1-资深买手；2-背包客）")
    private Integer isSelf;

    @ApiModelProperty(value = "买手类型文字")
    private String isSelfStr;

    @ApiModelProperty(value = "标签数组")
    private List<String> classifyTagsList;

    @ApiModelProperty(value = "居住国家")
    private String liveCountry;

    @ApiModelProperty(value = "商品封面图数组")
    private List<String> productRsurlList;

    @ApiModelProperty(value = "信任值")
    private Integer trustValue;

    public String getIsSelfStr() {
        return isSelfStr;
    }

    public void setIsSelfStr(String isSelfStr) {
        this.isSelfStr = isSelfStr;
    }

    @ApiModelProperty(value = "行程数组")
    private List<TourSiteDto> tours;

    @ApiModelProperty(value = "定居地国际码")
    private String liveCountryShortCode;

    @ApiModel(value = "行程站点列表")
    public static class TourSiteDto implements Serializable {

        @ApiModelProperty(value = "行程站点ID")
        private Long id;

        @ApiModelProperty(value = "行程id")
        private Long tourId;

        @ApiModelProperty(value = "地区编号")
        private String regionCode;

        @ApiModelProperty(value = "地区名称")
        private String regionName;

        @ApiModelProperty(value = "行程站点国旗")
        private String regionLogo;

        @ApiModelProperty(value = "是不是已签到0 没有签到 1 已经签到")
        private Integer isSignIn;

        @ApiModelProperty(value = "计划出发时间")
        private Date planBeginTime;

        @ApiModelProperty(value = "到达时间")
        private Date arriveTime;

        @ApiModelProperty(value = "站点类型 0:起点站，1：首站,2：中途站，3：返程站")
        private Integer siteType;

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

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public String getRegionLogo() {
            return regionLogo;
        }

        public void setRegionLogo(String regionLogo) {
            this.regionLogo = regionLogo;
        }

        public Integer getIsSignIn() {
            return isSignIn;
        }

        public void setIsSignIn(Integer isSignIn) {
            this.isSignIn = isSignIn;
        }

        public Date getPlanBeginTime() {
            return planBeginTime;
        }

        public void setPlanBeginTime(Date planBeginTime) {
            this.planBeginTime = planBeginTime;
        }

        public Date getArriveTime() {
            return arriveTime;
        }

        public void setArriveTime(Date arriveTime) {
            this.arriveTime = arriveTime;
        }

        public Integer getSiteType() {
            return siteType;
        }

        public void setSiteType(Integer siteType) {
            this.siteType = siteType;
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getTrustValue() {
        return trustValue;
    }

    public void setTrustValue(Integer trustValue) {
        this.trustValue = trustValue;
    }

    public List<String> getProductRsurlList() {
        return productRsurlList;
    }

    public void setProductRsurlList(List<String> productRsurlList) {
        this.productRsurlList = productRsurlList;
    }

    public List<String> getClassifyTagsList() {
        return classifyTagsList;
    }

    public void setClassifyTagsList(List<String> classifyTagsList) {
        this.classifyTagsList = classifyTagsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonalizedSignature() {
        return personalizedSignature;
    }

    public void setPersonalizedSignature(String personalizedSignature) {
        this.personalizedSignature = personalizedSignature;
    }

    public String getOftenPlace() {
        return oftenPlace;
    }

    public void setOftenPlace(String oftenPlace) {
        this.oftenPlace = oftenPlace;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarRsurl() {
        return avatarRsurl;
    }

    public void setAvatarRsurl(String avatarRsurl) {
        this.avatarRsurl = avatarRsurl;
    }

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }

    public String getLiveCountry() {
        return liveCountry;
    }

    public void setLiveCountry(String liveCountry) {
        this.liveCountry = liveCountry;
    }

    public List<TourSiteDto> getTours() {
        return tours;
    }

    public void setTours(List<TourSiteDto> tours) {
        this.tours = tours;
    }

    public String getLiveCountryShortCode() {
        return liveCountryShortCode;
    }

    public void setLiveCountryShortCode(String liveCountryShortCode) {
        this.liveCountryShortCode = liveCountryShortCode;
    }
}
