package com.xmyy.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.common.EnumConstants;
import com.xmyy.pay.mapper.DgPayLogMapper;
import com.xmyy.pay.model.DgPayLog;
import com.xmyy.pay.service.DgPayLogService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 支付记录 服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = DgPayLogService.class)
@CacheConfig(cacheNames = "DgPayLog")
public class DgPayLogServiceImpl extends BaseServiceImpl<DgPayLog, DgPayLogMapper> implements DgPayLogService {

    @Resource
    private DgPayLogMapper payLogMapper;

    @Override
    @Transactional
    public void closeOverTimePay() {
        EntityWrapper<DgPayLog> ew = new EntityWrapper<>();
        ew.eq("status", EnumConstants.PayStatus.WAITTING_PAY.getValue());
        long msec = (new Date()).getTime() - 24 * 60 * 60 * 1000;
        ew.lt("create_time", new Date(msec));
        List<DgPayLog> payLogList = payLogMapper.selectList(ew);
        for (DgPayLog payLog : payLogList) {
            payLog.setStatus(EnumConstants.PayStatus.PAY_CLOSE.getValue());
            update(payLog);
        }
    }
}
