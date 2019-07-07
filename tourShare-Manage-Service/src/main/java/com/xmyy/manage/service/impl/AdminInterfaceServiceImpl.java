package com.xmyy.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.manage.dao.AdminInterfaceDao;
import com.xmyy.manage.mapper.AdminInterfaceMapper;
import com.xmyy.manage.model.AdminInterface;
import com.xmyy.manage.service.AdminInterfaceService;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台权限设置  服务实现类
 *
 * @author LinBo
 */
@Service(interfaceClass = AdminInterfaceService.class)
@CacheConfig(cacheNames = "AdminInterface")
public class AdminInterfaceServiceImpl extends BaseServiceImpl<AdminInterface, AdminInterfaceMapper> implements AdminInterfaceService {

    @Resource
    private AdminInterfaceDao adminInterfaceDao;

    @Override
    public List<AdminInterface> queryForPermissionByUser(Long userId) {
        List<AdminInterface> interfaces = adminInterfaceDao.queryForPermissionByUser(userId);
        return interfaces;
    }
}
