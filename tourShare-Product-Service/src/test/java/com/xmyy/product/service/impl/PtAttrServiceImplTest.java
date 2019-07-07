package com.xmyy.product.service.impl;

import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.product.dto.AttrAddDto;
import com.xmyy.product.dto.AttrDeleteDto;
import com.xmyy.product.service.ManagePtAttrService;
import com.xmyy.product.service.PtAttrService;
import com.xmyy.product.vo.ApiAttrSimpleListParam;
import com.xmyy.product.vo.AttrItemAddParam;
import com.xmyy.product.vo.AttrItemListParam;
import com.xmyy.product.vo.AttrModifyParam;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Simon on 2018/7/17.
 */
public class PtAttrServiceImplTest extends BaseJUnitTest {

    @Resource
    private PtAttrService ptAttrService;
    @Resource
    private ManagePtAttrService managePtAttrService;

    @Test
    public void addAttrItem() {
        AttrItemAddParam params = new AttrItemAddParam();
        params.setCategoryId(55L);
        params.setCategoryId2(56L);
        params.setBrandId(17L);
        params.setAttrType("测试属性");
        Set<String> sets = new HashSet<>();
        sets.add("测试属性值");
        sets.add("测试属性值2");
        params.setAttrValue(sets);
        params.setShowType(0);
        Object result = managePtAttrService.addAttrItem(params, 1L);
        Assert.assertEquals(2,result);
    }

    @Test
    public void queryAttrItem() {
        AttrItemListParam params = new AttrItemListParam();
        params.setCategoryId(55L);
        params.setCategoryId2(56L);
        params.setBrandId(17L);
        Object result = managePtAttrService.queryAttrItem(params);
        Assert.assertNotNull(result);
    }

    @Test
    public void saveAttrItemModify() {
        AttrModifyParam params = new AttrModifyParam();
        params.setCategoryId(55L);
        params.setCategoryId2(56L);
        params.setBrandId(17L);

        AttrAddDto attrAddDto = new AttrAddDto();
        attrAddDto.setAttrTypeId(3L);
        attrAddDto.setAttrValueNames(Arrays.asList("增加一","增加二"));
        params.setAddItem(Arrays.asList(attrAddDto));

        AttrDeleteDto attrDeleteDto = new AttrDeleteDto();
        attrDeleteDto.setAttrTypeId(4L);
        attrDeleteDto.setAttrValueIds(Arrays.asList(134L));
        params.setDelItem(Arrays.asList(attrDeleteDto));
        Object result = managePtAttrService.saveAttrItemModify(params, 1L);
        System.out.println(result);
        Assert.assertNull(result);
    }

    @Test
    public void getSeriesAttr() {
        Object seriesAttr = ptAttrService.getSeriesAttr(3L);
        Assert.assertNotNull(seriesAttr);
    }

    @Test
    public void getAttrList() {
        ApiAttrSimpleListParam params = new ApiAttrSimpleListParam();
        params.setCategoryId(55L);
        params.setCategoryId2(56L);
        params.setBrandId(17L);
        Object attrList = ptAttrService.getAttrList(params);
        Assert.assertNotNull(attrList);
    }
}