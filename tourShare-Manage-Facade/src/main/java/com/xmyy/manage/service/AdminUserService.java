package com.xmyy.manage.service;

import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.manage.model.AdminUser;
import com.xmyy.manage.vo.ManageAdminUserEditParam;
import com.xmyy.manage.vo.ManageAdminUserQueryParam;
import com.xmyy.manage.vo.ManageAdminUserQueryResult;
import com.xmyy.manage.vo.ManagePermissionRole;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.exception.BizException;
import top.ibase4j.core.support.Pagination;

import java.util.List;

/**
 * <p>
 * 后台用户  服务类
 * </p>
 *
 * @author zlp
 * @since 2018-05-30
 */
public interface AdminUserService extends BaseService<AdminUser> {

    /**
     * 管理员账号
     */
    final String USER_ADMIN_ACCOUNT = "admin";

    /**
     * 条件查询用户信息
     * @param params
     * @return
     */
    Pagination<ManageAdminUserQueryResult> queryForPageList(ManageAdminUserQueryParam params);

    /**
     * 查询
     * @param userId
     * @param userInfo
     * @return
     */
    List<ManagePermissionRole> queryPermissionRoles(Long userId, AdminUserInfo userInfo);

    /**
     * 新增用户
     * @param params
     * @param userInfo
     * @return
     */
    AdminUser add(ManageAdminUserEditParam params, AdminUserInfo userInfo) throws BizException;

    /**
     * 更新用户
     * @param user
     * @param userInfo
     * @return
     */
    AdminUser update(AdminUser user, AdminUserInfo userInfo);
}