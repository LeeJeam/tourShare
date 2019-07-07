package com.xmyy.manage.service;

import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.manage.model.AdminMenu;
import com.xmyy.manage.vo.ManageMenuFrontendResult;
import com.xmyy.manage.vo.ManageMenuNodeCheckResult;
import com.xmyy.manage.vo.ManageMenuNodeResult;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * <p>
 * 10 菜单  服务类
 * </p>
 *
 * @author LinBo
 * @since 2018-06-29
 */
public interface AdminMenuService extends BaseService<AdminMenu> {

    /**
     * 查询有权限的树结构菜单，根据用户权限进行过滤
     * @param userId
     * @return
     */
    List<ManageMenuFrontendResult> queryForPermissionTree(Long userId);

    /**
     * 根据条件查询菜单列表
     * @param params
     * @return
     */
    List<ManageMenuNodeResult> queryForList(AdminMenu params);

    /**
     * 更新菜单
     * @param params
     * @param userInfo
     * @return
     */
    AdminMenu update(AdminMenu params, AdminUserInfo userInfo);

    /**
     * 新增菜单
     * @param params
     * @param userInfo
     * @return
     */
    AdminMenu add(AdminMenu params, AdminUserInfo userInfo);

    /**
     * 删除菜单
     * @param id
     * @param userInfo
     */
    Object delete(Long id, AdminUserInfo userInfo);

    /**
     * 查询指定角色的权限菜单
     * @param roleId
     * @return
     */
    List<ManageMenuNodeCheckResult> queryForTree(Long roleId);

}