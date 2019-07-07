package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.common.EnumConstants;
import com.xmyy.product.dao.PtSeriesAttrDao;
import com.xmyy.product.dto.*;
import com.xmyy.product.mapper.PtAttrMapper;
import com.xmyy.product.mapper.PtAttrTypeMapper;
import com.xmyy.product.mapper.PtSeriesColorMapper;
import com.xmyy.product.mapper.PtSeriesPriceMapper;
import com.xmyy.product.model.PtAttr;
import com.xmyy.product.model.PtAttrType;
import com.xmyy.product.model.PtSeriesColor;
import com.xmyy.product.model.PtSeriesPrice;
import com.xmyy.product.service.PtAttrService;
import com.xmyy.product.vo.ApiAttrListResult;
import com.xmyy.product.vo.ApiAttrSimpleListParam;
import com.xmyy.product.vo.ApiAttrSimpleListResult;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 模版属性  服务实现类
 * @author simon
 */
@Service(interfaceClass = PtAttrService.class)
@CacheConfig(cacheNames = "PtSeriesAttr")
public class PtAttrServiceImpl extends BaseServiceImpl<PtAttr, PtAttrMapper> implements PtAttrService {

    @Resource
    private PtSeriesColorMapper ptSeriesColorMapper;
    @Resource
    private PtSeriesPriceMapper ptSeriesPriceMapper;
    @Resource
    private PtAttrTypeMapper ptAttrTypeMapper;
    @Resource
    private PtAttrMapper ptAttrMapper;
    @Resource
    private PtSeriesAttrDao ptSeriesAttrDao;


    @Override
    @Transactional(readOnly = true)
    public Object getSeriesAttr(Long seriesId) {
        ApiAttrListResult result = new ApiAttrListResult();
        List<ApiAttrDto> seriesAttr = ptSeriesAttrDao.getSeriesAttr(seriesId);
        List<ApiAttrTypeDto> attrList = InstanceUtil.newArrayList();
        for (ApiAttrDto itemDto : seriesAttr) {
            Long attrTypeId = itemDto.getAttrTypeId();
            String attrTypeName = itemDto.getAttrTypeName();
            String attrValueIdStr = itemDto.getAttrValueIdStr();
            String attrValueNameStr = itemDto.getAttrValueNameStr();
            String[] valueIdArr = attrValueIdStr.split(",");
            String[] valueNameArr = attrValueNameStr.split(",");

            ApiAttrTypeDto attrTypeDto = new ApiAttrTypeDto();
            attrTypeDto.setAttrTypeId(attrTypeId);
            attrTypeDto.setAttrTypeName(attrTypeName);

            List<ApiAttrValueDto> attrValueDtoList = InstanceUtil.newArrayList();
            for (int i = 0; i < valueIdArr.length; i++) {
                ApiAttrValueDto attrValueDto = new ApiAttrValueDto();
                attrValueDto.setAttrValueId(Long.parseLong(valueIdArr[i]));
                attrValueDto.setAttrValueName(valueNameArr[i]);
                attrValueDtoList.add(attrValueDto);
            }

            attrTypeDto.setAttrValue(attrValueDtoList);
            attrList.add(attrTypeDto);
        }

        result.setAttr(attrList);

        EntityWrapper<PtSeriesColor> colorEw = new EntityWrapper<>();
        colorEw.eq("pt_series_id", seriesId);
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

        EntityWrapper<PtSeriesPrice> priceEw = new EntityWrapper<>();
        priceEw.eq("pt_series_id", seriesId);
        priceEw.eq("enable_", 1);
        List<PtSeriesPrice> priceList = ptSeriesPriceMapper.selectList(priceEw);
        List<ApiPriceDto> priceDtos = InstanceUtil.newArrayList();
        for (PtSeriesPrice price : priceList) {
            ApiPriceDto priceDto = new ApiPriceDto();
            BeanUtils.copyProperties(price, priceDto);
            priceDtos.add(priceDto);
        }

        result.setPriceAttr(priceDtos);
        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public Object getAttrList(ApiAttrSimpleListParam params) {
        EntityWrapper<PtAttrType> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("pt_category_id", params.getCategoryId());
        ew.eq("pt_category_id2", params.getCategoryId2());
        if (params.getBrandId() != null && params.getBrandId() > 0) {
            ew.eq("pt_brand_id", params.getBrandId());
        } else {
            ew.isNull("pt_brand_id");
        }

        List<PtAttrType> ptAttrTypes = ptAttrTypeMapper.selectList(ew);
        List<ApiAttrSimpleListResult> resultList = InstanceUtil.newArrayList();
        if (!CollectionUtils.isEmpty(ptAttrTypes)) {
            ptAttrTypes.forEach(v -> {
                ApiAttrSimpleListResult result = new ApiAttrSimpleListResult();
                result.setAttrTypeId(v.getId());
                result.setAttrTypeName(v.getName());
                resultList.add(result);
            });
        }
        return resultList;
    }


    @Override
    @Transactional(readOnly = true)
    public List<String> getAttrValueNameByProductId(Long id) {
        EntityWrapper<PtAttr> ew = new EntityWrapper<>();
        ew.eq("pt_product_id", id);
        ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        List<PtAttr> ptAttrs = ptAttrMapper.selectList(ew);
        if (!CollectionUtils.isEmpty(ptAttrs)) {
            return ptAttrs.stream().map(PtAttr::getPtAttrValueName).distinct().collect(Collectors.toList());
        }
        return new ArrayList<>();
    }


    @Override
    @Transactional(readOnly = true)
    public Map<Long, List<String>> getAttrValueNameByProductIds(List<Long> ids) {
        EntityWrapper<PtAttr> ew = new EntityWrapper<>();
        ew.in("pt_product_id", ids);
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        List<PtAttr> ptAttrs = ptAttrMapper.selectList(ew);
        if (!CollectionUtils.isEmpty(ptAttrs)) {
            Map<Long, List<PtAttr>> groupMap = ptAttrs.parallelStream().collect(Collectors.groupingBy(PtAttr::getPtProductId));
            Map<Long, List<String>> resultMap = InstanceUtil.newHashMap();
            for (Map.Entry<Long, List<PtAttr>> entry : groupMap.entrySet()) {
                List<PtAttr> value = entry.getValue();
                List<String> collect = value.stream().map(PtAttr::getPtAttrValueName).distinct().collect(Collectors.toList());
                resultMap.put(entry.getKey(), collect);
            }
            return resultMap;
        }
        return Collections.EMPTY_MAP;
    }

}
