package com.xmyy.manage.dao;

import com.xmyy.manage.model.AdminRole;
import com.xmyy.manage.vo.ManageAdminRoleQueryParam;
import com.xmyy.manage.vo.ManageAdminRoleQueryResult;
import com.xmyy.manage.vo.ManagePermissionRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义角色数据库操作接口
 * @author LinBo
 * @date 2018-6-30 13:56
 */
public interface AdminRoleDao {

    /**
     * 根据用户ID查询对应的角色
     * @param userId
     * @return
     */
    List<AdminRole> queryByUserId(@Param("userId") Long userId);

    /**
     * 统计分配到指定角色的用户数
     * @param roleId
     * @return
     */
    int countByUser(@Param("roleId") Long roleId);

    /**
     * 查询指定用户的角色授权
     * @param userId
     */
    List<ManagePermissionRole> queryPermissionRoles(@Param("userId") Long userId);

    /**
     * 查询列表数据
     * @param params
     * @return
     */
    List<ManageAdminRoleQueryResult> queryForList(ManageAdminRoleQueryParam params);
}
