package com.xmyy.circle.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 点赞记录
 * </p>
 *
 * @author zlp
 * @since 2018-06-07
 */
@ApiModel("点赞记录")
@TableName("dg_praise_record")
@SuppressWarnings("serial")
public class DgPraiseRecord extends BaseModel {

    @ApiModelProperty(value = "评论_ID")
	@TableField("comment_id_")
	private Long commentId;
    @ApiModelProperty(value = "动态_ID")
	@TableField("circle_id_")
	private Long circleId;
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
    @ApiModelProperty(value = "昵称")
	@TableField("nick_name")
	private String nickName;
    @ApiModelProperty(value = "目标昵称")
	@TableField("to_nick_name")
	private String toNickName;
    @ApiModelProperty(value = "目标头像")
	@TableField("to_avatar_rsurl")
	private String toAvatarRsurl;
    @ApiModelProperty(value = "商品评价_ID")
	@TableField("evaluate_id_")
	private Long evaluateId;


	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getCircleId() {
		return circleId;
	}

	public void setCircleId(Long circleId) {
		this.circleId = circleId;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public Long getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(Long evaluateId) {
		this.evaluateId = evaluateId;
	}

}