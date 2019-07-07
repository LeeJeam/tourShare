package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.product.mapper.PtSeriesPriceMapper;
import com.xmyy.product.model.PtSeriesPrice;
import com.xmyy.product.service.PtSeriesPriceService;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;

/**
 * 参考价格  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = PtSeriesPriceService.class)
@CacheConfig(cacheNames = "PtSeriesPrice")
public class PtSeriesPriceServiceImpl extends BaseServiceImpl<PtSeriesPrice, PtSeriesPriceMapper> implements PtSeriesPriceService {

}
