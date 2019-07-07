package com.xmyy.order.service;

import com.xmyy.order.model.DgOrderHistory;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * 订单状态历史，后台订单信息，APP订单跟踪
 */
public interface DgOrderHistoryService extends BaseService<DgOrderHistory> {

    /**
     * 根据订单ID查询历史操作记录
     * @param orderId 订单ID
     * @return 操作记录
     */
    List<DgOrderHistory> queryByOrderId(Long orderId);
}