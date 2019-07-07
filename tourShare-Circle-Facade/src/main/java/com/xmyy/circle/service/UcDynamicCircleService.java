package com.xmyy.circle.service;

import com.xmyy.circle.model.UcDynamicCircle;
import com.xmyy.circle.vo.BatchCancelParam;
import com.xmyy.circle.vo.CirclePageParam;
import com.xmyy.circle.vo.DynamicCircleParam;
import com.xmyy.circle.vo.CircleDetailResult;
import com.xmyy.circle.vo.CirclePageResult;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.support.Pagination;

/**
 * <p>
 * 动态圈  服务类
 * </p>
 *
 * @author zlp
 * @since 2018-05-31
 */
public interface UcDynamicCircleService extends BaseService<UcDynamicCircle> {

    /**
     * 笔记/视频发布
     * @param params
     * @return
     */
    Object addCircle(DynamicCircleParam params);

    /**
     * 笔记/视频详情
     * @param id
     * @param currUser
     * @return
     */
    CircleDetailResult getInfo(Long id, Long currUser);

    /**
     * 笔记/视频 列表（首页/发现列表/买家端买手详情页动态）
     * @param param
     * @return
     */
    public Pagination<CirclePageResult> list(CirclePageParam param);

    /**
     * 播放量增加
     * @param id
     * @return
     */
    Object addPlayCount(Long id);

    /**
     * 笔记视频 点赞/取消点赞
     * @param id
     * @param memberId
     * @param memberType
     * @return
     */
    Object praise(Long id, Long memberId, Integer memberType);

    /**
     * 加减收藏数
     * @param id
     * @param isAdd
     * @return
     */
    Object updateFavorites(Long id, Boolean isAdd);

    /**
     * 取消“过期”的动态置顶
     */
    void cancelTopOverTime();

    /**
     * 批量删除动态
     * @param params
     * @return
     */
    Object batchDel(BatchCancelParam params);

    /**
     * 买手端我的动态列表
     * @param sellerId
     * @param typeId
     * @param current
     * @param size
     * @return
     */
    Object myList(Long sellerId, Integer typeId, Integer current, Integer size);
}