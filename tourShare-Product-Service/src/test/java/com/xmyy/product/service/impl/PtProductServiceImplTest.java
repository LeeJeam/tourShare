package com.xmyy.product.service.impl;

import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.product.dto.ApiCustomAttrItemDto;
import com.xmyy.product.dto.ApiCustomSkuDto;
import com.xmyy.product.model.PtProduct;
import com.xmyy.product.service.ManagePtProductService;
import com.xmyy.product.service.PtProductService;
import com.xmyy.product.vo.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import top.ibase4j.core.support.Pagination;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Simon on 2018/7/17.
 */
public class PtProductServiceImplTest extends BaseJUnitTest {

    @Resource
    private PtProductService ptProductService;
    @Resource
    private ManagePtProductService managePtProductService;
    private Long userId;

    @Before
    public void init(){
        this.userId=1L;
    }

    @Test
    public void saveV2() {
        ApiProductCustomSaveParam params = new ApiProductCustomSaveParam();
        params.setId(null);
        params.setTitle("标题");
        params.setCategoryId(55L);
        params.setCategoryId2(56L);
        params.setBrandId(17L);
        params.setProductType(1);
        params.setBuyRegion("美国");
        params.setShopName("美国免税店");
        params.setProductDesc("商品描述");
        params.setExpressType(1);
        params.setExpiresTime(new Date());
        List<String> images = Arrays.asList("http://1.jpg","http://2.jpg");
        params.setImages(images);
        params.setTourId(42L);
        List<ApiCustomSkuDto> skus = new ArrayList<>();
        ApiCustomSkuDto skuDto = new ApiCustomSkuDto();
        skuDto.setPrice(new BigDecimal(100));
        skuDto.setStock(50);
        List<ApiCustomAttrItemDto> attrItem = new ArrayList<>();
        ApiCustomAttrItemDto attrItemDto = new ApiCustomAttrItemDto();
        attrItemDto.setAttrTypeId(3L);
        attrItemDto.setAttrValueName("容量");
        attrItem.add(attrItemDto);
        skuDto.setAttrItem(attrItem);
        skus.add(skuDto);
        params.setSku(skus);
        Object result = ptProductService.saveV2(params, userId);
        System.out.println(result);
    }

    @Test
    public void updateSale() {
        Object result = ptProductService.updateSale(45L, 1, userId);
        Assert.assertNotNull(result);
    }

    @Test
    public void getEdit() {
        Object result = ptProductService.getEdit(45L, userId);
        System.out.println(result);
        Assert.assertNotNull(result);
    }

    @Test
    public void getDetail() {
        Object result = ptProductService.getDetail(45L, 0, userId);
        System.out.println(result);
        Assert.assertNotNull(result);
    }

    @Test
    public void search() {
        Object result = ptProductService.search("三星");
        System.out.println(result);
        Assert.assertNotNull(result);
    }

    @Test
    public void getSkus() {
        Object skus = ptProductService.getSkus(45L);
        Assert.assertNotNull(skus);
    }

    @Test
    public void getAllAttrs() {
        Object allAttrs = ptProductService.getAllAttrs(45L);
        Assert.assertNotNull(allAttrs);
    }

    @Test
    public void getList() {
        ApiProductListParam params = new ApiProductListParam();
        params.setCurrent(1);
        params.setSize(10);
        params.setCategoryId(55L);
        params.setBuyRegion("美国");
        params.setIsSale(1);
        params.setSort(0);
        params.setSellerId(1L);
        params.setProductType(1);
        Object list = ptProductService.getList(params);
        System.out.println(list);
        Assert.assertNotNull(list);
    }

    @Test
    public void getListByTourId() {
        ApiProductListByTourIdParam params = new ApiProductListByTourIdParam();
        params.setTourId(42L);
        params.setCurrent(1);
        params.setSize(10);
        Object result = ptProductService.getListByTourId(params);
        System.out.println(result);
        Assert.assertNotNull(result);
    }

    @Test
    public void stopProductsByTourId() {
        Object result = ptProductService.stopProductsByTourId(45L);
        Assert.assertNotNull(result);
    }

    @Test
    public void getPresellList() {
        PresellProductParam params = new PresellProductParam();
        params.setCurrent(1);
        params.setSize(10);
        params.setBuyRegionShortCode("KR");
        Pagination<PresellProductResult> presellList = ptProductService.getPresellList(params);
        Assert.assertNotNull(presellList);
    }

    @Test
    public void statistic() {
        Object statistic = managePtProductService.statistic();
        Assert.assertNotNull(statistic);
    }

    @Test
    public void queryManageProductList() {
        ManageProductListParam params = new ManageProductListParam();
        params.setTitle("三星");
        params.setOrderCountMin(null);
        params.setOrderCountMax(null);
        params.setExpiresTime(new Date());
        params.setNickName("");
        params.setCurrent(1);
        params.setSize(10);
        Object result = managePtProductService.queryManageProductList(params);
        Assert.assertNotNull(result);
    }

    @Test
    public void setTop() {
        ManageProductTopParam params = new ManageProductTopParam();
        params.setId(45L);
        params.setTimes(120);
        Object result = managePtProductService.setTop(params);
        Assert.assertNotNull(result);
    }

    @Test
    public void cancelTop() {
        Object result = managePtProductService.cancelTop(45L, userId);
        Assert.assertNotNull(result);
    }

    @Test
    public void getCurrTopList() {
        Object currTopList = managePtProductService.getCurrTopList();
        System.out.println(currTopList);
        Assert.assertNotNull(currTopList);
    }

    @Test
    public void queryProductManageList() {
        ManageProductParams params = new ManageProductParams();
        params.setCurrent(1);
        params.setSize(10);
        params.setPtAttrParams(null);
        params.setProductIds(null);
        params.setCategoryId(55);
        params.setCategoryId2(56);
        params.setBrandId(17);
        Object result = managePtProductService.queryProductManageList(params);
        System.out.println(result);
        Assert.assertNotNull(result);
    }

    @Test
    public void updateFavorable() {
        Object result = ptProductService.updateFavorable(45L, 89.5F);
        Assert.assertNotNull(result);
    }

    @Test
    public void getCoverBySellerId() {
        List<PtProduct> result = ptProductService.getProductBySellerId(1L);
        Assert.assertNotNull(result);
    }

    @Test
    public void cancelTopOverTime() {
        ptProductService.cancelTopOverTime();
    }

    @Test
    public void queryManagePresellProductList() {
        ManagePresellProductParam params = new ManagePresellProductParam();
        params.setCategoryId(55L);
        params.setCategoryId2(56L);
        params.setBrandId(null);
        Object result = managePtProductService.queryManagePresellProductList(params);
        Assert.assertNotNull(result);
    }
}