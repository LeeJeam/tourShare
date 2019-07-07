package com.xmyy.circle.service;

import com.xmyy.circle.model.DgFavorites;
import com.xmyy.circle.vo.BatchCancelParam;
import com.xmyy.circle.vo.FavoritesAddParam;
import com.xmyy.circle.vo.MyFavoritesResult;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.support.Pagination;

import java.util.List;

/**
 * <p>
 * 收藏表  服务类
 * </p>
 *
 * @author zlp
 * @since 2018-05-31
 */
public interface DgFavoritesService extends BaseService<DgFavorites> {

    /**
     * 我的收藏
     * @param type 1，笔记；2，视频；3，商品
     * @param currUser
     * @param current
     * @param size
     * @param memberType 当前登陆的用户类型(1,买手，2买家),可理解为买家端，还是买手端.默认：2买家
     * @return
     */
    Pagination<MyFavoritesResult> mylist(Integer type, Long currUser, Integer current, Integer size, Integer memberType);

    /**
     * 收藏/取消收藏
     * @param params
     * @return
     */
    Object updateFavorites(FavoritesAddParam params);

    /**
     * 批量取消收藏
     * @param params
     * @return
     */
    Object batchCancel(BatchCancelParam params);

    List<DgFavorites> getFavoritesByMemberId(Long memberId, Long entityId, Integer entityTypeId);
    List<DgFavorites> getFavoritesByMemberId(Long memberId, List<Long> entityIds,Integer entityTypeId);
}