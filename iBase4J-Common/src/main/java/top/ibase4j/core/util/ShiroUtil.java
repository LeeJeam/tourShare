package top.ibase4j.core.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ibase4j.core.Constants;

public final class ShiroUtil {
    private static Logger logger = LoggerFactory.getLogger(ShiroUtil.class);

    /** 保存当前用户 */
    public static void saveCurrentUser(Object user) {
        setSession(Constants.CURRENT_USER, user);
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     * 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    public static void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }

    /** 获取当前用户 */
    public static Long getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            try {
                Session session = currentUser.getSession();
                if (null != session) {
                    return (Long)session.getAttribute(Constants.CURRENT_USER);
                }
            } catch (InvalidSessionException e) {
                logger.error("", e);
            }
        }
        return null;
    }

}
