package com.xmyy.circle.service.impl;

import com.xmyy.circle.service.UcFeedQuestionService;
import com.xmyy.circle.vo.UcFeedQuestionParam;
import com.xmyy.common.junit.BaseJUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

/**
 * Created by yeyu on 2018/7/5.
 * 描述：
 */
public class UcFeedQuestionServiceTests extends BaseJUnitTest {

    @Resource
    public UcFeedQuestionService service;

    @Test
    public void queryFeedQuestionList() {
        Object obj = service.queryFeedQuestionList(null);
        Assert.assertNotNull(obj);
    }

    @Rollback
    @Test
    public void addFeedQuestion() {
        UcFeedQuestionParam param = new UcFeedQuestionParam();
        param.setQuestion("无法支付");
        param.setAnswer("请使用支付宝");
        param.setSort(1);
        Object obj = service.addFeedQuestion(param, 23L);
        Assert.assertNotNull(obj);
    }

    @Rollback
    @Test
    public void updateFeedQuestion() {
        UcFeedQuestionParam param = new UcFeedQuestionParam();
        param.setQuestion("无法支付");
        param.setAnswer("请使用微信");
        param.setSort(1);
        param.setId(11L);
        Object obj = service.updateFeedQuestion(param, 23L);
        Assert.assertNotNull(obj);
    }

    @Rollback
    @Test
    public void deleteFeedQuestion() {
        Object obj = service.deleteFeedQuestion(11L, 1L);
        Assert.assertNotNull(obj);
    }
}
