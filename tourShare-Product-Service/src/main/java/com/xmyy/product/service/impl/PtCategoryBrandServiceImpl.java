package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.product.mapper.PtCategoryBrandMapper;
import com.xmyy.product.model.PtCategoryBrand;
import com.xmyy.product.service.PtCategoryBrandService;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;

/**
 * 类目品牌关联  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass= PtCategoryBrandService.class)
@CacheConfig(cacheNames = "PtCategoryBrand")
public class PtCategoryBrandServiceImpl extends BaseServiceImpl<PtCategoryBrand, PtCategoryBrandMapper> implements PtCategoryBrandService {

}
