package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.DateUtils;
import com.xmyy.manage.model.AdminUser;
import com.xmyy.manage.service.AdminUserService;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.product.dao.ProductManagerDao;
import com.xmyy.product.dao.PtProductDao;
import com.xmyy.product.dto.ProductStatisticDto;
import com.xmyy.product.dto.PtAttrDto;
import com.xmyy.product.mapper.PtAttrMapper;
import com.xmyy.product.mapper.PtProductMapper;
import com.xmyy.product.model.PtAttr;
import com.xmyy.product.model.PtProduct;
import com.xmyy.product.service.ManagePtProductService;
import com.xmyy.product.service.PtCategoryService;
import com.xmyy.product.vo.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品后台管理  服务实现类
 * @author simon
 */
@Service(interfaceClass = ManagePtProductService.class)
//@CacheConfig(cacheNames = "PtProduct")
public class ManagePtProductServiceImpl extends BaseServiceImpl<PtProduct, PtProductMapper> implements ManagePtProductService {

    @Resource
    private PtProductMapper ptProductMapper;
    @Resource
    private PtAttrMapper ptAttrMapper;
    @Resource
    private PtProductDao ptProductDao;
    @Resource
    private ProductManagerDao productManagerDao;
    @Reference
    private AdminUserService adminUserService;
    @Reference
    private UcSellerService ucSellerService;
    @Reference
    private PtCategoryService ptCategoryService;


    @Override
    @Transactional(readOnly = true)
    public Object statistic() {
        List<ProductStatisticDto> statisticByCategoryList = ptProductDao.getStatisticByCategory();
        Map<Long, String> categoryNameMap = ptCategoryService.getCategoryNameMap();

        Integer totalCount = 0;
        List<ProductStatisticResult.CategoryStatistic> list = InstanceUtil.newArrayList();
        for (ProductStatisticDto dto : statisticByCategoryList) {
            ProductStatisticResult.CategoryStatistic categoryStatistic = new ProductStatisticResult.CategoryStatistic();
            categoryStatistic.setCategoryId(dto.getCategoryId());
            categoryStatistic.setCategoryName(categoryNameMap.get(dto.getCategoryId()));
            categoryStatistic.setCount(dto.getNum());
            list.add(categoryStatistic);
            totalCount += dto.getNum();
        }

        ProductStatisticResult result = new ProductStatisticResult();
        result.setTotalCount(totalCount);
        result.setStatistics(list);
        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryManageProductList(ManageProductListParam params) {
        RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());
        List<ManageProductListResult> page = ptProductDao.queryProductListPage(params, rb);

        int count = this.ptProductDao.queryProductListCount(params);
        int pages = (count % params.getSize() == 0) ? count / params.getSize() : count / params.getSize() + 1;
        Pagination<ManageProductListResult> resultPagination = new Pagination<>();
        resultPagination.setTotal(count);
        resultPagination.setRecords(page);
        resultPagination.setCurrent(params.getCurrent());
        resultPagination.setSize(params.getSize());
        resultPagination.setPages(pages);

        return resultPagination;
    }


    @Override
    @Transactional
    public Object setTop(ManageProductTopParam params) {
        PtProduct ptProduct = ptProductMapper.selectById(params.getId());
        if (ptProduct == null || ptProduct.getEnable() == 0) {
            return "该商品不存在";
        }

        ptProduct.setIsTop(EnumConstants.YesOrNo.YES.getValue());
        Date now = new Date();
        ptProduct.setTopBeginTime(now);
        ptProduct.setTopEndTime(DateUtils.addMinutes(now, params.getTimes()));
        ptProduct.setTopOpId(params.getUpdateBy());
        ptProduct.setTopOpTime(new Date());

        return ptProductMapper.updateById(ptProduct);
    }


    @Override
    @Transactional
    public Object cancelTop(Long id, Long currUser) {
        PtProduct ptProduct = ptProductMapper.selectById(id);
        if (ptProduct == null || ptProduct.getEnable() == 0) {
            return "该商品不存在";
        }

        ptProduct.setIsTop(EnumConstants.YesOrNo.NO.getValue());
        ptProduct.setTopOpId(currUser);
        ptProduct.setTopOpTime(new Date());
        return ptProductMapper.updateById(ptProduct);
    }


    @Override
    @Transactional(readOnly = true)
    public Object getCurrTopList() {
        EntityWrapper<PtProduct> ew = new EntityWrapper<>();
        ew.ge("top_end_time", new Date());
        ew.eq("is_sale", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("enable_", EnumConstants.Enable.ENABLE.getValue());
        ew.eq("is_top", EnumConstants.YesOrNo.YES.getValue());
        ew.last("LIMIT 5");
        List<PtProduct> ptProductList = ptProductMapper.selectList(ew);

        List<Long> sellerIds = ptProductList.stream()
                .filter(s -> s.getCreateBy() != null)
                .map(PtProduct::getCreateBy).distinct()
                .collect(Collectors.toList());
        List<Long> opIds = ptProductList.stream()
                .filter(t -> t.getTopOpId() != null)
                .map(PtProduct::getTopOpId).distinct()
                .collect(Collectors.toList());

        List<UcSeller> sellerList = ucSellerService.queryList(sellerIds);
        List<AdminUser> adminList = adminUserService.queryList(opIds);

        Map<Long, String> sellerNameMap = InstanceUtil.newHashMap();
        sellerList.forEach(s -> sellerNameMap.put(s.getId(), s.getNickName()));
        Map<Long, String> adminNameMap = InstanceUtil.newHashMap();
        adminList.forEach(s -> adminNameMap.put(s.getId(), s.getUserName()));

        List<CurrTopResult> resultList = ptProductList.stream().map(p -> {
            CurrTopResult currTopResult = InstanceUtil.to(p, CurrTopResult.class);
            currTopResult.setPrice(p.getMinPrice());
            currTopResult.setSellerName(sellerNameMap.get(p.getCreateBy()));
            currTopResult.setOpName(adminNameMap.get(p.getTopOpId()));
            return currTopResult;
        }).collect(Collectors.toList());

        return resultList;
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryProductManageList(ManageProductParams param) {
        Integer pageNum = 1;
        if (param.getCurrent() != null && param.getCurrent() != 0) {
            pageNum = param.getCurrent();
        }
        int pageNextSize = 0;
        if (param.getSize() != null && param.getSize() != 0) {
            pageNextSize = (pageNum - 1) * param.getSize();
        }
        param.setCurrent(pageNextSize);
        if (!CollectionUtils.isEmpty(param.getPtAttrParams())) {
            List<PtAttrParam> pttrList = param.getPtAttrParams().stream()
                    .filter(this::isAttrNull).collect(Collectors.toList());
            param.setPtAttrParams(pttrList);
        }
        //获取所有的商品
        List<ProductManagerResult> resultList = productManagerDao.queryProductManageList(param);
        if (!CollectionUtils.isEmpty(resultList)) {
            //获取所有商品属性值
            List<Long> productIds = resultList.stream().map(ProductManagerResult::getProductId).collect(Collectors.toList());
            EntityWrapper<PtAttr> ew = new EntityWrapper<>();
            ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
            ew.in("pt_product_id", productIds);
            List<PtAttr> ptAttrList = ptAttrMapper.selectList(ew);
            resultList = resultList.stream().map(s -> getProductManager(s, ptAttrList)).collect(Collectors.toList());
        }

        //获取商品总条数
        Integer total = productManagerDao.countProductManage(param);
        Pagination<ProductManagerResult> page = new Pagination<>();
        page.setCurrent(pageNum);
        page.setSize(param.getSize());
        page.setTotal(total);
        page.setRecords(resultList);
        return page;
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryManagePresellProductList(ManagePresellProductParam params) {
        RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());
        List<ManagePresellProductResult> list = ptProductDao.queryManagePresellProductList(params, rb);

        if (!CollectionUtils.isEmpty(list)) {
            Map<Long, String> categoryNameMap = ptCategoryService.getCategoryNameMap();
            list.forEach(v -> v.setCategoryName2(categoryNameMap.get(v.getCategoryId2())));
        }

        int count = ptProductDao.queryManagePresellProductCount(params);
        int pages = (count % params.getSize() == 0) ? count / params.getSize() : count / params.getSize() + 1;
        Pagination<ManagePresellProductResult> resultPagination = new Pagination<>();
        resultPagination.setTotal(count);
        resultPagination.setRecords(list);
        resultPagination.setCurrent(params.getCurrent());
        resultPagination.setSize(params.getSize());
        resultPagination.setPages(pages);
        return resultPagination;
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
