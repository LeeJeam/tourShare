package com.xmyy.product.service.impl;

import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.product.model.DgCurrencyExchange;
import com.xmyy.product.service.DgCurrencyExchangeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Simon on 2018/7/17.
 */
@ActiveProfiles("build")
public class DgCurrencyExchangeServiceImplTest extends BaseJUnitTest {

    @Resource
    DgCurrencyExchangeService dgCurrencyExchangeService;

    @Test
    public void getList() {
        Object list = dgCurrencyExchangeService.getList();
        System.out.println(list);
    }

    @Test
    public void getExchangeList() {
        List<DgCurrencyExchange> exchangeList = dgCurrencyExchangeService.getExchangeList();
        System.out.println(exchangeList);
    }

    @Test
    public void getExchangeResult() {
        String from ="CNY";
        String to = "USD";
        Double exchangeResult = dgCurrencyExchangeService.getExchangeResult(from, to);
        System.out.println(exchangeResult);
        Assert.assertNotNull(exchangeResult);
    }
}