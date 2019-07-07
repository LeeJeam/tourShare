package com.xmyy.order.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmyy.circle.model.DgOrderEvaluate;
import com.xmyy.circle.model.DgProductEvaluate;
import com.xmyy.circle.service.DgOrderEvaluateService;
import com.xmyy.circle.service.DgProductEvaluateService;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.DateUtils;
import com.xmyy.common.util.ExpressQueryUtil;
import com.xmyy.common.vo.ExpressInfo;
import com.xmyy.common.vo.ExpressInputParam;
import com.xmyy.common.vo.MemberInfo;
import com.xmyy.demand.model.DgDemandOrder;
import com.xmyy.demand.service.DgDemandOrderService;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.order.mapper.DgOrderMapper;
import com.xmyy.order.mapper.DgProductOrderMapper;
import com.xmyy.order.model.DgOrder;
import com.xmyy.order.model.DgOrderHistory;
import com.xmyy.order.model.DgProductOrder;
import com.xmyy.order.service.DgOrderHistoryService;
import com.xmyy.order.service.DgOrderManageService;
import com.xmyy.order.service.DgProductOrderService;
import com.xmyy.order.vo.*;
import com.xmyy.pay.model.DgPayLog;
import com.xmyy.pay.service.DgPayLogService;
import com.xmyy.product.model.PtCategory;
import com.xmyy.product.model.PtProduct;
import com.xmyy.product.service.PtCategoryService;
import com.xmyy.product.service.PtProductService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单 后台管理服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = DgOrderManageService.class)
@CacheConfig(cacheNames = "DgOrderManage")
public class DgOrderManageServiceImpl extends BaseServiceImpl<DgOrder, DgOrderMapper> implements DgOrderManageService {

    @Resource
    private DgOrderMapper orderMapper;
    @Resource
    private DgProductOrderMapper productOrderMapper;
    @Resource
    private DgDemandOrderService demandOrderService;
    @Resource
    private DgProductOrderService productOrderService;
    @Resource
    private PtProductService productService;
    @Resource
    private PtCategoryService categoryService;
    @Resource
    private UcSellerService sellerService;
    @Resource
    private DgOrderHistoryService orderHistoryService;
    @Resource
    private DgOrderEvaluateService orderEvaluateService;
    @Resource
    private DgProductEvaluateService productEvaluateService;
    @Resource
    private DgPayLogService payLogService;

    @Override
    @Transactional(readOnly = true)
    public Object queryOrderList(QueryOrderManageParam params) {
        EntityWrapper<DgOrder> ew = new EntityWrapper<>();
        if (params.getRole() == EnumConstants.MemberType.seller.getValue()) {
            //来自买手的查询
            ew.eq("seller_id", params.getUserId());
            ew.eq("is_packer", "0");
            ew.ne("order_status", "1"); //买家订单列表过滤“待付款”
        } else if (params.getRole() == EnumConstants.MemberType.buyer.getValue()) {
            //来自买家的查询
            ew.eq("buyer_id", params.getUserId());
        } else if (params.getRole() == EnumConstants.MemberType.packer.getValue()) {
            //来自背包客的查询
            ew.eq("seller_id", params.getUserId());
            ew.eq("is_packer", "1");
            ew.ne("order_status", "1"); //买家订单列表过滤“待付款”
        }
        if (!StringUtils.isBlank(params.getProductName())) { //物品名称
            ew.like("product_name_str", params.getProductName().trim());
        }
        if (!StringUtils.isBlank(params.getOrderNo())) { //订单号
            ew.eq("order_no", params.getOrderNo());
        }
        if (!StringUtils.isBlank(params.getOrderStatus())) { //订单状态
            ew.eq("order_status", params.getOrderStatus());
        }
        if (!StringUtils.isBlank(params.getIsAppraisal())) { //是否鉴定
            ew.eq("is_appraisal", params.getIsAppraisal());
        }
        if (!StringUtils.isBlank(params.getIsEvaluate())) { //评价状态
            if (Integer.valueOf(params.getIsEvaluate()) == 0) {
                ew.eq("evaluate_status", "0");
            } else {
                ew.ne("evaluate_status", "0");
            }
        }
        if (params.getBeginTime() != null) {
            ew.ge("order_time", params.getBeginTime());
        }
        if (params.getEndTime() != null) {
            ew.le("order_time", params.getEndTime());
        }

        //TODO 订单下多个商品，原型的显示有问题

        ew.orderDesc(Lists.newArrayList("order_time"));
        int count = orderMapper.selectCount(ew);
        List<OrderManageResult> resultList = InstanceUtil.newArrayList();
        if (count > 0) {
            RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());
            List<DgOrder> orderList = orderMapper.selectPage(rb, ew);
            if (CollectionUtils.isNotEmpty(orderList)) {
                setListInfo(orderList, resultList);
            }
        }

        // 分页
        Pagination<OrderManageResult> pageResult = new Pagination<OrderManageResult>();
        pageResult.setCurrent(params.getCurrent());
        pageResult.setSize(params.getSize());
        pageResult.setTotal(count);
        pageResult.setRecords(resultList);
        int pages = (count % params.getSize() == 0) ? count / params.getSize() : count / params.getSize() + 1;
        pageResult.setPages(pages);
        return pageResult;
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryOrderDetail(Long id) {
        DgOrder order = this.queryById(id);
        if (order == null) {
            return "订单不存在";
        }
        OrderDetailManageResult result = new OrderDetailManageResult();

        //1.买家收货信息
        result.setConsigneeName(order.getConsigneeName());
        result.setConsigneeAddress(order.getConsigneeAddress());
        result.setConsigneePhone(order.getConsigneePhone());

        //2.卖家信息
        if (order.getIsPacker() == EnumConstants.YesOrNo.NO.getValue().intValue()) { //买手
            MemberInfo sellerInfo = sellerService.getMemberInfo(order.getSellerId(), EnumConstants.MemberType.seller.getValue());
            if (sellerInfo != null) {
                result.setNickName(sellerInfo.getNickName());
                result.setRealName(sellerInfo.getRealName());
                result.setMobile(sellerInfo.getMobile());
                result.setIsSelf(sellerInfo.getIsSelf());
                result.setRealState(sellerInfo.getRealState());
            }
        } else if (order.getIsPacker() == EnumConstants.YesOrNo.YES.getValue().intValue()) { //背包客
            MemberInfo buyerInfo = sellerService.getMemberInfo(order.getSellerId(), EnumConstants.MemberType.packer.getValue());
            if (buyerInfo != null) {
                result.setNickName(buyerInfo.getNickName());
                result.setRealName(buyerInfo.getRealName());
                result.setMobile(buyerInfo.getMobile());
                result.setIsSelf(buyerInfo.getIsSelf());
                result.setRealState(buyerInfo.getRealState());
            }
        }

        //3.订单信息
        result.setOrderNo(order.getOrderNo());
        result.setOrderTime(DateUtils.formatDate(order.getOrderTime(), "yyyy-MM-dd HH:mm:ss"));
        result.setTourNo(order.getTourNo());
        //支付信息
        DgPayLog payLog = payLogService.queryById(order.getApplyPaylogId());
        if (payLog != null) {
            result.setPayTime(DateUtils.formatDate(payLog.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            result.setOutTradeNo(payLog.getBizOrderNo());
            result.setPayMethod(EnumConstants.XBBPayMethod.getName(payLog.getPayMethod()));
        }
        //订单操作历史
        List<DgOrderHistory> historyList = orderHistoryService.queryByOrderId(order.getId());
        if (historyList != null && historyList.size() > 0) {
            ArrayList<OrderHistoryVo> orderHistoryResultList = InstanceUtil.newArrayList();
            for (DgOrderHistory history : historyList) {
                OrderHistoryVo vo = new OrderHistoryVo();
                vo.setOptContext(history.getOptContext());
                vo.setUpdateTime(DateUtils.formatDate(history.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
                orderHistoryResultList.add(vo);
            }
            result.setOrderHistoryList(orderHistoryResultList);
        }

        //4.物品详情
        if (order.getOrderType() == EnumConstants.OrderType.PRE_SALE.getValue().intValue()) { // 预售商品
            List<DgProductOrder> productOrderList = productOrderService.queryByOrderId(id);
            if (productOrderList != null && productOrderList.size() > 0) {
                DgProductOrder tmpProduct = productOrderList.get(0);
                result.setProductName(tmpProduct.getProductName());
                PtProduct product = productService.queryById(tmpProduct.getProductId());
                if (product != null) {
                    PtCategory ptCategory1 = categoryService.queryById(product.getPtCategoryId());
                    PtCategory ptCategory2 = categoryService.queryById(product.getPtCategoryId2());
                    String tmpCategory = null;
                    if (ptCategory1 != null) {
                        tmpCategory = ptCategory1.getName();
                    }
                    if (ptCategory2 != null) {
                        tmpCategory = tmpCategory + "-" + ptCategory2.getName();
                    }
                    result.setCategory(tmpCategory);
                    result.setSpecParams(tmpProduct.getSpecParams());
                    result.setPrice(tmpProduct.getProductPrice());
                }
            }
        } else if (order.getOrderType() == EnumConstants.OrderType.DEMAND_SALE.getValue().intValue()) { //需求
            result.setProductName(order.getProductNameStr());
            result.setPrice(order.getPayMoney());
        }

        //5.物流信息
        ExpressInputParam pExpress = new ExpressInputParam();
        pExpress.setCom(order.getLogisticsNo());
        pExpress.setNu(order.getWaybillNo());
        ExpressInfo expressInfo = ExpressQueryUtil.getExpressInfoByCompanyCodeAndNum(pExpress);
        if (expressInfo != null) {
            result.setExpressInfo(expressInfo.getData());
        }

        //6.评价信息
        //订单评价
        DgOrderEvaluate pOrderEvaluate = new DgOrderEvaluate();
        pOrderEvaluate.setOrderId(id);
        DgOrderEvaluate orderEvaluate = orderEvaluateService.selectOne(pOrderEvaluate);
        if (orderEvaluate != null) {
            result.setEvaluateTime(DateUtils.formatDate(orderEvaluate.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            result.setProductLevel(orderEvaluate.getProductLevel());
        }

        //订单下每个商品的评价
        List<DgProductEvaluate> productEvaluateList = productEvaluateService.queryByOrderId(id);
        if (productEvaluateList != null && productEvaluateList.size() > 0) {
            List<ProductEvaluateVo> productEvaluateResultList = InstanceUtil.newArrayList();
            for (DgProductEvaluate evaluate : productEvaluateList) {
                ProductEvaluateVo vo = new ProductEvaluateVo();
                vo.setProductEvaluate(evaluate.getContent());
                vo.setProductEvaluateImages(evaluate.getPicRsurl());
                productEvaluateResultList.add(vo);
            }
            result.setProductevaluatelist(productEvaluateResultList);
        }

        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public ArrayList<OrderInTourManageResult> queryOrderInTour(Long id) {
        ArrayList<OrderInTourManageResult> resultList = InstanceUtil.newArrayList();
        List<DgDemandOrder> demandOrderList = demandOrderService.queryByTourId(id);
        for (DgDemandOrder demandOrder : demandOrderList) {
            DgOrder order = queryById(demandOrder.getOrderId());
            if (order != null) {
                OrderInTourManageResult result = new OrderInTourManageResult();
                result.setOrderId(order.getId());
                result.setOrderNo(order.getOrderNo());
                result.setOrderStatusStr(EnumConstants.OrderStatus.getInfo(order.getOrderStatus(), 1));
                result.setCategory(null);
                result.setProductNum(order.getTotalNum());
                result.setPrice(order.getPayMoney());
                result.setBuyerId(order.getBuyerId());
                result.setToilsomePrice(demandOrder.getToilsomePrice());
                result.setPayMoney(order.getPayMoney());
                result.setDeliverStatusStr(order.getSendStatus() == 0 ? "未发货" : "已发货");
                resultList.add(result);
            }
        }

        List<DgProductOrder> productOrderList = productOrderService.queryByTourId(id);
        for (DgProductOrder productOrder : productOrderList) {
            DgOrder order = queryById(productOrder.getOrderId());
            if (order != null) {
                OrderInTourManageResult result = new OrderInTourManageResult();
                result.setOrderId(order.getId());
                result.setOrderNo(order.getOrderNo());
                result.setOrderStatusStr(EnumConstants.OrderStatus.getInfo(order.getOrderStatus(), 1));
                PtProduct product = productService.queryById(productOrder.getProductId());
                if (product != null) {
                    PtCategory ptCategory1 = categoryService.queryById(product.getPtCategoryId());
                    PtCategory ptCategory2 = categoryService.queryById(product.getPtCategoryId2());
                    String tmpCategory = null;
                    if (ptCategory1 != null) {
                        tmpCategory = ptCategory1.getName();
                    }
                    if (ptCategory2 != null) {
                        tmpCategory = tmpCategory + "-" + ptCategory2.getName();
                    }
                    result.setCategory(tmpCategory);
                }
                result.setProductNum(productOrder.getProductNum());
                result.setPrice(productOrder.getProductPrice());
                result.setBuyerId(order.getBuyerId());
                result.setToilsomePrice(null);
                result.setPayMoney(productOrder.getSubtotal());
                result.setDeliverStatusStr(order.getSendStatus() == 0 ? "未发货" : "已发货");
                resultList.add(result);
            }
        }

        return resultList;
    }

    private void setListInfo(List<DgOrder> orderList, List<OrderManageResult> resultList) {
        for (DgOrder order : orderList) {
            OrderManageResult result = new OrderManageResult();
            result.setOrderId(order.getId());
            result.setOrderNo(order.getOrderNo());
            result.setOrderTime(DateUtils.formatDate(order.getOrderTime(), "yyyy-MM-dd HH:mm:ss"));
            result.setOrderType(order.getOrderType());
            result.setOrderStatus(order.getOrderStatus());
            result.setTradeStatus(order.getOrderStatus() == EnumConstants.OrderStatus.WATI_TO_PAY.getValue().intValue() ? 0 : 1);
            if (order.getOrderType() == EnumConstants.OrderType.DEMAND_SALE.getValue().intValue()) {
                //需求订单
                DgDemandOrder demandOrder = demandOrderService.queryByOrderId(order.getId());
                if (demandOrder != null) {
                    result.setProductName(demandOrder.getDemandDesc());
                    result.setProductImage(demandOrder.getDemandImages());
                    result.setProductNum(null);
                    result.setPrice(order.getPayMoney());
                    result.setToilsomePrice(demandOrder.getToilsomePrice());
                    result.setDemandOrderNo(demandOrder.getDemandNo());
                }
            }
            if (order.getOrderType() == EnumConstants.OrderType.PRE_SALE.getValue().intValue()) {
                //预售订单
                EntityWrapper<DgProductOrder> ew = new EntityWrapper<>();
                ew.eq("order_id_", order.getId());
                List<DgProductOrder> productOrderList = productOrderMapper.selectList(ew);
                for (DgProductOrder productOrder : productOrderList) {
                    result.setProductName(productOrder.getProductName());
                    result.setProductImage(productOrder.getProductCover());
                    result.setProductNum(productOrder.getProductNum());
                    result.setPrice(productOrder.getProductPrice());
                }
            }
            result.setPayMoney(order.getPayMoney());
            result.setWayBillNo(order.getWaybillNo());
            result.setIsAppraisal(order.getIsAppraisal());
            result.setIsEvaluate(order.getEvaluateStatus() == 0 ? 0 : 1); //“未评价”还是显示“未评价”，“已评价”和“已追评”都是“已评价”
            result.setRemark(order.getBuyerMessage());
            resultList.add(result);
        }
    }

}
