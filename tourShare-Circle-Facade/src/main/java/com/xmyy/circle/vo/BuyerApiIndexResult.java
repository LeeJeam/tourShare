package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value = "买家首页对象")
public class BuyerApiIndexResult implements Serializable {

    @ApiModelProperty(value = "广告图")
    private List<Adverts> ads;

    @ApiModelProperty(value = "热门国家")
    private List<String> countrys;

    @ApiModelProperty(value = "热门标签")
    private List<String> tags;

    @ApiModelProperty(value = "买手行程标题")
    private String sellerTourTitle;

    @ApiModelProperty(value = "买手行程描述")
    private String sellerTourDesc;

    @ApiModelProperty(value = "买手行程列表")
    private List<Tours> tours;

    @ApiModelProperty(value = "最新预售标题")
    private String presellTitle;

    @ApiModelProperty(value = "最新预售描述")
    private String presellDec;

    @ApiModelProperty(value = "最新预售商品")
    private List<PresellProducts> presellProducts;

    @ApiModelProperty(value = "买手title")
    private String sellerTitle;

    @ApiModelProperty(value = "买手描述")
    private String sellerDec;

    @ApiModelProperty(value = "推荐买手")
    private List<Sellers> sellers;

    @ApiModelProperty(value = "精选内容title")
    private String circleTitle;

    @ApiModelProperty(value = "精选内容描述")
    private String circleDec;

    @ApiModelProperty(value = "精选内容")
    private List<Circles> circles;

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

    @ApiModel(value = "精选内容")
    public static class Circles implements Serializable {

        private Long id;

        @ApiModelProperty(value = "昵称")
        private String nickName;

        @ApiModelProperty(value = "头像")
        private String avatarRsurl;

        @ApiModelProperty(value = "类型（1，笔记，2，视频）")
        private Integer typeId;

        @ApiModelProperty(value = "标题")
        private String title;

        @ApiModelProperty(value = "封面地址(多个以英文逗号隔开)")
        private String coverRsurl;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public Integer getTypeId() {
            return typeId;
        }

        public void setTypeId(Integer typeId) {
            this.typeId = typeId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCoverRsurl() {
            return coverRsurl;
        }

        public void setCoverRsurl(String coverRsurl) {
            this.coverRsurl = coverRsurl;
        }
    }

    @ApiModel(value = "推荐买手")
    public static class Sellers implements Serializable {

        private Long id;

        @ApiModelProperty(value = "用户个性签名")
        private String personalizedSignature;

        @ApiModelProperty(value = "昵称")
        private String nickName;

        @ApiModelProperty(value = "头像")
        private String avatarRsurl;

        @ApiModelProperty(value = "买手类型")
        private String isSelfStr;

        @ApiModelProperty(value = "uuid")
        private String uuid;

        @ApiModelProperty(value = "分类标签")
        private String classifyTags;

        @ApiModelProperty(value = "买手类型：1，资深买手；0大众买手；2，背包客")
        private Integer isSelf;

        public Integer getIsSelf() {
            return isSelf;
        }

        public void setIsSelf(Integer isSelf) {
            this.isSelf = isSelf;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getClassifyTags() {
            return classifyTags;
        }

        public void setClassifyTags(String classifyTags) {
            this.classifyTags = classifyTags;
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

        public String getIsSelfStr() {
            return isSelfStr;
        }

        public void setIsSelfStr(String isSelfStr) {
            this.isSelfStr = isSelfStr;
        }
    }

    @ApiModel(value = "最新预售")
    public static class PresellProducts implements Serializable {

        @ApiModelProperty(value = "商品ID")
        private Long id;

        @ApiModelProperty(value = "商品标题")
        private String title;

        @ApiModelProperty(value = "商品封面")
        private String cover;

        @ApiModelProperty(value = "购买国家")
        private String buyRegion;

        @ApiModelProperty(value = "预定数")
        private Integer orderCount;

        @ApiModelProperty(value = "价格")
        private BigDecimal price;

        @ApiModelProperty(value = "失效时间")
        private Date expiresTime;

        @ApiModelProperty(value = "行程ID")
        private Long tourId;

        @ApiModelProperty(value = "购买地图标")
        private String buyRegionLogo;

        @ApiModelProperty(value = "购买地币种")
        private String buyRegionCurrency;

        @ApiModelProperty(value = "购买地币种编码")
        private String buyRegionCurrencyCode;

        @ApiModelProperty(value = "当前币种")
        private String currency;

        @ApiModelProperty(value = "当前币种图标")
        private String currencyCode;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getBuyRegion() {
            return buyRegion;
        }

        public void setBuyRegion(String buyRegion) {
            this.buyRegion = buyRegion;
        }

        public Integer getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(Integer orderCount) {
            this.orderCount = orderCount;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public Date getExpiresTime() {
            return expiresTime;
        }

        public void setExpiresTime(Date expiresTime) {
            this.expiresTime = expiresTime;
        }

        public Long getTourId() {
            return tourId;
        }

        public void setTourId(Long tourId) {
            this.tourId = tourId;
        }

        public String getBuyRegionLogo() {
            return buyRegionLogo;
        }

        public void setBuyRegionLogo(String buyRegionLogo) {
            this.buyRegionLogo = buyRegionLogo;
        }

        public String getBuyRegionCurrency() {
            return buyRegionCurrency;
        }

        public void setBuyRegionCurrency(String buyRegionCurrency) {
            this.buyRegionCurrency = buyRegionCurrency;
        }

        public String getBuyRegionCurrencyCode() {
            return buyRegionCurrencyCode;
        }

        public void setBuyRegionCurrencyCode(String buyRegionCurrencyCode) {
            this.buyRegionCurrencyCode = buyRegionCurrencyCode;
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
    }

    @ApiModel(value = "买手行程")
    public static class Tours implements Serializable {

        @ApiModelProperty(value = "id")
        private Long id;

        @ApiModelProperty(value = "行程编号")
        private String tourNo;

        @ApiModelProperty(value = "标签，多个逗号隔开")
        private String tags;

        @ApiModelProperty(value = "创建时间")
        private Date createTime;

        @ApiModelProperty(value = "行程的状态（0-已下架，1-未进行，2-进行中，3-已完成）")
        private Integer status;

        @ApiModelProperty(value = "审核状态（0-未上传，默认；1-未审核；2-审核通过；3-审核不通过）")
        private Integer auditStatus;

        @ApiModelProperty(value = "首站名称")
        private String firstRegionName;

        @ApiModelProperty(value = "终点站名称")
        private String destRegionName;

        @ApiModelProperty(value = "首站时间")
        private Date firstTime;

        @ApiModelProperty(value = "返程时间")
        private Date returnTime;

        @ApiModelProperty(value = "行程站点数")
        private Integer siteCount;

        @ApiModelProperty(value = "当前站点名称")
        private String curRegionName;

        @ApiModelProperty(value = "当前站点logo")
        private String curImgLogo;

        @ApiModelProperty(value = "当前站点卡片")
        private String curImgUrl;

        @ApiModelProperty(value = "昵称")
        private String nickName;

        @ApiModelProperty(value = "头像")
        private String avatarRsurl;

        @ApiModelProperty(value = "用户ID")
        private Long userId;

        @ApiModelProperty(value = "用户UUID")
        private String uuid;

        @ApiModelProperty(value = "是否背包客（0否，1是）")
        private Integer isPacker;

        @ApiModelProperty(value = "是否正在直播（0否，1是）")
        private Integer isLive;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTourNo() {
            return tourNo;
        }

        public void setTourNo(String tourNo) {
            this.tourNo = tourNo;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(Integer auditStatus) {
            this.auditStatus = auditStatus;
        }

        public String getFirstRegionName() {
            return firstRegionName;
        }

        public void setFirstRegionName(String firstRegionName) {
            this.firstRegionName = firstRegionName;
        }

        public String getDestRegionName() {
            return destRegionName;
        }

        public void setDestRegionName(String destRegionName) {
            this.destRegionName = destRegionName;
        }

        public Date getFirstTime() {
            return firstTime;
        }

        public void setFirstTime(Date firstTime) {
            this.firstTime = firstTime;
        }

        public Date getReturnTime() {
            return returnTime;
        }

        public void setReturnTime(Date returnTime) {
            this.returnTime = returnTime;
        }

        public Integer getSiteCount() {
            return siteCount;
        }

        public void setSiteCount(Integer siteCount) {
            this.siteCount = siteCount;
        }

        public String getCurRegionName() {
            return curRegionName;
        }

        public void setCurRegionName(String curRegionName) {
            this.curRegionName = curRegionName;
        }

        public String getCurImgLogo() {
            return curImgLogo;
        }

        public void setCurImgLogo(String curImgLogo) {
            this.curImgLogo = curImgLogo;
        }

        public String getCurImgUrl() {
            return curImgUrl;
        }

        public void setCurImgUrl(String curImgUrl) {
            this.curImgUrl = curImgUrl;
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

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public Integer getIsPacker() {
            return isPacker;
        }

        public void setIsPacker(Integer isPacker) {
            this.isPacker = isPacker;
        }

        public Integer getIsLive() {
            return isLive;
        }

        public void setIsLive(Integer isLive) {
            this.isLive = isLive;
        }
    }

    public String getSellerTitle() {
        return sellerTitle;
    }

    public void setSellerTitle(String sellerTitle) {
        this.sellerTitle = sellerTitle;
    }

    public String getSellerDec() {
        return sellerDec;
    }

    public void setSellerDec(String sellerDec) {
        this.sellerDec = sellerDec;
    }

    public List<Sellers> getSellers() {
        return sellers;
    }

    public void setSellers(List<Sellers> sellers) {
        this.sellers = sellers;
    }


    public String getCircleTitle() {
        return circleTitle;
    }

    public void setCircleTitle(String circleTitle) {
        this.circleTitle = circleTitle;
    }

    public String getCircleDec() {
        return circleDec;
    }

    public void setCircleDec(String circleDec) {
        this.circleDec = circleDec;
    }

    public List<Circles> getCircles() {
        return circles;
    }

    public void setCircles(List<Circles> circles) {
        this.circles = circles;
    }

    public String getPresellTitle() {
        return presellTitle;
    }

    public void setPresellTitle(String presellTitle) {
        this.presellTitle = presellTitle;
    }

    public String getPresellDec() {
        return presellDec;
    }

    public void setPresellDec(String presellDec) {
        this.presellDec = presellDec;
    }

    public List<PresellProducts> getPresellProducts() {
        return presellProducts;
    }

    public void setPresellProducts(List<PresellProducts> presellProducts) {
        this.presellProducts = presellProducts;
    }

    public List<Adverts> getAds() {
        return ads;
    }

    public void setAds(List<Adverts> ads) {
        this.ads = ads;
    }

    public List<String> getCountrys() {
        return countrys;
    }

    public void setCountrys(List<String> countrys) {
        this.countrys = countrys;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSellerTourTitle() {
        return sellerTourTitle;
    }

    public void setSellerTourTitle(String sellerTourTitle) {
        this.sellerTourTitle = sellerTourTitle;
    }

    public String getSellerTourDesc() {
        return sellerTourDesc;
    }

    public void setSellerTourDesc(String sellerTourDesc) {
        this.sellerTourDesc = sellerTourDesc;
    }

    public List<Tours> getTours() {
        return tours;
    }

    public void setTours(List<Tours> tours) {
        this.tours = tours;
    }

}
