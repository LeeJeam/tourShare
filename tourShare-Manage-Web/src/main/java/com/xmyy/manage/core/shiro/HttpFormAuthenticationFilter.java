package com.xmyy.manage.core.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author LinBo
 * @date 2018-7-13 18:17
 */
public class HttpFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(HttpFormAuthenticationFilter.class);

    /**
     * 重写无权访问逻辑，改写shiro默认重定向为请求转发，避免前后端分离后，前端使用Ajax进行请求会重定向，无法获取响应报文。
     * 由前端进行状态识别，进行页面展示控制
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }
            request.getRequestDispatcher(getLoginUrl()).forward(request, response);
//            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }
}
