package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.circle.dto.PtProductDto;
import com.xmyy.circle.mapper.UcMemberRelationMapper;
import com.xmyy.circle.model.UcMemberRelation;
import com.xmyy.circle.service.UcMemberRelationService;
import com.xmyy.circle.vo.UcMemberRelationPageParam;
import com.xmyy.circle.vo.UcMemberRelationParam;
import com.xmyy.circle.vo.UcMemberRelationResult;
import com.xmyy.common.EnumConstants;
import com.xmyy.demand.service.DgDemandOrderService;
import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.UcBuyerService;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.member.vo.SellerPageResult;
import com.xmyy.product.model.PtProduct;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 关注  服务实现类
 *
 * @author AnCHeng
 */
@Service(interfaceClass = UcMemberRelationService.class)
@CacheConfig(cacheNames = "UcMemberRelation")
public class UcMemberRelationServiceImpl extends BaseServiceImpl<UcMemberRelation, UcMemberRelationMapper> implements UcMemberRelationService {

    @Resource
    private UcMemberRelationMapper ucMemberRelationMapper;
    @Resource
    private PtProductService productService;
    @Resource
    private UcBuyerService buyerService;
    @Resource
    private UcSellerService sellerService;
    @Resource
    private DgDemandOrderService demandOrderService;

    private String sellerSql = "select 1 from uc_seller s where s.id_=to_member_id and to_member_type=" + EnumConstants.MemberType.seller.getValue();
    private String buyerSql = "select 1 from uc_buyer s where s.id_=to_member_id and to_member_type<>" + EnumConstants.MemberType.seller.getValue();


    @Override
    @Transactional
    public Object addMember(Long buyerId, UcMemberRelationParam params) {
        if (isFollowedByMember(buyerId, params.getToMemberId())) {
            return "您已关注该用户";
        }
        UcMemberRelation memberRelation = new UcMemberRelation();
        memberRelation.setMemberId(buyerId);
        memberRelation.setToMemberId(params.getToMemberId());
        memberRelation.setToMemberType(EnumConstants.MemberType.seller.getValue());
        memberRelation.setCreateBy(buyerId);
        return update(memberRelation);
    }


    @Override
    @Transactional
    public Object delMember(Long toMemberRelationId, Long memberId, Integer toMemberType) {
        UcMemberRelation ucMemberRelation = new UcMemberRelation();
        ucMemberRelation.setUpdateTime(new Date());
        ucMemberRelation.setUpdateBy(memberId);
        ucMemberRelation.setEnable(EnumConstants.YesOrNo.NO.getValue());
        EntityWrapper<UcMemberRelation> ew = new EntityWrapper<>();
        ew.eq("member_id", memberId);
        ew.eq("to_member_id", toMemberRelationId);
        ew.eq("to_member_type", toMemberType);
        return mapper.update(ucMemberRelation, ew);
    }


    @Override
    @Transactional(readOnly = true)
    public Object getFocusList(UcMemberRelationPageParam params, Long memberId) {
        EntityWrapper<UcMemberRelation> ew = new EntityWrapper<>();
        ew.eq("member_id", memberId);
        ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        ew.orderBy("create_time", false);
        RowBounds rb = new RowBounds((params.getPageNum() - 1) * params.getPageSize(), params.getPageSize());
        List<UcMemberRelation> memberList = ucMemberRelationMapper.selectPage(rb, ew);

        //获取关注的所有背包客信息集合
        List<UcMemberRelationResult> resultList = InstanceUtil.newArrayList();
        for (UcMemberRelation memberRelation : memberList) {
            UcMemberRelationResult ucMemberRelationResult = InstanceUtil.to(memberRelation, UcMemberRelationResult.class);

            //获取买手信息
            UcSeller seller = sellerService.queryById(memberRelation.getToMemberId());
            ucMemberRelationResult.setNickName(seller.getNickName());
            ucMemberRelationResult.setAvatarRsurl(seller.getAvatarRsurl());
            ucMemberRelationResult.setLiveCountry(seller.getLiveCountry());
            ucMemberRelationResult.setClassifyTags(seller.getClassifyTags());
            ucMemberRelationResult.setToMemberUUID(seller.getUuid());

            //获取买手商品，最多返回3个
            List<PtProduct> productList = productService.getProductBySellerId(memberRelation.getToMemberId());
            if (!CollectionUtils.isEmpty(productList)) {
                List<PtProductDto> productDtolist = productList.stream().map(s -> InstanceUtil.to(s, PtProductDto.class)).collect(Collectors.toList());
                ucMemberRelationResult.setPtProductList(productDtolist);
            }
            resultList.add(ucMemberRelationResult);
        }

        Pagination<UcMemberRelationResult> page = new Pagination<>();
        page.setCurrent(params.getPageNum());
        page.setSize(params.getPageSize());
        page.setTotal(ucMemberRelationMapper.selectCount(ew));
        page.setRecords(resultList);
        return page;
    }



    @Override
    @Transactional(readOnly = true)
    public Integer countFocusByMerberId(Long merberId) {
        EntityWrapper<UcMemberRelation> ew = new EntityWrapper<>();
        ew.eq("member_id", merberId);
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.andNew().exists(true, sellerSql);
        ew.or().exists(true, buyerSql);
        return ucMemberRelationMapper.selectCount(ew);
    }


    @Override
    @Transactional(readOnly = true)
    public Integer countFansByToMerberId(Long toMerberId) {
        String sql = "select 1 from uc_buyer s where s.id_=member_id";
        EntityWrapper<UcMemberRelation> ew = new EntityWrapper<>();
        ew.eq("to_member_id", toMerberId);
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.exists(true, sql);
        return ucMemberRelationMapper.selectCount(ew);
    }


    @Override
    @Transactional(readOnly = true)
    public Boolean isFollowedByMember(Long merberId, Long toMemberId) {
        EntityWrapper<UcMemberRelation> ew = new EntityWrapper<>();
        ew.eq("member_id", merberId);
        ew.eq("to_member_id", toMemberId);
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("to_member_type", EnumConstants.MemberType.seller.getValue());
        return ucMemberRelationMapper.selectCount(ew) > 0;
    }


    @Override
    @Transactional(readOnly = true)
    public Object getFocusList2(Long buyerId, Integer current, Integer size) {
        if (current == null || current < 1) {
            current = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }

        //查询关注的买手ID列表
        EntityWrapper<UcMemberRelation> ew = new EntityWrapper<>();
        ew.eq("member_id", buyerId);
        ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        ew.orderBy("create_time", false);
        RowBounds rb = new RowBounds((current - 1) * size, size);
        List<UcMemberRelation> focusList = ucMemberRelationMapper.selectPage(rb, ew);

        List<SellerPageResult> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(focusList)) {
            //关注的买手列表
            List<Long> sellerIds = focusList.stream().map(UcMemberRelation::getToMemberId).collect(Collectors.toList());
            List<UcSeller> sellerlist = sellerService.querySellerList(sellerIds);

            //封装买手信息，并关联查询买手的商品信息
            list = sellerlist.stream().map(o -> {
                SellerPageResult r = InstanceUtil.to(o, SellerPageResult.class);
                List<PtProduct> products = productService.getProductBySellerId(r.getId());
                if (CollectionUtils.isNotEmpty(products)) {
                    List<String> imgs = products.stream().map(PtProduct::getCover).collect(Collectors.toCollection(ArrayList::new));
                    r.setProductRsurlList(imgs);
                }
                r.setIsSelfStr(EnumConstants.YesOrNo.of(o.getIsSelf()).getSellerLabel());
                if(!StringUtils.isBlank(o.getClassifyTags())){
                    r.setClassifyTagsList(Arrays.asList(o.getClassifyTags().split(",")));
                }
                return r;
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        return list;
    }


    @Override
    @Transactional(readOnly = true)
    public Object listSelected(Long buyerId, Integer current, Integer size) {
        if (current == null || current < 1) {
            current = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }

        //选择过的买手，需要查询历史需求，获取指定过的买手ID
        List<Long> sellerIds = demandOrderService.getSellerIds(buyerId, current, size);

        List<SellerPageResult> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sellerIds)) {
            //封装买手信息，并关联查询买手的商品信息
            List<UcSeller> sellerlist = sellerService.querySellerList(sellerIds);
            list = sellerlist.stream().map(o -> {
                SellerPageResult r = InstanceUtil.to(o, SellerPageResult.class);
                List<PtProduct> products = productService.getProductBySellerId(r.getId());
                if (CollectionUtils.isNotEmpty(products)) {
                    List<String> imgs = products.stream().map(PtProduct::getCover).collect(Collectors.toCollection(ArrayList::new));
                    r.setProductRsurlList(imgs);
                }
                r.setIsSelfStr(EnumConstants.YesOrNo.of(o.getIsSelf()).getSellerLabel());
                if(!StringUtils.isBlank(o.getClassifyTags())){
                    r.setClassifyTagsList(Arrays.asList(o.getClassifyTags().split(",")));
                }
                return r;
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        return list;
    }


    //TODO 以后删掉
    @Override
    @Transactional(readOnly = true)
    public Object getBuyerFocusList(UcMemberRelationPageParam params) {
        List<UcMemberRelation> memberLists = getFocusList(params);
        //获取关注的买家的ID集合
        List<Long> buyerids = memberLists.stream().map(UcMemberRelation::getToMemberId).collect(Collectors.toList());
        //获取关注的所有背包客信息集合
        List<UcBuyer> buyersList = buyerService.queryBuyerList(buyerids);
        //获取整合的数据集合
        List<UcMemberRelationResult> resultList = getResultList(memberLists, buyersList, null);
        //以分页方式返回
        Pagination<UcMemberRelationResult> page = new Pagination<>();
        page.setCurrent(params.getPageNum());
        page.setSize(params.getPageSize());
        //获取关注总条数
        page.setTotal(countFocusByMerberIdAndMerberType(params.getUserId()));
        page.setRecords(resultList);
        return page;
    }


    @Override
    @Transactional(readOnly = true)
    public Object getSellerFocusList(UcMemberRelationPageParam params) {
        List<UcMemberRelation> memberLists = getFocusList(params);
        //获取关注的买手的ID集合
        List<Long> sellerids = memberLists.stream()
                .map(UcMemberRelation::getToMemberId).collect(Collectors.toList());
        //获取关注所有买手的集合
        List<UcSeller> sellerList = sellerService.querySellerList(sellerids);
        //获取整合的数据集合
        List<UcMemberRelationResult> resultList = getResultList(memberLists, null, sellerList);

        Pagination<UcMemberRelationResult> page = new Pagination<>();
        page.setCurrent(params.getPageNum());
        page.setSize(params.getPageSize());
        page.setTotal(countFocusByMerberIdAndMerberType(params.getUserId()));
        page.setRecords(resultList);
        return page;
    }


    /**
     * 获取关注数量
     */
    private Integer countFocusByMerberIdAndMerberType(Long toMerberId) {
        EntityWrapper<UcMemberRelation> ew = new EntityWrapper<>();
        ew.eq("member_id", toMerberId);
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        return ucMemberRelationMapper.selectCount(ew);
    }

    /**
     * 获取关注买手
     */
    private List<UcMemberRelation> getFocusList(UcMemberRelationPageParam params) {
        EntityWrapper<UcMemberRelation> ew = new EntityWrapper<>();
        ew.eq("member_id", params.getUserId());
        ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        ew.orderBy("create_time", false);
        RowBounds rb = new RowBounds((params.getPageNum() - 1) * params.getPageSize(), params.getPageSize());
        return ucMemberRelationMapper.selectPage(rb, ew);
    }

    /**
     * 获取数据整合集合
     */
    private List<UcMemberRelationResult> getResultList(List<UcMemberRelation> memberLists, List<UcBuyer> buyersList, List<UcSeller> sellerList) {
        List<UcMemberRelationResult> resultList = InstanceUtil.newArrayList();
        for (UcMemberRelation ucMemberRelation : memberLists) {
            UcMemberRelationResult ucMemberRelationResult = new UcMemberRelationResult();
            ucMemberRelationResult.setCreateBy(ucMemberRelation.getCreateBy());
            ucMemberRelationResult.setMemberId(ucMemberRelation.getToMemberId());
            ucMemberRelationResult.setToMemberId(ucMemberRelation.getToMemberId());
            ucMemberRelationResult.setToMemberType(ucMemberRelation.getToMemberType());
            ucMemberRelationResult.setId(ucMemberRelation.getId());
            ucMemberRelationResult.setCreateTime(ucMemberRelation.getCreateTime());
            //获取买家信息
            if (buyersList != null && buyersList.size() > 0) {
                List<UcBuyer> lists = buyersList.stream().filter(s -> (s != null && s.getId() == ucMemberRelation.getToMemberId()))
                        .collect(Collectors.toList());
                //获取买家基本信息
                UcBuyer ucBuyer = lists.size() > 0 ? lists.get(0) : null;
                if (ucBuyer != null) {
                    ucMemberRelationResult.setNickName(ucBuyer.getNickName());
                    ucMemberRelationResult.setAvatarRsurl(ucBuyer.getAvatarRsurl());
                    ucMemberRelationResult.setLiveCountry(ucBuyer.getLiveCountry());
                    ucMemberRelationResult.setClassifyTags(ucBuyer.getClassifyTags());
                    ucMemberRelationResult.setToMemberUUID(ucBuyer.getUuid());
                    resultList.add(ucMemberRelationResult);
                }
            }
            if (sellerList != null && sellerList.size() > 0) {
                List<UcSeller> lists = sellerList.stream().filter(s -> (s != null && s.getId() == ucMemberRelation.getToMemberId()))
                        .collect(Collectors.toList());
                //获取关注的买手基本信息
                UcSeller ucSeller = (lists != null && lists.size() > 0) ? lists.get(0) : null;
                if (ucSeller != null) {
                    ucMemberRelationResult.setNickName(ucSeller.getNickName());
                    ucMemberRelationResult.setAvatarRsurl(ucSeller.getAvatarRsurl());
                    ucMemberRelationResult.setLiveCountry(ucSeller.getLiveCountry());
                    ucMemberRelationResult.setClassifyTags(ucSeller.getClassifyTags());
                    ucMemberRelationResult.setToMemberUUID(ucSeller.getUuid());
                    resultList.add(ucMemberRelationResult);
                }

            }
        }
        return resultList;
    }

}
