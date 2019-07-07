package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.xmyy.circle.mapper.UcDynamicCircleMapper;
import com.xmyy.circle.model.UcDynamicCircle;
import com.xmyy.circle.model.WsAdvertContent;
import com.xmyy.circle.service.*;
import com.xmyy.circle.vo.*;
import com.xmyy.common.EnumConstants;
import com.xmyy.member.service.UcBuyerService;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.member.vo.MemberInfoResult;
import com.xmyy.member.vo.SellerPageParam;
import com.xmyy.member.vo.SellerPageResult;
import com.xmyy.product.dto.UserAuthInfoDto;
import com.xmyy.product.service.PtProductService;
import com.xmyy.product.service.TourService;
import com.xmyy.product.vo.ApiTourInBuyerIndexParam;
import com.xmyy.product.vo.ApiTourInBuyerIndexResult;
import com.xmyy.product.vo.PresellProductParam;
import com.xmyy.product.vo.PresellProductResult;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 首页  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = ApiIndexService.class)
@CacheConfig(cacheNames = "ApiIndex")
public class ApiIndexServiceImpl extends BaseServiceImpl<UcDynamicCircle, UcDynamicCircleMapper> implements ApiIndexService {

    @Resource
    private UcSellerService sellerService;
    @Resource
    private UcDynamicCircleService circleService;
    @Resource
    private PtProductService ptProductService;
    @Resource
    private WsAdvertContentService advertContentService;
    @Resource
    private DgCountryService countryService;
    @Resource
    DgTagsService tagsService;
    @Resource
    private TourService tourService;
    @Resource
    private UcBuyerService buyerService;
    @Resource
    private DgEverydayRadioService idgEverydayRadioService;


    @Override
    @Transactional(readOnly = true)
    @TxTransaction(isStart = true)
    public BuyerApiIndexResult index(BuyerIndexParam param) {
        BuyerApiIndexResult result = new BuyerApiIndexResult();

        //买手行程
        ApiTourInBuyerIndexParam tourParams = new ApiTourInBuyerIndexParam();
        tourParams.setMatchRegion(param.getCountryShortCode());
        tourParams.setCardSize(param.getCardSize());
        tourParams.setDeviceType(param.getDeviceType());
        Pagination<ApiTourInBuyerIndexResult> tourIndexPage = null;
        try {
            tourIndexPage = tourService.listIndexForBuyer(tourParams);
        } catch (Exception e) {
            logger.error("获取行程报错：{}", e.getMessage());
        }
        if (tourIndexPage != null && CollectionUtils.isNotEmpty(tourIndexPage.getRecords())) {
            result.setSellerTourTitle("买手行程");
            result.setSellerTourDesc("真实行程");
            List<BuyerApiIndexResult.Tours> tourList = tourIndexPage.getRecords().stream().map(t -> {
                BuyerApiIndexResult.Tours tour = InstanceUtil.to(t, BuyerApiIndexResult.Tours.class);
                UserAuthInfoDto userAuthInfo = t.getUserAuthInfo();
                if (userAuthInfo != null) {
                    tour.setAvatarRsurl(userAuthInfo.getAvatarRsurl());
                    tour.setIsPacker(userAuthInfo.getIsPacker());
                    tour.setNickName(userAuthInfo.getUserName());
                    tour.setUserId(userAuthInfo.getUserId());
                    tour.setUuid(userAuthInfo.getUuid());
                }
                return tour;
            }).collect(Collectors.toCollection(ArrayList::new));
            result.setTours(tourList);
        }

        //最新预售
        PresellProductParam productParams = new PresellProductParam();
        productParams.setBuyRegionShortCode(param.getCountryShortCode());
        productParams.setBuyerId(param.getBuyerId());
        productParams.setCurrent(1);
        productParams.setSize(4);

        Pagination<PresellProductResult> productPage = null;
        try {
            productPage = ptProductService.getPresellList(productParams);
        } catch (Exception e) {
            logger.error("获取最新预售报错：{}", e.getMessage());
        }

        if (productPage != null && CollectionUtils.isNotEmpty(productPage.getRecords())) {
            result.setPresellTitle("最新预售");
            result.setPresellDec("全球抢购");
            List<BuyerApiIndexResult.PresellProducts> productList = productPage.getRecords().stream()
                    .map(o -> InstanceUtil.to(o, BuyerApiIndexResult.PresellProducts.class))
                    .collect(Collectors.toCollection(ArrayList::new));
            result.setPresellProducts(productList);
        }

        //推荐买手，目前是获取3个买手+2个背包客
        SellerPageParam seller = new SellerPageParam();
        seller.setBuyerId(param.getBuyerId());
        seller.setCurrent(1);
        seller.setSize(3);
        List<BuyerApiIndexResult.Sellers> sellersList = new ArrayList<>();
        Pagination<SellerPageResult> sellerPage = null;
        try {
            sellerPage = sellerService.topList(seller);
        } catch (Exception e) {
            logger.error("获取推荐买手报错：{}", e.getMessage());
        }

        if (sellerPage != null && CollectionUtils.isNotEmpty(sellerPage.getRecords())) {
            result.setSellerTitle("推荐买手");
            result.setSellerDec("千挑万选");
            sellersList = sellerPage.getRecords().stream().map(o -> {
                BuyerApiIndexResult.Sellers r = InstanceUtil.to(o, BuyerApiIndexResult.Sellers.class);
                r.setIsSelfStr(EnumConstants.YesOrNo.of(o.getIsSelf()).getSellerLabel());
                return r;
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        Pagination<SellerPageResult> packerPage = null;
        seller.setSize(2);
        try {
            packerPage = buyerService.topList(seller);
        } catch (Exception e) {
            logger.error("获取背包客报错：{}", e.getMessage());
        }

        if (packerPage != null && CollectionUtils.isNotEmpty(packerPage.getRecords())) {
            result.setSellerTitle("推荐买手");
            result.setSellerDec("千挑万选");
            List<BuyerApiIndexResult.Sellers> sellersList1 = packerPage.getRecords().stream().map(o -> {
                BuyerApiIndexResult.Sellers r = InstanceUtil.to(o, BuyerApiIndexResult.Sellers.class);
                r.setIsSelf(2);
                r.setIsSelfStr("背包客");
                return r;
            }).collect(Collectors.toCollection(ArrayList::new));
            sellersList.addAll(sellersList1);
        }

        result.setSellers(sellersList);

        //精选内容
        CirclePageParam circlePageParams = new CirclePageParam();
        circlePageParams.setBuyerId(param.getBuyerId());
        circlePageParams.setCurrent(1);
        circlePageParams.setSize(2);

        Pagination<CirclePageResult> circlePage = null;
        try {
            circlePage = circleService.list(circlePageParams);
        } catch (Exception e) {
            logger.error("获取精选内容报错：{}", e.getMessage());
        }

        if (circlePage != null && CollectionUtils.isNotEmpty(circlePage.getRecords())) {
            result.setCircleTitle("精选内容");
            result.setCircleDec("读万卷书行万里路");
            List<BuyerApiIndexResult.Circles> circlesList = circlePage.getRecords().stream()
                    .map(o -> InstanceUtil.to(o, BuyerApiIndexResult.Circles.class))
                    .collect(Collectors.toCollection(ArrayList::new));
            result.setCircles(circlesList);
        }

        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public ApiAppResult get() {
        ApiAppResult result = new ApiAppResult();
        AdApiParam ad = new AdApiParam();

        //买家端首页Banner
        ad.setCode("Buyer01");
        List<WsAdvertContent> adc1 = null;
        try {
            adc1 = advertContentService.listContent(ad);
        } catch (Exception e) {
            logger.error("获取广告报错：{}", e.getMessage());
        }

        if (CollectionUtils.isNotEmpty(adc1)) {
            List<ApiAppResult.Adverts> ads = adc1.stream()
                    .map(o -> InstanceUtil.to(o, ApiAppResult.Adverts.class))
                    .collect(Collectors.toCollection(ArrayList::new));
            result.setAds(ads);
        }

        //买家端首页发现
        ad.setCode("Buyer02");
        List<WsAdvertContent> wsAdvertContents = null;
        try {
            wsAdvertContents = advertContentService.listContent(ad);
        } catch (Exception e) {
            logger.error("获取广告报错：{}", e.getMessage());
        }

        if (CollectionUtils.isNotEmpty(wsAdvertContents)) {
            List<ApiAppResult.Adverts> findAds = wsAdvertContents.stream()
                    .map(o -> InstanceUtil.to(o, ApiAppResult.Adverts.class))
                    .collect(Collectors.toCollection(ArrayList::new));
            result.setFindAds(findAds);
        }

        //买家端直播Banner
        ad.setCode("Buyer03");
        List<WsAdvertContent> liveContents = null;
        try {
            liveContents = advertContentService.listContent(ad);
        } catch (Exception e) {
            logger.error("获取广告报错：{}", e.getMessage());
        }

        if (CollectionUtils.isNotEmpty(liveContents)) {
            List<ApiAppResult.Adverts> liveAds = liveContents.stream()
                    .map(o -> InstanceUtil.to(o, ApiAppResult.Adverts.class))
                    .collect(Collectors.toCollection(ArrayList::new));
            result.setLiveAds(liveAds);
        }

        //买家端背包客左菜单
        ad.setCode("Buyer06");
        List<WsAdvertContent> pcs = null;
        try {
            pcs = advertContentService.listContent(ad);
        } catch (Exception e) {
            logger.error("获取广告报错：{}", e.getMessage());
        }

        if (CollectionUtils.isNotEmpty(pcs)) {
            List<ApiAppResult.Adverts> paads = pcs.stream()
                    .map(o -> InstanceUtil.to(o, ApiAppResult.Adverts.class))
                    .collect(Collectors.toCollection(ArrayList::new));
            result.setPackAds(paads);
        }

        //热门标签
        List<String> tags = null;
        try {
            tags = tagsService.tagList();
        } catch (Exception e) {
            logger.error("获取热门标签报错：{}", e.getMessage());
        }
        result.setTags(tags);

        //热门国家
        List<CountryResult> hotCountryList = null;
        try {
            hotCountryList = countryService.getHotCountryList(null);
        } catch (Exception e) {
            logger.error("获取热门国家报错：{}", e.getMessage());
        }

        if (CollectionUtils.isNotEmpty(hotCountryList)) {
            List<ApiAppResult.Country> countrys = new ArrayList<>();
            for (CountryResult c : hotCountryList) {
                ApiAppResult.Country country = new ApiAppResult.Country();
                country.setCountryName(c.getName());
                country.setCountryShortCode(c.getShortCode());
                countrys.add(country);
            }
            result.setCountrys(countrys);
        }
        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public Object sellerGet() {
        ApiAppResult result = new ApiAppResult();
        AdApiParam ad = new AdApiParam();

        //买手端首页Banner
        ad.setCode("Seller04");
        List<WsAdvertContent> adc = null;
        try {
            adc = advertContentService.listContent(ad);
        } catch (Exception e) {
            logger.error("获取广告报错：{}", e.getMessage());
        }

        if (CollectionUtils.isNotEmpty(adc)) {
            List<ApiAppResult.Adverts> ads = adc.stream()
                    .map(o -> InstanceUtil.to(o, ApiAppResult.Adverts.class))
                    .collect(Collectors.toCollection(ArrayList::new));
            result.setAds(ads);
        }

        //热门标签
        List<String> tags = null;
        try {
            tags = tagsService.tagList();
        } catch (Exception e) {
            logger.error("获取热门标签报错：{}", e.getMessage());
        }

        result.setTags(tags);
        return result;
    }


    @Override
    public Object sellerIndex(Long id) {
        SellerApiIndexResult result = new SellerApiIndexResult();
        MemberInfoResult info = null;
        try {
            info = sellerService.getInfo(id);
        } catch (Exception e) {
            logger.error("获取当前买手报错：{}", e.getMessage());
        }

        if (info != null) {
            //TODO
            result.setRealState(info.getRealState());
            result.setTotalPrice(new BigDecimal(0));
            result.setTradeCount(info.getTradeCount());
            result.setAvatarRsurl(info.getAvatarRsurl());
        }

        AdApiParam ad = new AdApiParam();
        //买手端首页Banner
        ad.setCode("Seller04");
        List<WsAdvertContent> adc = null;
        try {
            adc = advertContentService.listContent(ad);
        } catch (Exception e) {
            logger.error("获取广告报错：{}", e.getMessage());
        }

        if (CollectionUtils.isNotEmpty(adc)) {
            List<SellerApiIndexResult.Adverts> ads = adc.stream()
                    .map(o -> InstanceUtil.to(o, SellerApiIndexResult.Adverts.class))
                    .collect(Collectors.toCollection(ArrayList::new));
            result.setAds(ads);
        }

        //今日提醒
        List<EveryDayRadioResult> resultList = null;
        try {
            resultList = (List<EveryDayRadioResult>) idgEverydayRadioService.queryEveryDayRadioList(id, EnumConstants.MemberType.seller.getValue());
        } catch (Exception e) {
            logger.error("今日提醒异常：{}", e.getMessage());
        }
        result.setEveryDayradios(resultList);

        //热门标签
        List<String> tags = null;
        try {
            tags = tagsService.tagList();
        } catch (Exception e) {
            logger.error("获取热门标签报错：{}", e.getMessage());
        }
        result.setTags(tags);

        return result;
    }

}
