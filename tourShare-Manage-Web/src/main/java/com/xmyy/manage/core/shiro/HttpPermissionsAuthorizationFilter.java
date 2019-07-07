package com.xmyy.manage.core.shiro;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Http 权限过滤器
 * @author LinBo
 * @date 2018-6-28 16:15
 */
//public class HttpPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
public class HttpPermissionsAuthorizationFilter extends AuthorizationFilter {

    /**
     * 由请求产生权限信息，由Realm进行查询有权限调用的接口集合，由 HttpPermission 的implies方法进行权限判断
     * @param request Http请求
     * @param response      Http响应
     * @param mappedValue   权限
     * @return  是否有权限访问
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getServletPath();
        String method = req.getMethod();
        Permission permission = new HttpPermission(path, method);
        return subject.isPermitted(permission);
    }

    /**
     * 重写无权访问逻辑，改写shiro默认重定向为请求转发，避免前后端分离后，前端使用Ajax进行请求会重定向，无法获取响应报文。
     * 由前端进行状态识别，进行页面展示控制
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        String unauthorizedUrl = super.getUnauthorizedUrl();
        try {
            Subject subject = super.getSubject(request, response);
            // 如果未登录，则提示请求转发未登录提示接口
            if (subject.getPrincipal() == null) {
//                super.saveRequestAndRedirectToLogin(request, response);
                String loginUrl = super.getLoginUrl();
                request.getRequestDispatcher(loginUrl).forward(request, response);
            } else {
                // 如果已有登录信息，但未成功认证，且有配置未认证地址，则请求转发未认证接口
                // If no unauthorized URL is specified, just return an unauthorized HTTP status code
                //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
                if (StringUtils.hasText(unauthorizedUrl)) {
//                    WebUtils.issueRedirect(request, response, unauthorizedUrl);
                    request.getRequestDispatcher(unauthorizedUrl).forward(request, response);
                } else {
                    WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        } catch (ServletException e) {
            throw new IOException(e.getRootCause());
        }
        return false;
    }
}
