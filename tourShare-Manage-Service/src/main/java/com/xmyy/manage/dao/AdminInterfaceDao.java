package com.xmyy.manage.dao;

import com.xmyy.manage.model.AdminInterface;

import java.util.List;

/**
 * 后台接口数据访问层
 * @author LinBo
 * @date 2018-7-11 18:25
 */
public interface AdminInterfaceDao {

    /**
     * 根据用户查询有权限的后台接口集合
     * @param userId
     * @return
     */
    List<AdminInterface> queryForPermissionByUser(Long userId);
}
