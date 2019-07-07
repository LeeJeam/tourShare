package com.xmyy.search.vo;

import com.xmyy.search.core.SearchUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Document(indexName = "#{esConfig.circledataIndexName}",type = SearchUtils.TYPE_CIRCLE)
public class CircleDoc {
	
	@Id
	private Long id;
	private Long sellerId;
	private Integer typeId;
	private String content;
	private Integer praiseCount;
	private Integer commentCount;
	private String videoRsurl;
	private Integer readCount;
	private String coverRsurl;
	private String classifyTags;
	private Integer state;
	private Integer isTop;
	private Date topTime;
	private Date topEndTime;
	private String title;
	private Integer videoTimes;
	private Integer favoritesCount;
	private Long topOpId;
	private String nickName;
	private String avatarRsurl;
	private Integer isSelf;
	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public String getVideoRsurl() {
		return videoRsurl;
	}

	public void setVideoRsurl(String videoRsurl) {
		this.videoRsurl = videoRsurl;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public String getCoverRsurl() {
		return coverRsurl;
	}

	public void setCoverRsurl(String coverRsurl) {
		this.coverRsurl = coverRsurl;
	}

	public String getClassifyTags() {
		return classifyTags;
	}

	public void setClassifyTags(String classifyTags) {
		this.classifyTags = classifyTags;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getVideoTimes() {
		return videoTimes;
	}

	public void setVideoTimes(Integer videoTimes) {
		this.videoTimes = videoTimes;
	}

	public Integer getFavoritesCount() {
		return favoritesCount;
	}

	public void setFavoritesCount(Integer favoritesCount) {
		this.favoritesCount = favoritesCount;
	}

	public Long getTopOpId() {
		return topOpId;
	}

	public void setTopOpId(Long topOpId) {
		this.topOpId = topOpId;
	}
}
