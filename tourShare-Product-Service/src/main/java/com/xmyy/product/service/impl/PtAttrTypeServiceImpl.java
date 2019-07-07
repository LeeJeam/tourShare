package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.product.mapper.PtAttrTypeMapper;
import com.xmyy.product.model.PtAttrType;
import com.xmyy.product.service.PtAttrTypeService;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;

/**
 * 属性类型  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = PtAttrTypeService.class)
@CacheConfig(cacheNames = "PtSeriesAttrType")
public class PtAttrTypeServiceImpl extends BaseServiceImpl<PtAttrType, PtAttrTypeMapper> implements PtAttrTypeService {

}
