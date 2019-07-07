package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.product.mapper.PtSeriesColorMapper;
import com.xmyy.product.model.PtSeriesColor;
import com.xmyy.product.service.PtSeriesColorService;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;

/**
 * 颜色属性  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = PtSeriesColorService.class)
@CacheConfig(cacheNames = "PtSeriesColor")
public class PtSeriesColorServiceImpl extends BaseServiceImpl<PtSeriesColor, PtSeriesColorMapper> implements PtSeriesColorService {

}
