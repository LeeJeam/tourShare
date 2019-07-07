package com.xmyy.manage.dao;

import com.xmyy.manage.vo.ManageAdminUserQueryParam;
import com.xmyy.manage.vo.ManageAdminUserQueryResult;

import java.util.List;

/**
 * 系统用户数据访问层接口
 * @author LinBo
 * @date 2018-7-3 14:33
 */
public interface AdminUserDao {

    /**
     * 条件查询
     * @param params
     * @return
     */
    List<ManageAdminUserQueryResult> queryForList(ManageAdminUserQueryParam params);

    /**
     * 条件统计
     * @param params
     * @return
     */
    Integer countForList(ManageAdminUserQueryParam params);
}
