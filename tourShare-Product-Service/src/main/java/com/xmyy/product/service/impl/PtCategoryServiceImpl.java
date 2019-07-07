package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.common.EnumConstants;
import com.xmyy.product.mapper.PtCategoryBrandMapper;
import com.xmyy.product.mapper.PtCategoryMapper;
import com.xmyy.product.model.PtCategory;
import com.xmyy.product.model.PtCategoryBrand;
import com.xmyy.product.service.PtCategoryService;
import com.xmyy.product.vo.CategoryListResult;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品类目  服务实现类
 * @author simon
 */
@Service(interfaceClass = PtCategoryService.class)
@CacheConfig(cacheNames = "PtCategory")
public class PtCategoryServiceImpl extends BaseServiceImpl<PtCategory, PtCategoryMapper> implements PtCategoryService {

    @Resource
    private PtCategoryMapper ptCategoryMapper;
    @Resource
    private PtCategoryBrandMapper ptCategoryBrandMapper;


    @Override
    @Transactional(readOnly = true)
    public List<CategoryListResult> getList(Long categoryId) {
        EntityWrapper<PtCategoryBrand> few = new EntityWrapper<>();
        few.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        List<PtCategoryBrand> ptCategories = ptCategoryBrandMapper.selectList(few);

        List<Long> ids = null;
        if (!CollectionUtils.isEmpty(ptCategories)) {
            if (categoryId == 0) {
                ids = ptCategories.stream().map(PtCategoryBrand::getPtCategoryId).distinct().collect(Collectors.toList());
            } else {
                ids = ptCategories.stream().map(PtCategoryBrand::getPtCategoryId2).distinct().collect(Collectors.toList());
            }
        }

        EntityWrapper<PtCategory> ew = new EntityWrapper<>();
        ew.eq("parent_id", categoryId);
        ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        if (!CollectionUtils.isEmpty(ids)) {
            ew.in("id_", ids);
        }
        List<PtCategory> ptCategoryList = ptCategoryMapper.selectList(ew);

        if (!CollectionUtils.isEmpty(ptCategoryList)) {
            return ptCategoryList.stream()
                    .map(o -> InstanceUtil.to(o, CategoryListResult.class))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        return new ArrayList<>();
    }


    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> getCategoryNameMap() {
        List<PtCategory> ptCategoryList = ptCategoryMapper.selectList(null);
        Map<Long, String> map = InstanceUtil.newHashMap();
        for (PtCategory ptCategory : ptCategoryList) {
            map.put(ptCategory.getId(), ptCategory.getName());
        }
        return map;
    }


    @Override
    @Transactional(readOnly = true)
    public Map<Long, Long> getParentIdMap() {
        EntityWrapper<PtCategory> ew = new EntityWrapper<>();
        ew.ne("parent_id", 0);
        List<PtCategory> ptCategoryList = ptCategoryMapper.selectList(null);
        Map<Long, Long> map = InstanceUtil.newHashMap();
        for (PtCategory ptCategory : ptCategoryList) {
            map.put(ptCategory.getId(), ptCategory.getParentId());
        }
        return map;
    }

}
