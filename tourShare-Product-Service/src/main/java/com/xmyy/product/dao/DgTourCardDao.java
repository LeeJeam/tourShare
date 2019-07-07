package com.xmyy.product.dao;

import com.xmyy.product.dto.ApiTourCardDto;
import com.xmyy.product.vo.ApiTourCardMatchParam;
import com.xmyy.product.vo.ApiTourCardMatchResult;

import java.util.List;

/**
 * 行程卡片DAO
 *
 * @author wangzejun
 */
public interface DgTourCardDao {

    /**
     * 查询行程卡片Map（cardCode-imgUrl）的List集合，同参数可能会返回多张卡片
     *
     * @param params
     * @return
     */
    List<ApiTourCardMatchResult> getTourCardsMapList(ApiTourCardMatchParam params);

    /**
     * 获取所有行程卡片
     *
     * @return
     */
    List<ApiTourCardDto> getAllList();
}