package com.xmyy.circle.service;

import com.xmyy.circle.model.DgPraiseRecord;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * <p>
 * 点赞记录  服务类
 * </p>
 *
 * @author zlp
 * @since 2018-06-06
 */
public interface DgPraiseRecordService extends BaseService<DgPraiseRecord> {

    /**
     * 我的赞
     * @param type
     * @param currUser
     * @param current
     * @param size
     * @return
     */
    Object mylist(Integer type, Long currUser, Integer current, Integer size);

    /**
     * 点赞/取消点赞
     * @param params
     * @return
     */
    Boolean addPraise(DgPraiseRecord params);

    /**
     * @param memberId
     * @param entityId
     * @param entityTypeId 评论_ID:comment_id_; 动态_ID:circle_id_;商品评价_ID:evaluate_id_
     * @return
     */
    List<DgPraiseRecord> getPraisesByMemberId(Long memberId, Long entityId, String entityTypeId);

    /**
     * 批量查询
     * @param memberId
     * @param entityIds
     * @param entityTypeId 评论_ID:comment_id; 动态_ID:circle_id;商品评价_ID:evaluate_id
     * @return
     */
    public List<DgPraiseRecord> getPraisesByMemberId(Long memberId, List<Long> entityIds, String entityTypeId);
}