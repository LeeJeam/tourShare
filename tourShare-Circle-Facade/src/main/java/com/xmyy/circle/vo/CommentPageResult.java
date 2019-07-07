package com.xmyy.circle.vo;

import com.xmyy.circle.model.DgComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "动态详情页评论列表")
public class CommentPageResult extends DgComment implements Serializable {

    @ApiModelProperty(value = "是否点赞（1是，0否）")
    private Integer isPraised;

    @ApiModelProperty(value = "回复")
    private CommentPageResult comment;

    public CommentPageResult getComment() {
        return comment;
    }

    public void setComment(CommentPageResult comment) {
        this.comment = comment;
    }

    public Integer getIsPraised() {
        return isPraised;
    }

    public void setIsPraised(Integer isPraised) {
        this.isPraised = isPraised;
    }
}
