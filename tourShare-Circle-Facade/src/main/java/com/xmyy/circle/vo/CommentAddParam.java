package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "动态圈评论/回复")
@SuppressWarnings("serial")
public class CommentAddParam implements Serializable {

    @ApiModelProperty(value = "非回复时，父评论id不传")
	private Long parentId;

    @NotNull(message = "笔记/视频ID不能为空")
    @ApiModelProperty(value = "笔记/视频ID", required = true)
	private Long circleId;

	@NotBlank(message = "评论内容不能为空")
    @ApiModelProperty(value = "评论内容", required = true)
	private String content;

    @ApiModelProperty(value = "当前登陆的买家/买手")
	private Long memberId;

	@NotNull(message = "当前登陆的用户类型不能为空")
    @ApiModelProperty(value = "当前登陆的用户类型(1买手端，2买家端)", required = true)
	private Integer memberType;

	@ApiModelProperty(value = "根评论Id")
	private Long commentId;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
}