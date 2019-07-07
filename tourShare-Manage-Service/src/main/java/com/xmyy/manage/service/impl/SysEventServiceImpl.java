package com.xmyy.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.manage.mapper.SysEventMapper;
import com.xmyy.manage.service.SysEventService;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.model.SysEvent;

@CacheConfig(cacheNames = "sysEvent")
@Service(interfaceClass = SysEventService.class)
public class SysEventServiceImpl extends BaseServiceImpl<SysEvent, SysEventMapper> implements SysEventService {

}
