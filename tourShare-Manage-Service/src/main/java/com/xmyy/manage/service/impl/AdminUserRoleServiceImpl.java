package com.xmyy.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.manage.mapper.AdminUserMapper;
import com.xmyy.manage.mapper.AdminUserRoleMapper;
import com.xmyy.manage.model.AdminUser;
import com.xmyy.manage.model.AdminUserRole;
import com.xmyy.manage.service.AdminUserRoleService;
import com.xmyy.manage.service.AdminUserService;
import com.xmyy.manage.vo.ManageAuthParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.base.BaseServiceImpl;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户角色  服务实现类
 *
 * @author LinBo
 */
@Service(interfaceClass = AdminUserRoleService.class)
@CacheConfig(cacheNames = "AdminUserRole")
public class AdminUserRoleServiceImpl extends BaseServiceImpl<AdminUserRole, AdminUserRoleMapper> implements AdminUserRoleService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    @Transactional
    public Object auth(ManageAuthParam params, AdminUserInfo userInfo) {
        Long userId = params.getUserId();
        // 管理员账号不允许编辑授权角色
        AdminUser adminUser = adminUserMapper.selectById(userId);
        boolean isAdminUser = AdminUserService.USER_ADMIN_ACCOUNT.equals(adminUser.getAccount());
        Assert.isTrue(!isAdminUser, "不允许编辑管理员账户对应的角色");

        List<Long> roleIds = params.getRoleIds();
        // 清空用户原有的授予的角色
        Wrapper<AdminUserRole> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id_", userId);
        mapper.delete(wrapper);
        if (!CollectionUtils.isEmpty(roleIds)) {
            // 给用户授予角色
            Date curTime = new Date();
            for (Long rId : roleIds) {
                AdminUserRole auth = new AdminUserRole();
                auth.setUserId(userId);
                auth.setRoleId(rId);
                auth.setCreateBy(userInfo.getId());
                auth.setCreateTime(curTime);
                mapper.insert(auth);
            }
        }
        return null;
    }
}
