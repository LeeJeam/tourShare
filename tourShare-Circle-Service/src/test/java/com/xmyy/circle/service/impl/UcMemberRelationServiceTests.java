package com.xmyy.circle.service.impl;

import com.xmyy.circle.service.UcMemberRelationService;
import com.xmyy.circle.vo.UcMemberRelationPageParam;
import com.xmyy.circle.vo.UcMemberRelationParam;
import com.xmyy.common.junit.BaseJUnitTest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

/**
 * Created by yeyu on 2018/7/5.
 * 描述：
 */
public class UcMemberRelationServiceTests extends BaseJUnitTest {

    @Resource
    private UcMemberRelationService service;

    @Rollback
    @Test
    public void addMember() {
        UcMemberRelationParam params = new UcMemberRelationParam();
        params.setToMemberId(2L);
        params.setToMemberType(1);
        Object obj = service.addMember(1L, params);
        Assert.assertNotNull(obj);
    }

    @Rollback
    @Test
    public void delMember() {
        Object obj = service.delMember(1L, 1L, 1);
        Assert.assertNotNull(obj);
    }

    @Test
    public void getBuyerFocusList() {
        UcMemberRelationPageParam params = new UcMemberRelationPageParam();
        params.setPageNum(1);
        params.setUserId(1L);
        params.setPageSize(10);
        Object obj = service.getBuyerFocusList(params);
        Assert.assertNotNull(obj);
    }

    @Test
    public void getSellerFocusList() {
        UcMemberRelationPageParam params = new UcMemberRelationPageParam();
        params.setPageNum(1);
        params.setUserId(1L);
        params.setPageSize(10);
        Object obj = service.getSellerFocusList(params);
        Assert.assertNotNull(obj);
    }

    @Test
    public void countFocusByMerberId() {
        Object obj = service.countFocusByMerberId(41L);
        Assert.assertNotNull(obj);
    }

    @Test
    public void countFansByToMerberId() {
        Object obj = service.countFansByToMerberId(3L);
        Assert.assertNotNull(obj);
    }

}
