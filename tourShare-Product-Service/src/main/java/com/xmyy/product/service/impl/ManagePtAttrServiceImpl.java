package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.common.EnumConstants;
import com.xmyy.product.dto.AttrAddDto;
import com.xmyy.product.dto.AttrDeleteDto;
import com.xmyy.product.dto.AttrValueDto;
import com.xmyy.product.mapper.PtAttrMapper;
import com.xmyy.product.mapper.PtAttrTypeMapper;
import com.xmyy.product.mapper.PtAttrValueMapper;
import com.xmyy.product.mapper.PtSeriesAttrMapper;
import com.xmyy.product.model.PtAttr;
import com.xmyy.product.model.PtAttrType;
import com.xmyy.product.model.PtAttrValue;
import com.xmyy.product.model.PtSeriesAttr;
import com.xmyy.product.service.ManagePtAttrService;
import com.xmyy.product.service.PtAttrService;
import com.xmyy.product.service.PtAttrTypeService;
import com.xmyy.product.service.PtAttrValueService;
import com.xmyy.product.vo.AttrItemAddParam;
import com.xmyy.product.vo.AttrItemListParam;
import com.xmyy.product.vo.AttrModifyParam;
import com.xmyy.product.vo.AttrTypeAndValueListResult;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.exception.BizException;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 模版属性后台管理  服务实现类
 * @author simon
 */
@Service(interfaceClass = ManagePtAttrService.class)
@CacheConfig(cacheNames = "PtSeriesAttr")
public class ManagePtAttrServiceImpl extends BaseServiceImpl<PtAttr, PtAttrMapper> implements ManagePtAttrService {

    @Resource
    private PtSeriesAttrMapper ptSeriesAttrMapper;
    @Resource
    private PtAttrTypeMapper ptAttrTypeMapper;
    @Resource
    private PtAttrValueMapper ptAttrValueMapper;
    @Resource
    private PtAttrMapper ptAttrMapper;
    @Reference
    private PtAttrTypeService ptAttrTypeService;
    @Reference
    private PtAttrValueService ptAttrValueService;


    @Override
    @Transactional
    public Object addAttrItem(AttrItemAddParam params, Long currUser) {
        PtAttrType ptAttrType = new PtAttrType();
        ptAttrType.setPtCategoryId(params.getCategoryId());
        ptAttrType.setPtCategoryId2(params.getCategoryId2());
        if (params.getBrandId() != null && params.getBrandId() > 0) {
            ptAttrType.setPtBrandId(params.getBrandId());
        }
        ptAttrType.setName(params.getAttrType());
        ptAttrType.setEnable(EnumConstants.Enable.ENABLE.getValue());
        PtAttrType attrType = ptAttrTypeMapper.selectOne(ptAttrType);
        if (attrType != null) {
            return "已存在该属性";
        }
        ptAttrType.setShowType(params.getShowType());
        ptAttrType = ptAttrTypeService.update(ptAttrType);

        Set<String> attrValues = params.getAttrValue();
        for (String attrValue : attrValues) {
            PtAttrValue ptAttrValue = new PtAttrValue();
            ptAttrValue.setPtAttrTypeId(ptAttrType.getId());
            ptAttrValue.setName(attrValue.replace(",", "")); //属性值去逗号
            ptAttrValue.setCreateBy(currUser);
            ptAttrValue.setEnable(EnumConstants.YesOrNo.YES.getValue());
            ptAttrValueService.update(ptAttrValue);
        }

        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryAttrItem(AttrItemListParam params) {
        //查询类目下的属性
        EntityWrapper<PtAttrType> ew = new EntityWrapper<>();
        ew.eq("pt_category_id", params.getCategoryId());
        ew.eq("pt_category_id2", params.getCategoryId2());
        ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        if (params.getBrandId() != null) {
            ew.eq("pt_brand_id", params.getBrandId());
        }
        List<PtAttrType> attrTypeList = ptAttrTypeMapper.selectList(ew);

        //查询属性下对应的属性值
        List<Long> attrTypeIds = attrTypeList.stream()
                .map(PtAttrType::getId)
                .collect(Collectors.toCollection(ArrayList::new));
        EntityWrapper<PtAttrValue> valueEw = new EntityWrapper<>();
        valueEw.in("pt_attr_type_id", attrTypeIds);
        valueEw.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        List<PtAttrValue> attrValueList = ptAttrValueMapper.selectList(valueEw);

        List<AttrTypeAndValueListResult> result = attrTypeList.stream().map(attrType -> {
            AttrTypeAndValueListResult resultList = new AttrTypeAndValueListResult();
            resultList.setId(attrType.getId());
            resultList.setName(attrType.getName());
            resultList.setShowType(attrType.getShowType());
            List<AttrValueDto> collect = attrValueList.stream()
                    .filter(o -> o.getPtAttrTypeId().equals(attrType.getId()))
                    .map(o -> InstanceUtil.to(o, AttrValueDto.class))
                    .collect(Collectors.toList());
            resultList.setAttrValue(collect);
            return resultList;
        }).collect(Collectors.toList());

        return result;
    }


    @Override
    @Transactional
    public Object saveAttrItemModify(AttrModifyParam params, Long currUser) {
        //删除项
        List<AttrDeleteDto> delItem = params.getDelItem();
        //增加项
        List<AttrAddDto> addItem = params.getAddItem();

        //删除属性类型、属性值
        for (AttrDeleteDto deleteDto : delItem) {
            Long attrTypeId = deleteDto.getAttrTypeId();
            List<Long> attrValueIds = deleteDto.getAttrValueIds();
            if (CollectionUtils.isEmpty(attrValueIds)) {   //删除属性类型
                if (attrTypeId != null && attrTypeId > 0) {
                    //判断系列有没有用到该属性类型
                    EntityWrapper<PtSeriesAttr> seriesAttrEw = new EntityWrapper<>();
                    seriesAttrEw.eq("pt_attr_type_id", attrTypeId);
                    seriesAttrEw.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
                    if (ptSeriesAttrMapper.selectCount(seriesAttrEw) > 0) {
                        throw new BizException("有系列使用了该属性类型，不能删除");
                    }
                    //判断商品有没有用到该属性类型
                    EntityWrapper<PtAttr> attrEw = new EntityWrapper<>();
                    attrEw.eq("pt_attr_type_id", attrTypeId);
                    attrEw.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
                    if (ptAttrMapper.selectCount(attrEw) > 0) {
                        throw new BizException("有商品使用了该属性，不能删除");
                    }

                    //属性类型设置为不可用
                    PtAttrType ptAttrType = new PtAttrType();
                    ptAttrType.setId(attrTypeId);
                    ptAttrType.setEnable(EnumConstants.Enable.DISABLE.getValue());
                    ptAttrTypeService.update(ptAttrType);

                    //类型下面的属性值设置为不可用
                    EntityWrapper<PtAttrValue> ptAttrValueEw = new EntityWrapper<>();
                    ptAttrValueEw.eq("pt_attr_type_id", attrTypeId);
                    List<PtAttrValue> ptAttrValues = ptAttrValueMapper.selectList(ptAttrValueEw);
                    for (PtAttrValue ptAttrValue : ptAttrValues) {
                        ptAttrValue.setEnable(EnumConstants.Enable.DISABLE.getValue());
                    }
                    ptAttrValueService.updateBatch(ptAttrValues);
                }
            } else { //删除属性类型下的属性值
                for (Long attrValueId : attrValueIds) {
                    if (attrValueId != null && attrValueId > 0) {
                        //判断系列有没有用到该属性值
                        EntityWrapper<PtSeriesAttr> seriesAttrEw = new EntityWrapper<>();
                        seriesAttrEw.eq("pt_attr_value_id", attrValueId)
                                .eq("enable_", EnumConstants.Enable.ENABLE.getValue());
                        if (ptSeriesAttrMapper.selectCount(seriesAttrEw) > 0) {
                            throw new BizException("有系列使用了该属性值，不能删除");
                        }

                        //判断商品有没有用到该属性值
                        EntityWrapper<PtAttr> attrEw = new EntityWrapper<>();
                        attrEw.eq("pt_attr_value_id", attrValueId);
                        attrEw.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
                        if (ptAttrMapper.selectCount(attrEw) > 0) {
                            throw new BizException("有商品使用了该属性值，不能删除");
                        }

                        PtAttrValue ptSeriesAttrValue = new PtAttrValue();
                        ptSeriesAttrValue.setId(attrValueId);
                        ptSeriesAttrValue.setEnable(EnumConstants.Enable.DISABLE.getValue());
                        ptAttrValueService.update(ptSeriesAttrValue);
                    }
                }
            }
        }

        //添加新属性值
        for (AttrAddDto addDto : addItem) {
            Long attrTypeId = addDto.getAttrTypeId();
            List<String> newValueNameList = addDto.getAttrValueNames().stream().distinct().collect(Collectors.toList());

            for (String attrValueName : newValueNameList) {
                EntityWrapper<PtAttrValue> ew = new EntityWrapper<>();
                ew.eq("pt_attr_type_id", attrTypeId);
                ew.eq("name", attrValueName);
                ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
                if (ptAttrValueMapper.selectCount(ew) > 0) {
                    throw new BizException("属性值[" + attrValueName + "]已存在，不能重复添加");
                }

                PtAttrValue ptSeriesAttrValue = new PtAttrValue();
                ptSeriesAttrValue.setPtAttrTypeId(attrTypeId);
                ptSeriesAttrValue.setName(attrValueName);
                ptSeriesAttrValue.setCreateBy(currUser);
                ptAttrValueService.update(ptSeriesAttrValue);
            }
        }
        return null;
    }

}
