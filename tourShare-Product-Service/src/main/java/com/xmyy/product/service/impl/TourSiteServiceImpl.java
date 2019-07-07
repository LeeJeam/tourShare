package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.codingapi.tx.annotation.TxTransaction;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.DateUtils;
import com.xmyy.product.dao.DgTourDao;
import com.xmyy.product.dao.DgTourSiteDao;
import com.xmyy.product.dto.ApiTourSiteDto;
import com.xmyy.product.mapper.DgTourMapper;
import com.xmyy.product.mapper.DgTourSiteMapper;
import com.xmyy.product.model.DgTour;
import com.xmyy.product.model.DgTourSite;
import com.xmyy.product.service.TourCardService;
import com.xmyy.product.service.TourSiteService;
import com.xmyy.product.vo.ApiTourCardMatchParam;
import com.xmyy.product.vo.ApiTourDetailParam;
import com.xmyy.product.vo.ApiTourSiteSignInParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 行程站点表  服务实现类
 *
 * @author wangzejun
 */
@Service(interfaceClass = TourSiteService.class)
//@CacheConfig(cacheNames = "DgTourSite")
public class TourSiteServiceImpl extends BaseServiceImpl<DgTourSite, DgTourSiteMapper> implements TourSiteService {

    @Resource
    private DgTourMapper dgTourMapper;
    @Resource
    private DgTourDao dgTourDao;
    @Resource
    private DgTourSiteMapper dgTourSiteMapper;
    @Resource
    private DgTourSiteDao dgTourSiteDao;
    @Reference
    private TourCardService tourCardService;
    @Value("${country.customeCountryCodes}")
    private String customCountryCodes;


    @Override
    @Transactional(readOnly = true)
    public List<ApiTourSiteDto> queryTourSiteInfoList(Long tourId) {
        return dgTourSiteDao.queryTourSitesByTourId(tourId);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ApiTourSiteDto> queryTourSiteAndCardInfoList(ApiTourDetailParam params) {
        //查询站点信息
        List<ApiTourSiteDto> apiTourSiteDtos = dgTourSiteDao.queryTourSitesByTourId(params.getId());

        if (apiTourSiteDtos != null && apiTourSiteDtos.size() > 0) {
            //补充站点卡片url
            ApiTourCardMatchParam cardParams = new ApiTourCardMatchParam();
            List<String> regionCodeList = new ArrayList<>();
            apiTourSiteDtos.forEach(t -> regionCodeList.add(t.getRegionCode()));
            cardParams.setCardCodes(regionCodeList);
            cardParams.setCardSize(params.getCardSize());
            cardParams.setCardType(params.getCardType());
            cardParams.setDeviceType(params.getDeviceType());
            cardParams.setSkinType(params.getSkinType());
            //获取cardCode List对应url的Map
            Map<String, String> caraMap = tourCardService.getTourCardsMapList(cardParams);
            for (ApiTourSiteDto tourSiteDto : apiTourSiteDtos) {
                tourSiteDto.setRegionImgUrl(caraMap.get(tourSiteDto.getRegionCode()));
            }
        }
        return apiTourSiteDtos;
    }


    @Override
    @Transactional
    public Object signTourSite(ApiTourSiteSignInParam signInParams) {
        DgTour tour = dgTourMapper.selectById(signInParams.getTourId());
        if (tour == null) {
            return "没找到行程";
        }

        if (EnumConstants.TourStatus.INVALID.getValue() == tour.getStatus()) {
            return "行程无效，不能签到";
        }

        if (EnumConstants.TourStatus.FINISH.getValue() == tour.getStatus()) {
            return "行程已经完成，不需要签到";
        }

        DgTourSite tourSite = queryById(signInParams.getId());
        if (StringUtils.isBlank(signInParams.getCountryCode())) {
            return "没有获取到定位信息";
        }

        if (EnumConstants.SignInStatus.YES.getValue() == tourSite.getIsSignIn()) {
            return "已经签到过啦";
        }

        if (DateUtils.compareDate(new Date(), tourSite.getPlanBeginTime()) == -1) {
            return "还未到签到时间哦";
        }

        //TODO 这里是否可以补签，产品确认
        if (tourSite.getSiteType() != EnumConstants.TourSiteType.RETURN_SITE.getValue()) {
            ApiTourSiteDto tourNextSite = dgTourSiteDao.queryTourSiteBySortAndTourId(tourSite.getSort() + 1, tourSite.getTourId());
            if (DateUtils.compareDate(new Date(), tourNextSite.getPlanBeginTime()) == 1) {
                return "你已经错过签到时间啦";
            }
        }

        //处理行程定位 传过来的国际码，兼容自定义城市国际码，例如迪拜没有国际码，这个国际码自己定义的
        if (StringUtils.isNotBlank(customCountryCodes)) {
            String[] customCodes = customCountryCodes.split("#");
            for (String customCode : customCodes) {
                String[] customCity = customCode.split(":");
                if (signInParams.getCountryCode().equals(customCity[0])) {
                    signInParams.setCustomCountryCode(customCity[1]);
                    break;
                }
            }
        }
        boolean isArriveFlag1 = signInParams.getCountryCode().equals(tourSite.getRegionCode());
        boolean isArriveFlag2 = StringUtils.isNotBlank(signInParams.getCustomCountryCode()) &&
                signInParams.getCustomCountryCode().equals(tourSite.getRegionCode());
        if (!isArriveFlag1 && !isArriveFlag2) {
            return String.format("到达%s后才能签到", tourSite.getRegionName());
        }

        tourSite.setIsSignIn(EnumConstants.SignInStatus.YES.getValue());
        dgTourSiteMapper.updateById(tourSite);

        //更新行程表的当前到达地点
        dgTourDao.updateCurSiteByTourSite(tourSite);
        return null;
    }


    @Override
    @Transactional
    public void deleteTourSitesByTourId(Long tourId) {
        EntityWrapper<DgTourSite> siteWp = new EntityWrapper<>();
        siteWp.eq("tour_id", tourId);
        DgTourSite site = new DgTourSite();
        site.setEnable(EnumConstants.Enable.DISABLE.getValue());
        dgTourSiteMapper.update(site, siteWp);
    }

}
