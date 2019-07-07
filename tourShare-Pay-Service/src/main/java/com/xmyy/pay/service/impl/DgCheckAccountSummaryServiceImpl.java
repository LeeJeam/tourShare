package com.xmyy.pay.service.impl;

import com.xmyy.pay.model.DgCheckAccountSummary;
import com.xmyy.pay.mapper.DgCheckAccountSummaryMapper;
import com.xmyy.pay.service.DgCheckAccountSummaryService;
import top.ibase4j.core.base.BaseServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import com.alibaba.dubbo.config.annotation.Service;
/**
 * 平台每日汇总对账表  服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = DgCheckAccountSummaryService.class)
@CacheConfig(cacheNames = "DgCheckAccountSummary")
public class DgCheckAccountSummaryServiceImpl extends BaseServiceImpl<DgCheckAccountSummary, DgCheckAccountSummaryMapper> implements DgCheckAccountSummaryService {

}
