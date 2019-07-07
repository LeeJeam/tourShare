package com.xmyy.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.manage.mapper.AdminRoleMapper;
import com.xmyy.manage.mapper.AdminRoleMenuMapper;
import com.xmyy.manage.model.AdminRole;
import com.xmyy.manage.model.AdminRoleMenu;
import com.xmyy.manage.service.AdminRoleMenuService;
import com.xmyy.manage.service.AdminRoleService;
import com.xmyy.manage.vo.ManageRoleAuthParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.util.Assert;
import top.ibase4j.core.base.BaseServiceImpl;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 权限-角色菜单  服务实现类
 *
 * @author LinBo
 */
@Service(interfaceClass = AdminRoleMenuService.class)
@CacheConfig(cacheNames = "AdminRoleMenu")
public class AdminRoleMenuServiceImpl extends BaseServiceImpl<AdminRoleMenu, AdminRoleMenuMapper> implements AdminRoleMenuService {

    @Resource
    private AdminRoleMapper adminRoleMapper;

    @Override
    public Object auth(ManageRoleAuthParam params, AdminUserInfo userInfo) {
        Long roleId = params.getRoleId();
        // 管理员角色不允许编辑权限
        AdminRole adminRole = adminRoleMapper.selectById(roleId);
        boolean isAdminRole = AdminRoleService.ROLE_ADMIN_CODE.equals(adminRole.getRoleCode());
        Assert.isTrue(!isAdminRole, "管理员角色不允许编辑");

        List<Long> menuIds = params.getMenuIds();
        Wrapper<AdminRoleMenu> wrapper = new EntityWrapper<>();
        wrapper.where("role_id_ = {0}", roleId);
        mapper.delete(wrapper);
        if (CollectionUtils.isNotEmpty(menuIds)) {
            Date now = new Date();
            for (Long menuId : menuIds) {
                AdminRoleMenu roleMenu = new AdminRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenu.setCreateBy(userInfo.getId());
                roleMenu.setCreateTime(now);
                roleMenu.setUpdateBy(userInfo.getId());
                roleMenu.setUpdateTime(now);
                mapper.insert(roleMenu);
            }
        }
        return null;
    }
}
