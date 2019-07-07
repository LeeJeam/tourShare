package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.codingapi.tx.annotation.TxTransaction;
import com.google.common.collect.Lists;
import com.xmyy.circle.model.DgFavorites;
import com.xmyy.circle.service.DgCountryService;
import com.xmyy.circle.service.DgFavoritesService;
import com.xmyy.circle.vo.CountryResult;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.BizSequenceUtils;
import com.xmyy.common.util.DateUtils;
import com.xmyy.manage.model.AdminUser;
import com.xmyy.manage.service.AdminUserService;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.product.dao.*;
import com.xmyy.product.dto.*;
import com.xmyy.product.mapper.*;
import com.xmyy.product.model.*;
import com.xmyy.product.service.*;
import com.xmyy.product.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.Constants;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.exception.BizException;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.support.mq.QueueSender;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品  服务实现类
 * @author simon
 */
@Service(interfaceClass = PtProductService.class)
//@CacheConfig(cacheNames = "PtProduct")
public class PtProductServiceImpl extends BaseServiceImpl<PtProduct, PtProductMapper> implements PtProductService {

    @Resource
    private PtProductMapper ptProductMapper;
    @Resource
    private PtAttrMapper ptAttrMapper;
    @Resource
    private PtCategoryMapper ptCategoryMapper;
    @Resource
    private PtBrandMapper ptBrandMapper;
    @Resource
    private PtSkuMapper ptSkuMapper;
    @Resource
    private PtSeriesColorMapper ptSeriesColorMapper;
    @Resource
    private PtSeriesMapper ptSeriesMapper;
    @Resource
    private PtAttrTypeMapper ptAttrTypeMapper;
    @Resource
    private DgTourMapper dgTourMapper;
    @Resource
    private PtCategoryDao ptCategoryDao;
    @Resource
    private PtBrandDao ptBrandDao;
    @Resource
    private PtAttrDao ptAttrDao;
    @Resource
    private PtProductDao ptProductDao;
    @Reference
    private DgFavoritesService dgFavoritesService;
    @Reference
    private DgCountryService dgCountryService;
    @Reference
    private PtAttrService ptAttrService;
    @Reference
    private UcSellerService ucSellerService;
    @Reference
    private TourService tourService;
    @Reference
    private PtSkuService ptSkuService;
    @Reference
    private PtSeriesColorService ptSeriesColorService;
    @Reference
    private PtAttrValueService ptAttrValueService;
    @Resource
    private QueueSender queueSender;


    @Override
    @Transactional
    public Object save(ApiProductSaveParam params, Long userId) {
        if (true) {
            return "无效接口（请使用V2接口）";
        }
        Long id = params.getId();
        if (id != null && id > 0) {
            //编辑商品（APP端已屏蔽编辑功能），使原商品失效
            PtProduct ptProduct = ptProductMapper.selectById(id);
            if (ptProduct == null) {
                return "找不到商品";
            }

            ptProduct.setIsSale(EnumConstants.YesOrNo.NO.getValue());
            ptProduct.setEnable(EnumConstants.YesOrNo.NO.getValue());
            ptProduct.setUpdateTime(new Date());
            ptProductMapper.updateById(ptProduct);

            EntityWrapper<PtSku> skuEw = new EntityWrapper<>();
            skuEw.eq("pt_product_id", id);
            List<PtSku> ptSkuList = ptSkuMapper.selectList(skuEw);
            if (!CollectionUtils.isEmpty(ptSkuList)) {
                for (PtSku ptSku : ptSkuList) {
                    Long skuId = ptSku.getId();
                    ptSku.setEnable(EnumConstants.YesOrNo.NO.getValue());
                    ptSku.setUpdateTime(new Date());
                    ptSkuMapper.updateById(ptSku);

                    EntityWrapper<PtAttr> attrEw = new EntityWrapper<>();
                    attrEw.eq("pt_product_id", id);
                    attrEw.eq("pt_sku_id", skuId);
                    List<PtAttr> attrList = ptAttrMapper.selectList(attrEw);
                    if (!CollectionUtils.isEmpty(attrList)) {
                        for (PtAttr ptAttr : attrList) {
                            ptAttr.setEnable(EnumConstants.YesOrNo.NO.getValue());
                            ptSku.setUpdateTime(new Date());
                            ptAttrMapper.updateById(ptAttr);
                        }
                    }
                }
            }
        }

        //添加商品
        PtProduct ptProduct = new PtProduct();
        ptProduct.setCreateBy(userId);
        ptProduct.setEnable(EnumConstants.YesOrNo.YES.getValue());
        ptProduct.setTitle(params.getTitle());
        ptProduct.setPtCategoryId(params.getCategoryId());
        ptProduct.setPtCategoryId2(params.getCategoryId2());
        ptProduct.setPtBrandId(params.getBrandId());
        ptProduct.setPtSeriesId(params.getSeriesId());
        ptProduct.setProductType(params.getProductType());
        ptProduct.setShopName(params.getShopName());
        ptProduct.setProductDesc(params.getProductDesc());
        ptProduct.setExpiresTime(params.getExpiresTime());
        ptProduct.setDgTourId(params.getTourId());
        ptProduct.setProductNo(BizSequenceUtils.generateBizNo(EnumConstants.BizCode.ProductNo));
        ptProduct.setIsSale(EnumConstants.YesOrNo.YES.getValue()); //上架
        ptProduct.setClickCount(0);
        ptProduct.setVideoClickCount(0);
        ptProduct.setFavorable(100f);

        //配送方式
        Integer logisticsType = params.getExpressType();
        if (logisticsType == null || logisticsType == 0) {
            logisticsType = 1;
        }
        ptProduct.setExpressType(logisticsType);

        //商品图片和封面图片
        List<String> images = params.getImages();
        String imageStr = "";
        String coverStr = "";
        if (!CollectionUtils.isEmpty(images)) {
            imageStr = StringUtils.join(images, ",");
            coverStr = images.get(0);
        }

        ptProduct.setImages(imageStr);
        ptProduct.setCover(coverStr);

        //国家
        ptProduct.setBuyRegion(params.getBuyRegion());
        CountryResult country = dgCountryService.getCountryByName(params.getBuyRegion());
        if (country != null) {
            ptProduct.setBuyRegionLogo(country.getLogo());
            ptProduct.setBuyRegionCurrency(country.getCurrency());
            ptProduct.setBuyRegionCurrencyCode(country.getCurrencyCode());
        }
        ptProduct.setCurrency("人民币");
        ptProduct.setCurrencyCode("CNY");

        List<ApiSkuDto> skuList = params.getSku();
        if (!CollectionUtils.isEmpty(skuList)) {
            BigDecimal minPrice = skuList.stream().map(ApiSkuDto::getPrice).min(BigDecimal::compareTo).get();
            BigDecimal maxPrice = skuList.stream().map(ApiSkuDto::getPrice).max(BigDecimal::compareTo).get();
            ptProduct.setMinPrice(minPrice);
            ptProduct.setMaxPrice(maxPrice);

        }
        this.update(ptProduct);

        Long ptProductId = ptProduct.getId();
        if (!CollectionUtils.isEmpty(skuList)) {
            skuList.stream().forEach(v -> {
                PtSku ptSku = new PtSku();
                ptSku.setPtProductId(ptProductId);
                ptSku.setPrice(v.getPrice());
                ptSku.setStock(v.getStock());
                ptSku.setPtSeriesColorId(v.getColorAttrId());
                ptSku.setEnable(EnumConstants.YesOrNo.YES.getValue());
                ptSku.setCreateBy(userId);
                ptSkuService.update(ptSku);

                Long ptSkuId = ptSku.getId();
                List<ApiAttrItemDto> attrItemList = v.getAttrItem();
                List<PtAttr> ptAttrs = InstanceUtil.newArrayList();
                if (!CollectionUtils.isEmpty(attrItemList)) {
                    attrItemList.stream().forEach(k -> {
                        PtAttr ptAttr = new PtAttr();
                        ptAttr.setPtProductId(ptProductId);
                        ptAttr.setPtSkuId(ptSkuId);
                        ptAttr.setPtAttrTypeId(k.getAttrTypeId());
                        ptAttr.setPtAttrTypeName(k.getAttrTypeName());
                        ptAttr.setPtAttrValueId(k.getAttrValueId());
                        ptAttr.setPtAttrValueName(k.getAttrValueName());
                        ptAttr.setEnable(EnumConstants.YesOrNo.YES.getValue());
                        ptAttr.setCreateBy(userId);
                        ptAttrs.add(ptAttr);
                    });
                    ptAttrService.updateBatch(ptAttrs);
                }

            });
        }

        return ptProduct.getId();
    }


    @Override
    @Transactional
    public Object saveV2(ApiProductCustomSaveParam params, Long userId) throws BizException {
        //添加商品
        PtProduct ptProduct = new PtProduct();
        ptProduct.setCreateBy(userId);
        ptProduct.setEnable(EnumConstants.YesOrNo.YES.getValue());
        ptProduct.setTitle(params.getTitle());
        ptProduct.setPtCategoryId(params.getCategoryId());
        ptProduct.setPtCategoryId2(params.getCategoryId2());
        ptProduct.setPtBrandId(params.getBrandId());
        ptProduct.setProductType(params.getProductType());
        ptProduct.setShopName(params.getShopName());
        ptProduct.setProductDesc(params.getProductDesc());
        ptProduct.setExpiresTime(params.getExpiresTime());
        ptProduct.setDgTourId(params.getTourId());
        ptProduct.setProductNo(BizSequenceUtils.generateBizNo(EnumConstants.BizCode.ProductNo));
        ptProduct.setIsSale(EnumConstants.YesOrNo.YES.getValue()); //上架
        ptProduct.setClickCount(0);
        ptProduct.setVideoClickCount(0);
        ptProduct.setFavorable(100f);

        List<ApiCustomSkuDto> skuList = params.getSku();
        if (skuList == null || skuList.isEmpty()) {
            return "商品SKU不能为空";
        }

        List<String> images = params.getImages();
        if (images == null || images.isEmpty()) {
            return "图片不能为空";
        }

        EntityWrapper<DgTour> tourEw = new EntityWrapper<>();
        tourEw.eq("create_by", userId);
        tourEw.eq("id_", params.getTourId());
        Integer count = dgTourMapper.selectCount(tourEw);
        if (count == 0) {
            return "行程不存在";
        }

        //配送方式
        Integer expressType = params.getExpressType();
        if (expressType == null || expressType == 0) {
            expressType = 1;
        }
        ptProduct.setExpressType(expressType);

        //商品图片和封面图片
        String imageStr = StringUtils.join(images, ",");
        String coverStr = images.get(0);
        ptProduct.setImages(imageStr);
        ptProduct.setCover(coverStr);

        //国家
        CountryResult country;
        if (params.getBuyRegionShortCode() != null) {
            country = dgCountryService.getCountryByShortCode(params.getBuyRegionShortCode());
        } else {
            country = dgCountryService.getCountryByName(params.getBuyRegion());
        }
        if (country == null) {
            return "国家不存在";
        }

        ptProduct.setBuyRegion(params.getBuyRegion());
        ptProduct.setBuyRegionShortCode(country.getShortCode());
        ptProduct.setBuyRegionLogo(country.getLogo());
        ptProduct.setBuyRegionCurrency(country.getCurrency());
        ptProduct.setBuyRegionCurrencyCode(country.getCurrencyCode());
        ptProduct.setCurrency("人民币");
        ptProduct.setCurrencyCode("CNY");

        BigDecimal minPrice = skuList.stream().map(ApiCustomSkuDto::getPrice).min(BigDecimal::compareTo).get();
        BigDecimal maxPrice = skuList.stream().map(ApiCustomSkuDto::getPrice).max(BigDecimal::compareTo).get();
        ptProduct.setMinPrice(minPrice);
        ptProduct.setMaxPrice(maxPrice);
        //保存商品
        this.update(ptProduct);
        Long ptProductId = ptProduct.getId();

        //保存属性
        List<ApiCustomAttrItemDto> apiCustomAttrItemDtoList = InstanceUtil.newArrayList();
        for (ApiCustomSkuDto apiSkuDto : skuList) {
            apiCustomAttrItemDtoList.addAll(apiSkuDto.getAttrItem());
        }

        if (CollectionUtils.isEmpty(apiCustomAttrItemDtoList)) {
            throw new BizException("商品属性不能为空");
        }

        List<ApiCustomAttrItemDto> collect = apiCustomAttrItemDtoList.stream().distinct().collect(Collectors.toList());
        List<PtAttrValue> attrValueList = InstanceUtil.newArrayList();
        for (ApiCustomAttrItemDto attrItemDto : collect) {
            PtAttrValue ptAttrValue = new PtAttrValue();
            ptAttrValue.setPtAttrTypeId(attrItemDto.getAttrTypeId());
            ptAttrValue.setName(attrItemDto.getAttrValueName());
            ptAttrValue.setIsCustom(EnumConstants.YesOrNo.YES.getValue());
            ptAttrValue.setCustomBy(userId);
            ptAttrValue.setCreateBy(userId);
            attrValueList.add(ptAttrValue);
        }
        attrValueList = ptAttrValueService.updateBatchThenReturn(attrValueList);

        //保存SKU
        for (ApiCustomSkuDto apiSkuDto : skuList) {
            PtSku ptSku = new PtSku();
            ptSku.setPtProductId(ptProductId);
            ptSku.setPrice(apiSkuDto.getPrice());
            ptSku.setStock(apiSkuDto.getStock());
            ptSku.setEnable(EnumConstants.YesOrNo.YES.getValue());
            ptSku.setCreateBy(userId);
            ptSku = ptSkuService.update(ptSku);
            Long ptSkuId = ptSku.getId();

            List<ApiCustomAttrItemDto> attrItemList = apiSkuDto.getAttrItem();
            List<PtAttr> ptAttrs = InstanceUtil.newArrayList();
            if (attrItemList == null || attrItemList.isEmpty()) {
                throw new BizException("属性不能为空");
            }

            //属性类型较验
            List<Long> attrTypeIdList = attrItemList.stream().map(ApiCustomAttrItemDto::getAttrTypeId).collect(Collectors.toList());
            EntityWrapper<PtAttrType> ew = new EntityWrapper<>();
            ew.in("id_", attrTypeIdList);
            List<PtAttrType> ptAttrTypes = ptAttrTypeMapper.selectList(ew);

            if (ptAttrTypes == null || ptAttrTypes.isEmpty()) {
                throw new BizException("属性ID" + attrTypeIdList.toString() + "不存在");
            } else {
                List<Long> attrTypeIdListDb = ptAttrTypes.stream().map(PtAttrType::getId).collect(Collectors.toList());
                List<Long> attrTypeIdListDiff = attrTypeIdList.stream().filter(t -> !attrTypeIdListDb.contains(t)).collect(Collectors.toList());
                if (!attrTypeIdListDiff.isEmpty()) {
                    throw new BizException("属性ID" + attrTypeIdListDiff.toString() + "不存在");
                }
            }

            //属性值回写
            Map<String, Long> attrValueIdMap = InstanceUtil.newHashMap();
            attrValueList.forEach(v -> attrValueIdMap.put(v.getName(), v.getId()));

            Map<Long, String> attrTypeNameMap = InstanceUtil.newHashMap();
            ptAttrTypes.forEach(s -> attrTypeNameMap.put(s.getId(), s.getName()));

            attrItemList.forEach(k -> {
                PtAttr ptAttr = new PtAttr();
                ptAttr.setPtProductId(ptProductId);
                ptAttr.setPtSkuId(ptSkuId);
                ptAttr.setPtAttrTypeId(k.getAttrTypeId());
                ptAttr.setPtAttrTypeName(attrTypeNameMap.get(k.getAttrTypeId()));
                ptAttr.setPtAttrValueId(attrValueIdMap.get(k.getAttrValueName()));
                ptAttr.setPtAttrValueName(k.getAttrValueName());
                ptAttr.setEnable(EnumConstants.YesOrNo.YES.getValue());
                ptAttr.setCreateBy(userId);
                ptAttrs.add(ptAttr);
            });
            ptAttrService.updateBatch(ptAttrs);

        }
        //同步到搜索引擎
        queueSender.send("Product:Search:importProduct.queue", ptProduct.getId().toString());
        //返回商品ID
        return ptProduct.getId();
    }


    @Override
    @Transactional
    public Object updateSale(Long id, Integer isSale, Long userId) {
        PtProduct ptProduct = new PtProduct();
        ptProduct.setId(id);
        ptProduct.setIsSale(isSale);
        ptProduct.setUpdateBy(userId);
        return this.update(ptProduct);
    }


    @Override
    @Transactional(readOnly = true)
    public Object getEdit(Long id, Long userId) {
        PtProduct ptProduct = ptProductMapper.selectById(id);
        ApiProductEditResult result = new ApiProductEditResult();

        Long categoryId = ptProduct.getPtCategoryId();
        Long categoryId2 = ptProduct.getPtCategoryId2();
        Long ptBrandId = ptProduct.getPtBrandId();
        Long ptSeriesId = ptProduct.getPtSeriesId();
        Long ptProductId = ptProduct.getId();
        String images = ptProduct.getImages();

        String buyRegion = ptProduct.getBuyRegion();
        String shopName = ptProduct.getShopName();
        String productDesc = ptProduct.getProductDesc();
        Integer expressType = ptProduct.getExpressType();
        Date expiresTime = ptProduct.getExpiresTime();
        Long dgTourId = ptProduct.getDgTourId();
        Integer productType = ptProduct.getProductType();

        PtCategory ptCategory = ptCategoryMapper.selectById(categoryId);
        PtCategory ptCategory2 = ptCategoryMapper.selectById(categoryId2);
        PtBrand ptBrand = ptBrandMapper.selectById(ptBrandId);
        PtSeries ptSeries = ptSeriesMapper.selectById(ptSeriesId);

        String categoryName = ptCategory.getName();
        String categoryName2 = ptCategory2.getName();
        String brandName = ptBrand.getName();
        String seriesName = ptSeries.getName();

        //SKU
        EntityWrapper<PtSku> skuEw = new EntityWrapper<>();
        skuEw.eq("pt_product_id", ptProductId);
        List<PtSku> ptSkuList = ptSkuMapper.selectList(skuEw);
        List<ApiSkuDto> skus = InstanceUtil.newArrayList();
        for (PtSku ptSku : ptSkuList) {
            ApiSkuDto apiSkuDto = new ApiSkuDto();
            apiSkuDto.setPrice(ptSku.getPrice());
            apiSkuDto.setStock(ptSku.getStock());

            Long ptSkuId = ptSku.getId();
            EntityWrapper<PtAttr> attrEw = new EntityWrapper<>();
            attrEw.eq("pt_product_id", ptProductId);
            attrEw.eq("pt_sku_id", ptSkuId);
            List<PtAttr> attrList = ptAttrMapper.selectList(attrEw);
            List<ApiAttrItemDto> attrItem = InstanceUtil.newArrayList();
            for (PtAttr ptAttr : attrList) {
                ApiAttrItemDto attrItemDto = new ApiAttrItemDto();
                attrItemDto.setAttrTypeId(ptAttr.getPtAttrTypeId());
                attrItemDto.setAttrTypeName(ptAttr.getPtAttrTypeName());
                attrItemDto.setAttrValueId(ptAttr.getPtAttrValueId());
                attrItemDto.setAttrValueName(ptAttr.getPtAttrValueName());
                attrItem.add(attrItemDto);
            }
            apiSkuDto.setAttrItem(attrItem);
            skus.add(apiSkuDto);
        }

        //类别品牌
        result.setCategoryId(ptProduct.getPtCategoryId());
        result.setCategoryId2(ptProduct.getPtCategoryId2());
        result.setBrandId(ptProduct.getPtBrandId());
        result.setSeriesId(ptProduct.getPtSeriesId());
        result.setCategoryName(categoryName);
        result.setCategoryName2(categoryName2);
        result.setBrandName(brandName);
        result.setSeriesName(seriesName);

        result.setId(ptProductId);
        //商品表标题
        result.setTitle(ptProduct.getTitle());
        //商品图片
        List<String> imageList = InstanceUtil.newArrayList();
        Collections.addAll(imageList, images.split(","));
        result.setImages(imageList);
        result.setBuyRegion(buyRegion);
        result.setShopName(shopName);
        result.setProductDesc(productDesc);
        result.setExpressType(expressType);
        result.setExpiresTime(expiresTime);
        result.setTourId(dgTourId);
        result.setProductType(productType);
        //SKU
        result.setSku(skus);
        return result;
    }


    @Override
    @Transactional
    public Object getDetail(Long id, Integer source, Long userId) {
        //商品信息
        ApiProductDetailDto productDetailDto = new ApiProductDetailDto();
        PtProduct ptProduct = ptProductMapper.selectById(id);

        if (ptProduct == null || ptProduct.getEnable() == 0) {
            return "商品不存在";
        }

        productDetailDto.setId(ptProduct.getId());
        productDetailDto.setTitle(ptProduct.getTitle());
        productDetailDto.setProductDesc(ptProduct.getProductDesc());
        productDetailDto.setPrice(ptProduct.getMinPrice());
        String images = ptProduct.getImages();
        List<String> imageList = InstanceUtil.newArrayList();
        Collections.addAll(imageList, images.split(","));
        productDetailDto.setImages(imageList);
        productDetailDto.setBuyRegion(ptProduct.getBuyRegion());
        productDetailDto.setShopName(ptProduct.getShopName());
        productDetailDto.setExpiresTime(ptProduct.getExpiresTime());
        productDetailDto.setOrderCount(ptProduct.getOrderCount());

        productDetailDto.setBuyRegionLogo(ptProduct.getBuyRegionLogo());
        productDetailDto.setBuyRegionCurrency(ptProduct.getBuyRegionCurrency());
        productDetailDto.setBuyRegionCurrencyCode(ptProduct.getBuyRegionCurrencyCode());
        productDetailDto.setCurrency(ptProduct.getCurrency());
        productDetailDto.setCurrencyCode(ptProduct.getCurrencyCode());
        productDetailDto.setIsSale(ptProduct.getIsSale());
        productDetailDto.setExpressType(ptProduct.getExpressType());

        //商品是否收藏
        productDetailDto.setIsFavorites(EnumConstants.YesOrNo.NO.getValue());
        if (userId != null) {
            List<DgFavorites> favorites = dgFavoritesService.getFavoritesByMemberId(userId, id, EnumConstants.CircleType.product.getValue());
            if (!CollectionUtils.isEmpty(favorites)) {
                productDetailDto.setIsFavorites(EnumConstants.YesOrNo.YES.getValue());
            }
        }

        ApiProductDetailResult result = new ApiProductDetailResult();
        result.setProduct(productDetailDto);

        //买手信息
        Long createBy = ptProduct.getCreateBy();
        UcSeller seller = ucSellerService.queryById(createBy);
        if (seller != null) {
            ApiProductSellerDto productSellerDto = new ApiProductSellerDto();
            productSellerDto.setId(seller.getId());
            productSellerDto.setUuid(seller.getUuid());
            productSellerDto.setNickName(seller.getNickName());
            productSellerDto.setAvatarRsurl(seller.getAvatarRsurl());
            productSellerDto.setOftenPlace(seller.getOftenPlace());
            productSellerDto.setPersonalizedSignature(seller.getPersonalizedSignature());
            result.setSeller(productSellerDto);
        }

        //行程信息
        ApiTourShortInfoResult tourShortInfoResult = tourService.getTourById(ptProduct.getDgTourId());
        result.setTour(tourShortInfoResult);

        //买手背包（最多6条）
        EntityWrapper<PtProduct> ew = new EntityWrapper<>();
        ew.ne("id_", id);
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("create_by", ptProduct.getCreateBy());
        ew.eq("is_sale", EnumConstants.YesOrNo.YES.getValue());
        ew.gt("expires_time", new Date());
        ew.orderBy("create_time", false);
        ew.last("LIMIT 6");
        List<PtProduct> ptProductList = ptProductMapper.selectList(ew);

        List<ApiProductSimpleDto> sellerProducts = InstanceUtil.newArrayList();
        for (PtProduct product : ptProductList) {
            ApiProductSimpleDto productSimpleDto = new ApiProductSimpleDto();
            productSimpleDto.setId(product.getId());
            productSimpleDto.setTitle(product.getTitle());
            productSimpleDto.setCover(product.getCover());
            productSimpleDto.setPrice(product.getMinPrice());
            productSimpleDto.setBuyRegion(product.getBuyRegion());

            productSimpleDto.setBuyRegionLogo(product.getBuyRegionLogo());
            productSimpleDto.setBuyRegionCurrency(product.getBuyRegionCurrency());
            productSimpleDto.setBuyRegionCurrencyCode(product.getBuyRegionCurrencyCode());
            productSimpleDto.setCurrency(product.getCurrency());
            productSimpleDto.setCurrencyCode(product.getCurrencyCode());

            sellerProducts.add(productSimpleDto);
        }
        result.setSellerProducts(sellerProducts);

        //记录直播商品点击数
        if (source != null && source == 1) {
            ptProductDao.increaseVideoClick(id);
        }

        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public Object search(String keyword) {
        if (StringUtils.isNotBlank(keyword)) {
            List<Long> categoryIdList = ptCategoryDao.selectIdByKeyword(keyword);
            List<Long> brandIdList = ptBrandDao.selectIdByKeyword(keyword);
            List<Long> productIdFilter = ptAttrDao.selectProductIdByKeyword(keyword);

            if (CollectionUtils.isEmpty(categoryIdList)) {
                categoryIdList = null;
            }
            if (CollectionUtils.isEmpty(brandIdList)) {
                brandIdList = null;
            }
            if (CollectionUtils.isEmpty(productIdFilter)) {
                productIdFilter = null;
            }

            return ptProductDao.selectIdByKeyword(keyword, categoryIdList, brandIdList, productIdFilter);
        }
        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public Object getSkus(Long id) {
        PtProduct ptProduct = ptProductMapper.selectById(id);
        if (ptProduct == null || ptProduct.getEnable() == 0) {
            return "商品不存在";
        }
        EntityWrapper<PtSku> ew = new EntityWrapper<>();
        ew.eq("pt_product_id", id)
                .eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        List<PtSku> ptSkuList = ptSkuMapper.selectList(ew);

        List<Long> skuIds = ptSkuList.stream().map(PtSku::getId).distinct().collect(Collectors.toList());

        Map<Long, String> colorNameMap = InstanceUtil.newHashMap();

        List<Long> colorIds = ptSkuList.stream().filter(s -> s.getPtSeriesColorId() != null).map(PtSku::getPtSeriesColorId).distinct().collect(Collectors.toList());
        if (!colorIds.isEmpty()) {
            List<PtSeriesColor> ptSeriesColors = ptSeriesColorService.queryList(colorIds);
            ptSeriesColors.forEach(s -> colorNameMap.put(s.getId(), s.getName()));
        }

        EntityWrapper<PtAttr> attrEw = new EntityWrapper<>();
        attrEw.eq("pt_product_id", id);
        attrEw.in("pt_sku_id", skuIds);
        attrEw.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        List<PtAttr> ptAttrList = ptAttrMapper.selectList(attrEw);

        List<ApiSkuListResult> list = ptSkuList.stream().map(v -> {
            ApiSkuListResult result = new ApiSkuListResult();
            result.setId(v.getId());
            result.setPrice(v.getPrice());
            result.setStock(v.getStock());

            Long colorId = v.getPtSeriesColorId();
            result.setColorAttrId(colorId);
            result.setColorAttrName(colorNameMap.get(colorId));

            Map<Long, ApiAttrItemDto> attrItem = InstanceUtil.newHashMap();

            List<PtAttr> attrs = ptAttrList.stream().filter(p -> p.getPtSkuId().equals(v.getId())).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(attrs)) {
                attrs.forEach(e -> {
                    ApiAttrItemDto attrItemDto = new ApiAttrItemDto();
                    attrItemDto.setAttrTypeId(e.getPtAttrTypeId());
                    attrItemDto.setAttrTypeName(e.getPtAttrTypeName());
                    attrItemDto.setAttrValueId(e.getPtAttrValueId());
                    attrItemDto.setAttrValueName(e.getPtAttrValueName());
                    attrItem.put(e.getPtAttrTypeId(), attrItemDto);
                    result.setAttrItem(attrItem);
                });
            }

            return result;
        }).collect(Collectors.toList());

        return list;
    }


    @Override
    @Transactional(readOnly = true)
    public Object getAllAttrs(Long id) {
        if (id == null) {
            return "id不能为空";
        }

        PtProduct ptProduct = ptProductMapper.selectById(id);
        if (ptProduct == null || ptProduct.getEnable() == 0) {
            return "商品不存在";
        }

        //属性
        EntityWrapper<PtAttr> ptAttrEw = new EntityWrapper<>();
        ptAttrEw.eq("pt_product_id", id);
        List<PtAttr> ptAttrs = ptAttrMapper.selectList(ptAttrEw);

        Map<Long, List<PtAttr>> typeIdGroup = ptAttrs.stream().collect(Collectors.groupingBy(PtAttr::getPtAttrTypeId));

        List<ApiAttrTypeDto> attr = InstanceUtil.newArrayList();
        if (typeIdGroup != null && typeIdGroup.size() > 0) {
            typeIdGroup.forEach((k, v) -> {
                ApiAttrTypeDto apiAttrTypeDto = new ApiAttrTypeDto();
                apiAttrTypeDto.setAttrTypeId(k);
                List<ApiAttrValueDto> attrValues = InstanceUtil.newArrayList();
                for (PtAttr ptAttr : v) {
                    apiAttrTypeDto.setAttrTypeName(ptAttr.getPtAttrTypeName());

                    ApiAttrValueDto apiAttrValueDto = new ApiAttrValueDto();
                    apiAttrValueDto.setAttrValueId(ptAttr.getPtAttrValueId());
                    apiAttrValueDto.setAttrValueName(ptAttr.getPtAttrValueName());
                    attrValues.add(apiAttrValueDto);
                }
                attrValues = attrValues.stream().distinct().collect(Collectors.toList());
                apiAttrTypeDto.setAttrValue(attrValues);
                attr.add(apiAttrTypeDto);
            });
        }
        ApiAttrListResult result = new ApiAttrListResult();
        result.setAttr(attr);

        EntityWrapper<PtSku> skuEw = new EntityWrapper<>();
        skuEw.eq("pt_product_id", id);
        List<PtSku> ptSkus = ptSkuMapper.selectList(skuEw);
        List<Long> colorIds = InstanceUtil.newArrayList();
        for (PtSku skus : ptSkus) {
            colorIds.add(skus.getPtSeriesColorId());
        }

        EntityWrapper<PtSeriesColor> colorEw = new EntityWrapper<>();
        colorEw.in("id_", colorIds);
        colorEw.eq("enable_", 1);
        List<PtSeriesColor> colorList = ptSeriesColorMapper.selectList(colorEw);
        List<ApiColorDto> colorDtos = InstanceUtil.newArrayList();
        for (PtSeriesColor color : colorList) {
            ApiColorDto colorDto = new ApiColorDto();
            colorDto.setAttrValueId(color.getId());
            colorDto.setAttrValueName(color.getName());
            colorDtos.add(colorDto);
        }

        result.setColorAttr(colorDtos);
        result.setPriceAttr(Collections.EMPTY_LIST);

        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public Object getList(ApiProductListParam params) {
        //排序（0：不排序，1时间降，2时间升，3销量降，4销量升,5价格升，6价格降）
        if (params.getSort() == null) {
            params.setSort(0);
        }

        Pagination<ApiProductListResult> resultPagination = new Pagination<>();

        String continentCode = params.getContinentCode();
        if (StringUtils.isNotBlank(continentCode)) {
            List<String> countryShortCodes = dgCountryService.getCountryShortCodesByZhouCode(continentCode);
            if (CollectionUtils.isEmpty(countryShortCodes)) {
                return "获取洲包含国家列表为空";
            }
            params.setShortCodes(countryShortCodes);
        }

        int totalCount = this.ptProductDao.getProductPageCount(params);
        RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());
        List<ApiProductListResult> page = ptProductDao.getProductPageList(params, rb);

        int pages = (totalCount % params.getSize() == 0) ? totalCount / params.getSize() : totalCount / params.getSize() + 1;
        resultPagination.setTotal(totalCount);
        resultPagination.setRecords(page);
        resultPagination.setCurrent(params.getCurrent());
        resultPagination.setSize(params.getSize());
        resultPagination.setPages(pages);
        return resultPagination;
    }


    @Override
    @Transactional(readOnly = true)
    public Pagination<ApiProductListByTourIdResult> getListByTourId(ApiProductListByTourIdParam params) {
        RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());
        EntityWrapper<PtProduct> ew = new EntityWrapper<>();
        ew.eq("dg_tour_id", params.getTourId());
        List<PtProduct> ptProductList = ptProductMapper.selectPage(rb, ew);

        List<ApiProductListByTourIdResult> resultList = InstanceUtil.newArrayList();
        for (PtProduct ptProduct : ptProductList) {
            ApiProductListByTourIdResult result = new ApiProductListByTourIdResult();
            result.setId(ptProduct.getId());
            result.setBuyRegion(ptProduct.getBuyRegion());
            result.setCover(ptProduct.getCover());
            result.setPrice(ptProduct.getMinPrice());
            result.setTitle(ptProduct.getTitle());
            result.setShopName(ptProduct.getShopName());
            result.setVideoClickCount(ptProduct.getVideoClickCount());
            resultList.add(result);
        }

        Pagination<ApiProductListByTourIdResult> page = new Pagination<>();
        page.setCurrent(params.getCurrent());
        page.setSize(params.getSize());
        page.setTotal(resultList.size());
        page.setRecords(resultList);

        return page;
    }


    @Override
    @Transactional
    @TxTransaction
    public boolean stopProductsByTourId(Long tourId) {
        try {
            ptProductDao.stopProductsByTourId(tourId, EnumConstants.YesOrNo.NO.getValue());
            return true;
        } catch (Exception e) {
            logger.error("下架商品出错，错误信息：{}", e.getMessage());
            return false;
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Pagination<PresellProductResult> getPresellList(PresellProductParam params) {
        RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());
        EntityWrapper<PtProduct> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("is_sale", EnumConstants.YesOrNo.YES.getValue());
        ew.gt("expires_time", new Date());
        ew.orderDesc(Lists.newArrayList("is_top", "order_count", "create_time"));
        if (StringUtils.isNotBlank(params.getBuyRegionShortCode())) {
            ew.eq("buy_region_short_code", params.getBuyRegionShortCode());
        }

        List<PresellProductResult> list = null;
        Integer count = ptProductMapper.selectCount(ew);
        if (count > 0) {
            List<PtProduct> ptProductList = ptProductMapper.selectPage(rb, ew);
            list = ptProductList.stream().map(o -> {
                PresellProductResult r = InstanceUtil.to(o, PresellProductResult.class);
                r.setPrice(o.getMinPrice());
                r.setTourId(o.getDgTourId());
                return r;
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        Pagination<PresellProductResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(params.getCurrent());
        page.setSize(params.getSize());
        page.setTotal(count);

        return page;
    }


    @Override
    @Transactional(readOnly = true)
    public Object updateFavorable(Long id, Float favorable) {
        PtProduct ptProduct = new PtProduct();
        ptProduct.setId(id);
        ptProduct.setFavorable(favorable);
        return ptProductMapper.updateById(ptProduct);
    }


    @Override
    @Transactional(readOnly = true)
    public List<PtProduct> getProductBySellerId(Long sellerId) {
        if (sellerId == null) {
            return null;
        }

        EntityWrapper<PtProduct> ew = new EntityWrapper<>();
        ew.eq("is_sale", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("create_by", sellerId);
        ew.orderBy("create_time", false);
        RowBounds rb = new RowBounds(0, 3);
        return mapper.selectPage(rb, ew);
    }


    @Override
    @Transactional
    public void cancelTopOverTime() {
        EntityWrapper<PtProduct> ew = new EntityWrapper<>();
        ew.where("top_end_time <= NOW()");
        ew.eq("is_sale", EnumConstants.Enable.ENABLE.getValue());
        ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        ew.eq("is_top", EnumConstants.Enable.ENABLE.getValue());

        PtProduct product = new PtProduct();
        product.setIsTop(EnumConstants.Enable.DISABLE.getValue());
        mapper.update(product, ew);
    }


    /**
     * 验证对象中的属性是否为空或为0
     */
    private boolean isAttrNull(PtAttrParam s) {
        if (s != null) {
            if (s.getPtAttrTypeId() == 0 || s.getPtAttrTypeId() == null || s.getPtAttrValueId() == 0 || s.getPtAttrValueId() == null)
                return false;
        }
        return true;
    }


    /**
     * 获取商品的属性集合
     * @param pmr        商品对象
     * @param ptAttrList 商品属性集合
     */
    private ProductManagerResult getProductManager(ProductManagerResult pmr, List<PtAttr> ptAttrList) {
        if (!CollectionUtils.isEmpty(ptAttrList)) {
            List<PtAttrDto> list = ptAttrList.stream().filter(p -> p.getPtProductId() == pmr.getProductId()).map(p -> setPtAttrDto(pmr, p)).collect(Collectors.toList());
            pmr.setPtAttrDtoList(list);
        }
        return pmr;
    }


    /**
     * 转换附加属性至返回参数
     * @param pmr 商品对象
     * @param pt  商品属性对象
     */
    private PtAttrDto setPtAttrDto(ProductManagerResult pmr, PtAttr pt) {
        PtAttrDto ptAttrDto = new PtAttrDto();
        ptAttrDto.setPtAttrTypeId(pt.getPtAttrTypeId());
        ptAttrDto.setPtAttrValueId(pt.getPtAttrValueId());
        ptAttrDto.setPtAttrTypeName(pt.getPtAttrTypeName());
        ptAttrDto.setPtAttrValueName(pt.getPtAttrValueName());
        return ptAttrDto;
    }

}
