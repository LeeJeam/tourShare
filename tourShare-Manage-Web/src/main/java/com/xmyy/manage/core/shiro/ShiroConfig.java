package com.xmyy.manage.core.shiro;

import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.ibase4j.core.listener.SessionListener;
import top.ibase4j.core.support.cache.shiro.RedisCacheManager;
import top.ibase4j.core.support.shiro.Realm;
import top.ibase4j.core.support.shiro.RedisSessionDAO;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.PropertiesUtil;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限拦截配置
 *
 * @author ShenHuaJie
 * @since 2017年8月14日 上午10:40:20
 */
@Configuration
public class ShiroConfig {

    // shiro 过滤拦截规则
    private static final String SHIRO_FILTER ;

    static {
        // 无需认证接口
        String[] annonList = {
                "/swagger**", "/swagger-resources/**", "/configuration/**", "/v2/api-docs", "/webjars/**", // swagger-ui
                "/manage/region", "/manage/login", "/unauthorized", "/forbidden", "/manage/enum/get", "/manage/logout" // 系统基础接口
        };
        // 需要登录认证接口
        String[] authcList = {
                "/manage/users/update", "/manage/menus/permissionTree" // 用户基础接口
        };
        StringBuilder filterBuf = new StringBuilder();
        for (String str : annonList) {
            filterBuf.append(str).append("=anon;");
        }
        for (String str : authcList) {
            filterBuf.append(str).append("=authc;");
        }
        filterBuf.append("/**=roles[admin];"); // admin角色用于所有接口权限
        filterBuf.append("/**=perms;"); // 其他用户进行权限拦截
        SHIRO_FILTER = filterBuf.toString();
    }

    @Bean
    public SessionListener sessionListener() {
        return new SessionListener();
    }

    @Bean
    public SessionDAO sessionDao(Realm realm) {
        RedisSessionDAO dao = new RedisSessionDAO();
        realm.setSessionDAO(dao);
        return dao;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(AuthorizingRealm realm, SessionManager sessionManager,
                                                     RememberMeManager rememberMeManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        manager.setCacheManager(new RedisCacheManager());
        manager.setSessionManager(sessionManager);
        manager.setRememberMeManager(rememberMeManager);
        return manager;
    }

    @Bean
    public SessionManager sessionManager(SessionDAO sessionDao, SessionListener sessionListener, Cookie cookie) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDao);
        sessionManager.getSessionListeners().add(sessionListener);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }

    @Bean
    public Cookie cookie() {
        SimpleCookie cookie = new SimpleCookie("IBASE4JSESSIONID");
        cookie.setSecure(PropertiesUtil.getBoolean("session.cookie.secure", false));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        return cookie;
    }

    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.getCookie().setMaxAge(PropertiesUtil.getInt("rememberMe.cookie.maxAge", 60 * 60 * 24));
        return rememberMeManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
        factory.setSecurityManager(securityManager);
        factory.setLoginUrl("/unauthorized");
        factory.setUnauthorizedUrl("/forbidden");
        Map<String, Filter> fm = new HashMap<>();
        fm.put("perms", new HttpPermissionsAuthorizationFilter());
        fm.put("authc", new HttpFormAuthenticationFilter ());
        factory.setFilters(fm);

        Map<String, String> filterMap = InstanceUtil.newLinkedHashMap();
        for (String filter : SHIRO_FILTER.split("\\;")) {
            String[] keyValue = filter.split("\\=");
            filterMap.put(keyValue[0], keyValue[1]);
        }
        factory.setFilterChainDefinitionMap(filterMap);
        return factory;
    }

}
