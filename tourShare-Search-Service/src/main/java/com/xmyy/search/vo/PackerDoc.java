package com.xmyy.search.vo;

import com.xmyy.search.core.SearchUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Document(indexName = "#{esConfig.packerdataIndexName}",type = SearchUtils.TYPE_PACKER)
public class PackerDoc {
	
	@Id
	private Long id;
	private String uuid;
	private Integer state;
	private String mobile;
	private String personalizedSignature;
	private String oftenPlace;
	private String realName;
	private String nickName;
	private String avatarRsurl;
	private Integer trustValue;
	private Integer isPassIdentity;
	private Integer isPassPassport;
	private Integer isPassZhima;
	private Integer realState;
	private String classifyTags;
	private Integer gender;
	private String liveCountry;
	private Integer isPack;
	private Integer source;
	private Integer tourCount;
	private Integer tradeCount;
	private Integer favoritesCount;
	private String buyerNo;
	private Integer isVirtual;
	private String idCardNumber;
	private Integer loginSource;
	private Date tourTime;
	private Integer isTop;
	private Date topTime;
	private Date topEndTime;

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Date getTopTime() {
		return topTime;
	}

	public void setTopTime(Date topTime) {
		this.topTime = topTime;
	}

	public Date getTopEndTime() {
		return topEndTime;
	}

	public void setTopEndTime(Date topEndTime) {
		this.topEndTime = topEndTime;
	}

	public Date getTourTime() {
		return tourTime;
	}

	public void setTourTime(Date tourTime) {
		this.tourTime = tourTime;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public Integer getRealState() {
		return realState;
	}

	public void setRealState(Integer realState) {
		this.realState = realState;
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

	public Integer getIsPack() {
		return isPack;
	}

	public void setIsPack(Integer isPack) {
		this.isPack = isPack;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getTourCount() {
		return tourCount;
	}

	public void setTourCount(Integer tourCount) {
		this.tourCount = tourCount;
	}

	public Integer getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(Integer tradeCount) {
		this.tradeCount = tradeCount;
	}

	public Integer getFavoritesCount() {
		return favoritesCount;
	}

	public void setFavoritesCount(Integer favoritesCount) {
		this.favoritesCount = favoritesCount;
	}

	public String getBuyerNo() {
		return buyerNo;
	}

	public void setBuyerNo(String buyerNo) {
		this.buyerNo = buyerNo;
	}

	public Integer getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(Integer isVirtual) {
		this.isVirtual = isVirtual;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public Integer getLoginSource() {
		return loginSource;
	}

	public void setLoginSource(Integer loginSource) {
		this.loginSource = loginSource;
	}
}
