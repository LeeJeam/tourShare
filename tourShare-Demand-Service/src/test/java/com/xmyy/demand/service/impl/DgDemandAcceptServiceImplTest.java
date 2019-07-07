package com.xmyy.demand.service.impl;

import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.demand.service.DgDemandAcceptService;
import org.junit.Test;

import javax.annotation.Resource;

public class DgDemandAcceptServiceImplTest extends BaseJUnitTest {

    @Resource
    private DgDemandAcceptService dgDemandAcceptService;

    @Test
    public void canAccept() {
        dgDemandAcceptService.canAccept(0L, 0, 2L);
    }
}