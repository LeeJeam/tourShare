package com.xmyy.order;

import com.xmyy.order.service.DgOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JunitOrderTest {

    @Autowired
    private DgOrderService orderService;

    @Test
    public void test1() {
        System.out.println(132);
    }
}
