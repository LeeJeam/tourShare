package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 动态圈评论
 * </p>
 *
 * @author zlp
 * @since 2018-08-23
 */
@ApiModel("动态圈评论")
@TableName("dg_comment")
@SuppressWarnings("serial")
public class DgComment extends BaseModel {

    @ApiModelProperty(value = "父评论_ID")
	@TableField("parent_id_")
	private Long parentId;
    @ApiModelProperty(value = "动态圈_ID")
	@TableField("circle_id_")
	private Long circleId;
    @ApiModelProperty(value = "类型（1，笔记，2，视频）")
	@TableField("type_id")
	private Integer typeId;
    @ApiModelProperty(value = "评论内容")
	private String content;
    @ApiModelProperty(value = "点赞数")
	@TableField("praise_count")
	private Integer praiseCount;
    @ApiModelProperty(value = "买家/买手")
	@TableField("member_id")
	private Long memberId;
    @ApiModelProperty(value = "用户类型(1,买手，2买家)")
	@TableField("member_type")
	private Integer memberType;
    @ApiModelProperty(value = "目标用户id")
	@TableField("to_member_id")
	private Long toMemberId;
    @ApiModelProperty(value = "目标用户类型(1,买手，2买家)")
	@TableField("to_member_type")
	private Integer toMemberType;
    @ApiModelProperty(value = "头像")
	@TableField("avatar_rsurl")
	private String avatarRsurl;
    @ApiModelProperty(value = "目标昵称")
	@TableField("to_nick_name")
	private String toNickName;
    @ApiModelProperty(value = "目标头像")
	@TableField("to_avatar_rsurl")
	private String toAvatarRsurl;
    @ApiModelProperty(value = "昵称")
	@TableField("nick_name")
	private String nickName;
    @ApiModelProperty(value = "第一条回复json内容")
	@TableField("json_content")
	private String jsonContent;
    @ApiModelProperty(value = "回复数")
	@TableField("comment_count")
	private Integer commentCount;
    @ApiModelProperty(value = "根评论Id")
	@TableField("comment_id")
	private Long commentId;


	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getCircleId() {
		return circleId;
	}

	public void setCircleId(Long circleId) {
		this.circleId = circleId;
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

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Long getToMemberId() {
		return toMemberId;
	}

	public void setToMemberId(Long toMemberId) {
		this.toMemberId = toMemberId;
	}

	public Integer getToMemberType() {
		return toMemberType;
	}

	public void setToMemberType(Integer toMemberType) {
		this.toMemberType = toMemberType;
	}

	public String getAvatarRsurl() {
		return avatarRsurl;
	}

	public void setAvatarRsurl(String avatarRsurl) {
		this.avatarRsurl = avatarRsurl;
	}

	public String getToNickName() {
		return toNickName;
	}

	public void setToNickName(String toNickName) {
		this.toNickName = toNickName;
	}

	public String getToAvatarRsurl() {
		return toAvatarRsurl;
	}

	public void setToAvatarRsurl(String toAvatarRsurl) {
		this.toAvatarRsurl = toAvatarRsurl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getJsonContent() {
		return jsonContent;
	}

	public void setJsonContent(String jsonContent) {
		this.jsonContent = jsonContent;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

}