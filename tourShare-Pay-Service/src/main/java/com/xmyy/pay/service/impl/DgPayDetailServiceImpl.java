package com.xmyy.pay.service.impl;

import com.xmyy.pay.model.DgPayDetail;
import com.xmyy.pay.mapper.DgPayDetailMapper;
import com.xmyy.pay.service.DgPayDetailService;
import top.ibase4j.core.base.BaseServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import com.alibaba.dubbo.config.annotation.Service;
/**
 * 支付明细，记录每笔支付记录的组成  服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = DgPayDetailService.class)
@CacheConfig(cacheNames = "DgPayDetail")
public class DgPayDetailServiceImpl extends BaseServiceImpl<DgPayDetail, DgPayDetailMapper> implements DgPayDetailService {

}
