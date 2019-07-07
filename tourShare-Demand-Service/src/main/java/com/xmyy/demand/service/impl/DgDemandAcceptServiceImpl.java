package com.xmyy.demand.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.codingapi.tx.annotation.TxTransaction;
import com.xmyy.circle.vo.EveryDayRadioParam;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.DateUtils;
import com.xmyy.common.util.ModelMapUtil;
import com.xmyy.common.vo.MemberInfo;
import com.xmyy.demand.mapper.DgDemandAcceptMapper;
import com.xmyy.demand.model.DgDemandAccept;
import com.xmyy.demand.model.DgDemandOrder;
import com.xmyy.demand.service.DgDemandAcceptService;
import com.xmyy.demand.service.DgDemandOrderService;
import com.xmyy.demand.vo.AddAcceptParam;
import com.xmyy.demand.vo.CancelAcceptParam;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.product.dto.ApiTourSiteDto;
import com.xmyy.product.service.TourService;
import com.xmyy.product.service.TourSiteService;
import com.xmyy.product.vo.ApiTourInfoResult;
import com.xmyy.product.vo.ApiTourMatchParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.support.mq.QueueSender;

import javax.annotation.Resource;
import java.util.List;

/**
 * 需求接单  服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = DgDemandAcceptService.class)
@CacheConfig(cacheNames = "DgDemandAccept")
public class DgDemandAcceptServiceImpl extends BaseServiceImpl<DgDemandAccept, DgDemandAcceptMapper> implements DgDemandAcceptService {

    @Resource
    private DgDemandAcceptMapper demandAcceptMapper;
    @Resource
    private DgDemandOrderService demandOrderService;
    @Resource
    private TourService tourService;
    @Resource
    private TourSiteService tourSiteService;
    @Resource
    private UcSellerService sellerService;
    @Resource
    private QueueSender queueSender;


    @Override
    @Transactional
    public Object accept(AddAcceptParam params, Long memberId) {
        DgDemandOrder demand = demandOrderService.queryById(params.getId());
        if (null == demand) {
            return "需求单不存在";
        }
        if (EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue() != demand.getBudgetStatus().intValue() &&
                EnumConstants.DemandStatus.WATI_TO_APPOINT.getValue() != demand.getBudgetStatus().intValue()) {
            return "当前需求不允许接单";
        }
        if (demand.getAcceptedNumber() >= 10) {
            return "接单人数已达上限";
        }

        // 校验是否已经接过此需求
        DgDemandAccept accept = new DgDemandAccept();
        accept.setSellerId(memberId);
        accept.setDemandOrderId(params.getId());
        accept.setBudgetStatus(EnumConstants.DemandAcceptStatus.ACCEPT_WAIT_CONFIRM.getValue());
        accept.setIsPacker(params.getIsPacker());
        DgDemandAccept acc = selectOne(accept);
        if (null != acc) {
            return "请勿重复接单";
        }

        accept.setBudgetStatus(EnumConstants.DemandAcceptStatus.ACCEPT_WAIT_CONFIRM.getValue());
        accept.setIsPacker(params.getIsPacker());
        accept.setBenefitStatus(params.getBenefitStatus());
        accept.setBenefits(params.getPolicyPrice());
        accept.setBenefitsLogId(params.getBenefitsLogId());
        accept.setBenefitsName(params.getBenefitsName());
        accept.setUpdateBy(memberId);
        accept.setTourId(params.getTourId());

        // 行程站点信息
        List<ApiTourSiteDto> apiTourSiteDtos = tourSiteService.queryTourSiteInfoList(params.getTourId());
        StringBuilder sb = new StringBuilder();
        for (ApiTourSiteDto tourSiteDto : apiTourSiteDtos) {
            sb.append(DateUtils.formatDate(tourSiteDto.getPlanBeginTime(), "yyyy-MM-dd"))
                    .append(" ")
                    .append(tourSiteDto.getRegionName())
                    .append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        accept.setTourList(sb.toString());

        // 买手、背包客信息
        if (params.getIsPacker() == EnumConstants.YesOrNo.NO.getValue().intValue()) {
            MemberInfo sellerInfo = sellerService.getMemberInfo(memberId, EnumConstants.MemberType.seller.getValue());
            accept.setOftenTour(sellerInfo.getOftenPlace());
            accept.setTrustDgree(sellerInfo.getTrustValue());
        } else {
            MemberInfo packerInfo = sellerService.getMemberInfo(memberId, EnumConstants.MemberType.seller.getValue());
            accept.setOftenTour(packerInfo.getOftenPlace());
            accept.setTrustDgree(packerInfo.getTrustValue());
        }
        update(accept);

        //更新需求状态为待指定，接单人数+1
        demand.setBudgetStatus(EnumConstants.DemandStatus.WATI_TO_APPOINT.getValue());
        demand.setAcceptedNumber(demand.getAcceptedNumber() + 1);
        demandOrderService.update(demand);

        //通知买手接单成功
        EveryDayRadioParam everyDayRadioParam = new EveryDayRadioParam();
        everyDayRadioParam.setMemberId(demand.getBuyerId());
        everyDayRadioParam.setToMemberId(memberId);
        everyDayRadioParam.setType(EnumConstants.EveryDayRadioType.SELLER_ACCEPT.getValue());
        if (EnumConstants.YesOrNo.NO.getValue() == params.getIsPacker().intValue()) {
            everyDayRadioParam.setToMemberType(EnumConstants.MemberType.seller.getValue());
        } else {
            everyDayRadioParam.setToMemberType(EnumConstants.MemberType.packer.getValue());
        }
        queueSender.send("circle.setEveryDayRadio.queue", everyDayRadioParam);

        return demand.getId();
    }


    @Override
    @Transactional
    public Object cancelAccept(CancelAcceptParam params, Long memberId) {
        DgDemandOrder demand = demandOrderService.queryById(params.getId());
        if (null == demand) {
            return "需求不存在";
        }

        DgDemandAccept pAccept = new DgDemandAccept();
        pAccept.setDemandOrderId(params.getId());
        pAccept.setIsPacker(params.getIsPacker());
        pAccept.setSellerId(memberId);
        pAccept.setBudgetStatus(EnumConstants.DemandAcceptStatus.ACCEPT_WAIT_CONFIRM.getValue());
        DgDemandAccept demandAccept = selectOne(pAccept);
        if (null == demandAccept) {
            return "您并没有接单";
        }
        //只有“待指定”需求，即未付款时可以取消
        if (demandAccept.getBudgetStatus() != EnumConstants.DemandAcceptStatus.ACCEPT_WAIT_CONFIRM.getValue().intValue()) {
            return "买家已指定您并且已付款，不能取消";
        }

        demandAccept.setBudgetStatus(EnumConstants.DemandAcceptStatus.INVALID.getValue());
        demandAccept.setCancelReason(params.getCancelReason());
        demandAccept.setCancelText(params.getCancelText());
        update(demandAccept);

        // 需求接单人数-1，如果接单人数为0，将需求状态设置为待受理
        demand.setAcceptedNumber(demand.getAcceptedNumber() - 1);
        if (demand.getAcceptedNumber() == 0) {
            demand.setBudgetStatus(EnumConstants.DemandStatus.WATI_TO_ACCEPT.getValue());
        }
        demandOrderService.update(demand);

        return demand.getId();
    }


    @Override
    @Transactional
    @TxTransaction
    public Object paySuccess(Long demandId, Long sellerId) {
        EntityWrapper<DgDemandAccept> ew = new EntityWrapper<>();
        ew.eq("demand_order_id_", demandId);
        List<DgDemandAccept> demandAcceptList = demandAcceptMapper.selectList(ew);
        for (DgDemandAccept demandAccept : demandAcceptList) {
            if (demandAccept.getSellerId() == sellerId.longValue()) {
                //成功接单者，状态更新为“被指定”
                demandAccept.setBudgetStatus(EnumConstants.DemandAcceptStatus.ACCEPT_SUCCESS_CONFIRM.getValue());
            } else {
                //未被指定的接单更新为“未被指定”
                demandAccept.setBudgetStatus(EnumConstants.DemandAcceptStatus.ACCEPT_FAILURE_CONFIRM.getValue());
            }
            update(demandAccept);
        }

        return null;
    }


    /**
     * 1、获取需求单行程和收货时间
     * 2、获取买手/背包客行程
     * 3、判断是否满足添加
     **/
    @Override
    @Transactional(readOnly = true)
    public Object canAccept(Long id, Integer isPacker, Long memberId) {
        if (null == memberId) {
            return "用户未登录";
        }
        if (null == id) {
            return "需求ID不能为空";
        }

        DgDemandOrder demand = demandOrderService.queryById(id);
        if (null == demand) {
            return "需求单不存在";
        }

        ApiTourMatchParam params = new ApiTourMatchParam();
        params.setMatchMaxTime(demand.getDeliveryTime());
        params.setMatchRegionCode(demand.getShortCode());
        //接单人的信息
        params.setCreateBy(memberId);
        params.setIsPacker(isPacker);
        Pagination<ApiTourInfoResult> apiTourDtoPagination = tourService.getMatchTourList(params);
        if (CollectionUtils.isEmpty(apiTourDtoPagination.getRecords())) {
            return ModelMapUtil.setModelMap("660", "没有找到匹配的行程");
        }
        return apiTourDtoPagination.getRecords();
    }

}
