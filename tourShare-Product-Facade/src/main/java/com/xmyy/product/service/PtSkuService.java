package com.xmyy.product.service;

import com.xmyy.product.model.PtSku;
import top.ibase4j.core.base.BaseService;

/**
 * 商品SKU  服务接口
 *
 * @author simon
 */
public interface PtSkuService extends BaseService<PtSku> {

    PtSku selectById(Long id);
}