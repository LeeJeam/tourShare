package com.xmyy.pay.service.impl;

import com.xmyy.pay.model.DgCheckAccountDetail;
import com.xmyy.pay.mapper.DgCheckAccountDetailMapper;
import com.xmyy.pay.service.DgCheckAccountDetailService;
import top.ibase4j.core.base.BaseServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * 明细对账表  服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = DgCheckAccountDetailService.class)
@CacheConfig(cacheNames = "DgCheckAccountDetail")
public class DgCheckAccountDetailServiceImpl extends BaseServiceImpl<DgCheckAccountDetail, DgCheckAccountDetailMapper> implements DgCheckAccountDetailService {

}
