package com.xmyy.product.service;

import com.xmyy.product.model.DgCurrencyExchange;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * 汇率  服务接口
 *
 * @author simon
 */
public interface DgCurrencyExchangeService extends BaseService<DgCurrencyExchange> {

    Object getList();

    List<DgCurrencyExchange> getExchangeList();

    Double getExchangeResult(String from, String to);
	
}