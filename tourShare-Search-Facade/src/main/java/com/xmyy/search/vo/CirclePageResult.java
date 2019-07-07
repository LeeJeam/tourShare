package com.xmyy.search.vo;

import com.alibaba.dubbo.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ApiModel(value = "动态搜索列表")
public class CirclePageResult implements Serializable {

    private Long id;

    @ApiModelProperty(value = "类型（1，笔记，2，视频）")
    private Integer typeId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "笔记内容")
    private String content;

    @ApiModelProperty(value = "视频路径")
    private String videoRsurl;

    @ApiModelProperty(value = "阅读数")
    private Integer readCount;

    @ApiModelProperty(value = "封面地址(多个以逗号隔开)")
    private String coverRsurl;

    @ApiModelProperty(value = "分类标签(多个以逗号隔开)")
    private String classifyTags;

    @ApiModelProperty(value = "状态（0发布中，10,审核不通过，50上架，-50下架）")
    private Integer state;

    @ApiModelProperty(value = "视频时长")
    private Integer videoTimes;

    @ApiModelProperty(value = "收藏数")
    private Integer favoritesCount;

    @ApiModelProperty(value = "点赞数")
    private Integer praiseCount;

    @ApiModelProperty(value = "评论数")
    private Integer commentCount;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatarRsurl;

    @ApiModelProperty(value = "买手类型（1资深买手；0大众买手）")
    private Integer isSelf;

    @ApiModelProperty(value = "买手类型（1资深买手；0大众买手）")
    private String isSelfStr;

    @ApiModelProperty(value = "创建时间,比如：58分钟前")
    private String createTimeStr;

    @ApiModelProperty(value = "封面地址(多个以英文逗号隔开)")
    private List<String> coverRsurlList;

    @ApiModelProperty(value = "是否点赞（1是，0否）")
    private Integer isPraised;

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }

    public String getIsSelfStr() {
        return isSelfStr;
    }

    public void setIsSelfStr(String isSelfStr) {
        this.isSelfStr = isSelfStr;
    }

    public List<String> getCoverRsurlList() {
        if(!StringUtils.isBlank(this.getCoverRsurl())){
            coverRsurlList = Arrays.asList(this.getCoverRsurl().split(","));
        }
        return coverRsurlList;
    }

    public void setCoverRsurlList(List<String> coverRsurlList) {
        this.coverRsurlList = coverRsurlList;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
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

    public Integer getIsPraised() {
        return isPraised;
    }

    public void setIsPraised(Integer isPraised) {
        this.isPraised = isPraised;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
