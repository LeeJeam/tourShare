package com.xmyy.order.dao;

import com.xmyy.order.model.DgOrder;

import java.util.List;

/**
 * 订单DAO
 *
 * @author AnCheng
 */
public interface DgOrderDao {

    /**
     * 查询买家的待处理订单统计
     * @param id 买家ID
     * @return 返回数组为各待处理订单数，0-4依次为（待支付、待发货、待收货、售后、待评价）
     */
    Integer[] selectBuyerOrderInfo(Long id);

}