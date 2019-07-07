package com.xmyy.common.shiro;

import com.xmyy.common.vo.AdminUserInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public final class ShiroUtils {

    private static Logger logger = LogManager.getLogger();

    public static AdminUserInfo getCurrentUserInfo() {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            try {
                Session session = currentUser.getSession();
                if (null != session) {
                    return (AdminUserInfo)session.getAttribute("CURRENT_USERINFO");
                }
            } catch (InvalidSessionException var2) {
                logger.error("", var2);
            }
        }

        return null;
    }
}
