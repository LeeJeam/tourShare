package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value = "用户信息")
public class MemberInfoResult implements Serializable {

    private Long id;

    @ApiModelProperty(value = "登录成功后，把该sessid值添加Headers,参数名为sessid")
    private String sessid;

    @ApiModelProperty(value = "用户uuid")
    private String uuid;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "用户个性签名")
    private String personalizedSignature;

    @ApiModelProperty(value = "常去的地方")
    private String oftenPlace;

    @ApiModelProperty(value = "真实名字")
    private String realName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatarRsurl;

    @ApiModelProperty(value = "信任值")
    private Integer trustValue;

    @ApiModelProperty(value = "是否身份实名认证（1是，0否）")
    private Integer isPassIdentity;

    @ApiModelProperty(value = "是否护照实名认证（1是，0否）")
    private Integer isPassPassport;

    @ApiModelProperty(value = "是否芝麻认证（1是，0否）")
    private Integer isPassZhima;

    @ApiModelProperty(value = "分类标签")
    private String classifyTags;

    @ApiModelProperty(value = "性别(1男，2女)")
    private Integer gender;

    @ApiModelProperty(value = "居住国家")
    private String liveCountry;

    @ApiModelProperty(value = "是否背包客（1是，0否）")
    private Integer isPack;

    @ApiModelProperty(value = "登陆地")
    private String loginCountry;

    @ApiModelProperty(value = "成交量")
    private Integer tradeCount;

    @ApiModelProperty(value = "买手二维码，json数据:{\"content\":\"1\",\"type\":2};type:1，买手，2，背包客")
    private String qrCode;

    @ApiModelProperty(value = "关注数")
    private Integer followCount;

    @ApiModelProperty(value = "认证状态（0提交认证，50认证审核通过，-50认证审核不通过）")
    private Integer realState;

    @ApiModelProperty(value = "是否身份实名认证")
    private String isPassIdentityLabel;

    @ApiModelProperty(value = "是否护照实名认证")
    private String isPassPassportLabel;

    @ApiModelProperty(value = "是否芝麻认证")
    private String isPassZhimaLabel;

    @ApiModelProperty(value = "通联绑定手机号")
    private String bindPhone;

    @ApiModelProperty(value = "平台支付密码")
    private Long payPasswordId;

    @ApiModelProperty(value = "性别文字（女，男）")
    private String genderLabel;

    @ApiModelProperty(value = "买手是否被此买家关注（1是，0否）")
    private Integer isFollowed;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNumber;

    @ApiModelProperty(value = "买家端 我的订单各状态统计数")
    private OrderInfo orderInfo;

    @ApiModelProperty(value = "买手编号")
    private String sellerNo;

    @ApiModelProperty(value = "买家编号")
    private String buyerNo;

    @ApiModelProperty(value = "当前登录来源端（1，安卓，2苹果，3小程序，4PC）")
    private Integer loginSource;

    @ApiModelProperty(value = "个人信息是否全部设置（0未完全设置，1已全部设置）")
    private Integer isFinishSetInfo;

    @ApiModelProperty(value = "尚未上传登机牌的行程ID列表")
    private List<Long> noCheckPicTourIds;

    @ApiModelProperty(value = "收到的评价列表")
    private List<EvaluateList> evaluates;

    @ApiModel(value = "收到的评价")
    public static class EvaluateList implements Serializable {

        @ApiModelProperty(value = "商品评价ID")
        private Long id;

        @ApiModelProperty(value = "订单ID")
        private Long orderId;

        @ApiModelProperty(value = "买手/背包客ID")
        private String sellerId;

        @ApiModelProperty(value = "买家ID")
        private String buyerId;

        @ApiModelProperty(value = "买家昵称")
        private String nickName;

        @ApiModelProperty(value = "买家头像")
        private String avatarRsurl;

        @ApiModelProperty(value = "评价内容")
        private String content;

        @ApiModelProperty(value = "评价图片数组")
        private List<String> pics;

        @ApiModelProperty(value = "追评内容")
        private String reviewContent;

        @ApiModelProperty(value = "评价时间")
        private Date createTime;

        @ApiModelProperty(value = "追价时间")
        private Date reviewTime;

        @ApiModelProperty(value = "追评间隔天数")
        private Integer dayDiff;

        @ApiModelProperty(value = "商品快照ID")
        private Long productOrderId;

        @ApiModelProperty(value = "商品ID（点击跳转使用这个ID）")
        private Long productId;

        @ApiModelProperty(value = "商品标题")
        private String title;

        @ApiModelProperty(value = "商品总计金额")
        private BigDecimal subtotal;

        @ApiModelProperty(value = "商品类型(1预售，2现货，3需求)")
        private Integer productType;

        @ApiModelProperty(value = "商品封面")
        private String productCover;

        @ApiModelProperty(value = "商品单价")
        private BigDecimal productPrice;

        public Integer getProductType() {
            return productType;
        }

        public void setProductType(Integer productType) {
            this.productType = productType;
        }

        public String getProductCover() {
            return productCover;
        }

        public void setProductCover(String productCover) {
            this.productCover = productCover;
        }

        public BigDecimal getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(BigDecimal productPrice) {
            this.productPrice = productPrice;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
        }

        public String getBuyerId() {
            return buyerId;
        }

        public void setBuyerId(String buyerId) {
            this.buyerId = buyerId;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getReviewContent() {
            return reviewContent;
        }

        public void setReviewContent(String reviewContent) {
            this.reviewContent = reviewContent;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Date getReviewTime() {
            return reviewTime;
        }

        public void setReviewTime(Date reviewTime) {
            this.reviewTime = reviewTime;
        }

        public Integer getDayDiff() {
            return dayDiff;
        }

        public void setDayDiff(Integer dayDiff) {
            this.dayDiff = dayDiff;
        }

        public Long getProductOrderId() {
            return productOrderId;
        }

        public void setProductOrderId(Long productOrderId) {
            this.productOrderId = productOrderId;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public BigDecimal getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(BigDecimal subtotal) {
            this.subtotal = subtotal;
        }

        public List<String> getPics() {
            return pics;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }
    }

    @ApiModelProperty(value = "行程数统计显示文字")
    private String tourTitle;

    @ApiModelProperty(value = "最新进行中行程")
    private TourDto tour;

    public static class TourDto implements Serializable {
        @ApiModelProperty(value = "行程ID")
        private Long tourId;

        @ApiModelProperty(value = "行程创建时间文字")
        private String createTimeStr;

        @ApiModelProperty(value = "行程状态文字")
        private String statusStr;

        @ApiModelProperty(value = "行程站点信息")
        private List<TourSiteDto> tourSites;

        @ApiModel(value = "行程站点信息")
        public static class TourSiteDto implements Serializable {
            @ApiModelProperty(value = "站点名称")
            private String regionName;

            @ApiModelProperty(value = "站点国旗")
            private String regionLogo;

            @ApiModelProperty(value = "是否签到（0未签到；1-已签到）")
            private Integer isSignIn;

            @ApiModelProperty(value = "出发时间文字")
            private String departureTimeStr;

            @ApiModelProperty(value = "该站点时间是否跨年（0否；1是）")
            private Integer acrossYear = 0;

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

            public String getDepartureTimeStr() {
                return departureTimeStr;
            }

            public void setDepartureTimeStr(String departureTimeStr) {
                this.departureTimeStr = departureTimeStr;
            }

            public Integer getAcrossYear() {
                return acrossYear;
            }

            public void setAcrossYear(Integer acrossYear) {
                this.acrossYear = acrossYear;
            }
        }

        public Long getTourId() {
            return tourId;
        }

        public void setTourId(Long tourId) {
            this.tourId = tourId;
        }

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public List<TourSiteDto> getTourSites() {
            return tourSites;
        }

        public void setTourSites(List<TourSiteDto> tourSites) {
            this.tourSites = tourSites;
        }

        public String getStatusStr() {
            return statusStr;
        }

        public void setStatusStr(String statusStr) {
            this.statusStr = statusStr;
        }
    }

    public TourDto getTour() {
        return tour;
    }

    public void setTour(TourDto tour) {
        this.tour = tour;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public void setIsPassIdentityLabel(String isPassIdentityLabel) {
        this.isPassIdentityLabel = isPassIdentityLabel;
    }

    public String getIsPassPassportLabel() {
        return isPassPassportLabel;
    }

    public void setIsPassPassportLabel(String isPassPassportLabel) {
        this.isPassPassportLabel = isPassPassportLabel;
    }

    public String getIsPassZhimaLabel() {
        return isPassZhimaLabel;
    }

    public void setIsPassZhimaLabel(String isPassZhimaLabel) {
        this.isPassZhimaLabel = isPassZhimaLabel;
    }

    public String getGenderLabel() {
        return genderLabel;
    }

    public void setGenderLabel(String genderLabel) {
        this.genderLabel = genderLabel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public Integer getTrustValue() {
        return trustValue;
    }

    public void setTrustValue(Integer trustValue) {
        this.trustValue = trustValue;
    }

    public Integer getIsPassIdentity() {
        return isPassIdentity;
    }

    public void setIsPassIdentity(Integer isPassIdentity) {
        this.isPassIdentity = isPassIdentity;
    }

    public Integer getIsPassPassport() {
        return isPassPassport;
    }

    public void setIsPassPassport(Integer isPassPassport) {
        this.isPassPassport = isPassPassport;
    }

    public Integer getIsPassZhima() {
        return isPassZhima;
    }

    public void setIsPassZhima(Integer isPassZhima) {
        this.isPassZhima = isPassZhima;
    }

    public String getClassifyTags() {
        return classifyTags;
    }

    public void setClassifyTags(String classifyTags) {
        this.classifyTags = classifyTags;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getLiveCountry() {
        return liveCountry;
    }

    public void setLiveCountry(String liveCountry) {
        this.liveCountry = liveCountry;
    }

    public String getTourTitle() {
        return tourTitle;
    }

    public void setTourTitle(String tourTitle) {
        this.tourTitle = tourTitle;
    }

    public Integer getIsPack() {
        return isPack;
    }

    public void setIsPack(Integer isPack) {
        this.isPack = isPack;
    }

    public String getIsPassIdentityLabel() {
        return isPassIdentityLabel;
    }

    public String getLoginCountry() {
        return loginCountry;
    }

    public void setLoginCountry(String loginCountry) {
        this.loginCountry = loginCountry;
    }

    public Integer getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Integer tradeCount) {
        this.tradeCount = tradeCount;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public List<EvaluateList> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<EvaluateList> evaluates) {
        this.evaluates = evaluates;
    }

    public Integer getRealState() {
        return realState;
    }

    public void setRealState(Integer realState) {
        this.realState = realState;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    public Integer getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(Integer isFollowed) {
        this.isFollowed = isFollowed;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getSessid() {
        return sessid;
    }

    public void setSessid(String sessid) {
        this.sessid = sessid;
    }

    public String getSellerNo() {
        return sellerNo;
    }

    public void setSellerNo(String sellerNo) {
        this.sellerNo = sellerNo;
    }

    public String getBuyerNo() {
        return buyerNo;
    }

    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    public Integer getLoginSource() {
        return loginSource;
    }

    public void setLoginSource(Integer loginSource) {
        this.loginSource = loginSource;
    }

    public Integer getIsFinishSetInfo() {
        return isFinishSetInfo;
    }

    public void setIsFinishSetInfo(Integer isFinishSetInfo) {
        this.isFinishSetInfo = isFinishSetInfo;
    }

    public List<Long> getNoCheckPicTourIds() {
        return noCheckPicTourIds;
    }

    public void setNoCheckPicTourIds(List<Long> noCheckPicTourIds) {
        this.noCheckPicTourIds = noCheckPicTourIds;
    }

    public String getBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(String bindPhone) {
        this.bindPhone = bindPhone;
    }

    public Long getPayPasswordId() {
        return payPasswordId;
    }

    public void setPayPasswordId(Long payPasswordId) {
        this.payPasswordId = payPasswordId;
    }
}
