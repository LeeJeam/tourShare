package com.xmyy.circle.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 动态圈
 * </p>
 *
 * @author zlp
 * @since 2018-08-20
 */
@ApiModel("动态圈")
@TableName("uc_dynamic_circle")
@SuppressWarnings("serial")
public class UcDynamicCircle extends BaseModel {

    @ApiModelProperty(value = "买手_ID")
	@TableField("seller_id_")
	private Long sellerId;
    @ApiModelProperty(value = "类型（1，笔记，2，视频）")
	@TableField("type_id")
	private Integer typeId;
    @ApiModelProperty(value = "笔记内容")
	private String content;
    @ApiModelProperty(value = "点赞数")
	@TableField("praise_count")
	private Integer praiseCount;
    @ApiModelProperty(value = "评论数")
	@TableField("comment_count")
	private Integer commentCount;
    @ApiModelProperty(value = "视频路径")
	@TableField("video_rsurl")
	private String videoRsurl;
    @ApiModelProperty(value = "阅读数")
	@TableField("read_count")
	private Integer readCount;
    @ApiModelProperty(value = "视频播放数")
	@TableField("play_count")
	private Integer playCount;
    @ApiModelProperty(value = "封面地址(多个以逗号隔开)")
	@TableField("cover_rsurl")
	private String coverRsurl;
    @ApiModelProperty(value = "分类标签(多个以逗号隔开)")
	@TableField("classify_tags")
	private String classifyTags;
    @ApiModelProperty(value = "状态（0发布中，10,审核不通过，50上架，-50下架）")
	private Integer state;
    @ApiModelProperty(value = "是否置顶（0否，1是）")
	@TableField("is_top")
	private Integer isTop;
    @ApiModelProperty(value = "置顶操作时间")
	@TableField("top_time")
	private Date topTime;
    @ApiModelProperty(value = "置顶有效时间")
	@TableField("top_end_time")
	private Date topEndTime;
    @ApiModelProperty(value = "标题")
	private String title;
    @ApiModelProperty(value = "视频时长")
	@TableField("video_times")
	private Integer videoTimes;
    @ApiModelProperty(value = "收藏数")
	@TableField("favorites_count")
	private Integer favoritesCount;
    @ApiModelProperty(value = "置顶操作人")
	@TableField("top_op_id")
	private Long topOpId;
    @ApiModelProperty(value = "第一条回复json内容")
	@TableField("json_content")
	private String jsonContent;


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

	public Integer getPlayCount() {
		return playCount;
	}

	public void setPlayCount(Integer playCount) {
		this.playCount = playCount;
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

	public String getJsonContent() {
		return jsonContent;
	}

	public void setJsonContent(String jsonContent) {
		this.jsonContent = jsonContent;
	}

}