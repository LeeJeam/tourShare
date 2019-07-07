package com.xmyy.manage.service;

import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.manage.model.AdminUserRole;
import com.xmyy.manage.vo.ManageAuthParam;
import top.ibase4j.core.base.BaseService;

/**
 * <p>
 * 10 用户角色  服务类
 * </p>
 *
 * @author LinBo
 * @since 2018-06-29
 */
public interface AdminUserRoleService extends BaseService<AdminUserRole> {

    /**
     * 人员授权角色
     * @param params
     * @param userInfo
     * @return
     */
    Object auth(ManageAuthParam params, AdminUserInfo userInfo);
}