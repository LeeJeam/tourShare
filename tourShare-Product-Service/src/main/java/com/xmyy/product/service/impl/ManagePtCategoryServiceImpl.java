package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.common.EnumConstants;
import com.xmyy.product.mapper.PtCategoryMapper;
import com.xmyy.product.model.PtCategory;
import com.xmyy.product.service.ManagePtCategoryService;
import com.xmyy.product.vo.CategoryAddParam;
import com.xmyy.product.vo.CategoryListResult;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品类目后台管理  服务实现类
 * @author simon
 */
@Service(interfaceClass = ManagePtCategoryService.class)
@CacheConfig(cacheNames = "PtCategory")
public class ManagePtCategoryServiceImpl extends BaseServiceImpl<PtCategory, PtCategoryMapper> implements ManagePtCategoryService {

    @Resource
    private PtCategoryMapper ptCategoryMapper;

    @Override
    @Transactional
    public Object add(CategoryAddParam params) {
        PtCategory ptCategory = new PtCategory();
        ptCategory.setName(params.getName());
        ptCategory.setParentId(params.getParentId() == null ? 0 : params.getParentId());

        EntityWrapper<PtCategory> ew = new EntityWrapper<>();
        ew.eq("name", params.getName());
        Integer count = ptCategoryMapper.selectCount(ew);
        if (count > 0) {
            return "类目名称已存在";
        }

        return update(ptCategory);
    }


    @Override
    @Transactional(readOnly = true)
    public List<CategoryListResult> getCategoryList(Long id) {
        EntityWrapper<PtCategory> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        ew.eq("parent_id", id);
        List<PtCategory> list = ptCategoryMapper.selectList(ew);

        if (!CollectionUtils.isEmpty(list)) {
            return list.stream()
                    .map(o -> InstanceUtil.to(o, CategoryListResult.class))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        return new ArrayList<>();
    }


    @Override
    @Transactional(readOnly = true)
    public List<CategoryListResult> getCategoryList2() {
        EntityWrapper<PtCategory> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        ew.ne("parent_id", 0);
        List<PtCategory> list = ptCategoryMapper.selectList(ew);

        if (!CollectionUtils.isEmpty(list)) {
            return list.stream()
                    .map(o -> InstanceUtil.to(o, CategoryListResult.class))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        return new ArrayList<>();
    }

}
