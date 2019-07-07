package com.xmyy.pay.service;

import com.xmyy.pay.model.DgPayLog;
import top.ibase4j.core.base.BaseService;

/**
 * 支付记录  接口
 *
 * @author AnCheng
 */
public interface DgPayLogService extends BaseService<DgPayLog> {

    /**
     * 关闭超时“未支付”的支付记录
     */
    void closeOverTimePay();

}
