package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.circle.mapper.WsAdvertPositionMapper;
import com.xmyy.circle.model.WsAdvertPosition;
import com.xmyy.circle.service.WsAdvertPositionService;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;

/**
 * 广告位  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = WsAdvertPositionService.class)
@CacheConfig(cacheNames = "WsAdvertPosition")
public class WsAdvertPositionServiceImpl extends BaseServiceImpl<WsAdvertPosition, WsAdvertPositionMapper> implements WsAdvertPositionService {

}
