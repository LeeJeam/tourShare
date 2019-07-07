package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.circle.model.DgSysMessageRead;
import com.xmyy.circle.mapper.DgSysMessageReadMapper;
import com.xmyy.circle.service.DgSysMessageReadService;
import top.ibase4j.core.base.BaseServiceImpl;
import org.springframework.cache.annotation.CacheConfig;

/**
 * 系统消息阅读记录  服务实现类
 *
 * @author yeyu
 */
@Service(interfaceClass = DgSysMessageReadService.class)
@CacheConfig(cacheNames = "DgSysMessageRead")
public class DgSysMessageReadServiceImpl extends BaseServiceImpl<DgSysMessageRead, DgSysMessageReadMapper> implements DgSysMessageReadService {

}
