package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.product.dao.PtBrandDao;
import com.xmyy.product.mapper.PtBrandMapper;
import com.xmyy.product.model.PtBrand;
import com.xmyy.product.service.PtBrandService;
import com.xmyy.product.vo.ApiBrandListResult;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 品牌  服务实现类
 * @author simon
 */
@Service(interfaceClass = PtBrandService.class)
@CacheConfig(cacheNames = "PtBrand")
public class PtBrandServiceImpl extends BaseServiceImpl<PtBrand, PtBrandMapper> implements PtBrandService {

    @Resource
    PtBrandMapper ptBrandMapper;
    @Resource
    PtBrandDao ptBrandDao;


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


    @Override
    public Map<Long, String> getBrandNameMap() {
        List<PtBrand> ptBrandList = ptBrandMapper.selectList(null);
        Map<Long, String> map = InstanceUtil.newHashMap();
        for (PtBrand ptBrand : ptBrandList) {
            map.put(ptBrand.getId(), ptBrand.getName());
        }
        return map;
    }

}
