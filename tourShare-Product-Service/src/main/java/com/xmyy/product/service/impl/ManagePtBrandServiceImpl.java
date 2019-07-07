package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.BizSequenceUtils;
import com.xmyy.common.util.ListUtil;
import com.xmyy.product.dao.PtBrandDao;
import com.xmyy.product.dto.ApiBrandCategoryDto;
import com.xmyy.product.mapper.*;
import com.xmyy.product.model.*;
import com.xmyy.product.service.ManagePtBrandService;
import com.xmyy.product.service.PtCategoryBrandService;
import com.xmyy.product.service.PtCategoryService;
import com.xmyy.product.vo.ApiBrandListResult;
import com.xmyy.product.vo.BrandListParam;
import com.xmyy.product.vo.BrandListResult;
import com.xmyy.product.vo.BrandSaveParam;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.exception.BizException;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 品牌后台管理  服务实现类
 * @author simon
 */
@Service(interfaceClass = ManagePtBrandService.class)
@CacheConfig(cacheNames = "PtBrand")
public class ManagePtBrandServiceImpl extends BaseServiceImpl<PtBrand, PtBrandMapper> implements ManagePtBrandService {

    @Resource
    PtBrandMapper ptBrandMapper;
    @Resource
    PtCategoryBrandMapper ptCategoryBrandMapper;
    @Resource
    PtCategoryBrandService categoryBrandService;
    @Resource
    PtSeriesMapper ptSeriesMapper;
    @Resource
    PtAttrTypeMapper ptAttrTypeMapper;
    @Resource
    PtProductMapper ptProductMapper;
    @Reference
    PtCategoryService ptCategoryService;
    @Resource
    PtBrandDao ptBrandDao;


    @Override
    @Transactional(readOnly = true)
    public Object query(BrandListParam params) {
        RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());
        List<BrandListResult> brandList = ptBrandDao.getBrandIdPageList(params, rb);
        Map<Long, String> categoryNameMap = ptCategoryService.getCategoryNameMap();

        for (BrandListResult brand : brandList) {
            PtBrand select = queryById(brand.getId());
            brand.setId(select.getId());
            brand.setName(select.getName());
            brand.setInitial(select.getInitial());
            brand.setBrandNo(select.getBrandNo());

            EntityWrapper<PtCategoryBrand> ew = new EntityWrapper<>();
            ew.eq("pt_brand_id", brand.getId());
            ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
            List<PtCategoryBrand> categoryBrands = ptCategoryBrandMapper.selectList(ew);

            List<ApiBrandCategoryDto> dtos = InstanceUtil.newArrayList();
            for (PtCategoryBrand cb : categoryBrands) {
                ApiBrandCategoryDto dto = new ApiBrandCategoryDto();
                dto.setProductCount(cb.getProductCount());
                dto.setCategoryId2(cb.getPtCategoryId2());
                dto.setCategoryName2(categoryNameMap.get(cb.getPtCategoryId2()));
                dtos.add(dto);
            }
            brand.setBrandCategory(dtos);
        }

        int count = ptBrandDao.getBrandIdPageCount(params);
        Pagination<BrandListResult> pageResult = new Pagination<>();
        pageResult.setTotal(count);
        pageResult.setPages(count % params.getSize() == 0 ? count / params.getSize() : count / params.getSize() + 1);
        pageResult.setRecords(brandList);
        pageResult.setCurrent(params.getCurrent());
        pageResult.setSize(params.getSize());

        return pageResult;
    }


    @Override
    @Transactional
    public Object save(BrandSaveParam params) throws BizException {
        Long id = params.getId();
        if (id != null && id > 0) {  //编辑
            PtBrand brand = ptBrandMapper.selectById(id);
            if (brand == null || brand.getEnable() == 0) {
                return "不存在该品牌";
            }

            EntityWrapper<PtBrand> checkEw = new EntityWrapper<>();
            checkEw.eq("name", params.getName());
            checkEw.ne("id_", id);
            checkEw.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
            if (ptBrandMapper.selectCount(checkEw) > 0) {
                return "已存在该名称的品牌";
            }

            //查询品牌原来关联的类目
            EntityWrapper<PtCategoryBrand> ew = new EntityWrapper<>();
            ew.eq("pt_brand_id", id);
            ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
            List<PtCategoryBrand> categoryBrand_old = ptCategoryBrandMapper.selectList(ew);
            //品牌原来关联的二级类目ID
            List<Long> categoryIdList_old = categoryBrand_old.stream()
                    .map(PtCategoryBrand::getPtCategoryId2)
                    .collect(Collectors.toCollection(ArrayList::new));

            //品牌修改后需要关联的二级类目ID
            List<Long> categoryIdList_new = params.getCategoryList2();

            //品牌新关联的二级类目ID
            List<Long> addList = ListUtil.getAddaListThanbList(categoryIdList_new, categoryIdList_old);

            //品牌减少关联的二级类目ID
            List<Long> reduceList = ListUtil.getReduceaListThanbList(categoryIdList_new, categoryIdList_old);

            //删除中间表减少的关联关系
            for (Long categoryId2 : reduceList) {
                //较验系列、系列属性、商品是否用到
                EntityWrapper<PtSeries> ptSeriesEw = new EntityWrapper<>();
                ptSeriesEw.eq("pt_brand_id", id);
                ptSeriesEw.eq("pt_category_id2", categoryId2);
                ptSeriesEw.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
                if (ptSeriesMapper.selectCount(ptSeriesEw) > 0) {
                    throw new BizException("该品牌下有商品系列，不能删除");
                }

                EntityWrapper<PtAttrType> ptSeriesAttrTypeEw = new EntityWrapper<>();
                ptSeriesAttrTypeEw.eq("pt_brand_id", id);
                ptSeriesAttrTypeEw.eq("pt_category_id2", categoryId2);
                ptSeriesAttrTypeEw.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
                if (ptAttrTypeMapper.selectCount(ptSeriesAttrTypeEw) > 0) {
                    throw new BizException("该品牌下有商品系列属性，不能删除");
                }

                EntityWrapper<PtProduct> ptProductEw = new EntityWrapper<>();
                ptProductEw.eq("pt_brand_id", id);
                ptProductEw.eq("pt_category_id2", categoryId2);
                ptProductEw.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
                if (ptProductMapper.selectCount(ptProductEw) > 0) {
                    throw new BizException("该品牌下有商品，不能删除");
                }

                EntityWrapper<PtCategoryBrand> ptCategoryBrandEw = new EntityWrapper<>();
                ptCategoryBrandEw.eq("pt_brand_id", id);
                ptCategoryBrandEw.eq("pt_category_id2", categoryId2);
                ptCategoryBrandEw.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
                List<PtCategoryBrand> ptCategoryBrands = ptCategoryBrandMapper.selectList(ptCategoryBrandEw);
                for (PtCategoryBrand ptCategoryBrand : ptCategoryBrands) {
                    categoryBrandService.del(ptCategoryBrand.getId(), null);
                }
            }

            //添加中间表新增的关联关系
            Map<Long, Long> parentIdMap = ptCategoryService.getParentIdMap();
            for (Long categoryId2 : addList) {
                PtCategoryBrand ptCategoryBrand = new PtCategoryBrand();
                ptCategoryBrand.setPtBrandId(id);
                ptCategoryBrand.setPtCategoryId(parentIdMap.get(categoryId2));
                ptCategoryBrand.setPtCategoryId2(categoryId2);
                categoryBrandService.update(ptCategoryBrand);
            }

            brand.setName(params.getName());
            brand.setInitial(params.getInitial().trim().substring(0, 1).toUpperCase());
            brand.setLogo(params.getLogo());
            brand.setEnName(params.getEnName());
            update(brand);

        } else {  //新增
            EntityWrapper<PtBrand> ew = new EntityWrapper<>();
            ew.eq("name", params.getName());
            if (ptBrandMapper.selectCount(ew) > 0) {
                return "已存在该品牌名称";
            }

            PtBrand ptBrand = new PtBrand();
            ptBrand.setName(params.getName());
            ptBrand.setInitial(params.getInitial().trim().substring(0, 1).toUpperCase());
            ptBrand.setLogo(params.getLogo());
            ptBrand.setEnName(params.getEnName());
            ptBrand.setBrandNo(BizSequenceUtils.generateBizNo(EnumConstants.BizCode.BrandNo));
            update(ptBrand);

            PtCategoryBrand ptCategoryBrand = new PtCategoryBrand();
            ptCategoryBrand.setPtBrandId(ptBrand.getId());
            Map<Long, Long> parentIdMap = ptCategoryService.getParentIdMap();
            for (Long categoryId2 : params.getCategoryList2()) {
                ptCategoryBrand.setPtCategoryId(parentIdMap.get(categoryId2));
                ptCategoryBrand.setPtCategoryId2(categoryId2);
                categoryBrandService.update(ptCategoryBrand);
            }
        }
        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public Object edit(Long id) {
        PtBrand ptBrand = ptBrandMapper.selectById(id);
        if (ptBrand == null) {
            return "品牌不存在";
        }

        BrandListResult result = new BrandListResult();
        result.setId(ptBrand.getId());
        result.setName(ptBrand.getName());
        result.setEnName(ptBrand.getEnName());
        result.setInitial(ptBrand.getInitial());
        result.setLogo(ptBrand.getLogo());

        EntityWrapper<PtCategoryBrand> ew = new EntityWrapper<>();
        ew.eq("pt_brand_id", id);
        ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        List<PtCategoryBrand> ptCategoryBrandList = ptCategoryBrandMapper.selectList(ew);

        List<ApiBrandCategoryDto> brandCategoryDtos = InstanceUtil.newArrayList();
        Map<Long, String> categoryNameMap = ptCategoryService.getCategoryNameMap();
        for (PtCategoryBrand ptCategoryBrand : ptCategoryBrandList) {
            ApiBrandCategoryDto dto = new ApiBrandCategoryDto();
            Long categoryId2 = ptCategoryBrand.getPtCategoryId2();
            dto.setCategoryId2(categoryId2);
            dto.setCategoryName2(categoryNameMap.get(categoryId2));
            brandCategoryDtos.add(dto);
        }
        result.setBrandCategory(brandCategoryDtos);

        return result;
    }


    @Override
    @Transactional
    public Object deleteBrand(Long id) {
        if (id == null) {
            return "id不能为空";
        }
        PtBrand brand = queryById(id);
        if (brand == null) {
            return "查询不到改品牌";
        }

        EntityWrapper<PtSeries> ptSeriesEw = new EntityWrapper<>();
        ptSeriesEw.eq("pt_brand_id", id);
        ptSeriesEw.eq("enable_", 1);
        Integer ptSeriesCount = ptSeriesMapper.selectCount(ptSeriesEw);
        if (ptSeriesCount > 0) {
            return "该品牌下有商品系列，不能删除";
        }

        EntityWrapper<PtAttrType> ptSeriesAttrTypeEw = new EntityWrapper<>();
        ptSeriesAttrTypeEw.eq("pt_brand_id", id);
        ptSeriesAttrTypeEw.eq("enable_", 1);
        Integer ptSeriesAttrTypeCount = ptAttrTypeMapper.selectCount(ptSeriesAttrTypeEw);
        if (ptSeriesAttrTypeCount > 0) {
            return "该品牌下有商品系列属性，不能删除";
        }

        EntityWrapper<PtProduct> ptProductEw = new EntityWrapper<>();
        ptProductEw.eq("pt_brand_id", id);
        ptProductEw.eq("enable_", 1);
        Integer ptProductCount = ptProductMapper.selectCount(ptProductEw);
        if (ptProductCount > 0) {
            return "该品牌下有商品，不能删除";
        }

        brand.setEnable(EnumConstants.Enable.DISABLE.getValue());
        update(brand);

        //将品牌关联类目的中间表数据设置enable_=0
        EntityWrapper<PtCategoryBrand> ptCategoryBrandEw = new EntityWrapper<>();
        ptCategoryBrandEw.eq("pt_brand_id", id);
        ptCategoryBrandEw.eq("enable_", 1);
        List<PtCategoryBrand> ptCategoryBrands = ptCategoryBrandMapper.selectList(ptCategoryBrandEw);
        ptCategoryBrands.forEach(ptCategoryBrand -> {
            ptCategoryBrand.setEnable(EnumConstants.Enable.DISABLE.getValue());
            categoryBrandService.update(ptCategoryBrand);
        });

        return brand;
    }


    @Override
    @Transactional(readOnly = true)
    public Object getList(Long categoryId, Long categoryId2, String initial) {
        List<ApiBrandListResult> resultList;
        if (categoryId == null && categoryId2 == null) {
            resultList = ptBrandDao.getSimpleBrandAllList(initial);
        } else {
            resultList = ptBrandDao.getSimpleBrandList(categoryId, categoryId2, initial);
            resultList = resultList.stream().distinct().collect(Collectors.toList());
        }
        return resultList;
    }

}
