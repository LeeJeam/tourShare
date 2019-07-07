package com.xmyy.order.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.order.dao.DgOrderDao;
import com.xmyy.order.mapper.DgOrderMapper;
import com.xmyy.order.model.DgOrder;
import com.xmyy.order.service.DgOrderService;
import com.xmyy.order.vo.ConfirmReceiveParam;
import com.xmyy.pay.model.DgPayLog;
import com.xmyy.pay.service.DgPayLogService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.support.mq.QueueSender;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

public class OrderServiceTest extends BaseJUnitTest {

    @Resource
    private DgOrderService orderService;
    @Resource
    private DgOrderDao orderDao;
    @Resource
    private DgPayLogService payLogService;
    @Resource
    private QueueSender sender;
    @Resource
    private DgOrderMapper orderMapper;

    @Test
    public void test1() {
        ConfirmReceiveParam param = new ConfirmReceiveParam();
        param.setOrderId(123L);
        orderService.confirmReceive(param);
    }

    @Test
    public void test2() {
        HashSet<String> set = new HashSet<>();
        set.add("234aaa");
        set.add("ccc");
        set.add("sbbbsd");
        String join = StringUtils.join(set, ",");
        System.out.println(join);
    }

    @Test
    public void test4() {
        System.out.println("=======================");
        System.out.println(Arrays.toString(orderDao.selectBuyerOrderInfo(42L)));
        System.out.println("=======================");
    }

    @Test
    @Transactional
    @Rollback
    public void testTxTransaction() {
        System.out.println("=======================");
        DgOrder order = new DgOrder();
        order.setRemark("1234");
        orderService.update(order);

        DgPayLog payLog = new DgPayLog();
        payLog.setOrderIds(order.getId().toString());
        payLogService.update(payLog);
        System.out.println("=======================");
    }

    @Test
    public void testSender() {
        System.out.println("============ start ===========");
        sender.send("Order:Tour:orderSuccess.query", 1L + "," + new BigDecimal(59.89));
        System.out.println("============== end =========");
    }

    @Test
    public void testqueryList() {
        System.out.println("============ start ===========");
        Map<String, Object> param = new HashMap<>();
        param.put("order_no", "123");
        List<DgOrder> orderList = orderService.queryList(param);
        for (DgOrder order : orderList) {
            System.out.println(order.getOrderNo());
        }
        System.out.println("============== end =========");
    }

    @Test
    public void testqueryList2() {
        System.out.println("============ start ===========");
        Map<String, Object> param = new HashMap<>();
        param.put("orderNo", "123");
        orderMapper.selectIdPage(param);
        System.out.println("============== end =========");
    }

    @Test
    public void testInMapper() {
        System.out.println("============ start ===========");
        EntityWrapper<DgOrder> ew = new EntityWrapper<>();
        ew.in("id_", "999");
        List<DgOrder> orderList = orderMapper.selectList(ew);
        long count = orderList.stream().count();
        System.out.println("============== end =========");
    }

}
