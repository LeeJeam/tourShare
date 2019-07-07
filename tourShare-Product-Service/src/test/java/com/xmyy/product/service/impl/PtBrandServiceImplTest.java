package com.xmyy.product.service.impl;

import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.product.service.ManagePtBrandService;
import com.xmyy.product.service.PtBrandService;
import com.xmyy.product.vo.BrandListParam;
import com.xmyy.product.vo.BrandSaveParam;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Simon on 2018/7/17.
 */
public class PtBrandServiceImplTest extends BaseJUnitTest {

    @Resource
    private PtBrandService ptBrandService;
    @Resource
    private ManagePtBrandService managePtBrandService;

    @Test
    public void query() {
        BrandListParam params = new BrandListParam();
        params.setCategoryId2(56L);
        params.setInitial("");
        params.setName("");
        params.setCurrent(1);
        params.setSize(10);
        Object query = managePtBrandService.query(params);
        System.out.println(query);
        Assert.assertNotNull(query);
    }

    @Test
    public void save() {
        BrandSaveParam params = new BrandSaveParam();
        params.setId(null);
        params.setCategoryList2(Arrays.asList(56L));
        params.setName("品牌名称");
        params.setEnName("BrandName");
        params.setInitial("P");
        params.setLogo("http://logo,jpg");
        Object save = managePtBrandService.save(params);
        Assert.assertEquals(1, save);
    }

    @Test
    public void edit() {
        Object edit = managePtBrandService.edit(17L);
        Assert.assertNotNull(edit);
    }

    @Test
    public void deleteBrand() {
        Object result = managePtBrandService.deleteBrand(18L);
        Assert.assertNotNull(result);
    }

    @Test
    public void getList() {
        Long categoryId = 55L;
        Long categoryId2 = null;
        String initial = null;
        Object list = ptBrandService.getList(categoryId, categoryId2, initial);
        System.out.println(list);
    }

    @Test
    public void getBrandNameMap() {
        Map<Long, String> nameMap = ptBrandService.getBrandNameMap();
        System.out.println(nameMap);
    }
}