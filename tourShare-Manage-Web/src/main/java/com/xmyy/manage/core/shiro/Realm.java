package com.xmyy.manage.core.shiro;

import com.xmyy.common.util.PasswordUtil;
import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.manage.model.AdminInterface;
import com.xmyy.manage.model.AdminRole;
import com.xmyy.manage.model.AdminSession;
import com.xmyy.manage.model.AdminUser;
import com.xmyy.manage.service.AdminInterfaceService;
import com.xmyy.manage.service.AdminRoleService;
import com.xmyy.manage.service.AdminSessionService;
import com.xmyy.manage.service.AdminUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.springframework.stereotype.Component;
import top.ibase4j.core.support.shiro.RedisSessionDAO;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.ShiroUtil;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限检查类
 * zlp
 */
@Component
public class Realm extends AuthorizingRealm implements top.ibase4j.core.support.shiro.Realm {

    private final Logger logger = LogManager.getLogger();

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private AdminSessionService adminSessionService;

    @Resource
    private AdminInterfaceService adminInterfaceService;

    @Resource
    private AdminRoleService adminRoleService;

    private RedisSessionDAO sessionDAO;

    private static PatternMatcher antPathMatcher = new AntPathMatcher();

    public void setSessionDAO(RedisSessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    // 查询登陆用户权限
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Long userId = (Long)ShiroUtil.getCurrentUser();
        // 接口权限
        List<AdminInterface> interfaces = adminInterfaceService.queryForPermissionByUser(userId);
        for (AdminInterface intf : interfaces) {
            String method = intf.getMethod();
            String url = intf.getUrl();
            HttpPermission httpPermission = new HttpPermission(url, method);
            info.addObjectPermission(httpPermission);
        }
        // 角色
        List<AdminRole> roles = adminRoleService.queryByUserId(userId);
        for (AdminRole role : roles) {
            info.addRole(role.getRoleCode());
        }
        return info;
    }

    /**
     * 认证是否有权限调用
     * @param principals
     * @param permission
     * @return
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        // super.getAuthorizationInfo 会优先从缓存中获取登录人对应的权限，对应redis key为 S:iBase4J:SHIRO-CACHE:{角色编码}
        // 如果没有缓存，则调用this.doGetAuthorizationInfo方法进行获取
        AuthorizationInfo info = super.getAuthorizationInfo(principals);
        Collection<String> roles = info.getRoles();
        if (CollectionUtils.isNotEmpty(roles) && roles.contains(AdminUserService.USER_ADMIN_ACCOUNT)) {
            // 管理员角色拥有所有权限
            return true;
        }
        boolean isPermission = super.isPermitted(permission, info);
        return isPermission;
    }

    /**
     * 请求指定用户的权限缓存
     * @param userId
     */
    public void clearCachedAuthorizationInfo(Long userId) {
        AdminUser adminUser = adminUserService.queryById(userId);
        String account = adminUser.getAccount();
        PrincipalCollection principals = new SimplePrincipalCollection(account, account);
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除所有权限缓存
     */
    public void clearCache() {
        Cache<Object, AuthorizationInfo> authorizationCache = super.getAuthorizationCache();
        authorizationCache.clear();
    }

    // 登录验证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("enable", 1);
        params.put("account", token.getUsername());
        List<?> list = adminUserService.queryList(params);
        if (list.size() > 0) {
            AdminUser user = (AdminUser)list.get(0);
            StringBuilder sb = new StringBuilder(100);
            for (int i = 0; i < token.getPassword().length; i++) {
                sb.append(token.getPassword()[i]);
            }
            if (PasswordUtil.verifyPassword(sb.toString(),user.getPassword())) {
                //if (user.getPassword().equals(SecurityUtil.encryptPassword(sb.toString()))) {
                ShiroUtil.saveCurrentUser(user.getId());

                AdminUserInfo userInbfo = InstanceUtil.to(user, AdminUserInfo.class);
                userInbfo.setPassword(null);

                ShiroUtil.setSession("CURRENT_USERINFO",userInbfo);

                saveSession(user.getAccount(), token.getHost());

                AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getAccount(), sb.toString(),
                        user.getUserName());
                return authcInfo;
            }
            logger.warn("USER [{}] PASSWORD IS WRONG: {}", token.getUsername(), sb.toString());
            return null;
        } else {
            logger.warn("No user: {}", token.getUsername());
            return null;
        }
    }

    /** 保存session */
    private void saveSession(String account, String host) {
        // 踢出用户
        AdminSession record = new AdminSession();
        record.setAccount(account);
        List<?> sessionIds = adminSessionService.querySessionIdByAccount(record);
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        String currentSessionId = session.getId().toString();
        if (sessionIds != null) {
            for (Object sessionId : sessionIds) {
                record.setSessionId((String)sessionId);
                adminSessionService.deleteBySessionId(record);
                if (!currentSessionId.equals(sessionId)) {
                    sessionDAO.delete((String)sessionId);
                }
            }
        }
        // 保存用户
        record.setSessionId(currentSessionId);
        record.setIp(StringUtils.isBlank(host) ? session.getHost() : host);
        record.setStartTime(session.getStartTimestamp());
        adminSessionService.update(record);
    }
}
