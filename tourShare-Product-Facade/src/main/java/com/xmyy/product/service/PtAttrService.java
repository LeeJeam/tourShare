package com.xmyy.product.service;

import com.xmyy.product.model.PtAttr;
import com.xmyy.product.vo.ApiAttrSimpleListParam;
import com.xmyy.product.vo.AttrItemAddParam;
import com.xmyy.product.vo.AttrItemListParam;
import com.xmyy.product.vo.AttrModifyParam;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.exception.BizException;

import java.util.List;
import java.util.Map;

/**
 * 模版属性  服务接口
 * @author simon
 */
public interface PtAttrService extends BaseService<PtAttr> {

    /**
     * APP-获取系列拥有的全部属性
     * @param seriesId
     * @return
     */
    Object getSeriesAttr(Long seriesId);

    /**
     * APP属性列表
     * @param params
     * @return
     */
    Object getAttrList(ApiAttrSimpleListParam params);

    /**
     * 根据商品ID，查询商品所有的属性名称
     * @param id
     * @return
     */
    List<String> getAttrValueNameByProductId(Long id);

    /**
     * 根据商品ID，查询商品所有的属性名称Map集合
     * @param ids
     * @return
     */
    Map getAttrValueNameByProductIds(List<Long> ids);

}