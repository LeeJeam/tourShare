package com.xmyy.circle.service.impl;

import com.xmyy.circle.service.UcFeedbackService;
import com.xmyy.circle.vo.UcFeedbackPageParam;
import com.xmyy.circle.vo.UcFeedbackParam;
import com.xmyy.common.junit.BaseJUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

/**
 * Created by yeyu on 2018/7/5.
 * 描述：
 */
public class UcFeedbackServiceTests extends BaseJUnitTest {

    @Resource
    private UcFeedbackService iucFeedbackService;

    @Test
    public void queryAll() {
        UcFeedbackPageParam param = new UcFeedbackPageParam();
        param.setSize(10);
        param.setCurrent(1);
        Object obj = iucFeedbackService.queryAll(param);
        Assert.assertNotNull(obj);
    }

    @Rollback
    @Test
    public void addFeedBack() {
        UcFeedbackParam param = new UcFeedbackParam();
        param.setContent("测试------------");
        param.setQuestionType(1);
        Object obj = iucFeedbackService.addFeedBack(param, 23L);
        Assert.assertNotNull(obj);
    }

    @Rollback
    @Test
    public void updateFeedBack() {
        UcFeedbackParam param = new UcFeedbackParam();
        param.setQuestionType(1);
        param.setContent("测试2222222222");
        param.setId(2L);
        Object obj = iucFeedbackService.updateFeedBack(param, 23L);
        Assert.assertNotNull(obj);
    }

    @Rollback
    @Test
    public void delFeedBack() {
        Object obj = iucFeedbackService.delFeedBack(2L, 1L);
        Assert.assertNotNull(obj);
    }
}
