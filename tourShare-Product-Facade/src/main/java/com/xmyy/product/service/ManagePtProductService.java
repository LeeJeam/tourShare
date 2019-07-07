package com.xmyy.product.service;

import com.xmyy.product.model.PtProduct;
import com.xmyy.product.vo.ManagePresellProductParam;
import com.xmyy.product.vo.ManageProductListParam;
import com.xmyy.product.vo.ManageProductParams;
import com.xmyy.product.vo.ManageProductTopParam;
import top.ibase4j.core.base.BaseService;

/**
 * 商品后台管理  服务接口
 * @author simon
 */
public interface ManagePtProductService extends BaseService<PtProduct> {

    /**
     * 查询商品、一级类目统计
     * @return
     */
    Object statistic();

    /**
     * 查询商品置顶列表
     * @param params
     * @return
     */
    Object queryManageProductList(ManageProductListParam params);

    /**
     * 商品置顶
     * @param params
     * @return
     */
    Object setTop(ManageProductTopParam params);

    /**
     * 商品置顶
     * @param id
     * @param currUser
     * @return
     */
    Object cancelTop(Long id, Long currUser);

    /**
     * 查询当前已置顶的商品列表
     * @return
     */
    Object getCurrTopList();

    /**
     * 获取商品信息集合
     * @param params
     * @return
     */
    Object queryProductManageList(ManageProductParams params);

    /**
     * 预售商品管理列表
     * @param params
     * @return
     */
    Object queryManagePresellProductList(ManagePresellProductParam params);

}