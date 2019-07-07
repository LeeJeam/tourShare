package com.xmyy.manage.service;

import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.manage.model.AdminRole;
import com.xmyy.manage.vo.ManageAdminRoleQueryParam;
import com.xmyy.manage.vo.ManageAdminRoleQueryResult;
import com.xmyy.manage.vo.ManagePermissionRole;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * <p>
 * 10 角色  服务类
 * </p>
 *
 * @author LinBo
 * @since 2018-06-29
 */
public interface AdminRoleService extends BaseService<AdminRole> {

    /**
     * 管理员角色代码
     */
    final String ROLE_ADMIN_CODE = "admin";

    /**
     * 根据用户ID查询对应角色
     * @param userId
     * @return
     */
    List<AdminRole> queryByUserId(Long userId);

    /**
     * 新增角色
     * @param params
     * @return
     */
    AdminRole add(AdminRole params);

    /**
     * 删除角色
     * @param id
     * @param userInfo
     * @return
     */
    Object delete(Long id, AdminUserInfo userInfo);

    /**
     * 查询角色列表数据
     * @param params
     * @return
     */
    List<ManageAdminRoleQueryResult> queryForList(ManageAdminRoleQueryParam params);

    /**
     * 查询用户对应角色授权信息
     * @param userId
     * @param userInfo
     * @return
     */
    List<ManagePermissionRole> queryPermissionRoles(Long userId, AdminUserInfo userInfo);

}