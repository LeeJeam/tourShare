package com.xmyy.manage.service;

import com.xmyy.manage.model.AdminDic;
import com.xmyy.manage.vo.ManageAdminDicPageParam;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.support.Pagination;

import java.util.Set;

/**
 * <p>
 * 数据字典明细表  服务类
 * </p>
 *
 * @author zlp
 * @since 2018-07-02
 */
public interface AdminDicService extends BaseService<AdminDic> {

    AdminDic insert(AdminDic dic);

    Set<String> findHotWords(String type, String code, String keywords);

    Pagination<AdminDic> list(ManageAdminDicPageParam param);


    Object save(AdminDic param);
}