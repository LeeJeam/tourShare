package com.xmyy.order.service.impl;

import com.xmyy.order.model.DgOrderReturn;
import com.xmyy.order.mapper.DgOrderReturnMapper;
import com.xmyy.order.service.DgOrderReturnService;
import top.ibase4j.core.base.BaseServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * 订单退货记录 服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = DgOrderReturnService.class)
@CacheConfig(cacheNames = "DgOrderReturn")
public class DgOrderReturnServiceImpl extends BaseServiceImpl<DgOrderReturn, DgOrderReturnMapper> implements DgOrderReturnService {

}
