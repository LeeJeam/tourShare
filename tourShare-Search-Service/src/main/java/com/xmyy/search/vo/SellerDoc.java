package com.xmyy.search.vo;

import com.xmyy.search.core.SearchUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Document(indexName = "#{esConfig.sellerdataIndexName}",type = SearchUtils.TYPE_SELLER)
//@Setting(settingPath = "/mapping/yy_sellerdata.mapping.json")
public class SellerDoc {
	
	@Id
	private Long id;
	//	@Field(type = FieldType.keyword,fielddata = true,analyzer="fulltextAnalyzerMax", searchAnalyzer="fulltextAnalyzerMax")
	//@CompletionField(analyzer="fulltextAnalyzerMax", searchAnalyzer="fulltextAnalyzerMax")
	private String uuid;
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
	private Integer isSelf;
	private Integer realState;
	private String liveCountry;
	private String liveCountryShortCode;
	private Date birthday;
	private Integer gender;
	private Integer isTop;
	private Date topTime;
	private Date topEndTime;
	private String classifyTags;
	private Integer tradeCount;
	private Date tourTime;
	private Integer tourCount;
	private String sellerNo;
	private Integer state;
	private String productRsurl;

	public String getProductRsurl() {
		return productRsurl;
	}

	public void setProductRsurl(String productRsurl) {
		this.productRsurl = productRsurl;
	}


	//@CompletionField(analyzer="fulltextAnalyzerMax", searchAnalyzer="fulltextAnalyzerMax")
	//private Completion autoCompletion;


	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public Integer getIsSelf() {
		return isSelf;
	}

	public void setIsSelf(Integer isSelf) {
		this.isSelf = isSelf;
	}

	public Integer getRealState() {
		return realState;
	}

	public void setRealState(Integer realState) {
		this.realState = realState;
	}

	public String getLiveCountry() {
		return liveCountry;
	}

	public void setLiveCountry(String liveCountry) {
		this.liveCountry = liveCountry;
	}

	public String getLiveCountryShortCode() {
		return liveCountryShortCode;
	}

	public void setLiveCountryShortCode(String liveCountryShortCode) {
		this.liveCountryShortCode = liveCountryShortCode;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

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

	public String getClassifyTags() {
		return classifyTags;
	}

	public void setClassifyTags(String classifyTags) {
		this.classifyTags = classifyTags;
	}

	public Integer getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(Integer tradeCount) {
		this.tradeCount = tradeCount;
	}

	public Date getTourTime() {
		return tourTime;
	}

	public void setTourTime(Date tourTime) {
		this.tourTime = tourTime;
	}

	public Integer getTourCount() {
		return tourCount;
	}

	public void setTourCount(Integer tourCount) {
		this.tourCount = tourCount;
	}

	public String getSellerNo() {
		return sellerNo;
	}

	public void setSellerNo(String sellerNo) {
		this.sellerNo = sellerNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*//public Completion getAutoCompletion() {
		return autoCompletion;
	}

	public void setAutoCompletion(Completion autoCompletion) {
		this.autoCompletion = autoCompletion;
	}*/
}
