package com.xmyy.product.service.impl;

import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.product.service.ManagePtCategoryService;
import com.xmyy.product.service.PtCategoryService;
import com.xmyy.product.vo.CategoryAddParam;
import com.xmyy.product.vo.CategoryListResult;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Simon on 2018/7/17.
 */
public class PtCategoryServiceImplTest extends BaseJUnitTest {

    @Resource
    private PtCategoryService ptCategoryService;
    @Resource
    private ManagePtCategoryService managePtCategoryService;

    @Test
    public void insert() {
        CategoryAddParam params = new CategoryAddParam();
        params.setName("测试类目");
        params.setParentId(0L);
        Object insert = managePtCategoryService.add(params);
        Assert.assertEquals(1,insert);
    }

    @Test
    public void getList() {
        List<CategoryListResult> list = ptCategoryService.getList(0L);
        System.out.println(list);
    }

    @Test
    public void getCategoryList2() {
        List<CategoryListResult> categoryList2 = managePtCategoryService.getCategoryList2();
        System.out.println(categoryList2);
    }

    @Test
    public void getCategoryNameMap() {
        Map<Long, String> categoryNameMap = ptCategoryService.getCategoryNameMap();
        System.out.println(categoryNameMap);
    }

    @Test
    public void getParentIdMap() {
        Map<Long, Long> parentIdMap = ptCategoryService.getParentIdMap();
        System.out.println(parentIdMap);
    }
}