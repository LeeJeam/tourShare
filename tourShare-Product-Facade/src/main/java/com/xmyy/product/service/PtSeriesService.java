package com.xmyy.product.service;

import com.xmyy.product.model.PtSeries;
import com.xmyy.product.vo.ManageSeriesParam;
import com.xmyy.product.vo.SeriesAddParam;
import top.ibase4j.core.base.BaseService;

/**
 * 商品模版  服务接口
 *
 * @author simon
 */
public interface PtSeriesService extends BaseService<PtSeries> {

    /**
     * APP查询商品系列
     * @param categoryId
     * @param categoryId2
     * @param brandId
     * @return
     */

    Object getList(Long categoryId,Long categoryId2,Long brandId);

    /**
     * 新增商品模板
     * @param params
     * @return
     */
    Object add(SeriesAddParam params);

    /**
     * 查询商品系列列表
     * @param params
     * @return
     */
    Object queryManagerPtSeriesList(ManageSeriesParam params);

    /**
     * 商品系列统计
     * @return
     */
    Object statistic();
	
}