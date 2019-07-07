package com.xmyy.product.service;

import com.xmyy.product.model.PtBrand;
import top.ibase4j.core.base.BaseService;

import java.util.Map;

/**
 * 品牌  服务接口
 * @author simon
 */
public interface PtBrandService extends BaseService<PtBrand> {

    /**
     * 查询品牌列表
     * @param categoryId
     * @param categoryId2
     * @param initial
     * @return
     */
    Object getList(Long categoryId, Long categoryId2, String initial);

    /**
     * 品牌ID、名称对应MAP
     * @return
     */
    Map<Long, String> getBrandNameMap();

}