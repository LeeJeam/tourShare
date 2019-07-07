package com.xmyy.member.service.impl;

import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.member.service.UcBuyerManageService;
import com.xmyy.member.vo.BuyerManagePageParam;
import com.xmyy.member.vo.BuyerManagePageResult;
import org.junit.Test;
import top.ibase4j.core.support.Pagination;

import javax.annotation.Resource;

public class UcBuyerManageServiceTest extends BaseJUnitTest {

    @Resource
    private UcBuyerManageService buyerManageService;

    @Test
    public void getList() {
        BuyerManagePageParam param = new BuyerManagePageParam();
        Pagination<BuyerManagePageResult>  page =  buyerManageService.list(param);
        System.out.println(page);
    }

}
