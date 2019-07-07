package com.xmyy.demand.service.impl;

import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.demand.dao.DgDemandOrderDao;
import com.xmyy.demand.service.DgDemandOrderService;
import com.xmyy.demand.vo.AddDemandParam;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

public class DgDemandOrderServiceImplTest extends BaseJUnitTest {

    @Resource
    private DgDemandOrderService demandOrderService;
    @Resource
    private DgDemandOrderDao demandDao;

    @Test
    public void searchDemand() {
        Object o = demandOrderService.searchDemand("1", 1, 1L, 1, 0, "time", "");
        System.out.println("返回结果："+o);
    }

    @Test
    public void createDemand(){
       /* // 9b10a17fee714aefa1294da105b809fe
        List<PushUser> pushUsers = InstanceUtil.newArrayList();
        PushUser pushUser = new PushUser("9b10a17fee714aefa1294da105b809fe",1);
        PushUser pushUser1 = new PushUser("e60336e0960044cfae6b869c6ddd4284",1);
        pushUsers.add(pushUser);
        pushUsers.add(pushUser1);
        Map<String,Object> customMap = InstanceUtil.newHashMap();
        // 推送类型
        customMap.put("operationType", 3);
        customMap.put("id",12L);
        // 批量推送
        PushMessage. getSellerInstance().pushSysMessageToAccountList("发现新需求哦","买家发布需求并期望您接单",customMap,null,pushUsers);
        */

        AddDemandParam params = new AddDemandParam();
        params.setBudgetPrice(new BigDecimal(600));
        params.setBuyCountry("日本");
        params.setBuyerPhone("18718909631");
        params.setConsigneeAddress("32楼");
        params.setDeliveryTime(new Date());
        params.setDemandDesc("测试");
        params.setDemandImages("87878787878");
        params.setIsAppraisal(0);
        params.setProductType("不存在");
        params.setReleaseAddress("dkfhdkfdkl");
        params.setSellerIds("3");
        demandOrderService.createDemand(params,1L);

    }

    @Test
    public void queryListNoLogin() {
        Object o = demandOrderService.queryListNoLogin(1, 10, "");
        System.out.println(o.toString());
    }

    @Test
    public void closeDeliveryTimeOutDemand() {
        demandOrderService.closeDeliveryTimeOutDemand();
        System.out.println("--------");
    }
}