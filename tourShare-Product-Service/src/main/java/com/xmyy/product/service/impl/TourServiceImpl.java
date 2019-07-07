package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.codingapi.tx.annotation.TxTransaction;
import com.xmyy.circle.service.DgCountryService;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.push.PushMessage;
import com.xmyy.common.push.PushUser;
import com.xmyy.common.util.BizSequenceUtils;
import com.xmyy.common.util.DateUtils;
import com.xmyy.common.vo.MemberInfo;
import com.xmyy.demand.service.DgDemandOrderService;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.order.service.DgOrderService;
import com.xmyy.product.dao.DgTourDao;
import com.xmyy.product.dto.ApiTourImgDto;
import com.xmyy.product.dto.UserAuthInfoDto;
import com.xmyy.product.mapper.DgTourMapper;
import com.xmyy.product.model.DgTour;
import com.xmyy.product.model.DgTourSite;
import com.xmyy.product.service.PtProductService;
import com.xmyy.product.service.TourCardService;
import com.xmyy.product.service.TourService;
import com.xmyy.product.service.TourSiteService;
import com.xmyy.product.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.support.mq.QueueSender;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 行程  服务实现类
 *
 * @author wangzejun
 */
@Service(interfaceClass = TourService.class)
public class TourServiceImpl extends BaseServiceImpl<DgTour, DgTourMapper> implements TourService {

    @Resource
    private DgTourMapper tourMapper;
    @Resource
    private DgTourDao dgTourDao;
    @Reference
    private TourSiteService tourSiteService;
    @Reference
    private PtProductService ptProductService;
    @Reference
    private DgDemandOrderService dgDemandOrderService;
    @Reference
    private DgOrderService dgOrderService;
    @Reference
    private UcSellerService ucSellerService;
    @Reference
    private TourCardService tourCardService;
    @Reference
    private DgCountryService dgCountryService;
    @Resource
    private QueueSender queueSender;

    @Value("${country.customeCountryCodes}")
    private String customCountryCodes;

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public Object add(ApiTourAddParam param, Long memberId) {
        //1.实名认证校验
        MemberInfo memberInfo;
        if (param.getIsPacker() == EnumConstants.YesOrNo.YES.getValue().intValue()) {
            memberInfo = ucSellerService.getMemberInfo(memberId, EnumConstants.MemberType.packer.getValue());
        } else {
            memberInfo = ucSellerService.getMemberInfo(memberId, EnumConstants.MemberType.seller.getValue());
        }

        if (memberInfo.getRealState() == null ||
                memberInfo.getRealState() != EnumConstants.identityAuthStatus.PASS.getValue().intValue()) {
            return "您还未通过认证，请前进行实名认证";
        }

        //2.检查行程的出发时间和返程时间
        Date startTime = DateUtils.getDateStart(param.getMiddleSites().get(0).getPlanBeginTime());
        Date returnTime = DateUtils.getDateStart(param.getReturnSite().getPlanBeginTime());
        if (DateUtils.compareDate(new Date(), startTime) == 1) {
            return "出发时间不能小于今天";
        }

        //查询“未进行”和“进行中”的行程，新的行程不能与这些行程冲突
        EntityWrapper<DgTour> ew = new EntityWrapper<>();
        ew.eq("create_by", memberId);
        ew.eq("is_packer", param.getIsPacker());
        ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        ew.and("(status={0} OR status={1})", EnumConstants.TourStatus.WAITING.getValue(), EnumConstants.TourStatus.RUNNING.getValue());
        List<DgTour> tourList = tourMapper.selectList(ew);
        for (DgTour tour : tourList) {
            /*
             * 行程时间冲突的情况有2种：
             * 1.新行程与原有行程相交
             * 2.新行程与原有行程一方包含另一方
             *
             * 判断不冲突的标准为：
             * 1.新行程返程时间小于等于现有行程的出发时间
             * 2.新行程的出发时间大于等于现有行程的返程时间
             */
            if (DateUtils.compareDate(returnTime, tour.getFirstTime()) <= 0) {
                continue;
            } else if (DateUtils.compareDate(startTime, tour.getReturnTime()) >= 0) {
                continue;
            } else {
                return String.format(
                        "新的行程与现有行程有冲突，冲突的行程时间为【%s 至 %s】",
                        DateUtils.formatDate(tour.getFirstTime()),
                        DateUtils.formatDate(tour.getReturnTime()));
            }
        }

        /*
         * 3.检查行程站点信息：
         *      1）相邻站点不能相同
         *      2）站点时间必需递增
         */
        List<ApiTourAddParam.TourSiteDto> tourSitesParam = new ArrayList<>();
        tourSitesParam.add(param.getStartSite());
        tourSitesParam.addAll(param.getMiddleSites());
        tourSitesParam.add(param.getReturnSite());
        ArrayList<DgTourSite> tourSites = tourSitesParam.stream()
                .map(o -> InstanceUtil.to(o, DgTourSite.class))
                .collect(Collectors.toCollection(ArrayList::new));

        for (int i = 1; i < tourSites.size(); i++) {
            DgTourSite frontTourSite = tourSites.get(i - 1);
            DgTourSite tourSite = tourSites.get(i);
            if (frontTourSite.getRegionName().equals(tourSite.getRegionName())) {
                return "相邻站点不能相同，请认真规划您的出行路线";
            }

            if (DateUtils.compareDate(frontTourSite.getPlanBeginTime(), tourSite.getPlanBeginTime()) == 1) {
                return String.format(
                        "站点【%s】的出发时间不能小于站点【%s】的出发时间",
                        tourSite.getRegionName(),
                        frontTourSite.getRegionName());
            }
        }

        //4.保存行程与行程站点信息
        DgTour tour = insertTour(tourSites, memberInfo, param.getTags(), param.getIsPacker(), startTime, returnTime);
        insertTourSites(memberId, tour, tourSites);

        return tour;
    }


    /**
     * 组装行程信息，保存行程
     */
    private DgTour insertTour(ArrayList<DgTourSite> tourSites, MemberInfo memberInfo, String tags, Integer isPacker, Date firstTime, Date returnTime) {
        DgTour tour = new DgTour();
        tour.setTourNo(BizSequenceUtils.generateBizNo(EnumConstants.BizCode.TourNo));
        tour.setTags(tags);
        tour.setFirstTime(firstTime);
        tour.setReturnTime(returnTime);

        //tourSites为所有站点信息，包括出发站和返程站
        if (tourSites.size() > 3) { //行程有多个中途站
            //首站为第一个中途站点
            tour.setFirstRegionCode(tourSites.get(1).getRegionCode());
            tour.setFirstRegionName(tourSites.get(1).getRegionName());
            //目的站为最后一个中途站点
            tour.setDestRegionCode(tourSites.get(tourSites.size() - 2).getRegionCode());
            tour.setDestRegionName(tourSites.get(tourSites.size() - 2).getRegionName());
            tour.setSiteCount(tourSites.size() - 2);
        } else { //行程只有一个中途站
            tour.setDestRegionCode(tourSites.get(1).getRegionCode());
            tour.setDestRegionName(tourSites.get(1).getRegionName());
            tour.setSiteCount(1);
        }

        tour.setCreateBy(memberInfo.getId());
        tour.setUpdateBy(memberInfo.getId());
        tour.setIsSelf(memberInfo.getIsSelf());
        tour.setIsPacker(isPacker);
        tour.setRealName(memberInfo.getRealName());
        tour.setMobile(memberInfo.getMobile());
        return update(tour);
    }


    /**
     * 组装行程站点信息，保存行程站点
     */
    private void insertTourSites(Long memberId, DgTour tour, List<DgTourSite> tourSites) {
        //查询行程站点国旗
        List<String> countryList = new ArrayList<>();
        tourSites.forEach(t -> countryList.add(t.getRegionCode()));
        Map<String, String> nationFlags = dgCountryService.getNationFlagsByShortCodes(countryList);

        int index = 0;
        for (DgTourSite tourSite : tourSites) {
            tourSite.setId(null);
            tourSite.setTourId(tour.getId());
            tourSite.setIsPacker(tour.getIsPacker());
            tourSite.setRegionLogo(nationFlags.get(tourSite.getRegionCode()));
            tourSite.setUpdateBy(memberId);
            tourSite.setCreateBy(memberId);
            tourSite.setSort(index++);
        }
        tourSiteService.updateBatch(tourSites);
    }


    @Override
    @Transactional
    public Object delete(Long id, Long memberId) {
        DgTour dgTour = super.queryById(id);
        if (dgTour == null) {
            return "行程不存在";
        }
        if (dgTour.getStatus() != EnumConstants.TourStatus.INVALID.getValue().intValue()) {
            return "只能删除下架行程";
        }
        super.del(id, memberId);
        tourSiteService.deleteTourSitesByTourId(id);
        return id;
    }


    @Override
    @Transactional(readOnly = true)
    public Pagination<ApiTourInfoResult> listForSeller(Long createBy, Integer isPacker) {
        //“我的行程”由最新行程与历史行程组成
        List<ApiTourInfoResult> records = new ArrayList<>();

        List<ApiTourInfoResult> recentList = dgTourDao.listRecentForSeller(createBy, isPacker);
        if (recentList != null && recentList.size() > 0) {
            records.addAll(recentList.stream()
                    .peek(o -> o.setIsHistory(EnumConstants.YesOrNo.NO.getValue()))
                    .collect(Collectors.toCollection(ArrayList::new)));
        }

        List<ApiTourInfoResult> historyList = dgTourDao.listHistoryForSeller(createBy, isPacker);
        if (historyList != null && historyList.size() > 0) {
            records.addAll(historyList.stream()
                    .peek(o -> o.setIsHistory(EnumConstants.YesOrNo.YES.getValue()))
                    .collect(Collectors.toCollection(ArrayList::new)));
        }

        Pagination<ApiTourInfoResult> page = new Pagination<>();
        page.setRecords(records);
        return page;
    }


    @Override
    @Transactional(readOnly = true)
    public Pagination<ApiTourInBuyerIndexResult> listPageForBuyer(ApiTourListForBuyerParam params) {
        formatTourListForBuyerParam(params);

        List<ApiTourImgDto> apiTourImgDtos;
        int totalCount;
        if (params.getQueryType() == EnumConstants.YesOrNo.NO.getValue()) {
            apiTourImgDtos = dgTourDao.listPageForBuyer1(params);
            totalCount = (dgTourDao.countTourForBuyer1(params)).intValue();
        } else {
            apiTourImgDtos = dgTourDao.listPageForBuyer2(params);
            totalCount = (dgTourDao.countTourForBuyer2(params)).intValue();
        }

        List<ApiTourInBuyerIndexResult> tourList = new ArrayList<>();
        ApiTourCardMatchParam cardParams = new ApiTourCardMatchParam();
        cardParams.setCardSize(params.getCardSize());
        cardParams.setCardType(params.getCardType());
        cardParams.setDeviceType(params.getDeviceType());
        cardParams.setSkinType(params.getSkinType());

        if (apiTourImgDtos != null && apiTourImgDtos.size() > 0) {
            List<String> curRegionCodes = InstanceUtil.newArrayList("CN");
            apiTourImgDtos.forEach(t -> {
                if (StringUtils.isNotBlank(t.getCurRegionCode())) {
                    curRegionCodes.add(t.getCurRegionCode());
                }
            });
            cardParams.setCardCodes(curRegionCodes);
            Map<String, String> cardMap = tourCardService.getTourCardsMapList(cardParams);
            for (ApiTourImgDto apiTourImgDto : apiTourImgDtos) {
                ApiTourInBuyerIndexResult tourResult = InstanceUtil.to(apiTourImgDto, ApiTourInBuyerIndexResult.class);

                //如果当前为空，就默认显示中国
                if (StringUtils.isBlank(apiTourImgDto.getCurRegionCode())) {
                    tourResult.setCurImgUrl(cardMap.get("CN"));
                } else {
                    tourResult.setCurImgUrl(cardMap.get(apiTourImgDto.getCurRegionCode()));
                }
                UserAuthInfoDto userAuthInfo = getUserAuthInfo(tourResult.getCreateBy(), tourResult.getIsPacker());
                if (userAuthInfo != null) {
                    tourResult.setUserAuthInfo(userAuthInfo);
                }
                tourList.add(tourResult);
            }
        }

        Pagination<ApiTourInBuyerIndexResult> pageResult = new Pagination<>();
        if (totalCount > 0) {
            int pageSize = params.getSize();
            pageResult.setPages(totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize + 1));
        }
        pageResult.setCurrent(params.getCurrent());
        pageResult.setTotal(totalCount);
        pageResult.setRecords(tourList);
        return pageResult;
    }


    /**
     * 把前端传过来的参数标准化
     */
    private void formatTourListForBuyerParam(ApiTourListForBuyerParam params) {
        if (StringUtils.isNotBlank(params.getTags())) {
            params.setTagsGroup(CollectionUtils.arrayToList(params.getTags().split(",")));
        }

        if (StringUtils.isNotBlank(params.getContinentCode())) {
            List<String> shortCodeList = dgCountryService.getCountryShortCodesByZhouCode(params.getContinentCode());
            params.setDestRegionCodes(shortCodeList);
        }

        //格式化 是不是自营买手，是不是背包客
        if (params.getSellerType() != null && params.getSellerType() != 0) {
            switch (params.getSellerType()) {
                //自营买手
                case 1:
                    params.setIsPacker(EnumConstants.YesOrNo.NO.getValue());
                    params.setIsSelf(EnumConstants.YesOrNo.YES.getValue());
                    break;
                //大众买手
                case 2:
                    params.setIsPacker(EnumConstants.YesOrNo.NO.getValue());
                    params.setIsSelf(EnumConstants.YesOrNo.NO.getValue());
                    break;
                //背包客
                case 3:
                    params.setIsPacker(EnumConstants.YesOrNo.YES.getValue());
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Pagination<ApiTourInBuyerIndexResult> listIndexForBuyer(ApiTourInBuyerIndexParam params) {
        if (StringUtils.isNotBlank(params.getTags())) {
            params.setTagsGroup(CollectionUtils.arrayToList(params.getTags().split(",")));
        }

        //行程、站点信息
        List<ApiTourImgDto> apiTourImgDtos = dgTourDao.listIndexForBuyer(params);
        List<ApiTourInBuyerIndexResult> tourList = new ArrayList<>();
        if (apiTourImgDtos != null && apiTourImgDtos.size() > 0) {
            for (ApiTourImgDto apiTourImgDto : apiTourImgDtos) {
                tourList.add(InstanceUtil.to(apiTourImgDto, ApiTourInBuyerIndexResult.class));
            }
        }

        //行程卡片信息
        ApiTourCardMatchParam cardParams = new ApiTourCardMatchParam();
        cardParams.setCardType(params.getCardType());
        cardParams.setCardType(params.getCardType());
        cardParams.setDeviceType(params.getDeviceType());
        cardParams.setSkinType(params.getSkinType());
        List<String> curRegionCodes = new ArrayList<>();
        tourList.forEach(t -> {
            if (StringUtils.isNotBlank(t.getCurRegionName())) {
                curRegionCodes.add(t.getCurRegionCode());
            }
        });
        curRegionCodes.add("CN");
        cardParams.setCardCodes(curRegionCodes);
        Map<String, String> cardMap = tourCardService.getTourCardsMapList(cardParams);

        for (ApiTourInBuyerIndexResult tourResult : tourList) {
            //如果当前为空，就默认显示中国
            if (StringUtils.isBlank(tourResult.getCurRegionCode())) {
                tourResult.setCurImgUrl(cardMap.get("CN"));
            } else {
                tourResult.setCurImgUrl(cardMap.get(tourResult.getCurRegionCode()));
            }

            UserAuthInfoDto userAuthInfo = getUserAuthInfo(tourResult.getCreateBy(), tourResult.getIsPacker());
            if (userAuthInfo != null) {
                tourResult.setUserAuthInfo(userAuthInfo);
            }
        }

        int totalCount = (dgTourDao.countIndexForBuyer(params)).intValue();
        Pagination<ApiTourInBuyerIndexResult> pagination = new Pagination<>();
        if (totalCount > 0) {
            int pageSize = params.getSize();
            pagination.setPages(totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize + 1));
        }
        pagination.setCurrent(params.getCurrent());
        pagination.setTotal(totalCount);
        pagination.setRecords(tourList);
        return pagination;
    }


    @Override
    @Transactional(readOnly = true)
    public Object getDetail(ApiTourDetailParam param) {
        DgTour tour = super.queryById(param.getId());
        if (tour == null) {
            return "找不到行程详情";
        }

        ApiTourDetailResult tourDetail = new ApiTourDetailResult();
        tourDetail.setId(tour.getId());
        tourDetail.setTags(tour.getTags());
        tourDetail.setCheckPic(tour.getCheckPic());
        tourDetail.setCheckType(tour.getCheckType());
        tourDetail.setTourNo(tour.getTourNo());
        tourDetail.setStatus(tour.getStatus());
        tourDetail.setAuditStatus(tour.getAuditStatus());
        tourDetail.setIsPacker(tour.getIsPacker());
        tourDetail.setCreateTime(tour.getCreateTime());
        tourDetail.setIsLive(tour.getIsLive());
        //证件未审核通过
        if (EnumConstants.TourAuditStatus.NO_CERT.getValue() == tour.getAuditStatus() ||
                EnumConstants.TourAuditStatus.UNPASS_AUDIT.getValue() == tour.getAuditStatus()) {
            if (tour.getCheckType() == EnumConstants.CheckType.NO_CERT.getValue()) {
                //登机牌
                tourDetail.setRemainTime(DateUtils.addHour(tour.getFirstTime(), 36).getTime() - new Date().getTime());
            } else if (tour.getCheckType() == EnumConstants.CheckType.WAIT_AUDIT.getValue()) {
                //港澳台出境小票
                tourDetail.setRemainTime(DateUtils.addHour(tour.getFirstTime(), 24).getTime() - new Date().getTime());
            }
        }

        //行程站点信息、卡片
        tourDetail.setTourSites(tourSiteService.queryTourSiteAndCardInfoList(param));

        //买手、背包客信息
        UserAuthInfoDto userAuthInfo = getUserAuthInfo(tour.getCreateBy(), tour.getIsPacker());
        if (userAuthInfo != null) {
            tourDetail.setUserAuthInfo(userAuthInfo);
        }

        return tourDetail;
    }


    @Override
    @Transactional(readOnly = true)
    public ApiTourInfoInPackerIndexResult getTourInfoForPackerIndex(Long memberId) {
        EntityWrapper<DgTour> ew = new EntityWrapper<>();
        ew.eq("create_by", memberId);
        ew.eq("is_packer", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("status", EnumConstants.TourStatus.FINISH.getValue());
        //统计背包客已完成的行程数
        Integer count = tourMapper.selectCount(ew);

        //查询背包客进行中的行程
        ApiTourInfoResult tourInfoResult = dgTourDao.listRunningTour(memberId);

        ApiTourInfoInPackerIndexResult result = new ApiTourInfoInPackerIndexResult();
        if (tourInfoResult != null) {
            result.setTour(tourInfoResult);
        }
        result.setCount(count);

        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public Map<String, ApiTourInfoResult> queryNewTourByMemberIds(List<Long> memberIds) {
        List<ApiTourInfoResult> tourNewList = dgTourDao.queryNewTourByMemberIds(memberIds);
        //返回Map集合，key对应背包客ID，value为最新行程信息
        Map<String, ApiTourInfoResult> tourNewMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(tourNewList)) {
            tourNewList.forEach(tour -> tourNewMap.put(tour.getCreateBy().toString(), tour));
            return tourNewMap;
        }

        return null;
    }


    /**
     * 获取用户认证信息
     */
    private UserAuthInfoDto getUserAuthInfo(Long userId, Integer isPacker) {
        MemberInfo memberInfo;
        if (isPacker == EnumConstants.YesOrNo.YES.getValue().intValue()) {
            memberInfo = ucSellerService.getMemberInfo(userId, EnumConstants.MemberType.packer.getValue());
        } else {
            memberInfo = ucSellerService.getMemberInfo(userId, EnumConstants.MemberType.seller.getValue());
        }
        if (memberInfo != null) {
            UserAuthInfoDto userAuthInfo = new UserAuthInfoDto();
            userAuthInfo.setUserId(memberInfo.getId());
            userAuthInfo.setUserName(memberInfo.getNickName());
            userAuthInfo.setRealName(memberInfo.getRealName());
            userAuthInfo.setMobilePhone(memberInfo.getMobile());
            userAuthInfo.setIsAuthRealName(memberInfo.getIsPassIdentity());
            userAuthInfo.setIsAuthesameCredit(memberInfo.getIsPassZhima());
            userAuthInfo.setIsAuthPassport(memberInfo.getIsPassPassport());
            userAuthInfo.setAvatarRsurl(memberInfo.getAvatarRsurl());
            userAuthInfo.setUsualRegion(memberInfo.getOftenPlace());
            userAuthInfo.setUuid(memberInfo.getUuid());
            userAuthInfo.setIsPacker(isPacker);
            userAuthInfo.setIsSelf(memberInfo.getIsSelf());
            userAuthInfo.setRealState(memberInfo.getRealState());
            return userAuthInfo;
        } else {
            return null;
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<Long> queryTourIdsNoCheckPic(Long memberId, Integer isPacker) {
        EntityWrapper<DgTour> ew = new EntityWrapper<>();
        ew.setSqlSelect("id_");
        ew.eq("enable_", 1);
        ew.eq("create_by", memberId);
        ew.eq("is_packer", isPacker);
        ew.eq("audit_status", EnumConstants.TourAuditStatus.NO_CERT.getValue());
        ew.and("((first_time>{0} AND check_type=0) OR (first_time>{1} AND check_type=1))",
                DateUtils.reduceHour(new Date(), 36),
                DateUtils.reduceHour(new Date(), 24));

        List<Long> tourIds = new ArrayList<>();
        for (Object id : tourMapper.selectObjs(ew)) {
            tourIds.add(Long.parseLong(id.toString()));
        }

        return tourIds;
    }


    @Override
    @Transactional(readOnly = true)
    public void pushTodayToursInfo() {
        List<ApiTourTodayResult> todayTours = dgTourDao.queryAllTodayTours();
        List<Long> buyerIds = new ArrayList<>();
        List<Long> sellerIds = new ArrayList<>();
        if (todayTours != null && todayTours.size() > 0) {
            todayTours.forEach(t -> {
                if (t.getIsPacker() == EnumConstants.YesOrNo.NO.getValue()) {
                    sellerIds.add(t.getId());
                } else {
                    buyerIds.add(t.getId());
                }
            });
        }

        if (buyerIds.size() > 0) {
            Map<String, MemberInfo> manyBuyerInfos = ucSellerService.getManyMemberInfos(buyerIds, EnumConstants.MemberType.buyer.getValue());
            if (manyBuyerInfos.size() > 0) {
                for (ApiTourTodayResult todayTour : todayTours) {
                    if (todayTour.getIsPacker() == EnumConstants.YesOrNo.YES.getValue()) {
                        pushTourMessage(manyBuyerInfos.get(todayTour.getCreateBy().toString()), todayTour);
                    }
                }
            }
        }

        if (sellerIds.size() > 0) {
            Map<String, MemberInfo> manySellerInfos = ucSellerService.getManyMemberInfos(sellerIds, EnumConstants.MemberType.seller.getValue());
            for (ApiTourTodayResult todayTour : todayTours) {
                if (todayTour.getIsPacker() == EnumConstants.YesOrNo.NO.getValue()) {
                    pushTourMessage(manySellerInfos.get(todayTour.getCreateBy().toString()), todayTour);
                }
            }
        }
    }


    /**
     * 推送行程消息
     */
    private void pushTourMessage(MemberInfo memberInfo, ApiTourTodayResult todayTour) {
        String pushTitle = "行程签到提醒";
        String nextRegionName = todayTour.getTourSites().get(0).getRegionName();
        StringBuilder pushContent = new StringBuilder();
        pushContent.append("您");
        pushContent.append(todayTour.getFirstRegionName());
        pushContent.append("至");
        pushContent.append(todayTour.getDestRegionName());
        pushContent.append("的行程，今天计划到达");
        pushContent.append(nextRegionName);
        pushContent.append("，请记得签到哦");
        Map<String, Object> customMap = new LinkedHashMap<>();
        customMap.put("tourId", todayTour.getId());
        PushMessage.getSellerInstance().pushSingleMessage(
                pushTitle, pushContent.toString(), customMap,
                new PushUser(memberInfo.getUuid(), memberInfo.getLoginSource()),
                DateUtils.formatDate(new Date()) + "10:00:00");
    }


    @Override
    @Transactional
    public void updateLiveStatus(ApiTourLiveParam params) {
        DgTour dgTour = InstanceUtil.to(params, DgTour.class);
        EntityWrapper<DgTour> wrapper = new EntityWrapper<>();
        wrapper.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        wrapper.eq("id_", params.getTourId());
        wrapper.eq("create_by", params.getCreateBy());
        tourMapper.update(dgTour, wrapper);
    }


    @Override
    @Transactional
    public Object recordCheck(ApiTourUploadParam param) {
        DgTour tour = queryById(param.getId());
        if (tour == null) {
            return "没有找到行程";
        }
        tour.setCheckPic(param.getCheckPic());
        tour.setAuditStatus(EnumConstants.TourAuditStatus.WAIT_AUDIT.getValue());
        return update(tour);
    }


    @Override
    @Transactional
    public void updateDemandCountInTour(Long tourId) {
        DgTour tour = tourMapper.selectById(tourId);
        if (tour != null) {
            tour.setDemandCount(tour.getDemandCount() + 1);
        }
        super.update(tour);
    }


    @Override
    @Transactional
    public void updateOrderCountInTour(Long tourId, BigDecimal payMoney) {
        DgTour tour = tourMapper.selectById(tourId);
        if (tour != null) {
            tour.setOrderCount(tour.getOrderCount() + 1);
            BigDecimal addAmount = tour.getAmount().add(payMoney);
            tour.setAmount(addAmount);
        }
        super.update(tour);
    }


    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public Object stop(Long id, Long createBy) {
        DgTour tour = queryById(id);
        if (tour == null) {
            return "当前行程不存在";
        }

        if (tour.getCreateBy().longValue() != createBy) {
            return "用户信息错误";
        }

        if (EnumConstants.TourStatus.FINISH.getValue() == tour.getStatus()) {
            return "当前行程已经完成，不能下架";
        }

        if (!dgOrderService.isTourCanBeCanceled(id)) {
            return "当前行程存已经发货订单，不能下架";
        }

        if (EnumConstants.TourStatus.INVALID.getValue() == tour.getStatus()) {
            return "当前行程已经下架";
        }

        //下架预售商品
        ptProductService.stopProductsByTourId(id);

        //下架关联需求
        dgDemandOrderService.cancelDemandByTourId(id);

        //关闭订单
        dgOrderService.cancelTourRelateOrder(id);

        //修改行程状态
        tour.setStatus(EnumConstants.TourStatus.INVALID.getValue());
        tour.setCancelWay(EnumConstants.CancelWay.CANCEL_BY_HAND.getValue());
        tour.setCancelTime(new Date());
        super.update(tour);

        return tour;
    }


    @Override
    @Transactional(readOnly = true)
    public Pagination<ApiTourInfoResult> getMatchTourList(ApiTourMatchParam params) {
        //处理直播定位传过来的国际码，兼容自定义城市国际码，例如迪拜没有国际码，这个国际码自己定义的
        if (StringUtils.isNotBlank(customCountryCodes) && params.getMatchRegionCode() != null) {
            String[] customCodes = customCountryCodes.split("#");
            for (String customCode : customCodes) {
                String[] customCity = customCode.split(":");
                if (params.getMatchRegionCode().equals(customCity[0])) {
                    params.setCustomRegionCode(customCity[1]);
                    break;
                }
            }
        }

        /*
         * 匹配逻辑：
         * 1.开直播，行程返程时间要大于当前时间，return_time>matchMinTime
         * 2.接需求，行程返程时间要小于需求收货时限，return_time<matchMaxTime
         * 3.发预售，行程返程时间要小于预售过期时间，return_time<matchMaxTime
         */
        int totalCount = dgTourDao.countByMacthCondtion(params).intValue();

        Pagination<ApiTourInfoResult> pagination = new Pagination<>();
        if (totalCount > 0) {
            pagination.setPages(totalCount % params.getSize() == 0 ? totalCount / params.getSize() : (totalCount / params.getSize() + 1));
            pagination.setRecords(dgTourDao.matchPageListByMacthCondtion(params));
            pagination.setCurrent(params.getCurrent());
            pagination.setTotal(totalCount);
        }
        return pagination;
    }


    @Override
    @Transactional
    public ApiTourShortInfoResult getTourById(Long id) {
        DgTour tour = super.queryById(id);
        ApiTourShortInfoResult tourShortInfoResult = new ApiTourShortInfoResult();

        if (tour != null) {
            tourShortInfoResult.setId(tour.getId());
            tourShortInfoResult.setTourNo(tour.getTourNo());
            tourShortInfoResult.setTags(tour.getTags());
            tourShortInfoResult.setCreateTime(tour.getCreateTime());
            tourShortInfoResult.setFirstRegion(tour.getFirstRegionName());
            tourShortInfoResult.setDestRegion(tour.getDestRegionName());
            tourShortInfoResult.setSiteCount(tour.getSiteCount());
            tourShortInfoResult.setAuditStatus(tour.getAuditStatus());
            tourShortInfoResult.setStatus(tour.getStatus());
            tourShortInfoResult.setFirstTime(tour.getFirstTime());
            tourShortInfoResult.setReturnTime(tour.getReturnTime());
        }

        return tourShortInfoResult;
    }


    @Override
    @Transactional
    public void finishTour() {
        // 查询已过返程时间，状态为“进行中”的行程更新为“已完成”
        Wrapper<DgTour> ew = new EntityWrapper<>();
        ew.eq("status", EnumConstants.TourStatus.RUNNING.getValue());
        ew.lt("return_time", new Date());
        List<DgTour> tourList = mapper.selectList(ew);
        // 设置行程结束
        for (DgTour tour : tourList) {
            tour.setStatus(EnumConstants.TourStatus.FINISH.getValue());
            mapper.updateById(tour);
        }
    }


    @Override
    @Transactional
    public void abolishTour() {
        // 查询未通过认证、行程已开始超时的行程，进行行程下架处理
        Wrapper<DgTour> ew = new EntityWrapper<>();
        ew.and("(audit_status={0} OR audit_status={1})",
                EnumConstants.TourAuditStatus.NO_CERT.getValue(),
                EnumConstants.TourAuditStatus.UNPASS_AUDIT.getValue());
        ew.lt("return_time", new Date());
        List<DgTour> tourList = mapper.selectList(ew);
        // TODO 行程下架还存在问题，若不满足下架条件呢，还有循环执行时间问题，线程池？MQ分离？
        for (DgTour tour : tourList) {
            stop(tour.getId(), tour.getCreateBy());
        }
    }

}
