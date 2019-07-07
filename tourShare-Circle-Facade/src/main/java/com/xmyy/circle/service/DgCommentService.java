package com.xmyy.circle.service;

import com.xmyy.circle.model.DgComment;
import com.xmyy.circle.vo.CommentAddParam;
import com.xmyy.circle.vo.CommentPageResult;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.support.Pagination;

/**
 * <p>
 * 动态圈评论  服务类
 * </p>
 *
 * @author zlp
 * @since 2018-05-31
 */
public interface DgCommentService extends BaseService<DgComment> {

    /**
     * 动态详情页评论列表
     * @param id
     * @param current
     * @param size
     * @param currUser
     * @return
     */
    Pagination<CommentPageResult> getPageByCircleId(Long id, Integer current, Integer size,Long currUser);

    /**
     * 评论/回复
     * @param params
     * @return
     */
    Object addComment(CommentAddParam params);

    /**
     * 评论/回复 点赞/取消点赞
     * @param id    评论/回复id
     * @param memberId  当前登陆的用户id
     * @param memberType 当前登陆的用户类型(1买手端，2买家端)
     * @return
     */
    Object praise(Long id, Long memberId, Integer memberType);

    /**
     * 我的评论
     * @param type 1,收到的评论；2，发出的评论
     * @param memberId 当前登录用户
     * @param current
     * @param size
     * @return
     */
    Object mylist(Integer type, Integer memberType, Long memberId, Integer current, Integer size);

    /**
     * 回复列表
     * @param parentId
     * @param current
     * @param size
     * @param currUser
     * @return
     */
    Object getPageByParentId(Long parentId, Integer current, Integer size, Long currUser);

    Object getInfo(Long parentId, Long currUser);
}