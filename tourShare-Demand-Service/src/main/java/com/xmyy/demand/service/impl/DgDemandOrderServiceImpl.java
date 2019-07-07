package com.xmyy.demand.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.codingapi.tx.annotation.TxTransaction;
import com.xmyy.circle.service.DgCountryService;
import com.xmyy.circle.vo.CountryResult;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.push.PushMessage;
import com.xmyy.common.push.PushUser;
import com.xmyy.common.util.BigDecimalUtil;
import com.xmyy.common.util.BizSequenceUtils;
import com.xmyy.common.util.DateUtils;
import com.xmyy.common.vo.MemberInfo;
import com.xmyy.demand.mapper.DgDemandAcceptMapper;
import com.xmyy.demand.mapper.DgDemandOrderMapper;
import com.xmyy.demand.model.DgDemandAccept;
import com.xmyy.demand.model.DgDemandOrder;
import com.xmyy.demand.service.DgDemandAcceptService;
import com.xmyy.demand.service.DgDemandOrderService;
import com.xmyy.demand.vo.*;
import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.UcBuyerService;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.order.model.DgOrder;
import com.xmyy.order.service.DgOrderService;
import com.xmyy.order.vo.ToPayOrderResult;
import com.xmyy.product.dto.ApiTourSiteDto;
import com.xmyy.product.model.DgTour;
import com.xmyy.product.service.TourService;
import com.xmyy.product.service.TourSiteService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.xmyy.common.EnumConstants.MemberType.buyer;

/**
 * 需求  服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = DgDemandOrderService.class)
@CacheConfig(cacheNames = "DgDemandOrder")
public class DgDemandOrderServiceImpl extends BaseServiceImpl<DgDemandOrder, DgDemandOrderMapper> implements DgDemandOrderService {

    @Resource
    private DgDemandOrderMapper demandOrderMapper;
    @Resource
    private DgDemandAcceptMapper demandAcceptMapper;
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
    @Resource
    private TourSiteService tourSiteService;
    @Resource
    private DgCountryService countryService;


    @Override
    @Transactional
    public Object addToilsome(AddToilsomeParam params, Long memberId) {
        DgDemandOrder orderParam = new DgDemandOrder();
        orderParam.setBuyerId(memberId);
        orderParam.setId(params.getId());
        orderParam.setIsToilsome(EnumConstants.YesOrNo.YES.getValue());
        orderParam.setToilsomePrice(params.getPayPrice());
        update(orderParam);
        return params.getPayPrice();
    }


    @Override
    @Transactional(readOnly = true)
    public Object againRelease(Long id, Long memberId) {
        if (null == id) {
            return "需求ID不能为空";
        }
        DgDemandOrder demand = queryById(id);
        if (null == demand) {
            return "需求不存在";
        }

        return InstanceUtil.to(demand, AgainReleaseResult.class);
    }


    @Override
    @TxTransaction
    @Transactional
    public boolean cancelDemandByTourId(Long tourId) {
        try {
            //取消已接单未指定的接单记录
            EntityWrapper<DgDemandAccept> ew1 = new EntityWrapper<>();
            ew1.eq("tour_id", tourId);
            ew1.eq("budget_status", EnumConstants.DemandAcceptStatus.ACCEPT_WAIT_CONFIRM.getValue());
            List<DgDemandAccept> acceptLists = demandAcceptMapper.selectList(ew1);
            for (DgDemandAccept acc : acceptLists) {
                acc.setBudgetStatus(EnumConstants.DemandAcceptStatus.INVALID.getValue());
                acc.setRemark("行程下架");
                demandAcceptService.update(acc);
            }

            /*
             * 行程下架，下架关联的需求
             * 只有“待发货”的需求需要下架，即指定了发行程的买手，且未发货的需求
             * 原因：
             *  1.未付款(待受理、待指定)的需求只需要取消接单，需求并未指定该买手
             *  2.已发货的需求、订单不允许下架行程
             */
            EntityWrapper<DgDemandOrder> ew = new EntityWrapper<>();
            ew.eq("tour", tourId);
            ew.eq("budget_status", EnumConstants.DemandStatus.WATI_TO_SEND.getValue());
            List<DgDemandOrder> demandList = demandOrderMapper.selectList(ew);
            for (DgDemandOrder demand : demandList) {
                demand.setUpdateTime(new Date());
                demand.setBudgetStatus(EnumConstants.DemandStatus.INVALID.getValue());
                demand.setRemark("行程下架");
                update(demand);
            }

            return true;
        } catch (Exception e) {
            logger.error("下架需求出错，错误信息：{}", e.getMessage());
            return false;
        }
    }


    @Override
    @Transactional
    public void closeDeliveryTimeOutDemand() {
        EntityWrapper<DgDemandOrder> ew = new EntityWrapper<>();
        ew.and("(budget_status = {0} or budget_status = {1})",
                EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue(),
                EnumConstants.DemandStatus.WATI_TO_APPOINT.getValue());
        ew.lt("delivery_time", new Date());
        List<DgDemandOrder> orderList = demandOrderMapper.selectList(ew);
        for (DgDemandOrder demand : orderList) {
            demand.setBudgetStatus(EnumConstants.DemandStatus.INVALID.getValue());
            update(demand);
            // TODO 退款
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<Long> getSellerIds(Long buyerId, Integer current, Integer size) {
        EntityWrapper<DgDemandOrder> ew = new EntityWrapper<>();
        ew.eq("buyer_id", buyerId);
        ew.eq("is_packer", EnumConstants.YesOrNo.NO.getValue());
        ew.in("budget_status", Arrays.asList(
                EnumConstants.DemandStatus.WATI_TO_SEND,
                EnumConstants.DemandStatus.HAS_SENDED,
                EnumConstants.DemandStatus.FINISH));
        RowBounds rb = new RowBounds((current - 1) * size, size);
        List<DgDemandOrder> demandList = demandOrderMapper.selectPage(rb, ew);
        return demandList.stream().map(DgDemandOrder::getSellerId).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public Object createDemand(AddDemandParam params, Long memberId) {
        // 创建需求（待受理）
        DgDemandOrder demand = createDemandOrder(params, memberId);
        if (null == demand) {
            return "发布需求失败";
        }

        //推送消息给买家希望接单的买手
        if (StringUtils.isNotBlank(params.getSellerIds())) {
            List<Long> sellerIdList = InstanceUtil.newArrayList();
            for (String id : params.getSellerIds().split(",")) {
                sellerIdList.add(Long.parseLong(id));
            }
            List<UcSeller> sellers = sellerService.querySellerList(sellerIdList);
            List<PushUser> pushUsers = InstanceUtil.newArrayList();
            if (CollectionUtils.isNotEmpty(sellers)) {
                for (UcSeller s : sellers) {
                    PushUser pushUser = new PushUser(s.getUuid(), s.getLoginSource());
                    pushUsers.add(pushUser);
                }
            }
            Map<String, Object> customMap = InstanceUtil.newHashMap();
            customMap.put("operationType", EnumConstants.PushType.NEW_DEMAND.getValue());
            customMap.put("memberType", EnumConstants.MemberType.seller.getValue());
            customMap.put("id", demand.getId());
            // 批量推送
            PushMessage.getSellerInstance().pushSysMessageToAccountList("发现新需求", "买家发布需求并期望您接单", customMap, null, pushUsers);
        }

        AddDemandResult result = new AddDemandResult();
        result.setId(demand.getId());
        result.setDemandNo(demand.getDemandNo());
        result.setBudgetPrice(demand.getBudgetPrice());
        return result;
    }


    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public Object appoint(DemandToOrderParam params, Long memberId) {
        DgDemandOrder demand = queryById(params.getId());
        if (null == demand) {
            return "需求不存在";
        }
        if (demand.getBudgetStatus() != EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue().intValue() &&
                demand.getBudgetStatus() != EnumConstants.DemandStatus.WATI_TO_APPOINT.getValue().intValue()) {
            return "需求状态错误";
        }

        DgTour tour = tourService.queryById(params.getTourId());
        if (null == tour) {
            return "行程不存在";
        }

        //生成"待支付"订单，需求不变，只有支付成功需求才更新
        demand.setSellerId(params.getSellerId());
        demand.setIsPacker(params.getIsPacker());
        demand.setTour(tour.getId());
        demand.setTourNo(tour.getTourNo());
        Long orderId = createOrderOfDemand(demand);
        if (null == orderId) {
            return "指定买手失败";
        }

        //封装托管代收所需参数
        ToPayOrderResult result = new ToPayOrderResult();
        result.setAmount(BigDecimalUtil.changeY2F(demand.getBudgetPrice()));
        result.setFee(0L);
        result.setPayerId(demand.getBuyerId());

        //收款人列表，单独支付，收款人只有一个
        JSONArray recieverList = new JSONArray();
        JSONObject recieverMap = new JSONObject();
        if (demand.getIsPacker() == EnumConstants.YesOrNo.NO.getValue().intValue()) {
            MemberInfo sellerInfo = sellerService.getMemberInfo(demand.getSellerId(), EnumConstants.MemberType.seller.getValue());
            recieverMap.put("bizUserId", sellerInfo.getUuid());
        } else {
            MemberInfo sellerInfo = sellerService.getMemberInfo(demand.getSellerId(), EnumConstants.MemberType.packer.getValue());
            recieverMap.put("bizUserId", sellerInfo.getUuid());
        }
        recieverMap.put("amount", result.getAmount());
        recieverList.add(recieverMap);
        result.setRecieverList(recieverList);

        ArrayList<Long> orderIdList = new ArrayList<>();
        orderIdList.add(orderId);
        result.setOrderIdList(orderIdList);

        return result;
    }


    private DemandDetailResult setInfo(DgDemandOrder demandOrder) {
        DemandDetailResult result = InstanceUtil.to(demandOrder, DemandDetailResult.class);
        result.setDeliveryTimeText(DateUtils.formatDate(demandOrder.getDeliveryTime(), "yyyy-MM-dd"));
        result.setDemandTimeText(DateUtils.formatDate(demandOrder.getDemandTime(), "yyyy-MM-dd"));
        return result;
    }


    private Pagination<DemandListResult> listToPage(List<DgDemandOrder> demands, Integer role, Integer pageIndex) {
        List<DemandListResult> resultList = InstanceUtil.newArrayList();
        Pagination<DemandListResult> pageResult = new Pagination<>();
        for (DgDemandOrder demand : demands) {
            DemandListResult result = new DemandListResult();
            result.setBudgetPrice(demand.getBudgetPrice());
            result.setBudgetStatus(demand.getBudgetStatus());
            result.setBudgetStatusText(EnumConstants.DemandStatus.getInfo(demand.getBudgetStatus(), role));
            result.setBuyerId(demand.getBuyerId());
            result.setDeliveryTime(demand.getDeliveryTime());
            result.setDemandDesc(demand.getDemandDesc());
            result.setDemandImages(demand.getDemandImages());
            result.setDemandCover(demand.getDemandCover());
            result.setId(demand.getId());
            result.setDeliveryTimeText(DateUtils.formatDate(demand.getDeliveryTime(), "yyyy-MM-dd"));
            resultList.add(result);
        }
        pageResult.setRecords(resultList);
        pageResult.setTotal(resultList.size());
        pageResult.setPages(resultList.size() % pageResult.getSize() == 0 ? resultList.size() / pageResult.getSize() : resultList.size() / pageResult.getSize() + 1);
        return pageResult;
    }


    @Override
    @Transactional
    public Object cancelDemandOrder(CancleDemandParam params, Long memberId) {
        DgDemandOrder demand = queryById(params.getId());
        if (null == demand) {
            return "需求不存在";
        }

        if (demand.getBudgetStatus() == EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue().intValue() ||
                demand.getBudgetStatus() == EnumConstants.DemandStatus.WATI_TO_APPOINT.getValue().intValue()) {
            demand.setBudgetStatus(EnumConstants.DemandStatus.INVALID.getValue());
            demand.setCancelReason(params.getCancelReason());
            demand.setCancelText(params.getCancelText());
            update(demand);

            // 更新接单表,将所有接单状态更新为买家取消
            EntityWrapper<DgDemandAccept> ew = new EntityWrapper<>();
            ew.eq("demand_order_id_", params.getId());
            List<DgDemandAccept> demandAcceptList = demandAcceptMapper.selectList(ew);
            for (DgDemandAccept accept : demandAcceptList) {
                accept.setBudgetStatus(EnumConstants.DemandAcceptStatus.INVALID.getValue());
                accept.setDemandOrderId(params.getId());
                demandAcceptService.update(accept);
            }

            return null;
        }

        return "需求已支付，如果需要取消请选择申请售后";
    }


    @Override
    @Transactional
    public Object deleteDemand(DeleteDemandParam params, Long memberId) {
        DgDemandOrder demand = queryById(params.getId());
        if (demand == null) {
            return "需求不存在";
        }

        if (params.getRole() == EnumConstants.MemberType.buyer.getValue()) {
            demand.setBuyerDel(EnumConstants.BillDeleteStatus.DELETE.getStatus());
        } else {
            demand.setSellerDel(EnumConstants.BillDeleteStatus.DELETE.getStatus());
        }
        update(demand);

        return params.getId();
    }


    @Override
    @Transactional(readOnly = true)
    public DgDemandOrder queryByOrderId(Long orderId) {
        DgDemandOrder param = new DgDemandOrder();
        param.setOrderId(orderId);
        return demandOrderMapper.selectOne(param);
    }


    /**
     * 优先查询已接单，待指定需求，该页数据不足时，再查询已被指定的需求
     */
    @Override
    @Transactional(readOnly = true)
    public Object queryDemandList(Integer role, Long memberId, Integer pageIndex, Integer size) {
        if (role == null) {
            return "角色不能为空";
        }
        if (pageIndex == null || pageIndex < 1) {
            pageIndex = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }

        Pagination<DemandListResult> pageResult = new Pagination<>();
        pageResult.setCurrent(pageIndex);
        pageResult.setSize(size);

        if (role == buyer.getValue()) {
            // 买家端
            EntityWrapper<DgDemandOrder> ew = new EntityWrapper<>();
            ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
            ew.eq("buyer_id", memberId);
            ew.ne("budget_status", EnumConstants.DemandStatus.INVALID.getValue());
            ew.eq("buyer_del", EnumConstants.YesOrNo.NO.getValue());
            ew.orderBy("id_", false);

            int count = demandOrderMapper.selectCount(ew);

            if (count > 0) {
                RowBounds rb = new RowBounds((pageIndex - 1) * pageResult.getSize(), pageResult.getSize());
                List<DgDemandOrder> demands = demandOrderMapper.selectPage(rb, ew);
                pageResult = listToPage(demands, role, pageIndex);
            }
            return pageResult;

        } else if (role == EnumConstants.MemberType.seller.getValue() || role == EnumConstants.MemberType.packer.getValue()) {
            // 买手端、背包客
            EntityWrapper<DgDemandOrder> ewHasOrder = new EntityWrapper<>(); //已接单且被指定需求查询条件
            ewHasOrder.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
            ewHasOrder.eq("seller_id", memberId);
            ewHasOrder.eq("seller_del", EnumConstants.YesOrNo.NO.getValue());
            ewHasOrder.orderBy("id_", false);

            // 查询已接单待指定需求数
            EntityWrapper<DgDemandAccept> ewNoOrder = new EntityWrapper<>(); //已接单待指定需求查询条件
            ewNoOrder.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
            ewNoOrder.eq("seller_id", memberId);
            ewNoOrder.eq("budget_status", EnumConstants.DemandAcceptStatus.ACCEPT_WAIT_CONFIRM.getValue());
            ewNoOrder.orderBy("id_", false);

            if (role == EnumConstants.MemberType.seller.getValue()) {
                ewHasOrder.eq("is_packer", EnumConstants.YesOrNo.NO.getValue());
                ewNoOrder.eq("is_packer", EnumConstants.YesOrNo.NO.getValue());
            } else {
                ewHasOrder.eq("is_packer", EnumConstants.YesOrNo.YES.getValue());
                ewNoOrder.eq("is_packer", EnumConstants.YesOrNo.YES.getValue());
            }
            int accCount = demandAcceptMapper.selectCount(ewNoOrder);

            if (accCount >= pageIndex * pageResult.getSize()) {
                // 全部显示已接单待指定需求
                RowBounds rb = new RowBounds((pageIndex - 1) * pageResult.getSize(), pageResult.getSize());
                List<DgDemandAccept> demandAccepts = demandAcceptMapper.selectPage(rb, ewNoOrder);
                List<Long> demandIds = InstanceUtil.newArrayList();
                for (DgDemandAccept d : demandAccepts) {
                    demandIds.add(d.getDemandOrderId());
                }
                List<DgDemandOrder> demands = demandOrderMapper.selectBatchIds(demandIds);
                pageResult = listToPage(demands, role, pageIndex);
                return pageResult;

            } else if ((pageIndex - 1) * pageResult.getSize() >= accCount) {
                // 全部显示已接单且被指定需求
                RowBounds rb = new RowBounds((pageIndex - 1) * pageResult.getSize() - accCount, pageResult.getSize());
                List<DgDemandOrder> demands = demandOrderMapper.selectPage(rb, ewHasOrder);
                pageResult = listToPage(demands, role, pageIndex);
                return pageResult;

            } else {
                // 同时显示 已接单待指定+已接单且被指 需求
                // 已接单待指定需求
                RowBounds rb = new RowBounds((pageIndex - 1) * pageResult.getSize(), pageResult.getSize());
                List<DgDemandAccept> accepts = demandAcceptMapper.selectPage(rb, ewNoOrder);
                List<Long> demandIds = InstanceUtil.newArrayList();
                for (DgDemandAccept acc : accepts) {
                    demandIds.add(acc.getDemandOrderId());
                }
                List<DgDemandOrder> demandList = demandOrderMapper.selectBatchIds(demandIds);

                // 已接单且被指需求
                RowBounds rbs = new RowBounds(0, pageResult.getSize() - demandList.size());
                demandList.addAll(demandOrderMapper.selectPage(rbs, ewHasOrder));
                pageResult = listToPage(demandList, role, pageIndex);
                return pageResult;
            }

        } else {
            return "参数错误";
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Object listAcceptor(Long id, Long memberId) {
        if (id == null) {
            return "需求单ID不能为空";
        }

        EntityWrapper<DgDemandAccept> ew = new EntityWrapper<>();
        ew.eq("budget_status", EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue());
        ew.eq("demand_order_id_", id);
        List<DgDemandAccept> demandAcceptList = demandAcceptMapper.selectList(ew);

        List<AcceptListResult> results = InstanceUtil.newArrayList();
        for (DgDemandAccept accept : demandAcceptList) {
            AcceptListResult result = new AcceptListResult();

            // 买手、背包客信息
            MemberInfo memberInfo;
            if (accept.getIsPacker() == EnumConstants.YesOrNo.NO.getValue().intValue()) {
                memberInfo = sellerService.getMemberInfo(accept.getSellerId(), EnumConstants.MemberType.seller.getValue());
                result.setIsPacker(EnumConstants.YesOrNo.NO.getValue());
                result.setTourId(accept.getTourId());
            } else {
                memberInfo = sellerService.getMemberInfo(accept.getSellerId(), EnumConstants.MemberType.packer.getValue());
                result.setIsPacker(EnumConstants.YesOrNo.YES.getValue());
                result.setTourId(accept.getTourId());
            }
            result.setId(memberInfo.getId());
            result.setNickName(memberInfo.getNickName());
            result.setAvatarRsurl(memberInfo.getAvatarRsurl());
            result.setOftenPlace(memberInfo.getOftenPlace());
            result.setUuid(memberInfo.getUuid());
            result.setTrustValue(memberInfo.getTrustValue());

            // 行程信息
            DgTour tour = tourService.queryById(accept.getTourId());
            if (null != tour) {
                //查询该行程的站点信息
                List<ApiTourSiteDto> apiTourSiteDtos = tourSiteService.queryTourSiteInfoList(tour.getId());

                List<TourSiteInfoResult> tourSiteLists = InstanceUtil.newArrayList();
                apiTourSiteDtos.stream().map(o -> InstanceUtil.to(o, TourSiteInfoResult.class)).forEach(tourSiteLists::add);

                TourInfoResult tourInfo = InstanceUtil.to(tour, TourInfoResult.class);
                tourInfo.setTourSites(tourSiteLists);
                result.setTourInfo(tourInfo);
            }

            results.add(result);
        }
        return results;
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryCancelDemandList(Long id, Integer current, Integer size) {
        if (id == null) {
            return "用户ID不能为空";
        }
        if (current == null || current < 1) {
            current = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }

        EntityWrapper<DgDemandOrder> ew = new EntityWrapper<>();
        ew.eq("buyer_id", id);
        ew.eq("budget_status", EnumConstants.DemandStatus.INVALID.getValue());
        ew.eq("buyer_del", EnumConstants.BillDeleteStatus.ENABLE.getStatus());
        RowBounds rb = new RowBounds((current - 1) * size, size);
        List<DgDemandOrder> demandList = demandOrderMapper.selectPage(rb, ew);

        List<DemandListResult> resultList = InstanceUtil.newArrayList();
        if (CollectionUtils.isNotEmpty(demandList)) {
            for (DgDemandOrder demand : demandList) {
                DemandListResult result = new DemandListResult();
                result.setId(demand.getId());
                result.setBuyerId(demand.getBuyerId());
                result.setBudgetStatus(demand.getBudgetStatus());
                result.setBudgetStatusText(EnumConstants.DemandStatus.getInfo(demand.getBudgetStatus(), buyer.getValue()));
                result.setDemandDesc(demand.getDemandDesc());
                result.setDemandImages(demand.getDemandImages());
                result.setDemandCover(demand.getDemandCover());
                result.setBudgetPrice(demand.getBudgetPrice());
                result.setDeliveryTime(demand.getDeliveryTime());
                result.setDeliveryTimeText(DateUtils.formatDate(demand.getDeliveryTime(), "yyyy-MM-dd"));
                resultList.add(result);
            }
        }

        Pagination<DemandListResult> pageResult = new Pagination<>();
        pageResult.setRecords(resultList);
        pageResult.setCurrent(current);
        pageResult.setSize(size);
        pageResult.setTotal(resultList.size());
        pageResult.setPages(resultList.size() % size == 0 ? resultList.size() / size : resultList.size() / size + 1);

        return pageResult;
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryDemandDetail(Long id, Integer role, Long memberId) {
        if (null == role) {
            return "角色不能为空";
        }
        if (null == memberId) {
            return "用户ID不能为空";
        }
        if (null == id) {
            return "需求ID不能为空";
        }
        DgDemandOrder demand = queryById(id);
        if (null == demand) {
            return "需求不存在";
        }

        //封装需求信息
        DemandDetailResult result = setInfo(demand);

        //封装买家信息
        MemberInfo buyerInfo = sellerService.getMemberInfo(demand.getBuyerId(), EnumConstants.MemberType.buyer.getValue());
        if (buyerInfo != null) {
            result.setBuyerNickname(buyerInfo.getNickName());
            result.setBuyerImage(buyerInfo.getAvatarRsurl());
            result.setBuyerUUID(buyerInfo.getUuid());
        }

        if (role == buyer.getValue()) { //1-买家的查询
            result.setBudgetStatusText(EnumConstants.DemandStatus.getInfo(demand.getBudgetStatus(), role));
            // 如果买家查询“待指定”需求，返回已接单买手/背包客的头像列表
            if (demand.getBudgetStatus() == EnumConstants.DemandStatus.WATI_TO_APPOINT.getValue().intValue() &&
                    role == buyer.getValue()) {
                //获取该需求所有接单人id列表
                EntityWrapper<DgDemandAccept> ew = new EntityWrapper<>();
                ew.eq("demand_order_id_", demand.getId());
                List<DgDemandAccept> demandAcceptList = demandAcceptMapper.selectList(ew);

                List<Long> packerIds = InstanceUtil.newArrayList();
                List<Long> sellerIds = InstanceUtil.newArrayList();
                for (DgDemandAccept accept : demandAcceptList) {
                    if (accept.getIsPacker() == EnumConstants.YesOrNo.NO.getValue().intValue()) {
                        sellerIds.add(accept.getSellerId());
                    } else {
                        packerIds.add(accept.getSellerId());
                    }
                }

                //根据接单人id，获取头像列表
                List<String> sellerImageList = InstanceUtil.newArrayList();
                if (CollectionUtils.isNotEmpty(sellerIds)) {
                    for (Long sellerId : sellerIds) {
                        MemberInfo sellerInfo = sellerService.getMemberInfo(sellerId, EnumConstants.MemberType.seller.getValue());
                        sellerImageList.add(sellerInfo.getAvatarRsurl());
                    }
                }
                if (CollectionUtils.isNotEmpty(packerIds)) {
                    for (Long packerId : packerIds) {
                        MemberInfo packerInfo = sellerService.getMemberInfo(packerId, EnumConstants.MemberType.packer.getValue());
                        sellerImageList.add(packerInfo.getAvatarRsurl());
                    }
                }
                result.setSellerImage(sellerImageList);
            }

        } else { //2-买手、背包客 的查询
            if (memberId.equals(demand.getSellerId())) {
                // 已形成订单
                result.setBudgetStatusText(EnumConstants.DemandStatus.getInfo(demand.getBudgetStatus(), role));
                if (EnumConstants.YesOrNo.YES.getValue().intValue() == demand.getIsPacker() && buyerInfo != null) {
                    MemberInfo sellerInfo = sellerService.getMemberInfo(demand.getSellerId(), EnumConstants.MemberType.packer.getValue());
                    if (sellerInfo != null) {
                        result.setSellerUUID(sellerInfo.getUuid());
                    }
                } else {
                    MemberInfo sellerInfo = sellerService.getMemberInfo(demand.getSellerId(), EnumConstants.MemberType.seller.getValue());
                    if (sellerInfo != null) {
                        result.setSellerUUID(sellerInfo.getUuid());
                    }
                }
            } else {
                // 待指定需求，判断是否有人接单
                EntityWrapper<DgDemandAccept> ew = new EntityWrapper<>();
                ew.eq("demand_order_id_", demand.getId());
                ew.eq("seller_id", memberId);
                ew.ne("budget_status", EnumConstants.DemandAcceptStatus.INVALID.getValue());
                if (EnumConstants.MemberType.seller.getValue() == role) {
                    ew.eq("is_packer", EnumConstants.YesOrNo.NO.getValue());
                } else {
                    ew.eq("is_packer", EnumConstants.YesOrNo.YES.getValue());
                }
                if (demandAcceptMapper.selectCount(ew) > 0) {
                    result.setBudgetStatusText(EnumConstants.DemandStatus.getInfo(demand.getBudgetStatus(), role));
                } else {
                    result.setBudgetStatus(EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue());
                    result.setBudgetStatusText("待受理");
                }
            }
        }

        return result;
    }


    /**
     * 生成待受理需求
     */
    private DgDemandOrder createDemandOrder(AddDemandParam params, Long memberId) {
        DgDemandOrder demand = new DgDemandOrder();
        demand.setDemandNo(BizSequenceUtils.generateBizNo(EnumConstants.BizCode.DemandNo));
        demand.setBuyerId(memberId);
        demand.setBudgetStatus(EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue());
        demand.setReleaseSellerIds(params.getSellerIds());
        demand.setDemandTime(new Date());
        demand.setBuyCountry(params.getBuyCountry());
        demand.setShortCode(params.getShortCode());
        demand.setDemandDesc(params.getDemandDesc());
        demand.setDemandImages(params.getDemandImages());
        demand.setDemandCover(params.getDemandImages().split(",")[0]);
        demand.setDeliveryTime(params.getDeliveryTime());
        demand.setBudgetPrice(params.getBudgetPrice());
        demand.setProductType(params.getProductType());
        demand.setConsigneeName(params.getConsigneeName());
        demand.setConsigneeAddress(params.getConsigneeAddress());
        demand.setBuyerPhone(params.getBuyerPhone());
        demand.setIsAppraisal(params.getIsAppraisal());
        demand.setAcceptedNumber(0);
        demand.setIsToilsome(0);
        demand.setDemandPosition(params.getReleaseAddress());
        demand.setCreateBy(memberId);
        demand.setCreateTime(new Date());
        demand.setUpdateBy(memberId);
        demand.setUpdateTime(new Date());
        return update(demand);
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryWaitingAcceptList(String tag, Integer pageSize, Integer pageIndex, Long memberId, Integer isPacker) {
        if (null == isPacker) {
            return "参数错误";
        }
        if (EnumConstants.YesOrNo.YES.getValue().intValue() != isPacker && EnumConstants.YesOrNo.NO.getValue().intValue() != isPacker) {
            return "参数错误";
        }
        if (pageIndex == null || pageIndex < 1) {
            pageIndex = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        EntityWrapper<DgDemandOrder> ew = new EntityWrapper<>(); //需求查询条件
        //按照标签查询
        if (StringUtils.isNotBlank(tag)) {
            ew.and("product_type like {0} ", "%" + tag + "%");
        } else {
            // 没有传入标签，则根据个人设置的标签筛选需求
            if (EnumConstants.YesOrNo.YES.getValue().intValue() == isPacker) {
                UcBuyer buyer = buyerService.queryById(memberId);
                String tags = buyer.getClassifyTags();
                if (StringUtils.isNotBlank(tags)) {
                    ew.in("product_type", tags.split(","));
                }
            } else {
                UcSeller seller = sellerService.queryById(memberId);
                String tags = seller.getClassifyTags();
                if (StringUtils.isNotBlank(tags)) {
                    ew.in("product_type", tags.split(","));
                }
            }
        }
        //如果按照标签查询不到需求，那就不用标签过滤
        if (mapper.selectCount(ew) == 0) {
            ew = new EntityWrapper<>();
        }

        //已经接了的需求不显示，查询买手已接单待指定的ID列表
        EntityWrapper<DgDemandAccept> acceptEw = new EntityWrapper<>();
        acceptEw.eq("seller_id", memberId);
        acceptEw.eq("budget_status", EnumConstants.DemandAcceptStatus.ACCEPT_WAIT_CONFIRM.getValue());
        acceptEw.eq("is_packer", isPacker);
        List<DgDemandAccept> acceptList = demandAcceptMapper.selectList(acceptEw);
        if (CollectionUtils.isNotEmpty(acceptList)) {
            ArrayList<Long> idsList = acceptList.stream().map(DgDemandAccept::getDemandOrderId).collect(Collectors.toCollection(ArrayList::new));
            ew.notIn("id_", idsList);
        }

        //背包客情况下需要排除自身发布的需求
        if (EnumConstants.YesOrNo.YES.getValue().intValue() == isPacker) {
            ew.ne("buyer_id", memberId);
        }

        // 只查询未形成订单的需求
        ew.and("(budget_status = {0} or budget_status = {1})",
                EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue(),
                EnumConstants.DemandStatus.WATI_TO_APPOINT.getValue());
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.orderBy("id_", false);

        RowBounds rb = new RowBounds((pageIndex - 1) * pageSize, pageSize);
        List<DgDemandOrder> demandOrderList = demandOrderMapper.selectPage(rb, ew);

        List<DemandListByTagsResult> results = new ArrayList<>();
        insertDemandListResult(demandOrderList, results);

        Pagination<DemandListByTagsResult> pageResult = new Pagination<>();
        pageResult.setCurrent(pageIndex);
        pageResult.setSize(pageSize);
        pageResult.setTotal(results.size());
        pageResult.setRecords(results);
        pageResult.setPages(results.size() % pageSize == 0 ? results.size() / pageSize : results.size() / pageSize + 1);
        return pageResult;
    }


    @Override
    @Transactional(readOnly = true)
    public List<DgDemandOrder> queryByTourId(Long id) {
        EntityWrapper<DgDemandOrder> ew = new EntityWrapper<>();
        ew.eq("tour", id);
        return demandOrderMapper.selectList(ew);
    }


    /**
     * 封装需求列表返回数据
     */
    private void insertDemandListResult(List<DgDemandOrder> demandOrderList, List<DemandListByTagsResult> results) {
        for (DgDemandOrder demand : demandOrderList) {
            DemandListByTagsResult result = new DemandListByTagsResult();
            result.setId(demand.getId());
            result.setBudgetPrice(demand.getBudgetPrice());
            result.setBuyerId(demand.getBuyerId());
            result.setDeliveryTime(demand.getDeliveryTime());
            result.setDemandDesc(demand.getDemandDesc());
            result.setDemandImages(demand.getDemandImages());
            result.setDemandCover(demand.getDemandCover());
            result.setProductType(demand.getProductType());
            result.setDeliveryTimeText(DateUtils.formatDate(demand.getDeliveryTime(), "yyyy-MM-dd"));
            result.setBuyCountry(demand.getBuyCountry());
            MemberInfo memberInfo = sellerService.getMemberInfo(demand.getBuyerId(), EnumConstants.MemberType.buyer.getValue());
            result.setBuyerUUID(memberInfo.getUuid());
            CountryResult country = countryService.getCountryByName(demand.getBuyCountry());
            if (null != country) {
                result.setCountryIcon(country.getLogo());
            }
            results.add(result);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryListNoLogin(Integer pageIndex, Integer pageSize, String tag) {
        if (pageIndex == null || pageIndex < 1) {
            pageIndex = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 1;
        }

        EntityWrapper<DgDemandOrder> ew = new EntityWrapper<>();
        ew.eq("budget_status", EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue());
        if (StringUtils.isNotBlank(tag)) {
            ew.eq("product_type", tag);
        }
        RowBounds rb = new RowBounds((pageIndex - 1) * pageSize, pageSize);
        List<DgDemandOrder> demandList = demandOrderMapper.selectPage(rb, ew);

        List<DemandListByTagsResult> results = new ArrayList<>();
        insertDemandListResult(demandList, results);

        Pagination<DemandListByTagsResult> pageResult = new Pagination<>();
        pageResult.setCurrent(pageIndex);
        pageResult.setSize(pageSize);
        pageResult.setTotal(results.size());
        pageResult.setRecords(results);
        pageResult.setPages(results.size() % pageSize == 0 ? results.size() / pageSize : results.size() / pageSize + 1);
        return pageResult;
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryDetailNoLogin(Long id) {
        if (id == null) {
            return "需求ID不能为空";
        }
        DgDemandOrder demand = queryById(id);
        if (demand == null) {
            return "需求不存在";
        }
        if (demand.getBudgetStatus() != EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue().intValue()) {
            return "请先登陆";
        }

        DemandDetailResult result = setInfo(demand);

        result.setBudgetStatusText("待受理");
        MemberInfo memberInfo = sellerService.getMemberInfo(demand.getBuyerId(), EnumConstants.MemberType.buyer.getValue());
        if (memberInfo != null) {
            result.setBuyerNickname(memberInfo.getNickName());
            result.setBuyerImage(memberInfo.getAvatarRsurl());
        }

        return result;
    }


    @Override
    @Transactional
    public Object payForDemand(PayDemandParam params, Long memberId) {
        DgDemandOrder demand = queryById(params.getId());
        if (null == demand) {
            return "需求不存在";
        }

        demand.setBudgetStatus(EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue());
        update(demand);
        return demand.getId();
    }


    @Override
    @Transactional(readOnly = true)
    public Object searchDemand(String keyword, Integer pageIndex,
                               Long memberId, Integer isPacker,
                               Integer isDesc, String sortType, String tag) {
        if (null == isPacker) {
            return "买手、背包客标识未填";
        }
        if (StringUtils.isBlank(keyword)) {
            return "关键字为空";
        }

        // 查询买手已接单待指定的需求单id
        EntityWrapper<DgDemandAccept> ew1 = new EntityWrapper<>(); //需求接单查询条件
        ew1.eq("seller_id", memberId);
        ew1.eq("budget_status", EnumConstants.DemandAcceptStatus.ACCEPT_WAIT_CONFIRM.getValue());
        ew1.eq("is_packer", isPacker);
        ew1.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        List<DgDemandAccept> acceptList = demandAcceptMapper.selectList(ew1);

        EntityWrapper<DgDemandOrder> ew = new EntityWrapper<>(); //需求查询条件
        ArrayList<Long> idsList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(acceptList)) {
            for (DgDemandAccept accept : acceptList) {
                idsList.add(accept.getDemandOrderId());
            }
            ew.notIn("id_", idsList);
        }

        if (StringUtils.isNotBlank(tag)) {
            ew.eq("product_type", tag);
        }

        ew.and("(demand_desc like {0} or product_type like {1} or " +
                        "buy_country like {2} or demand_no like {3})",
                "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");

        if (EnumConstants.YesOrNo.YES.getValue().intValue() == isPacker) {
            ew.ne("buyer_id", memberId);
        }
        if (StringUtils.isNotBlank(sortType)) {
            if ("time".equals(sortType)) {
                // 时间排序
                if (isDesc != null && isDesc == EnumConstants.YesOrNo.YES.getValue().intValue()) {
                    ew.orderBy("delivery_time", false);
                } else {
                    ew.orderBy("delivery_time", true);
                }
            } else {
                // 价格排序
                if (isDesc != null && isDesc == EnumConstants.YesOrNo.YES.getValue().intValue()) {
                    ew.orderBy("budget_price", false);
                } else {
                    ew.orderBy("budget_price", true);
                }
            }
        }
        // 查询状态为1,2的需求单
        ew.and("(budget_status = {0} or budget_status = {1})",
                EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue(),
                EnumConstants.DemandStatus.WATI_TO_APPOINT.getValue());
        ew.eq("enable_", 1);
        if (pageIndex == null || pageIndex < 1) {
            pageIndex = 1;
        }
        int pageSize = 10;
        int count = demandOrderMapper.selectCount(ew);
        Pagination<DemandListByTagsResult> pageResult = new Pagination<>();
        if (count > 0) {
            RowBounds rb = new RowBounds((pageIndex - 1) * pageSize, pageSize);
            List<DgDemandOrder> orderList = demandOrderMapper.selectPage(rb, ew);

            List<DemandListByTagsResult> results = new ArrayList<>();
            insertDemandListResult(orderList, results);

            pageResult.setCurrent(pageIndex);
            pageResult.setSize(pageSize);
            pageResult.setTotal(results.size());
            pageResult.setRecords(results);
            int pages = results.size() % pageSize == 0 ? results.size() / pageSize : results.size() / pageSize + 1;
            pageResult.setPages(pages);
        }
        return pageResult;
    }


    /**
     * 生成需求"待支付"订单
     */
    private Long createOrderOfDemand(DgDemandOrder demand) {
        DgOrder order = new DgOrder();
        BigDecimal payMoney = demand.getBudgetPrice();
        if (demand.getIsToilsome().intValue() == EnumConstants.YesOrNo.YES.getValue()) {
            payMoney = payMoney.add(demand.getToilsomePrice());
        }
        order.setDemandId(demand.getId());
        order.setProductNameStr(demand.getDemandDesc());
        order.setPayMoney(payMoney); //需求订单实付金额：预算+辛苦费
        order.setIsAppraisal(demand.getIsAppraisal());
        order.setOrderNo(BizSequenceUtils.generateBizNo(EnumConstants.BizCode.OrderNo));
        order.setOrderType(EnumConstants.OrderType.DEMAND_SALE.getValue());
        order.setOrderStatus(EnumConstants.OrderStatus.WATI_TO_PAY.getValue());
        order.setSellerId(demand.getSellerId());
        order.setIsPacker(demand.getIsPacker());
        order.setBuyerId(demand.getBuyerId());
        order.setConsigneeName(demand.getConsigneeName());
        order.setConsigneeAddress(demand.getConsigneeAddress());
        order.setConsigneePhone(demand.getBuyerPhone());
        order.setTourId(demand.getTour());
        order.setTourNo(demand.getTourNo());
        order.setOrderTime(new Date());
        order.setDeliveryTime(demand.getDeliveryTime());
        order.setEvaluateStatus(EnumConstants.EvaluateStatus.NO_EVALUATE.getValue());
        order.setBuyerDel(EnumConstants.BillDeleteStatus.ENABLE.getStatus());
        order.setSellerDel(EnumConstants.BillDeleteStatus.ENABLE.getStatus());
        order.setProductNameStr(demand.getDemandDesc());
        order = orderService.update(order);

        return order.getId();
    }

}
