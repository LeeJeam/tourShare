package com.xmyy.search.service.impl;

import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.search.service.CircleSearchService;
import com.xmyy.search.vo.CirclePageParam;
import org.junit.Test;

import javax.annotation.Resource;


public class SearchServiceImplTest extends BaseJUnitTest {

    @Resource
    private CircleSearchService circleSearchService;

    @Test
    public void batchSendTest() {
        CirclePageParam param = new CirclePageParam();
        param.setBuyerId(1L);
        Object search = circleSearchService.search(param);
        System.out.println(search);
    }
}