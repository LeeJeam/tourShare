package com.xmyy.manage.service;

import com.xmyy.manage.model.AdminInterface;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * <p>
 * 接口  服务类
 * </p>
 *
 * @author LinBo
 * @since 2018-07-11
 */
public interface AdminInterfaceService extends BaseService<AdminInterface> {

    /**
     * 查询自定用户对应有权限的后台接口集合
     * @param userId
     * @return
     */
    List<AdminInterface> queryForPermissionByUser(Long userId);
}