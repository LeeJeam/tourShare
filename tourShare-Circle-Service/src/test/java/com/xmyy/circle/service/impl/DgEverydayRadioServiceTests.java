package com.xmyy.circle.service.impl;

import com.xmyy.circle.service.DgEverydayRadioService;
import com.xmyy.circle.vo.EveryDayRadioParam;
import com.xmyy.circle.vo.EveryDayRadioResult;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.junit.BaseJUnitTest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeyu on 2018/7/4.
 * 描述：今日提醒单元测试
 */
public class DgEverydayRadioServiceTests extends BaseJUnitTest {

    @Resource
    private DgEverydayRadioService service;

    @Test
    @Rollback
    public void addEveryDayRadio() {
        Long userId = 23L;
        EveryDayRadioParam everyDayRadioParam=new EveryDayRadioParam();
        everyDayRadioParam.setType(EnumConstants.EveryDayRadioType.SELLER_ACCEPT.getValue());
        everyDayRadioParam.setToMemberId(17L);
        everyDayRadioParam.setToMemberType(EnumConstants.MemberType.seller.getValue());
        everyDayRadioParam.setMemberId(userId);
        Object sum=  service.addEveryDayRadio(everyDayRadioParam);
        Assert.assertNotNull(sum);
    }
    @Test
    public void queryEveryDayRadioList() {
        List<EveryDayRadioResult> resultList= (List<EveryDayRadioResult>) service.queryEveryDayRadioList(17L,EnumConstants.MemberType.seller.getValue());
        Assert.assertNotNull(resultList);
    }
}
