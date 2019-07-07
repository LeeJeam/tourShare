package com.xmyy.pay.service.impl;

import com.xmyy.pay.model.DgCheckAccountTemp;
import com.xmyy.pay.mapper.DgCheckAccountTempMapper;
import com.xmyy.pay.service.DgCheckAccountTempService;
import top.ibase4j.core.base.BaseServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import com.alibaba.dubbo.config.annotation.Service;
/**
 * 明细对账表（暂存） 服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = DgCheckAccountTempService.class)
@CacheConfig(cacheNames = "DgCheckAccountTemp")
public class DgCheckAccountTempServiceImpl extends BaseServiceImpl<DgCheckAccountTemp, DgCheckAccountTempMapper> implements DgCheckAccountTempService {

}
