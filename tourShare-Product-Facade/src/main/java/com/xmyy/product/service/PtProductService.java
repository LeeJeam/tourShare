package com.xmyy.product.service;

import com.xmyy.product.model.PtProduct;
import com.xmyy.product.vo.*;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.exception.BizException;
import top.ibase4j.core.support.Pagination;

import java.util.List;

/**
 * 商品  服务接口
 * @author simon
 */
public interface PtProductService extends BaseService<PtProduct> {

    /**
     * 新增商品/编辑商品保存
     * @param params
     * @param userId
     * @return
     */
    Object save(ApiProductSaveParam params, Long userId);

    /**
     * 新增商品（买手自定义属性值）
     * @param params
     * @param userId
     * @return
     * @throws BizException
     */
    Object saveV2(ApiProductCustomSaveParam params, Long userId) throws BizException;

    /**
     * 商品上下架
     * @param id
     * @param isSale
     * @param userId
     * @return
     */
    Object updateSale(Long id, Integer isSale, Long userId);

    /**
     * 编辑商品（打开历史预售）
     * @param id
     * @param userId
     * @return
     */
    Object getEdit(Long id, Long userId);

    /**
     * 商品详情
     * @param id
     * @param source
     * @param userId
     * @return
     */
    Object getDetail(Long id, Integer source, Long userId);

    /**
     * 商品搜索
     * @param keyWord
     * @return
     */
    Object search(String keyWord);

    /**
     * 获取商品所有的SKU
     * @param id
     * @return
     */
    Object getSkus(Long id);

    /**
     * 获取商品拥有的全部属性
     * @param id
     * @return
     */
    Object getAllAttrs(Long id);

    /**
     * 商品列表
     * @param params
     * @return
     */
    Object getList(ApiProductListParam params);

    /**
     * 根据买手ID获取最新的预售，最多返回3个
     * @param sellerId
     * @return
     */
    List<PtProduct> getProductBySellerId(Long sellerId);

    /**
     * 根据行程ID，获取商品列表
     * @param params
     * @return
     */
    Pagination<ApiProductListByTourIdResult> getListByTourId(ApiProductListByTourIdParam params);

    /**
     * 行程下架，关联预售商品下架
     * @param tourId
     * @return
     */
    boolean stopProductsByTourId(Long tourId);

    /**
     * APP首页最新预售
     * @param params
     * @return
     */
    Pagination<PresellProductResult> getPresellList(PresellProductParam params);

    /**
     * 更新商品的好评度
     * @param id
     * @param favorable
     * @return
     */
    Object updateFavorable(Long id, Float favorable);

    /**
     * 取消“过期”的商品置顶
     */
    void cancelTopOverTime();

}