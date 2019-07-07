package com.xmyy.order.service;

import com.xmyy.common.vo.BuyerOrderInfo;
import com.xmyy.demand.model.DgDemandOrder;
import com.xmyy.order.model.DgOrder;
import com.xmyy.order.vo.*;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.exception.BizException;

import java.util.List;

/**
 * <p>
 * 订单表
 * </p>
 */
public interface DgOrderService extends BaseService<DgOrder> {

    /**
     * 查询订单列表
     * @param params 订单列表查询条件
     * @param id 用户id
     * @return 订单列表
     */
    Object queryOrderList(QueryOrderParam params, Long id);

    /**
     * 订单详情
     * @param id 订单id
     * @param role 用户角色
     * @return 订单详情
     */
    Object queryOrderDetail(Long id, Integer role);

    /**
     * 订单结算
     * @param params 结算订单参数
     * @param buyerId 买家ID
     * @return 返回结果
     */
    Object createOrders(CreateOrderListParam params, Long buyerId) throws BizException;

    /**
     * 查询物流
     * @param id 订单ID
     * @return 物流信息
     */
    Object queryExpress(Long id);

    /**
     * 取消订单
     * @param params 取消订单参数
     * @return 返回结果
     */
    Object cancelOrder(CancelOrderParam params);

    /**
     * 删除订单
     * @param params 删除订单参数
     * @return 返回结果
     */
    Object deleteOrder(DeleteOrderParam params);

    /**
     * 买手发货
     * @param params 发货参数
     * @return 返回结果
     */
    Object deliverGoods(DeliverGoodsParam params);

    /**
     * 买手确认退款
     * @param params 确认退款参数
     * @return 返回结果
     */
    Object agreeRefund(AgreeRefundParam params);

    /**
     * 申请售后
     * @param params 申请售后参数
     * @return 返回结果
     */
    Object afterSale(AfterSaleParam params);

    /**
     * 查询退款售后列表
     * @param id 用户id
     * @param params 查询参数
     * @return 售后列表
     */
    Object queryAfterSaleList(Long id, QueryOrderParam params);

    /**
     * 查询售后详情
     * @param id 售后单id
     * @return 售后详情
     */
    Object queryAfterSaleDetail(Long id);

    /**
     * 根据物流单号查询快递公司
     * @param wayBillNo 物流单号
     * @return 快递公司
     */
    Object queryExpressCompany(String wayBillNo);

    /**
     * 获取物流公司列表
     * @return 物流公司列表
     */
    Object queryCompanyList();

    /**
     * 提醒买手发货
     * @param id 订单id
     * @return 返回结果
     */
    Object remind(Long id);

    /**
     * 买家确认收货
     * @param params 确认收货入参
     * @return 返回结果
     */
    Object confirmReceive(ConfirmReceiveParam params);

    /**
     * 关闭超时的“待支付”订单
     */
    void closeOverTimeOrder();

    /**
     * 行程下架关联的订单操作
     * @param id 行程ID
     * @return 返回结果
     */
    boolean cancelTourRelateOrder(Long id);

    /**
     * 判断行程是否可以下架
     * @param id 行程ID
     * @return true可以下架，false不能下架
     */
    boolean isTourCanBeCanceled(Long id);

    /**
     * 买家详情页，订单统计数
     * @param id 买家ID
     * @return
     */
    BuyerOrderInfo queryBuyerOrderInfo(Long id);

    /**
     * 根据订单ID列表查询订单列表
     * @param ids
     * @return
     */
    List<DgOrder> listByOrderIds(List<Long> ids);

    /**
     * 支付接通后暂停使用 TODO
     * @param ids
     * @return
     */
    Object getPayStatus(String ids);

    /**
     * 支付接通后暂停使用 TODO
     * @param params
     * @return
     */
    Object toPay(ToPayParam params);

    /**
     * 支付‘待支付’订单，获取支付所需数据
     * @param id
     * @return
     */
    Object getPayParams(Long id);

    /**
     * 订单支付成功回调
     * @param applyPaylogId
     */
    void paySuccess(Long applyPaylogId);

    /**
     * 确认收货，托管代付成功回调
     * @param payPaylogId
     */
    void confirmReceiveSuccess(Long payPaylogId);

    /**
     * 确认退款成功回调
     * @param refundPaylogId
     */
    void agreeRefundSuccess(Long refundPaylogId);

}