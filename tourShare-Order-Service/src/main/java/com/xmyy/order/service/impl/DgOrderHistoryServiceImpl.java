package com.xmyy.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmyy.order.mapper.DgOrderHistoryMapper;
import com.xmyy.order.model.DgOrderHistory;
import com.xmyy.order.service.DgOrderHistoryService;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单状态历史，后台订单信息，APP订单跟踪
 *
 * @author AnCheng
 */
@Service(interfaceClass = DgOrderHistoryService.class)
@CacheConfig(cacheNames = "DgOrderHistory")
public class DgOrderHistoryServiceImpl extends BaseServiceImpl<DgOrderHistory, DgOrderHistoryMapper> implements DgOrderHistoryService {

    @Resource
    private DgOrderHistoryMapper orderHistoryMapper;

    @Override
    public List<DgOrderHistory> queryByOrderId(Long orderId) {
        EntityWrapper<DgOrderHistory> ew = new EntityWrapper<>();
        ew.eq("order_id_", orderId);
        ew.orderDesc(Lists.newArrayList("update_time"));
        List<DgOrderHistory> orderHistoryList = orderHistoryMapper.selectList(ew);

        return orderHistoryList;
    }
}
