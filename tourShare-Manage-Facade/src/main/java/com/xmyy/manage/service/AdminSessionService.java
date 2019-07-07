package com.xmyy.manage.service;

import com.xmyy.manage.model.AdminSession;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * <p>
 * 10后台登录日志  服务类
 * </p>
 *
 * @author zlp
 * @since 2018-05-30
 */
public interface AdminSessionService extends BaseService<AdminSession> {

    List<?> querySessionIdByAccount(AdminSession record);

    void deleteBySessionId(AdminSession record);
}