package com.xmyy.manage.dao;

import com.xmyy.manage.model.AdminMenu;
import com.xmyy.manage.vo.ManageMenuNodeCheckResult;
import com.xmyy.manage.vo.ManageMenuNodeResult;
import com.xmyy.manage.vo.ManageRoleMenusMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LinBo
 * @date 2018-6-29 14:26
 */
public interface AdminMenuDao {

    /**
     * 条件查询、指定用户有权限的菜单
     * @param params
     * @param userId
     * @return
     */
    List<ManageMenuNodeResult> queryForPermissionList(@Param("params") AdminMenu params, @Param("userId") Long userId);

    /**
     * 条件查询菜单
     * @param params
     * @return
     */
    List<ManageMenuNodeResult> queryForList(@Param("params") AdminMenu params);

    /**
     * 统计指定菜单分配的角色数
     * @param menuId
     * @return
     */
    Integer countForRole(@Param("menuId") Long menuId);

    /**
     * 查询指定角色下，所有菜单对应的权限
     * @param roleId
     * @return
     */
    List<ManageMenuNodeCheckResult> queryForCheckList(@Param("roleId") Long roleId);

    /**
     * 查询角色对应菜单集合。如果传入用户ID，则只返回用户对应角色和对应菜单集合
     * @param userId
     * @return
     */
    List<ManageRoleMenusMap> queryGroupByRole(@Param("userId") Long userId);
}
