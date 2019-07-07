package com.xmyy.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmyy.pay.allinpay.bean.OrderPayBean;
import com.xmyy.pay.service.AllinPayOrderService;
import com.xmyy.pay.service.CallBackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 通联回调  服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = CallBackService.class)
@CacheConfig(cacheNames = "callBackService")
public class CallBackServiceImpl implements CallBackService {
    private static Logger logger = LoggerFactory.getLogger(CallBackServiceImpl.class);

    @Resource
    private AllinPayOrderService payOrderService;

    @Override
    public Object signContractCallBack(String strRps) {
        saveCallBackLog(strRps);

        JSONObject rps = JSONObject.parseObject(strRps);
        String status = rps.getString("status");
        if ("OK".equalsIgnoreCase(status)) {  //签约成功
            String returnValue = rps.getString("returnValue");
            JSONObject result = JSONObject.parseObject(returnValue);
            String bizUserId = result.getString("bizUserId");

        } else {  //签约失败
            logger.info(rps.getString("message"));
            String errorCode = rps.getString("errorCode");
            String message = rps.getString("message");
        }

        return null;
    }


    @Override
    @Transactional
    public Object orderPayCallBack(String strRps) {
        saveCallBackLog(strRps);

        JSONObject rps = JSONObject.parseObject(strRps);
        String status = rps.getString("status");
        if ("OK".equalsIgnoreCase(status)) {  //支付成功
            JSONObject returnValue = rps.getJSONObject("returnValue");
            OrderPayBean payBean = JSON.toJavaObject(returnValue, OrderPayBean.class);

            //主动查询支付状态，若支付状态发生变更进行处理
            Object obj = payOrderService.getOrderDetail(payBean.getBizOrderNo());
            if (obj instanceof String) {
                logger.error("订单异步回调，查询支付状态失败：{}", obj);
                return null;
            }

        } else { //支付失败、进行中
            //TODO 提现、代付到银行卡，无论是否成功都会有通知
            logger.info(rps.getString("message"));
            String errorCode = rps.getString("errorCode");
            String message = rps.getString("message");
        }

        return null;
    }


    private void saveCallBackLog(String rps) {
        //TODO 记录请求回调JSON到数据库
    }

}
