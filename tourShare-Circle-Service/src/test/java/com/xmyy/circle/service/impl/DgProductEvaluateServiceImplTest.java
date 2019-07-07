package com.xmyy.circle.service.impl;

import com.xmyy.circle.service.DgProductEvaluateService;
import com.xmyy.circle.vo.*;
import com.xmyy.common.junit.BaseJUnitTest;
import org.junit.Assert;
import org.junit.Test;
import top.ibase4j.core.support.Pagination;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Simon on 2018/7/18.
 */
public class DgProductEvaluateServiceImplTest extends BaseJUnitTest {

    @Resource
    DgProductEvaluateService dgProductEvaluateService;

    @Test
    public void reviewOpen() {
        dgProductEvaluateService.reviewOpen(28L,1L);
    }

    @Test
    public void reviewSave() {
        ReviewParam params = new ReviewParam();
        params.setOrderId(28L);
        ReviewParam.Review review = new ReviewParam.Review();
        review.setId(59L);
        review.setReviewContent("追加内容");
        List<ReviewParam.Review> list = Arrays.asList(review);
        params.setReviews(list);
        dgProductEvaluateService.reviewSave(params,1L);
    }

    @Test
    public void evaluateList() {
        ProductEvaluateListParam params = new ProductEvaluateListParam();
        params.setCurrent(1);
        params.setSize(10);
        params.setEvaluateType(2);
        params.setMemberType(1);
        Pagination<ProductEvaluateListResult> list = dgProductEvaluateService.evaluateList(params, 1L);
        Assert.assertNotNull(list);
    }

    @Test
    public void evaluateByProductIdList() {
        EvaluateByProductIdParam params = new EvaluateByProductIdParam();
        params.setCurrent(1);
        params.setSize(10);
        params.setProductId(45L);
        dgProductEvaluateService.evaluateByProductIdList(params,1L);
    }

    @Test
    public void praise() {
        dgProductEvaluateService.praise(5L,1L,1);
    }

    @Test
    public void queryProductEvaluate() {
        ProductEvaluateParam params = new ProductEvaluateParam();
        params.setCurrent(1);
        params.setSize(10);
        params.setProductName("");
        params.setWaybillNo("");
        params.setIsPicture(0);
        params.setProductLevel(0);
        params.setCommentStatus(-1);
        params.setUserType(2);
        params.setUserId(1L);
        dgProductEvaluateService.queryProductEvaluate(params);
    }

    @Test
    public void queryByOrderId() {
        dgProductEvaluateService.queryByOrderId(40L);
    }
}