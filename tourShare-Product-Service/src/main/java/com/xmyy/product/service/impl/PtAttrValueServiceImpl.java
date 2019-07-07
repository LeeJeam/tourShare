package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.product.mapper.PtAttrValueMapper;
import com.xmyy.product.model.PtAttrValue;
import com.xmyy.product.service.PtAttrValueService;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;

/**
 * 属性值  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = PtAttrValueService.class)
@CacheConfig(cacheNames = "PtSeriesAttrValue")
public class PtAttrValueServiceImpl extends BaseServiceImpl<PtAttrValue, PtAttrValueMapper> implements PtAttrValueService {

}
