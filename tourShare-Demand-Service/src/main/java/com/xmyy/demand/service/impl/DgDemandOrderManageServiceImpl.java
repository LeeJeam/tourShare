package com.xmyy.demand.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmyy.common.EnumConstants;
import com.xmyy.demand.dao.DgDemandOrderDao;
import com.xmyy.demand.mapper.DgDemandOrderMapper;
import com.xmyy.demand.model.DgDemandAccept;
import com.xmyy.demand.model.DgDemandOrder;
import com.xmyy.demand.service.DgDemandAcceptService;
import com.xmyy.demand.service.DgDemandOrderManageService;
import com.xmyy.demand.vo.*;
import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.UcBuyerService;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.order.model.DgOrder;
import com.xmyy.order.service.DgOrderService;
import com.xmyy.product.model.DgTour;
import com.xmyy.product.service.TourService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.xmyy.common.EnumConstants.MemberType.buyer;

/**
 * 需求后台管理  服务实现类
 *
 * @author wangmd
 */
@Service(interfaceClass = DgDemandOrderManageService.class)
@CacheConfig(cacheNames = "DgDemandOrderManage")
public class DgDemandOrderManageServiceImpl extends BaseServiceImpl<DgDemandOrder, DgDemandOrderMapper> implements DgDemandOrderManageService {

    @Resource
    private DgDemandOrderMapper demandOrderMapper;
    @Resource
    private DgDemandOrderDao demandOrderDao;
    @Resource
    private DgDemandAcceptService demandAcceptService;
    @Resource
    private UcSellerService sellerService;
    @Resource
    private UcBuyerService buyerService;
    @Resource
    private DgOrderService orderService;
    @Resource
    private TourService tourService;


    @Override
    @Transactional(readOnly = true)
    public Object getDetailByBack(Long id) {
        /*
         * 1、获取需求单详情
         * 2、获取订单信息
         * 3、获取买手或者背包客信息
         * 4、获取需求单状态
         * 5、获取行程信息
         *
         **/
        DgDemandOrder demand = queryById(id);
        if (null == demand) {
            return "需求单不存在";
        }
        DemBackDetailResult result = new DemBackDetailResult();
        result.setId(demand.getId());
        //  需求描述
        result.setDemandBrand(demand.getProductType());
        result.setDemandDesc(demand.getDemandDesc());
        result.setDemandImages(demand.getDemandImages());
        result.setBudgetPrice(demand.getBudgetPrice());
        result.setDemandCreateTime(demand.getCreateTime());
        // 订单信息
        DgOrder dgOrder = orderService.queryById(demand.getOrderId());
        result.setDemandNo(demand.getDemandNo());
        if (null != dgOrder) {
            result.setOrderCreateTime(dgOrder.getCreateTime());
            result.setDeliveryTime(demand.getDeliveryTime());
            result.setOrderNo(dgOrder.getOrderNo());
            result.setOrderId(dgOrder.getId());
            // 查询受理人信息
            if (dgOrder.getIsPacker() == EnumConstants.YesOrNo.YES.getValue().intValue()) {
                UcBuyer buyer = buyerService.queryById(dgOrder.getSellerId());
                if (null != buyer) {
                    result.setAcceptorName(buyer.getNickName());
                    result.setAcceptorId(buyer.getId());
                }

            } else {
                UcSeller seller = sellerService.queryById(dgOrder.getSellerId());
                if (null != seller) {
                    result.setAcceptorName(seller.getNickName());
                    result.setAcceptorId(seller.getId());
                }
            }

            // 受理时间，确认时间,获取demand_accept表
            if (demand.getBudgetStatus() != EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue().intValue() &&
                    demand.getBudgetStatus() != EnumConstants.DemandStatus.WATI_TO_APPOINT.getValue().intValue()) {
                HashMap<String, Object> acceptMap = InstanceUtil.newHashMap();
                acceptMap.put("demandOrderId", demand.getId());
                acceptMap.put("sellerId", demand.getSellerId());
                acceptMap.put("budgetStatus", EnumConstants.DemandAcceptStatus.ACCEPT_SUCCESS_CONFIRM.getValue());
                List<DgDemandAccept> dgDemandAcceptList = demandAcceptService.queryList(acceptMap);
                if (CollectionUtils.isNotEmpty(dgDemandAcceptList)) {
                    result.setAcceptTime(dgDemandAcceptList.get(0).getCreateTime());
                    result.setConfirmTime(dgDemandAcceptList.get(0).getUpdateTime());
                }
            }


        }
        // TODO 查询支付日志表获取支付时间、方式
        result.setPayTime(new Date());
        result.setPayType("支付宝");

        // TODO 查询订单操作日志表，获取状态记录
        List<HashMap<String, Object>> statusLists = InstanceUtil.newArrayList();
        HashMap<String, Object> statusMap1 = InstanceUtil.newHashMap();
        HashMap<String, Object> statusMap2 = InstanceUtil.newHashMap();
        HashMap<String, Object> statusMap3 = InstanceUtil.newHashMap();

        statusMap1.put("time", "2018-06-08 10:35:30");
        statusMap1.put("context", "需求单创建");
        statusLists.add(statusMap1);
        statusMap2.put("time", "2018-06-08 12:52:30");
        statusMap2.put("context", "买手接单");
        statusLists.add(statusMap2);
        statusMap3.put("time", "2018-06-08 18:19:20");
        statusMap3.put("context", "指定买手创建订单");
        statusLists.add(statusMap3);
        result.setDemandHistoryLists(statusLists);
        // 获取行程信息
        /*result.setTourNo("T11111111");
        result.setBoardCard("ptthkkxxxx.jsp");
        r
        result.setTourStartTime(new Date());
        result.setTourEndTime(new Date());*/
        result.setTourVerifier("茫茫");
        result.setTourVeriTime(new Date());
        DgTour dgTour = tourService.queryById(demand.getTour());
        if (null != dgTour) {
            result.setTourNo(dgTour.getTourNo());
            result.setBoardCard(dgTour.getCheckPic());
            result.setTourVeriTime(dgTour.getAuditTime());
            //　TODO 待获取后台审核人姓名
            /*if(dgTour.getIsPacker() == 0){
                UcSeller seller = sellerService.queryById(dgTour.getCreateBy());
                if(null == seller){
                    result.setTourVerifier(seller.);
                }
            }*/
            result.setTourStartTime(dgTour.getFirstTime());
            result.setTourEndTime(dgTour.getReturnTime());
        }
        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public Object getStatisticByTag() {
        return demandOrderDao.getStatisticByTag();
    }


    @Override
    @Transactional(readOnly = true)
    public Object listLibrary(DemandLibListManageParam params) {
        EntityWrapper<DgDemandOrder> ew = new EntityWrapper<>();
        if (StringUtils.isNotBlank(params.getDemandDesc())) {
            ew.and("demand_desc like {0} ", "%" + params.getDemandDesc() + "%");
        }
        if (StringUtils.isNotBlank(params.getTag())) {
            ew.eq("product_type", params.getTag());
        }
        ew.orderDesc(Lists.newArrayList("create_time"));

        int count = demandOrderMapper.selectCount(ew);
        List<DemandLibListManageResult> resultList = InstanceUtil.newArrayList();
        if (count > 0) {
            RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());
            List<DgDemandOrder> dOrderList = demandOrderMapper.selectPage(rb, ew);
            for (DgDemandOrder demand : dOrderList) {
                DemandLibListManageResult result = new DemandLibListManageResult();
                result.setId(demand.getId());
                result.setDemandNo(demand.getDemandNo());
                result.setBudgetPrice(demand.getBudgetPrice());
                result.setBuyCountry(demand.getBuyCountry());
                UcBuyer buyer = buyerService.queryById(demand.getBuyerId());
                if (null != buyer) {
                    result.setBuyerName(buyer.getNickName());
                }
                resultList.add(result);
            }
        }

        Pagination<DemandLibListManageResult> pageResult = new Pagination<>();
        pageResult.setCurrent(params.getCurrent());
        pageResult.setSize(params.getSize());
        pageResult.setTotal(count);
        pageResult.setRecords(resultList);
        pageResult.setPages(count % params.getSize() == 0 ? count / params.getSize() : count / params.getSize() + 1);
        return pageResult;
    }


    @Override
    @Transactional(readOnly = true)
    public Object listByPage(DemandListManageParam params) {
        EntityWrapper<DgDemandOrder> ew = new EntityWrapper<>();
        if (StringUtils.isNotBlank(params.getIsAppraisal())) {
            ew.eq("is_appraisal", params.getIsAppraisal());
        }
        if (StringUtils.isNotBlank(params.getDemandStatus())) {
            ew.eq("budget_status", params.getDemandStatus());
        }
        if (StringUtils.isNotBlank(params.getDemandNo())) {
            ew.eq("demand_no", params.getDemandNo());
        }
        if (StringUtils.isNotBlank(params.getDemandDesc())) {
            ew.and("demand_desc like {0} ", "%" + params.getDemandDesc() + "%");
        }
        if (params.getBeginTime() != null) {
            ew.and("delivery_time > {0}", params.getBeginTime());
        }
        if (params.getEndTime() != null) {
            ew.and("delivery_time < {0}", params.getEndTime());
        }
        ew.eq("buyer_id", params.getBuyerId());
        ew.orderDesc(Lists.newArrayList("create_time"));

        int count = demandOrderMapper.selectCount(ew);
        List<DemandListManageResult> resultList = InstanceUtil.newArrayList();
        if (count > 0) {
            RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());
            List<DgDemandOrder> demandList = demandOrderMapper.selectPage(rb, ew);
            if (CollectionUtils.isNotEmpty(demandList)) {
                resultList = insertDemandList(demandList);
            }
        }

        Pagination<DemandListManageResult> pageResult = new Pagination<>();
        pageResult.setCurrent(params.getCurrent());
        pageResult.setSize(params.getSize());
        pageResult.setTotal(count);
        pageResult.setRecords(resultList);
        pageResult.setPages(count % params.getSize() == 0 ? count / params.getSize() : count / params.getSize() + 1);
        return pageResult;
    }


    private List<DemandListManageResult> insertDemandList(List<DgDemandOrder> list) {
        List<DemandListManageResult> resultList = InstanceUtil.newArrayList();
        for (DgDemandOrder order : list) {
            DemandListManageResult result = new DemandListManageResult();
            result.setId(order.getId());
            result.setCreateTime(order.getCreateTime());
            result.setDemandImages(order.getDemandImages());
            result.setDemandDesc(order.getDemandDesc());
            result.setBudgetPrice(order.getBudgetPrice());
            result.setDeliveryTime(order.getDeliveryTime());
            result.setDemandStatus(order.getBudgetStatus());
            result.setDemandStatusText(EnumConstants.DemandStatus.getInfo(order.getBudgetStatus(), buyer.getValue()));
            result.setDemandNo(order.getDemandNo());
            result.setTourNo(order.getTourNo());
            result.setIsAppraisal(order.getIsAppraisal());
            if (order.getBudgetStatus() == EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue().intValue() ||
                    order.getBudgetStatus() == EnumConstants.DemandStatus.WATI_TO_APPOINT.getValue().intValue()) {
                result.setSecendConfirm("否");
            } else {
                result.setSecendConfirm("是");
            }
            resultList.add(result);
        }
        return resultList;
    }
}
