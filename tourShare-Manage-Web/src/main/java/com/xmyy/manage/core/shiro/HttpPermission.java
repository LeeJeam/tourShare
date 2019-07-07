package com.xmyy.manage.core.shiro;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.Assert;

import java.io.Serializable;

/**
 * HTTP 权限。根据HTTP 请求路径和请求方法进行判断
 * @author LinBo
 * @date 2018-7-11 20:18
 */
public class HttpPermission implements Permission, Serializable {

    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    private String url;

    private String method;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public HttpPermission(String url, String method) {
        Assert.isTrue(url != null, "请求路径为空");
        Assert.isTrue(method != null, "请求方法为空");
        this.url = url;
        this.method = method.toUpperCase();
    }

    /**
     * HTTP 请求的权限判断
     * @param p
     * @return
     */
    @Override
    public boolean implies(Permission p) {
        if (p instanceof HttpPermission) {
            HttpPermission hp = (HttpPermission) p;
            String tarUrl = hp.url;
            String tarMethod = hp.method;
            boolean matchUrl = MATCHER.match(this.url, tarUrl);
            boolean matchMethod = method.equals(tarMethod);
            return matchUrl && matchMethod;
        }
        return false;
    }
}
