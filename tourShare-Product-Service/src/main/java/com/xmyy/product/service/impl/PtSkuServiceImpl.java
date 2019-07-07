package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.product.mapper.PtSkuMapper;
import com.xmyy.product.model.PtSku;
import com.xmyy.product.service.PtSkuService;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;

import javax.annotation.Resource;

/**
 * 商品SKU  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = PtSkuService.class)
//@CacheConfig(cacheNames = "PtSku")
public class PtSkuServiceImpl extends BaseServiceImpl<PtSku, PtSkuMapper> implements PtSkuService {

    @Resource
    private PtSkuMapper skuMapper;

    @Override
    @Transactional
    public PtSku selectById(Long id) {
        return skuMapper.selectById(id);
    }
}
