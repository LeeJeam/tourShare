package com.xmyy.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.codingapi.tx.annotation.TxTransaction;
import com.xmyy.circle.vo.EveryDayRadioParam;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.*;
import com.xmyy.common.vo.*;
import com.xmyy.demand.model.DgDemandOrder;
import com.xmyy.demand.service.DgDemandAcceptService;
import com.xmyy.demand.service.DgDemandOrderService;
import com.xmyy.member.model.UcPayPassword;
import com.xmyy.member.service.UcPayPasswordService;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.order.dao.DgOrderDao;
import com.xmyy.order.mapper.DgOrderMapper;
import com.xmyy.order.mapper.DgOrderReturnMapper;
import com.xmyy.order.mapper.DgProductOrderMapper;
import com.xmyy.order.model.DgOrder;
import com.xmyy.order.model.DgOrderReturn;
import com.xmyy.order.model.DgProductOrder;
import com.xmyy.order.service.DgOrderReturnService;
import com.xmyy.order.service.DgOrderService;
import com.xmyy.order.service.DgProductOrderService;
import com.xmyy.order.vo.*;
import com.xmyy.pay.model.DgPayLog;
import com.xmyy.pay.service.AllinPayOrderService;
import com.xmyy.pay.service.DgPayLogService;
import com.xmyy.pay.vo.RefundParam;
import com.xmyy.pay.vo.SignalAgentPayParam;
import com.xmyy.product.model.PtProduct;
import com.xmyy.product.model.PtSku;
import com.xmyy.product.service.PtProductService;
import com.xmyy.product.service.PtSkuService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.exception.BizException;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.support.mq.QueueSender;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 订单 服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = DgOrderService.class)
//@CacheConfig(cacheNames = "DgOrder")
public class DgOrderServiceImpl extends BaseServiceImpl<DgOrder, DgOrderMapper> implements DgOrderService {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private DgOrderMapper orderMapper;
    @Resource
    private DgOrderDao orderDao;
    @Resource
    private DgProductOrderMapper productOrderMapper;
    @Resource
    private DgProductOrderService productOrderService;
    @Resource
    private DgDemandOrderService demandOrderService;
    @Resource
    private DgDemandAcceptService demandAcceptService;
    @Resource
    private UcSellerService sellerService;
    @Resource
    private DgOrderReturnMapper orderReturnMapper;
    @Resource
    private DgOrderReturnService orderReturnService;
    @Resource
    private PtProductService productService;
    @Resource
    private PtSkuService skuService;
    @Resource
    private QueueSender queueSender;
    @Resource
    private AllinPayOrderService payOrderService;
    @Resource
    private DgPayLogService payLogService;
    @Resource
    private UcPayPasswordService payPasswordService;


    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public Object afterSale(AfterSaleParam params) {
        DgOrder order = queryById(params.getOrderId());
        if (order == null) {
            return "订单不存在";
        }

        if (order.getOrderStatus().intValue() == EnumConstants.OrderStatus.WATI_TO_SEND.getValue() ||
                order.getOrderStatus().intValue() == EnumConstants.OrderStatus.WATI_TO_RECIEVE.getValue()) {
            //生成售后表记录
            DgOrderReturn orderReturn = new DgOrderReturn();
            orderReturn.setOrderId(params.getOrderId());
            orderReturn.setReturnNo(BizSequenceUtils.generateBizNo(EnumConstants.BizCode.RefundNo));
            orderReturn.setOrderType(order.getOrderType());
            if (order.getOrderType().intValue() == EnumConstants.OrderType.PRE_SALE.getValue()) { //预售售后
                DgProductOrder productOrder = productOrderService.queryById(params.getProductOrderId());
                if (productOrder == null) {
                    return "商品不存在";
                }
                productOrder.setIsRefund(EnumConstants.YesOrNo.YES.getValue());
                productOrderService.update(productOrder);
                orderReturn.setProductOrderId(params.getProductOrderId());
                orderReturn.setProductId(productOrder.getProductId());
                orderReturn.setProductTitle(productOrder.getProductName());
                orderReturn.setProductCover(productOrder.getProductImages());
                orderReturn.setSpecParams(productOrder.getSpecParams());
                orderReturn.setProductNum(productOrder.getProductNum());
                orderReturn.setProductPrice(productOrder.getProductPrice());
                orderReturn.setReturnMoney(productOrder.getSubtotal());
            } else if (order.getOrderType().intValue() == EnumConstants.OrderType.DEMAND_SALE.getValue()) { //需求售后
                //TODO 验证
//                QueryDemandByOrderIdCommand queryDemandByOrderIdCommand = new QueryDemandByOrderIdCommand(order.getId());
//                DgDemandOrder demandOrder = (DgDemandOrder) queryDemandByOrderIdCommand.execute();
                DgDemandOrder demandOrder = (DgDemandOrder) demandOrderService.queryByOrderId(order.getId());
                if (demandOrder == null) {
                    return "商品不存在";
                }
                orderReturn.setProductId(demandOrder.getId());
                orderReturn.setProductTitle(demandOrder.getDemandDesc());
                orderReturn.setProductCover(demandOrder.getDemandImages());
                orderReturn.setDeliveryTime(demandOrder.getDeliveryTime());
                orderReturn.setReturnMoney(order.getPayMoney());
                orderReturn.setProductPrice(order.getPayMoney());
            }
            orderReturn.setReturnReason(params.getReturnReason());
            orderReturn.setReasonText(EnumConstants.AfterSaleReason.getName(params.getReturnReason())); //售后原因文字
            orderReturn.setReturnSubmitTime(new Date());
            orderReturn.setBuyerId(order.getBuyerId());
            orderReturn.setSellerId(order.getSellerId());
            orderReturn.setIsPacker(order.getIsPacker());
            orderReturn.setReturnStatus(EnumConstants.AfterSaleStatus.ON_DEAL.getStatus());
            orderReturn.setCreateBy(order.getBuyerId());
            orderReturn.setUpdateBy(order.getBuyerId());
            orderReturnService.update(orderReturn);

            //判断订单是否全部为售后，如果是则改变订单状态为“售后处理中”
            if (order.getOrderType().intValue() == EnumConstants.OrderType.DEMAND_SALE.getValue()) {
                //需求订单，商品只有一件，直接变为“售后处理中”
                order.setOrderStatus(EnumConstants.OrderStatus.AFTER_SALE.getValue());
            } else if (order.getOrderType().intValue() == EnumConstants.OrderType.PRE_SALE.getValue()) {
                //预售订单，订单下所有商品都申请售后才变为“售后处理中”
                EntityWrapper<DgProductOrder> ew = new EntityWrapper<>();
                ew.eq("order_id_", order.getId());
                List<DgProductOrder> productOrderList = productOrderMapper.selectList(ew);
                boolean allRefund = true; //是否需要转换订单状态
                for (DgProductOrder productOrder : productOrderList) {
                    if (productOrder.getIsRefund() == EnumConstants.YesOrNo.NO.getValue().intValue()) {
                        allRefund = false;
                        break;
                    }
                }
                if (allRefund) {
                    order.setOrderStatus(EnumConstants.OrderStatus.AFTER_SALE.getValue());
                }
            }
            update(order);
        } else {
            return "订单状态错误";
        }

        //清空redis中买家订单统计数据
        CacheUtil.getLockManager().hdel("S:iBase4J:buyer:orderInfo_key:", order.getBuyerId());

        //发送提醒消息，买家申请售后
        setEveryDayRadio(order, EnumConstants.EveryDayRadioType.BUYER_APPLY_REFUND);

        return null;
    }


    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public Object agreeRefund(AgreeRefundParam params) {
        DgOrderReturn orderReturn = orderReturnService.queryById(params.getOrderReturnId());
        if (orderReturn == null) {
            return "售后单不存在";
        }

        DgOrder order = queryById(orderReturn.getOrderId());
        if (order == null) {
            return "订单不存在";
        }
        if (order.getOrderStatus().intValue() != EnumConstants.OrderStatus.WATI_TO_SEND.getValue() &&
                order.getOrderStatus().intValue() != EnumConstants.OrderStatus.WATI_TO_RECIEVE.getValue()) {
            return "订单状态错误";
        }

        MemberInfo memberInfo;
        if (order.getIsPacker() == EnumConstants.YesOrNo.YES.getValue().intValue()) {
            memberInfo = sellerService.getMemberInfo(order.getSellerId(), EnumConstants.MemberType.packer.getValue());
        } else {
            memberInfo = sellerService.getMemberInfo(order.getSellerId(), EnumConstants.MemberType.seller.getValue());
        }

        //验证买手/背包客的支付密码
        UcPayPassword payPassword = payPasswordService.queryById(memberInfo.getPayPasswordId());
        if (payPassword == null || payPassword.getPassword() == null) {
            return "请先设置支付密码";
        }
        if (!PasswordUtil.verifyPassword(params.getPayPassword(), payPassword.getPassword())) {
            return "支付密码错误";
        }

        //通联申请退款
        RefundParam refundParam = new RefundParam();
        refundParam.setOrderId(order.getId());
        refundParam.setBizUserId(sellerService.getMemberInfo(order.getBuyerId(), EnumConstants.MemberType.buyer.getValue()).getUuid());
        refundParam.setAmount(BigDecimalUtil.changeY2F(order.getPayMoney()));
        refundParam.setFee(0L);
        Object result = payOrderService.refund(refundParam);
        if (result instanceof String) {
            return result.toString();
        }

        return result;
    }


    @Override
    @Transactional
    public Object cancelOrder(CancelOrderParam params) {
        DgOrder order = queryById(params.getOrderId());
        if (order == null) {
            return "订单不存在";
        }

        if (order.getOrderStatus().intValue() == EnumConstants.OrderStatus.WATI_TO_PAY.getValue() &&
                order.getOrderType().intValue() == EnumConstants.OrderType.PRE_SALE.getValue()) {
            //只有“待支付”的预售现货可以取消订单，待发货之后都只能申请售后，需求订单不可取消
            order.setOrderStatus(EnumConstants.OrderStatus.SALE_CLOSE.getValue());
            order.setCancelReason(params.getCancelReason());
            order.setCancelText(EnumConstants.CancelOrderReason.getName(params.getCancelReason()));
            update(order);

            returnStock(order.getId()); //退库存
        } else {
            return "订单状态错误";
        }

        //清空redis中买家订单统计数据
        CacheUtil.getLockManager().hdel("S:iBase4J:buyer:orderInfo_key:", order.getBuyerId());

        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public boolean isTourCanBeCanceled(Long id) {
        //行程关联的订单，如有已发货、售后处理中的订单，不能下架
        EntityWrapper<DgOrder> ew = new EntityWrapper<>();
        ew.eq("tour_id", id);
        List<DgOrder> orderList = orderMapper.selectList(ew);
        for (DgOrder order : orderList) {
            if (order.getOrderStatus() == EnumConstants.OrderStatus.WATI_TO_RECIEVE.getValue().intValue() ||
                    order.getOrderStatus() == EnumConstants.OrderStatus.AFTER_SALE.getValue().intValue()) {
                return false;
            }
        }
        return true;
    }


    @Override
    @TxTransaction
    @Transactional
    public boolean cancelTourRelateOrder(Long id) {
        try {
            EntityWrapper<DgOrder> ew = new EntityWrapper<>();
            ew.eq("tour_id", id);
            List<DgOrder> orderList = orderMapper.selectList(ew);
            for (DgOrder order : orderList) {
                if (order.getOrderType().equals(EnumConstants.OrderType.PRE_SALE.getValue())) { //预售
                    //未付款的订单，直接取消即可，待发货的订单，需要做退款操作
                    if (order.getOrderStatus() == EnumConstants.OrderStatus.WATI_TO_PAY.getValue().intValue()) {
                        cancelOrder(new CancelOrderParam(order.getId(), EnumConstants.CancelOrderReason.TOUR_CLOSE.getValue()));
                    } else if (order.getOrderStatus().equals(EnumConstants.OrderStatus.WATI_TO_SEND.getValue())) {
                        //通联申请退款
                        RefundParam refundParam = new RefundParam();
                        refundParam.setOrderId(order.getId());
                        refundParam.setBizUserId(sellerService.getMemberInfo(order.getBuyerId(), EnumConstants.MemberType.buyer.getValue()).getUuid());
                        refundParam.setAmount(BigDecimalUtil.changeY2F(order.getPayMoney()));
                        refundParam.setFee(0L);
                        Object result = payOrderService.refund(refundParam);
                        if (result instanceof String) {
                            return false;
                        }

                        //更新订单状态
                        order.setOrderStatus(EnumConstants.OrderStatus.SALE_CLOSE.getValue());
                        order.setCancelReason(EnumConstants.CancelOrderReason.TOUR_CLOSE.getValue());
                        order.setCancelText(EnumConstants.CancelOrderReason.TOUR_CLOSE.getName());
                        update(order);

                        //退库存
                        returnStock(order.getId());
                    } else {
                        return false; //发货后的订单，目前不允许取消操作
                    }

                } else if (order.getOrderType() == EnumConstants.OrderType.DEMAND_SALE.getValue().intValue()) { //需求
                    if (order.getOrderStatus().equals(EnumConstants.OrderStatus.WATI_TO_SEND.getValue())) {
                        //更新订单状态
                        order.setOrderStatus(EnumConstants.OrderStatus.SALE_CLOSE.getValue());
                        order.setCancelReason(EnumConstants.CancelOrderReason.TOUR_CLOSE.getValue());
                        order.setCancelText(EnumConstants.CancelOrderReason.TOUR_CLOSE.getName());
                        update(order);
                    } else {
                        return false; //需求订单没有未付款的状态，待发货的可以取消，其他状态都不可以
                    }
                }

                //清空redis中买家订单统计数据
                CacheUtil.getLockManager().hdel("S:iBase4J:buyer:orderInfo_key:", order.getBuyerId());
            }
            return true;
        } catch (Exception e) {
            logger.error("下架订单失败，错误原因：{}", e.getMessage());
            return false;
        }
    }


    @Override
    @Transactional
    public void closeOverTimeOrder() {
        EntityWrapper<DgOrder> ew = new EntityWrapper<>();
        ew.eq("order_status", EnumConstants.OrderStatus.WATI_TO_PAY.getValue());
        long msec = (new Date()).getTime() - 24 * 60 * 60 * 1000;
        ew.lt("order_time", new Date(msec));
        List<DgOrder> orderList = orderMapper.selectList(ew);
        for (DgOrder order : orderList) {
            order.setOrderStatus(EnumConstants.OrderStatus.SALE_CLOSE.getValue());
            update(order);

            if (order.getOrderType() == EnumConstants.OrderType.PRE_SALE.getValue().intValue()) {
                //退库存
                returnStock(order.getId());
                //清空redis中买家订单统计数据
                CacheUtil.getLockManager().hdel("S:iBase4J:buyer:orderInfo_key:", order.getBuyerId());
            }
        }
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public Object confirmReceive(ConfirmReceiveParam params) {
        DgOrder order = queryById(params.getOrderId());
        if (order == null) {
            return "订单不存在";
        }
        if (order.getOrderStatus().intValue() != EnumConstants.OrderStatus.WATI_TO_RECIEVE.getValue()) {
            return "订单状态错误";
        }

        //验证买家的支付密码
        MemberInfo memberInfo = sellerService.getMemberInfo(order.getBuyerId(), EnumConstants.MemberType.buyer.getValue());
        UcPayPassword payPassword = payPasswordService.queryById(memberInfo.getPayPasswordId());
        if (payPassword == null || payPassword.getPassword() == null) {
            return "请先设置支付密码";
        }
        if (!PasswordUtil.verifyPassword(params.getPayPassword(), payPassword.getPassword())) {
            return "支付密码错误";
        }

        //调用托管代付
        SignalAgentPayParam payParam = new SignalAgentPayParam();
        if (order.getIsPacker() == EnumConstants.YesOrNo.NO.getValue().intValue()) {
            memberInfo = sellerService.getMemberInfo(order.getSellerId(), EnumConstants.MemberType.seller.getValue());
            payParam.setBizUserId(memberInfo.getUuid());
        } else {
            memberInfo = sellerService.getMemberInfo(order.getSellerId(), EnumConstants.MemberType.packer.getValue());
        }
        payParam.setBizUserId(memberInfo.getUuid());
        payParam.setOrderId(order.getId());
        payParam.setAmount(BigDecimalUtil.changeY2F(order.getPayMoney()));
        payParam.setFee(0L);
        Object payResult = payOrderService.signalAgentPay(payParam);
        if (payResult instanceof String) {
            return payResult.toString();
        }

        return payResult;
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public Object createOrders(CreateOrderListParam params, Long buyerId) {
        ToPayOrderResult result = new ToPayOrderResult();
        JSONArray recieverList = new JSONArray(); //收款人列表
        JSONObject recieverMap = new JSONObject();
        ArrayList<Long> orderIdList = InstanceUtil.newArrayList(); //生成的订单ID列表，用作返回

        //创建订单时，按照买手进行分单
        List<CreateOrderParam> orderList = params.getOrderList();
        BigDecimal tmpTotalMoney = new BigDecimal("0"); //计算该次支付总支付金额
        for (CreateOrderParam orderParams : orderList) { //每个orderParams就是一张订单的入参
            //创建订单
            DgOrder order = update(new DgOrder()); //保存空订单，用于生成订单id
            order.setOrderNo(BizSequenceUtils.generateBizNo(EnumConstants.BizCode.OrderNo));
            BigDecimal tmpPayMoney = new BigDecimal("0");       //订单实付金额
            order.setProductNameStr("");                        //商品名称字符串（逗号分割，用于后台管理系统查询）

            //遍历订单下的商品，校验商品是否可用、库存是否足够
            for (ProductParam productParam : orderParams.getProductList()) {
                //TODO 验证
//                QueryProductByIdCommand queryProductByIdCommand = new QueryProductByIdCommand(productService, productParam.getProductId());
//                PtProduct product = (PtProduct) queryProductByIdCommand.execute();
                PtProduct product = productService.queryById(productParam.getProductId());
                if (product == null) {
                    throw new BizException("该商品不存在");
                } else if (product.getEnable().intValue() == EnumConstants.YesOrNo.NO.getValue()) {
                    throw new BizException("该商品已下架");
                }

                //加库存锁（分布式锁）
                RedisLock redisLock = new RedisLock(redisTemplate);
                try {
                    if (redisLock.lock("ORDER:LOCK_GOODS" + order.getId())) { // 获取分布式锁
                        PtSku sku = skuService.selectById(productParam.getSkuId());
                        if (sku == null || sku.getPtProductId().longValue() != product.getId().longValue()) {
                            throw new BizException("该商品不存在");
                        } else if (sku.getEnable().intValue() == EnumConstants.YesOrNo.NO.getValue()) {
                            throw new BizException("该商品已下架");
                        } else if (sku.getStock() < productParam.getProductNum()) {
                            throw new BizException("库存不足");
                        }
                        sku.setStock(sku.getStock() - productParam.getProductNum());
                        skuService.update(sku);
                        redisLock.unlock("ORDER:LOCK_GOODS" + order.getId()); //释放锁

                        BigDecimal tmpSubtotal = null;
                        if (sku.getPrice() != null && productParam.getProductNum() != null) {
                            tmpSubtotal = sku.getPrice().multiply(new BigDecimal(productParam.getProductNum()));
                            tmpPayMoney = tmpPayMoney.add(tmpSubtotal);
                        }

                        //创建订单快照表记录
                        DgProductOrder productOrder = new DgProductOrder();
                        productOrder.setOrderId(order.getId());
                        productOrder.setProductId(sku.getPtProductId());
                        productOrder.setSkuId(sku.getId());
                        productOrder.setTourId(product.getDgTourId());
                        productOrder.setProductType(EnumConstants.OrderType.PRE_SALE.getValue());
                        productOrder.setProductName(product.getTitle());
                        productOrder.setProductImages(product.getImages());
                        productOrder.setProductCover(product.getCover());
                        productOrder.setProductPrice(sku.getPrice());
                        productOrder.setSpecParams(productParam.getSpecParams());
                        productOrder.setProductNum(productParam.getProductNum());
                        productOrder.setSubtotal(tmpSubtotal);
                        productOrder.setIsRefund(EnumConstants.YesOrNo.NO.getValue());
                        productOrder.setCreateBy(buyerId);
                        productOrderService.update(productOrder);

                        order.setTourId(product.getDgTourId());
                        order.setProductNameStr("".equals(order.getProductNameStr()) ? productOrder.getProductName() : order.getProductNameStr() + "," + productOrder.getProductName());

                        product.setOrderCount(product.getOrderCount() + 1);
                        productService.update(product);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error("【APP订单模块|订单结算】商品库存锁错误，错误信息：" + e.getMessage());
                }
            }

            //校验前端传入的订单付款是否正确
            if (tmpPayMoney.compareTo(orderParams.getPayMoney()) != 0) {
                throw new BizException("订单金额错误");
            } else {
                tmpTotalMoney = tmpTotalMoney.add(tmpPayMoney);
            }

            //设置订单剩余信息
            order.setOrderType(EnumConstants.OrderType.PRE_SALE.getValue());
            order.setOrderStatus(EnumConstants.OrderStatus.WATI_TO_PAY.getValue());
            order.setIsAppraisal(orderParams.getIsAppraisal());
            if (orderParams.getIsAppraisal() == EnumConstants.YesOrNo.YES.getValue().intValue()) {
                order.setAppraisalPrice(orderParams.getAppraisalPrice());
            }
            order.setBuyerId(buyerId);
            order.setSellerId(orderParams.getSellerId());
            order.setIsPacker(EnumConstants.YesOrNo.NO.getValue()); //购买预售，卖家不会是背包客
            order.setTotalNum(orderParams.getNum());
            order.setPayMoney(tmpPayMoney);
            order.setConsigneeName(params.getConsigneeName());
            order.setConsigneeAddress(params.getConsigneeAddress());
            order.setConsigneePhone(params.getConsigneePhone());
            order.setBuyerMessage(orderParams.getBuyerMessage());
            order.setOrderTime(new Date());
            order.setEvaluateStatus(EnumConstants.EvaluateStatus.NO_EVALUATE.getValue());
            order.setBuyerDel(EnumConstants.BillDeleteStatus.ENABLE.getStatus());
            order.setSellerDel(EnumConstants.BillDeleteStatus.ENABLE.getStatus());
            super.update(order);

            orderIdList.add(order.getId());

            //封装收款列表数据
            MemberInfo sellerInfo = sellerService.getMemberInfo(orderParams.getSellerId(), EnumConstants.MemberType.seller.getValue());
            recieverMap.put("bizUserId", sellerInfo.getUuid());
            recieverMap.put("amount", BigDecimalUtil.changeY2F(tmpPayMoney));
            recieverList.add(recieverMap);
        }

        if (tmpTotalMoney.compareTo(params.getPayMoney()) != 0) {
            throw new BizException("总支付金额错误");
        }

        //封装返回数据
        result.setAmount(BigDecimalUtil.changeY2F(tmpTotalMoney));
        result.setFee(0L);
        result.setPayerId(buyerId);
        result.setRecieverList(recieverList);
        result.setOrderIdList(orderIdList);

        //清空redis中买家订单统计数据
        CacheUtil.getLockManager().hdel("S:iBase4J:buyer:orderInfo_key:", buyerId);

        return result;
    }


    @Override
    @Transactional
    public Object deleteOrder(DeleteOrderParam params) {
        DgOrder order = queryById(params.getOrderId());
        if (order == null) {
            return "订单不存在";
        } else if (order.getOrderStatus().intValue() == EnumConstants.OrderStatus.REFUND_SUCCESS.getValue() ||
                order.getOrderStatus().intValue() == EnumConstants.OrderStatus.SALE_CLOSE.getValue() ||
                order.getOrderStatus().intValue() == EnumConstants.OrderStatus.SALE_SUCCESS.getValue()) {
            if (params.getRole() == EnumConstants.MemberType.seller.getValue() ||
                    params.getRole() == EnumConstants.MemberType.packer.getValue()) {
                order.setSellerDel(EnumConstants.BillDeleteStatus.DELETE.getStatus());
            } else if (params.getRole() == EnumConstants.MemberType.buyer.getValue()) {
                order.setBuyerDel(EnumConstants.BillDeleteStatus.DELETE.getStatus());
            } else {
                return "参数错误";
            }
        } else {
            return "此订单不可删除";
        }

        update(order);
        return null;
    }

    @Override
    @Transactional
    public Object deliverGoods(DeliverGoodsParam params) {
        DgOrder order = queryById(params.getOrderId());
        if (order == null) {
            return "订单不存在";
        } else if (order.getOrderStatus().intValue() != EnumConstants.OrderStatus.WATI_TO_SEND.getValue()) {
            return "订单状态错误";
        }
        order.setConsigneeName(params.getConsigneeName());
        order.setConsigneeAddress(params.getConsigneeAddress());
        order.setConsigneePhone(params.getConsigneePhone());
        order.setSendStatus(1);
        order.setWaybillNo(params.getWayBillNo());
        order.setLogisticsNo(params.getComCode());
        order.setLogisticsCompany(EnumConstants.ChinaExpressCompany.getName(params.getComCode()));
        order.setOrderStatus(EnumConstants.OrderStatus.WATI_TO_RECIEVE.getValue());
        update(order);

        if (order.getOrderType() == EnumConstants.OrderType.DEMAND_SALE.getValue().intValue()) {
            //同步需求单状态为“已发货”
            DgDemandOrder demand = demandOrderService.queryByOrderId(order.getId());
            demand.setBudgetStatus(EnumConstants.DemandStatus.HAS_SENDED.getValue());
            demandOrderService.update(demand);
        }

        //清空redis中买家订单统计数据
        CacheUtil.getLockManager().hdel("S:iBase4J:buyer:orderInfo_key:", order.getBuyerId());

        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public List<DgOrder> listByOrderIds(List<Long> ids) {
        EntityWrapper<DgOrder> ew = new EntityWrapper<>();
        ew.in("id_", ids);
        return orderMapper.selectList(ew);
    }


    @Override
    @Transactional
    public Object getPayStatus(String ids) {
        for (String id : ids.split(",")) {
            DgOrder order = queryById(Long.parseLong(id));
            if (order == null) {
                return "订单不存在";
            } else if (order.getOrderStatus() != EnumConstants.OrderStatus.WATI_TO_PAY.getValue().intValue()) {
                return "订单状态错误";
            }
            order.setOrderStatus(EnumConstants.OrderStatus.WATI_TO_SEND.getValue());
            update(order);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Object queryAfterSaleList(Long id, QueryOrderParam params) {
        //封装查询参数
        EntityWrapper<DgOrderReturn> ew = new EntityWrapper<>();
        if (params.getRole() == EnumConstants.MemberType.seller.getValue()) {
            //来自买手的查询
            ew.eq("seller_id", id);
            ew.eq("is_packer", "0");
        } else if (params.getRole() == EnumConstants.MemberType.buyer.getValue()) {
            //来自买家的查询
            ew.eq("buyer_id", id);
        } else if (params.getRole() == EnumConstants.MemberType.packer.getValue()) {
            //来自背包客的查询
            ew.eq("seller_id", id);
            ew.eq("is_packer", "1");
        }
        ew.orderBy("create_time", false);

        RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());
        List<DgOrderReturn> orderReturnList = orderReturnMapper.selectPage(rb, ew);
        ArrayList<AfterSaleResult> resultList = InstanceUtil.newArrayList();
        for (DgOrderReturn orderReturn : orderReturnList) {
            AfterSaleResult result = new AfterSaleResult();
            result.setReturnOrderId(orderReturn.getId());
            result.setOrderId(orderReturn.getOrderId());
            result.setOrderType(orderReturn.getOrderType());
            result.setProductId(orderReturn.getProductId());
            result.setProductTitle(orderReturn.getProductTitle());
            result.setProductImage(orderReturn.getProductCover());
            result.setProductPrice(orderReturn.getProductPrice());
            result.setProductNum(orderReturn.getProductNum());
            result.setSpecParams(orderReturn.getSpecParams());
            result.setDeliveryTime(orderReturn.getDeliveryTime());
            if (orderReturn.getDeliveryTime() != null) {
                result.setDeliveryTimeInfo(DateUtils.formatDateTime(orderReturn.getDeliveryTime()));
            }
            result.setReturnStatus(orderReturn.getReturnStatus());
            result.setBuyerId(orderReturn.getBuyerId());
            result.setSellerId(orderReturn.getSellerId());
            result.setIsPacker(orderReturn.getIsPacker());

            //买手、背包客头像，昵称，UUID
            if (orderReturn.getIsPacker().intValue() == EnumConstants.YesOrNo.NO.getValue()) { //买手
                MemberInfo sellerInfo = sellerService.getMemberInfo(orderReturn.getSellerId(), EnumConstants.MemberType.seller.getValue());
                if (sellerInfo != null) {
                    result.setSellerNickname(sellerInfo.getNickName());
                    result.setSellerImage(sellerInfo.getAvatarRsurl());
                    result.setSellerUUID(sellerInfo.getUuid());
                }
            } else if (orderReturn.getIsPacker().intValue() == EnumConstants.YesOrNo.YES.getValue()) { //背包客
                MemberInfo buyerInfo = sellerService.getMemberInfo(orderReturn.getSellerId(), EnumConstants.MemberType.packer.getValue());
                if (buyerInfo != null) {
                    result.setSellerNickname(buyerInfo.getNickName());
                    result.setSellerImage(buyerInfo.getAvatarRsurl());
                    result.setSellerUUID(buyerInfo.getUuid() + 1); //背包客UUID，聊天功能末尾+1区分买手UUID
                }
            }

            //买家头像，昵称，UUID
            MemberInfo buyerInfo = sellerService.getMemberInfo(orderReturn.getBuyerId(), EnumConstants.MemberType.buyer.getValue());
            if (buyerInfo != null) {
                result.setBuyerNickname(buyerInfo.getNickName());
                result.setBuyerImage(buyerInfo.getAvatarRsurl());
                result.setBuyerUUID(buyerInfo.getUuid());
            }

            resultList.add(result);
        }

        Pagination<AfterSaleResult> page = new Pagination<>();
        page.setCurrent(params.getCurrent());
        page.setSize(params.getSize());
        page.setTotal(resultList.size());
        page.setRecords(resultList);

        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Object queryAfterSaleDetail(Long id) {
        if (id == null) {
            return "售后ID不能为空";
        }
        DgOrderReturn orderReturn = orderReturnService.queryById(id);
        if (orderReturn == null) {
            return "售后详情不存在";
        }

        AfterSaleDetailResult result = new AfterSaleDetailResult();
        result.setReturnOrderId(orderReturn.getId());
        result.setReturnNo(orderReturn.getReturnNo());
        result.setOrderId(orderReturn.getOrderId());
        result.setOrderType(orderReturn.getOrderType());
        result.setProductId(orderReturn.getProductId());
        result.setProductTitle(orderReturn.getProductTitle());
        result.setProductImage(orderReturn.getProductCover());
        result.setProductPrice(orderReturn.getProductPrice());
        result.setProductNum(orderReturn.getProductNum());
        result.setSpecParams(orderReturn.getSpecParams());
        result.setDeliveryTime(orderReturn.getDeliveryTime());
        if (orderReturn.getDeliveryTime() != null) {
            result.setDeliveryTimeInfo(DateUtils.formatDateTime(orderReturn.getDeliveryTime()));
        }
        result.setReturnMoney(orderReturn.getReturnMoney());
        result.setReasonText(orderReturn.getReasonText());
        result.setReturnSubmitTime(orderReturn.getReturnSubmitTime());
        if (orderReturn.getReturnSubmitTime() != null) {
            result.setReturnSubmitTimeInfo(DateUtils.formatDateTime(orderReturn.getReturnSubmitTime()));
        }
        result.setBuyerId(orderReturn.getBuyerId());
        result.setSellerId(orderReturn.getSellerId());
        result.setIsPacker(orderReturn.getIsPacker());
        result.setReturnStatus(orderReturn.getReturnStatus());

        //买手、背包客UUID
        if (orderReturn.getIsPacker().intValue() == EnumConstants.YesOrNo.NO.getValue()) { //买手
            MemberInfo sellerInfo = sellerService.getMemberInfo(orderReturn.getSellerId(), EnumConstants.MemberType.seller.getValue());
            if (sellerInfo != null) {
                result.setSellerUUID(sellerInfo.getUuid());
            }
        } else if (orderReturn.getIsPacker().intValue() == EnumConstants.YesOrNo.YES.getValue()) { //背包客
            MemberInfo buyerInfo = sellerService.getMemberInfo(orderReturn.getSellerId(), EnumConstants.MemberType.packer.getValue());
            if (buyerInfo != null) {
                result.setSellerUUID(buyerInfo.getUuid() + 1); //背包客UUID，聊天功能末尾+1区分买手UUID
            }
        }

        //买家UUID
        MemberInfo buyerInfo = sellerService.getMemberInfo(orderReturn.getBuyerId(), EnumConstants.MemberType.buyer.getValue());
        if (buyerInfo != null) {
            result.setBuyerUUID(buyerInfo.getUuid());
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public BuyerOrderInfo queryBuyerOrderInfo(Long id) {
        BuyerOrderInfo info = new BuyerOrderInfo();
        Integer[] arrInfo = orderDao.selectBuyerOrderInfo(id);
        info.setId(id);
        info.setWaitPay(arrInfo[0]);
        info.setWaitDeliver(arrInfo[1]);
        info.setWaitReceipted(arrInfo[2]);
        info.setRefund(arrInfo[3]);
        info.setWaitEvaluate(arrInfo[4]);
        return info;
    }

    @Override
    @Transactional(readOnly = true)
    public Object queryExpress(Long id) {
        if (id == null) {
            return "订单ID不能为空";
        }
        DgOrder order = queryById(id);
        if (order == null) {
            return "订单不存在";
        }

        ExpressResult expressResult = new ExpressResult();
        expressResult.setLogisticsCompany(order.getLogisticsCompany());
        expressResult.setWaybillNo(order.getWaybillNo());
        //调用物流接口，查询物流单跟踪信息
        ExpressInputParam param = new ExpressInputParam();
        param.setCom(order.getLogisticsNo());
        param.setNu(order.getWaybillNo());
        ExpressInfo result = ExpressQueryUtil.getExpressInfoByCompanyCodeAndNum(param);
        if (result != null) {
            expressResult.setData(result.getData());
        }

        return expressResult;
    }

    @Override
    @Transactional(readOnly = true)
    public Object queryExpressCompany(String wayBillNo) {
        if (wayBillNo == null) {
            return "运单号不能为空";
        }

        ExpressInputParam param = new ExpressInputParam();
        param.setNu(wayBillNo);
        List<ExpressCompany> expressList = ExpressQueryUtil.getExpressCompanyByNum(param);
        ArrayList<Object> resultList = InstanceUtil.newArrayList();
        for (ExpressCompany expressCompany : expressList) {
            HashMap<String, String> hm = InstanceUtil.newHashMap();
            String comCode = expressCompany.getComCode();
            String name = EnumConstants.ChinaExpressCompany.getName(expressCompany.getComCode());
            hm.put("comCode", comCode);
            hm.put("comName", name);
            resultList.add(hm);
        }

        return resultList;
    }

    @Override
    @Transactional(readOnly = true)
    public Object queryCompanyList() {
        Map<String, String> map = EnumConstants.ChinaExpressCompany.getMap();
        ArrayList<Map<String, String>> list = InstanceUtil.newArrayList();
        for (String key : map.keySet()) {
            HashMap<String, String> hm = InstanceUtil.newHashMap();
            hm.put("comCode", key);
            hm.put("comName", map.get(key));
            list.add(hm);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Object queryOrderList(QueryOrderParam params, Long id) {
        //封装查询参数
        EntityWrapper<DgOrder> ew = new EntityWrapper<>();
        //不是查询全部，就设置订单状态
        if (params.getOrderStatus() != 0) {
            ew.eq("order_status", params.getOrderStatus());
        }

        if (params.getRole() == EnumConstants.MemberType.seller.getValue()) {
            //来自买手的查询
            ew.eq("seller_del", "0");
            ew.eq("seller_id", id);
            ew.eq("is_packer", "0");
        } else if (params.getRole() == EnumConstants.MemberType.buyer.getValue()) {
            //来自买家的查询
            ew.eq("buyer_id", id);
            ew.eq("buyer_del", "0");
            //如果查询的是买家“待评价”列表，“已追评”的订单不展示
            if (params.getOrderStatus().intValue() == EnumConstants.OrderStatus.SALE_SUCCESS.getValue()) {
                ew.ne("evaluate_status", 2);
            }
        } else if (params.getRole() == EnumConstants.MemberType.packer.getValue()) {
            //来自背包客的查询
            ew.eq("seller_del", "0");
            ew.eq("seller_id", id);
            ew.eq("is_packer", "1");
            ew.ne("order_status", EnumConstants.OrderStatus.WATI_TO_PAY.getValue()); //背包客不展示“待付款”订单
            ew.ne("order_status", EnumConstants.OrderStatus.SALE_CLOSE.getValue()); //背包客不展示“交易关闭”订单
        }
        ew.and("id_ NOT IN (SELECT id_ FROM dg_order WHERE order_type = 3 AND order_status = 1)"); //需求生成的临时“待支付”订单，不显示，区别与预售生成的“待支付”订单
        ew.orderBy("create_time", false);

        ArrayList<OrderResult> orderResultList = InstanceUtil.newArrayList();
        //分页查询订单列表，封装订单信息
        RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());
        List<DgOrder> orderList = orderMapper.selectPage(rb, ew);
        for (DgOrder order : orderList) {
            OrderResult orderResult = new OrderResult();
            insertInfo(order, orderResult, params.getRole());
            orderResultList.add(orderResult);
        }

        Pagination<OrderResult> page = new Pagination<>();
        page.setCurrent(params.getCurrent());
        page.setSize(params.getSize());
        page.setRecords(orderResultList);
        page.setTotal(orderResultList.size());

        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Object queryOrderDetail(Long id, Integer role) {
        if (id == null) {
            return "订单ID不能为空";
        }
        if (role == null) {
            return "参数错误";
        }

        DgOrder order = queryById(id);
        OrderDetailResult orderDetailResult = new OrderDetailResult();
        if (order == null) {
            return "订单不存在";
        } else {
            insertInfo(order, orderDetailResult, role);
            orderDetailResult.setConsigneeName(order.getConsigneeName());
            orderDetailResult.setConsigneeAddress(order.getConsigneeAddress());
            orderDetailResult.setConsigneePhone(order.getConsigneePhone());
            orderDetailResult.setOrderTime(order.getOrderTime());
            orderDetailResult.setOrderTimeString(DateUtils.formatDateTime(order.getOrderTime()));
        }
        return orderDetailResult;
    }


    @Override
    @Transactional(readOnly = true)
    public Object remind(Long id) {
        if (id == null) {
            return "订单ID不能为空";
        }
        DgOrder order = queryById(id);
        if (order == null) {
            return "订单不存在";
        }
        if (order.getOrderStatus().intValue() != EnumConstants.OrderStatus.WATI_TO_SEND.getValue()) {
            return "订单状态错误";
        }

        //发送提醒消息，提醒发货
        setEveryDayRadio(order, EnumConstants.EveryDayRadioType.BUYER_REMIND_DELIVERY);

        return null;
    }


    @Override
    @Transactional
    public Object toPay(ToPayParam params) {
        DgOrder order = queryById(params.getOrderId());
        if (order == null) {
            return "订单不存在";
        }
        if (order.getOrderStatus().intValue() != EnumConstants.OrderStatus.WATI_TO_PAY.getValue()) {
            return "订单状态错误";
        }

        //------------这里是模拟的支付逻辑------------
        order.setOrderStatus(EnumConstants.OrderStatus.WATI_TO_SEND.getValue());
        update(order);

        //清空redis中买家订单统计数据
        CacheUtil.getLockManager().hdel("S:iBase4J:buyer:orderInfo_key:", order.getBuyerId());

        //发送提醒消息，付款成功
        setEveryDayRadio(order, EnumConstants.EveryDayRadioType.BUYER_PAY_SUCCESS);

        return order.getId();
    }


    @Override
    @Transactional(readOnly = true)
    public Object getPayParams(Long id) {
        DgOrder order = queryById(id);
        if (order == null) {
            return "订单不存在";
        }
        if (order.getOrderStatus().intValue() != EnumConstants.OrderStatus.WATI_TO_PAY.getValue()) {
            return "订单状态错误";
        }

        //封装托管代收所需参数，与生成订单接口返回的是一样的
        ToPayOrderResult result = new ToPayOrderResult();
        result.setAmount(BigDecimalUtil.changeY2F(order.getPayMoney()));
        result.setFee(0L);
        result.setPayerId(order.getBuyerId());

        //收款人列表，单独支付订单，收款人只有一个
        JSONArray recieverList = new JSONArray();
        JSONObject recieverMap = new JSONObject();
        if (order.getIsPacker() == EnumConstants.YesOrNo.NO.getValue().intValue()) {
            MemberInfo sellerInfo = sellerService.getMemberInfo(order.getSellerId(), EnumConstants.MemberType.seller.getValue());
            recieverMap.put("bizUserId", sellerInfo.getUuid());
        } else {
            MemberInfo sellerInfo = sellerService.getMemberInfo(order.getSellerId(), EnumConstants.MemberType.packer.getValue());
            recieverMap.put("bizUserId", sellerInfo.getUuid());
        }
        recieverMap.put("amount", result.getAmount());
        recieverList.add(recieverMap);
        result.setRecieverList(recieverList);

        //单独的订单支付，订单id只有一个
        ArrayList<Long> orderIdList = new ArrayList<>();
        orderIdList.add(order.getId());
        result.setOrderIdList(orderIdList);

        return result;
    }


    @Override
    @Transactional
    @TxTransaction
    public void paySuccess(Long applyPaylogId) {
        //根据托管代收ID查询订单，并更新
        DgOrder pOrder = new DgOrder();
        pOrder.setApplyPaylogId(applyPaylogId);
        DgOrder order = orderMapper.selectOne(pOrder);
        order.setOrderStatus(EnumConstants.OrderStatus.WATI_TO_SEND.getValue());
        update(order);

        //如果是需求，更新需求、需求接单表
        if (order.getOrderType() == EnumConstants.OrderType.DEMAND_SALE.getValue().intValue()) {
            //更新需求表
            DgDemandOrder demand = demandOrderService.queryById(order.getDemandId());
            demand.setSellerId(order.getSellerId());
            demand.setIsPacker(order.getIsPacker());
            demand.setTour(order.getId());
            demand.setTourNo(order.getTourNo());
            demand.setOrderId(order.getId());
            demand.setBudgetStatus(EnumConstants.DemandStatus.WATI_TO_SEND.getValue());
            demandOrderService.update(demand);

            //更新需求接单表
            demandAcceptService.paySuccess(demand.getId(), order.getSellerId());

            //发送消息，更新行程需求统计
            queueSender.send("Order:Tour:demandSuccess.query", demand.getTour().toString());
        }

        //通知买手支付成功
        setEveryDayRadio(order, EnumConstants.EveryDayRadioType.BUYER_PAY_SUCCESS);

        //清空redis中买家订单统计数据
        CacheUtil.getLockManager().hdel("S:iBase4J:buyer:orderInfo_key:", order.getBuyerId());

        //发送消息，更新行程需求统计
        queueSender.send("Order:Tour:orderSuccess.query", order.getTourId() + "," + order.getPayMoney());
    }


    @Override
    @Transactional
    @TxTransaction
    public void confirmReceiveSuccess(Long payPaylogId) {
        //根据托管代付ID查询订单，并更新
        DgOrder pOrder = new DgOrder();
        pOrder.setPayPaylogId(payPaylogId);
        DgOrder order = orderMapper.selectOne(pOrder);
        order.setOrderStatus(EnumConstants.OrderStatus.SALE_SUCCESS.getValue());
        update(order);

        //更新买手/背包客成交量
        if (order.getIsPacker().intValue() == EnumConstants.YesOrNo.NO.getValue()) {
            queueSender.send("Order:Order:addTradeCount.queue", EnumConstants.MemberType.seller.getValue() + "," + order.getSellerId());
        } else if (order.getIsPacker().intValue() == EnumConstants.YesOrNo.YES.getValue()) {
            queueSender.send("Order:Order:addTradeCount.queue", EnumConstants.MemberType.packer.getValue() + "," + order.getSellerId());
        }

        //如果为需求订单，同步需求单状态为“已完成”
        if (order.getOrderType().intValue() == EnumConstants.OrderType.DEMAND_SALE.getValue()) {
            DgDemandOrder demand = demandOrderService.queryByOrderId(order.getId());
            demand.setBudgetStatus(EnumConstants.DemandStatus.FINISH.getValue());
            demandOrderService.update(demand);
        }

        //清空redis中买家订单统计数据
        CacheUtil.getLockManager().hdel("S:iBase4J:buyer:orderInfo_key:", order.getBuyerId());
    }


    @Override
    @Transactional
    @TxTransaction
    public void agreeRefundSuccess(Long refundPaylogId) {
        //更新订单与退款单
        DgOrder pOrder = new DgOrder();
        pOrder.setRefundPaylogId(refundPaylogId);
        DgOrder order = orderMapper.selectOne(pOrder);
        order.setOrderStatus(EnumConstants.OrderStatus.REFUND_SUCCESS.getValue());
        update(order);

        DgOrderReturn p = new DgOrderReturn();
        p.setOrderId(order.getId());
        DgOrderReturn orderReturn = orderReturnMapper.selectOne(p);
        orderReturn.setReturnStatus(EnumConstants.AfterSaleStatus.REFUND_SUCCESS.getStatus());
        orderReturnService.update(orderReturn);

        //查询代收的支付记录，状态4->5（交易成功->退款成功）
        DgPayLog payLog = payLogService.queryById(order.getApplyPaylogId());
        payLog.setStatus(EnumConstants.PayStatus.REFUND_SUCCESS.getValue());
        payLogService.update(payLog);

        //如果订单是“售后处理状态”，即订单下所有商品都申请了售后，变更状态为“退款成功”
        if (order.getOrderStatus().intValue() == EnumConstants.OrderStatus.AFTER_SALE.getValue()) {
            order.setOrderStatus(EnumConstants.OrderStatus.REFUND_SUCCESS.getValue());
            update(order);
        }

        //如果订单为需求订单，需要同步需求状态为“已失效”
        if (order.getOrderType().intValue() == EnumConstants.OrderType.DEMAND_SALE.getValue()) {
            DgDemandOrder demand = demandOrderService.queryByOrderId(order.getId());
            demand.setBudgetStatus(EnumConstants.DemandStatus.INVALID.getValue());
            demandOrderService.update(demand);
        }

        //清空redis中买家订单统计数据
        CacheUtil.getLockManager().hdel("S:iBase4J:buyer:orderInfo_key:", order.getBuyerId());
    }


    /**
     * 封装部分订单信息
     */
    private void insertInfo(DgOrder order, OrderResult orderResult, Integer role) {
        orderResult.setOrderId(order.getId());
        orderResult.setOrderNo(order.getOrderNo());
        orderResult.setOrderStatus(order.getOrderStatus());
        orderResult.setBuyerId(order.getBuyerId());
        orderResult.setSellerId(order.getSellerId());

        //买手头像，昵称，UUID
        if (order.getIsPacker().intValue() == EnumConstants.YesOrNo.NO.getValue()) { //买手
            MemberInfo sellerInfo = sellerService.getMemberInfo(order.getSellerId(), EnumConstants.MemberType.seller.getValue());
            if (sellerInfo != null) {
                orderResult.setSellerNickname(sellerInfo.getNickName());
                orderResult.setSellerImage(sellerInfo.getAvatarRsurl());
                orderResult.setSellerUUID(sellerInfo.getUuid());
            }
        } else if (order.getIsPacker().intValue() == EnumConstants.YesOrNo.YES.getValue()) { //背包客
            MemberInfo buyerInfo = sellerService.getMemberInfo(order.getSellerId(), EnumConstants.MemberType.packer.getValue());
            if (buyerInfo != null) {
                orderResult.setSellerNickname(buyerInfo.getNickName());
                orderResult.setSellerImage(buyerInfo.getAvatarRsurl());
                orderResult.setSellerUUID(buyerInfo.getUuid() + 1); //背包客UUID，聊天功能末尾+1区分买手UUID
            }
        }

        MemberInfo buyerInfo = sellerService.getMemberInfo(order.getBuyerId(), EnumConstants.MemberType.buyer.getValue());
        //买家头像，昵称，UUID
        if (buyerInfo != null) {
            orderResult.setBuyerNickname(buyerInfo.getNickName());
            orderResult.setBuyerImage(buyerInfo.getAvatarRsurl());
            orderResult.setBuyerUUID(buyerInfo.getUuid());
        }

        orderResult.setOrderType(order.getOrderType());
        orderResult.setIsPacker(order.getIsPacker());
        orderResult.setProductNum(order.getTotalNum());
        orderResult.setPayMoney(order.getPayMoney());
        orderResult.setAppraisalPrice(order.getAppraisalPrice());
        orderResult.setAppraisalUrl(order.getAppraisalUrl());
        orderResult.setWaybillNo(order.getWaybillNo());
        orderResult.setEvaluateStatus(order.getEvaluateStatus());
        orderResult.setOrderStatusInfo(EnumConstants.OrderStatus.getInfo(order.getOrderStatus(), role));
        orderResult.setEvaluateStatusInfo(EnumConstants.EvaluateStatus.getInfo(order.getEvaluateStatus()));
        orderResult.setCancelText(order.getCancelText());

        ArrayList<GoodsResult> goodsList = InstanceUtil.newArrayList();
        if (order.getOrderType().intValue() == EnumConstants.OrderType.PRE_SALE.getValue()) {
            //查询订单下的商品信息
            EntityWrapper<DgProductOrder> ew = new EntityWrapper<>();
            ew.eq("order_id_", order.getId());
            ew.orderBy("id_");
            List<DgProductOrder> productList = productOrderMapper.selectList(ew);
            for (DgProductOrder productOrder : productList) {
                GoodsResult goodsResult = new GoodsResult();
                //将订单下的预售商品信息封装
                goodsResult.setProductType(EnumConstants.OrderType.PRE_SALE.getValue());
                goodsResult.setProductId(productOrder.getProductId());
                goodsResult.setProductCover(productOrder.getProductCover());
                goodsResult.setProductPrice(productOrder.getProductPrice());
                goodsResult.setProductNum(productOrder.getProductNum());
                goodsResult.setSubtotal(productOrder.getProductPrice().multiply(new BigDecimal(productOrder.getProductNum())));
                goodsResult.setTitle(productOrder.getProductName());
                goodsResult.setSpecParams(productOrder.getSpecParams());
                goodsResult.setProductOrderId(productOrder.getId());
                //如果商品申请了售后，返回售后ID
                if (productOrder.getIsRefund() == EnumConstants.YesOrNo.YES.getValue().intValue()) {
                    DgOrderReturn param = new DgOrderReturn();
                    param.setOrderId(order.getId());
                    param.setProductOrderId(productOrder.getId());
                    DgOrderReturn orderReturn = orderReturnService.selectOne(param);
                    goodsResult.setOrderReturnId(orderReturn.getId());
                }
                goodsList.add(goodsResult);
            }
        } else if (order.getOrderType().intValue() == EnumConstants.OrderType.DEMAND_SALE.getValue()) {
            //查询订单下需求信息
            DgDemandOrder demand = demandOrderService.queryById(order.getDemandId());
            GoodsResult goodsResult = new GoodsResult();
            goodsResult.setProductType(EnumConstants.OrderType.DEMAND_SALE.getValue());
            goodsResult.setProductId(demand.getId()); //商品ID，需求为需求单ID
            goodsResult.setProductOrderId(demand.getId()); //商品快照ID，需求为需求单ID
            goodsResult.setProductCover(demand.getDemandCover());
            goodsResult.setProductPrice(demand.getBudgetPrice());
            goodsResult.setTitle(demand.getDemandDesc());
            goodsResult.setDeliveryTime(demand.getDeliveryTime());
            orderResult.setToilsomePrice(demand.getToilsomePrice());
            if (demand.getDeliveryTime() != null) {
                goodsResult.setDeliveryTimeString(DateUtils.formatDateTime(demand.getDeliveryTime()));
            }
            //需求订单是否申请售后，需求订单商品只有一个，所以申请了售后一定为“售后处理中”或“退款成功”
            if (order.getOrderStatus() == EnumConstants.OrderStatus.AFTER_SALE.getValue().intValue()
                    || order.getOrderStatus() == EnumConstants.OrderStatus.REFUND_SUCCESS.getValue().intValue()) {
                DgOrderReturn param = new DgOrderReturn();
                param.setOrderId(order.getId());
                DgOrderReturn orderReturn = orderReturnService.selectOne(param);
                if (orderReturn != null) {
                    goodsResult.setOrderReturnId(orderReturn.getId());
                }
            }
            goodsList.add(goodsResult);
        }

        orderResult.setGoodsList(goodsList);
    }


    /**
     * 取消订单退还库存
     *
     * @param orderId 订单ID
     */
    private void returnStock(Long orderId) {
        //退还订单中所有商品的库存
        EntityWrapper<DgProductOrder> ew = new EntityWrapper<>();
        ew.eq("order_id_", orderId);
        List<DgProductOrder> productOrderList = productOrderMapper.selectList(ew);
        RedisLock redisLock = new RedisLock(redisTemplate);
        for (DgProductOrder productOrder : productOrderList) {
            //加库存锁（分布式锁）
            try {
                if (redisLock.lock("ORDER:LOCK_GOODS" + orderId)) { // 获取分布式锁
                    PtSku sku = skuService.queryById(productOrder.getSkuId());
                    PtProduct product = productService.queryById(productOrder.getProductId());
                    //不为下架商品才退库存
                    if (product != null && product.getEnable().intValue() == EnumConstants.YesOrNo.YES.getValue() &&
                            sku != null && sku.getEnable().intValue() == EnumConstants.YesOrNo.YES.getValue()) {
                        sku.setStock(sku.getStock() + productOrder.getProductNum());
                    }
                    skuService.update(sku);
                    redisLock.unlock("ORDER:LOCK_GOODS" + orderId); //释放锁
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error("【APP订单模块|退还库存】商品库存锁错误，错误信息：" + e.getMessage());
            }
        }
    }


    /**
     * 今日提醒
     *
     * @param order 订单
     * @param type  提醒消息类型
     */
    private void setEveryDayRadio(DgOrder order, EnumConstants.EveryDayRadioType type) {
        EveryDayRadioParam everyDayRadioParam = new EveryDayRadioParam();
        everyDayRadioParam.setType(type.getValue());
        everyDayRadioParam.setToMemberId(order.getSellerId());
        everyDayRadioParam.setToMemberType(order.getIsPacker().intValue() == EnumConstants.YesOrNo.YES.getValue() ? EnumConstants.MemberType.packer.getValue() : EnumConstants.MemberType.seller.getValue());
        everyDayRadioParam.setMemberId(order.getBuyerId());
        queueSender.send("Order:Circle:setEveryDayRadio.queue", everyDayRadioParam);
    }

}
