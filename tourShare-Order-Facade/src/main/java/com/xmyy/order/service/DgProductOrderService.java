package com.xmyy.order.service;

import com.xmyy.order.model.DgProductOrder;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * <p>
 * 预售商品订单表
 * </p>
 */
public interface DgProductOrderService extends BaseService<DgProductOrder> {

    List<DgProductOrder> queryByOrderId(Long orderId);

    /**
     * 根据行程id查询订单商品快照
     * @param id 行程id
     * @return 订单商品快照列表
     */
    List<DgProductOrder> queryByTourId(Long id);
}