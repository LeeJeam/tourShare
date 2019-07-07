package com.xmyy.product.service;

import com.xmyy.product.model.DgTourCard;
import com.xmyy.product.vo.ApiTourCardMatchParam;
import com.xmyy.product.vo.ApiTourCardAddParam;
import top.ibase4j.core.base.BaseService;

import java.util.Map;

/**
 * 行程卡片  服务接口
 *
 * @author wangzejun
 */
public interface TourCardService extends BaseService<DgTourCard> {

    /**
     * 添加行程卡片，暂时未用到
     *
     * @param params
     * @param createId
     * @return
     */
    Object add(ApiTourCardAddParam params, Long createId);

    /**
     * 查询行程卡片Map（cardCode-imgUrl）集合，对应多张卡片的，默认取第一张
     *
     * @param params
     * @return
     */
    Map<String, String> getTourCardsMapList(ApiTourCardMatchParam params);

    /**
     * 加载行程卡片的数据到redis中
     *
     * @return
     */
    void loadRedisForTourCard();
}