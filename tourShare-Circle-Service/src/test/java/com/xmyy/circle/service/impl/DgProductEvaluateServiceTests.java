package com.xmyy.circle.service.impl;

import com.xmyy.circle.service.DgProductEvaluateService;
import com.xmyy.circle.vo.ProductEvaluateParam;
import com.xmyy.common.junit.BaseJUnitTest;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by yeyu on 2018/7/9.
 * 描述：
 */
public class DgProductEvaluateServiceTests extends BaseJUnitTest {

    @Resource
    private DgProductEvaluateService service;

    @Test
    public void queryProductEvaluate() {
        ProductEvaluateParam param = new ProductEvaluateParam();
        param.setUserType(1);
        param.setUserId(2L);
        Object obj = service.queryProductEvaluate(param);
        Assert.assertNotNull(obj);
    }
}
