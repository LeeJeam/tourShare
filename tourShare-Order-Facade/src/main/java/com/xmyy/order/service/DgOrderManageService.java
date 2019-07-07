package com.xmyy.order.service;

import com.xmyy.order.model.DgOrder;
import com.xmyy.order.vo.QueryOrderManageParam;
import com.xmyy.order.vo.OrderInTourManageResult;
import top.ibase4j.core.base.BaseService;

import java.util.ArrayList;

/**
 * <p>
 * 订单后台服务
 * </p>
 */
public interface DgOrderManageService extends BaseService<DgOrder> {

    /**
     * 查询订单列表
     * @param params 订单列表查询条件
     * @return 订单列表
     */
    Object queryOrderList(QueryOrderManageParam params);

    /**
     * 订单详情
     * @param id 订单id
     * @return 订单详情
     */
    Object queryOrderDetail(Long id);

    /**
     * 根据行程ID查询关联订单信息
     * @param id 行程ID
     * @return 订单信息
     */
    ArrayList<OrderInTourManageResult> queryOrderInTour(Long id);
}