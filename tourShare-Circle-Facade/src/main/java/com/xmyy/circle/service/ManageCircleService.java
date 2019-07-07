package com.xmyy.circle.service;

import com.xmyy.circle.model.UcDynamicCircle;
import com.xmyy.circle.vo.CircleDownParam;
import com.xmyy.circle.vo.CircleTopPageParam;
import com.xmyy.circle.vo.CircleTopParam;
import com.xmyy.circle.vo.CircleCountResult;
import com.xmyy.circle.vo.CircleTopPageResult;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.support.Pagination;

public interface ManageCircleService extends BaseService<UcDynamicCircle> {
    /**
     * 动态列表&买手动态列表
     * @param param
     * @return
     */
    Pagination<CircleTopPageResult> list(CircleTopPageParam param);

    /**
     * 置顶
     * @param param
     * @return
     */
    Object top(CircleTopParam param);

    /**
     * 上架
     * @param id
     * @param currUser
     * @return
     */
    Object setUp(Long id, Long currUser);

    /**
     * 下架
     * @param id
     * @param currUser
     * @return
     */
    Object setDown(Long id, Long currUser);

    /**
     * 获取统计结果
     * @param param
     * @return
     */
    CircleCountResult getResultCount(CircleTopPageParam param);

    /**
     * 取消置顶
     * @param param
     * @return
     */
    Object down(CircleDownParam param);
}
