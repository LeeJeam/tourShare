package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.BizSequenceUtils;
import com.xmyy.product.dao.PtSeriesAttrDao;
import com.xmyy.product.dao.PtSeriesDao;
import com.xmyy.product.dto.*;
import com.xmyy.product.mapper.PtSeriesAttrMapper;
import com.xmyy.product.mapper.PtSeriesMapper;
import com.xmyy.product.model.PtSeries;
import com.xmyy.product.model.PtSeriesAttr;
import com.xmyy.product.model.PtSeriesColor;
import com.xmyy.product.model.PtSeriesPrice;
import com.xmyy.product.service.PtCategoryService;
import com.xmyy.product.service.PtSeriesColorService;
import com.xmyy.product.service.PtSeriesPriceService;
import com.xmyy.product.service.PtSeriesService;
import com.xmyy.product.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品模版  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = PtSeriesService.class)
@CacheConfig(cacheNames = "PtSeries")
public class PtSeriesServiceImpl extends BaseServiceImpl<PtSeries, PtSeriesMapper> implements PtSeriesService {

    @Resource
    private PtSeriesMapper ptSeriesMapper;
    @Resource
    private PtSeriesAttrMapper ptSeriesAttrMapper;
    @Resource
    private PtSeriesAttrDao ptSeriesAttrDao;
    @Resource
    private PtSeriesDao ptSeriesDao;
    @Reference
    private PtCategoryService ptCategoryService;
    @Reference
    private PtSeriesColorService seriesColorService;
    @Reference
    private PtSeriesPriceService seriesPriceService;


    @Override
    @Transactional
    public Object add(SeriesAddParam params) {
        EntityWrapper<PtSeries> ew = new EntityWrapper<>();
        ew.eq("name", params.getName());
        if (StringUtils.isNotEmpty(params.getAlias())) {
            ew.or("alias", params.getAlias());
        }
        Integer ret = ptSeriesMapper.selectCount(ew);
        if (ret > 0) {
            return "已存在该名称的商品";
        }

        PtSeries ptSeries = new PtSeries();
        ptSeries.setPtCategoryId(params.getCategoryId());
        ptSeries.setPtCategoryId2(params.getCategoryId2());
        ptSeries.setPtBrandId(params.getBrandId());
        ptSeries.setName(params.getName());

        List<String> paramsImages = params.getImages();
        String images = "";
        if (!CollectionUtils.isEmpty(paramsImages)) {
            StringUtils.join(paramsImages, ",");
        }
        ptSeries.setImages(images);
        ptSeries.setAlias(params.getAlias());
        ptSeries.setSeriesNo(BizSequenceUtils.generateBizNo(EnumConstants.BizCode.SeriesNo));
        update(ptSeries);

        Long ptSeriesId = ptSeries.getId();
        //处理属性
        List<SeriesAttrDto> tplAttrDtoList = params.getAttrItem();
        for (SeriesAttrDto tplAttrDto : tplAttrDtoList) {
            Long attrTypeId = tplAttrDto.getId();

            List<AttrValueDto> attrValue = tplAttrDto.getAttrValue();
            for (AttrValueDto attrValueDto : attrValue) {
                Long attrValueId = attrValueDto.getId();

                PtSeriesAttr ptSeriesAttr = new PtSeriesAttr();
                ptSeriesAttr.setPtSeriesId(ptSeriesId);
                ptSeriesAttr.setPtAttrTypeId(attrTypeId);
                ptSeriesAttr.setPtAttrValueId(attrValueId);
                ptSeriesAttrMapper.insert(ptSeriesAttr);
            }
        }

        List<SeriesColorDto> colorItem = params.getColorItem();
        for (SeriesColorDto tplColorDto : colorItem) {
            PtSeriesColor ptSeriesColor = new PtSeriesColor();
            ptSeriesColor.setPtSeriesId(ptSeriesId);
            ptSeriesColor.setName(tplColorDto.getName());
            ptSeriesColor.setImage(tplColorDto.getImage());
            seriesColorService.update(ptSeriesColor);
        }

        List<SeriesPriceDto> priceItem = params.getPriceItem();
        for (SeriesPriceDto priceDto : priceItem) {
            PtSeriesPrice ptSeriesPrice = new PtSeriesPrice();
            ptSeriesPrice.setPtSeriesId(ptSeriesId);
            ptSeriesPrice.setRegion(priceDto.getRegion());
            ptSeriesPrice.setUnit(priceDto.getUnit());
            List<SeriesPriceDto.Shop> shops = priceDto.getShops();
            for (SeriesPriceDto.Shop shop : shops) {
                ptSeriesPrice.setShop(shop.getShop());
                ptSeriesPrice.setPrice(shop.getPrice());
                seriesPriceService.update(ptSeriesPrice);
            }
        }
        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public Object getList(Long categoryId, Long categoryId2, Long brandId) {
        EntityWrapper<PtSeries> ew = new EntityWrapper<>();
        ew.eq("pt_category_id", categoryId);
        ew.eq("pt_category_id2", categoryId2);
        ew.eq("pt_brand_id", brandId);
        List<PtSeries> ptSeriesList = ptSeriesMapper.selectList(ew);

        List<ApiSeriesListResult> resultList = InstanceUtil.newArrayList();
        for (PtSeries ptSeries : ptSeriesList) {
            ApiSeriesListResult result = new ApiSeriesListResult();
            result.setId(ptSeries.getId());
            result.setName(ptSeries.getName());
            resultList.add(result);
        }
        return resultList;
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryManagerPtSeriesList(ManageSeriesParam param) {
        if (!CollectionUtils.isEmpty(param.getPtAttrParams())) {
            List<PtAttrParam> pttrList = param.getPtAttrParams().stream()
                    .filter(this::isAttrNull).collect(Collectors.toList());
            param.setPtAttrParams(pttrList);
        }
        List<ManagerPtSeriesListResult> resultList = ptSeriesAttrDao.queryManagerPtSeriesList(param);
        if (!CollectionUtils.isEmpty(resultList)) {
            List<Long> seriesIds = resultList.stream().map(ManagerPtSeriesListResult::getSeriesId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(seriesIds)) {
                List<PtSeriesAttrDto> ptSeriesAttrList = ptSeriesAttrDao.getSeriesAttrList(seriesIds);
                resultList = resultList.stream().map(s -> getSeriesManager(s, ptSeriesAttrList)).collect(Collectors.toList());
            }
        }

        Integer seriesTotal = ptSeriesAttrDao.countSeriesTotal();
        Pagination<ManagerPtSeriesListResult> page = new Pagination<>();
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        page.setTotal(ptSeriesAttrDao.countManagerPtSeries(param));
        page.setRecords(resultList);
        page.setPages(seriesTotal);
        return page;
    }


    @Override
    @Transactional(readOnly = true)
    public Object statistic() {
        List<SeriesStatisticDto> statisticList = ptSeriesDao.getStatisticByCategory();
        Map<Long, String> categoryNameMap = ptCategoryService.getCategoryNameMap();

        Integer totalCount = 0;
        List<SeriesStatisticResult.CategoryStatistic> list = InstanceUtil.newArrayList();
        for (SeriesStatisticDto dto : statisticList) {
            SeriesStatisticResult.CategoryStatistic categoryStatistic = new SeriesStatisticResult.CategoryStatistic();
            categoryStatistic.setCategoryId(dto.getCategoryId());
            categoryStatistic.setCategoryName(categoryNameMap.get(dto.getCategoryId()));
            categoryStatistic.setCount(dto.getNum());
            list.add(categoryStatistic);

            totalCount += dto.getNum();
        }

        SeriesStatisticResult result = new SeriesStatisticResult();
        result.setTotalCount(totalCount);
        result.setStatistics(list);

        return result;
    }


    /**
     * 验证对象中的属性是否为空或为0
     */
    private boolean isAttrNull(PtAttrParam s) {
        if (s != null) {
            return s.getPtAttrTypeId() != 0 && s.getPtAttrTypeId() != null && s.getPtAttrValueId() != 0 && s.getPtAttrValueId() != null;
        }
        return true;
    }

    /**
     * 获取系列的属性集合
     *
     * @param pmr        商品对象
     * @param ptAttrList 商品属性集合
     * @return
     */
    private ManagerPtSeriesListResult getSeriesManager(ManagerPtSeriesListResult pmr, List<PtSeriesAttrDto> ptAttrList) {
        if (!CollectionUtils.isEmpty(ptAttrList)) {
            List<PtSeriesAttrDto> list = ptAttrList.stream().filter(p -> p.getPtSeriesId() == pmr.getSeriesId()).collect(Collectors.toList());
            pmr.setPtSeriesAttrDtoList(list);
        }
        return pmr;
    }


}
