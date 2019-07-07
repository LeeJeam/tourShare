package com.xmyy.pay.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ibase4j.core.interceptor.BaseInterceptor;
import top.ibase4j.core.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通联支付回调拦截器
 *
 * @author AnCheng
 */
public class PayCallBackInterceptor extends BaseInterceptor {
    private static Logger logger = LoggerFactory.getLogger(PayCallBackInterceptor.class);
    private static String[] hosts = {"127.0.0.1", "116.228.64.55", "yun.allinpay.com"};

    public PayCallBackInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String path = request.getServletPath();
        String clientIp;
        String url = "/api/pay/callBack";
        if (path.contains(url)) {
            clientIp = WebUtil.getHost(request);
            for (String host : hosts) {
                if (host.equals(clientIp)) {
                    flag = true;
                    break;
                }
            }
            logger.info("[callback] {},ip:{},flag:{}", path, clientIp, flag);
            if (flag) {
                return super.preHandle(request, response, handler);
            } else {
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }

}
