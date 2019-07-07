package com.xmyy.cert.service.impl;

import com.xmyy.cert.service.UcMemberCertService;
import com.xmyy.common.junit.BaseJUnitTest;
import org.junit.Test;

import javax.annotation.Resource;

public class CertServiceTest extends BaseJUnitTest {

    @Resource
    private UcMemberCertService memberCertService;

    @Test
    public void testQueryUserCertDetail() {
        Object o = memberCertService.queryUserCertDetail(1L, 2);
        System.out.println(o);
    }

}
