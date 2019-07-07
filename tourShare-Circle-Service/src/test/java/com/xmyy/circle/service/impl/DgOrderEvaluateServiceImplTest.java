package com.xmyy.circle.service.impl;

import com.xmyy.circle.dto.ApiOrderProductEvaluateDto;
import com.xmyy.circle.service.DgOrderEvaluateService;
import com.xmyy.circle.vo.OrderEvaluateAddParam;
import com.xmyy.common.junit.BaseJUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created by Simon on 2018/7/18.
 */
public class DgOrderEvaluateServiceImplTest extends BaseJUnitTest {

    @Resource
    DgOrderEvaluateService dgOrderEvaluateService;

    @Test
    public void add() {
        OrderEvaluateAddParam params = new OrderEvaluateAddParam();
        params.setOrderId(10L);
        params.setBuyService(5);
        params.setProductLevel(5);
        ApiOrderProductEvaluateDto dto = new ApiOrderProductEvaluateDto();
        dto.setContent("内容");
        dto.setImages(Arrays.asList("http://images.jpg"));
        dto.setProductId(45L);
        dto.setProductOrderId(15L);
        params.setProductEvaluate(Arrays.asList(dto));
        dgOrderEvaluateService.add(params,1L);
    }

    @Test
    public void list() {
        Object list = dgOrderEvaluateService.list(90L, 1L);
        Assert.assertNotNull(list);
    }
}