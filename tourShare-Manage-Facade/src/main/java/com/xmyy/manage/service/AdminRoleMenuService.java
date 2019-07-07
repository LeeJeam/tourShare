package com.xmyy.manage.service;

import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.manage.model.AdminRoleMenu;
import com.xmyy.manage.vo.ManageRoleAuthParam;
import top.ibase4j.core.base.BaseService;

/**
 * <p>
 * 10 角色菜单  服务类
 * </p>
 *
 * @author LinBo
 * @since 2018-06-29
 */
public interface AdminRoleMenuService extends BaseService<AdminRoleMenu> {

    /**
     * 角色授权
     * @param params
     * @param userInfo
     * @return
     */
    Object auth(ManageRoleAuthParam params, AdminUserInfo userInfo);

}