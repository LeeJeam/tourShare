package com.xmyy.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.common.util.PasswordUtil;
import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.manage.dao.AdminMenuDao;
import com.xmyy.manage.dao.AdminRoleDao;
import com.xmyy.manage.dao.AdminUserDao;
import com.xmyy.manage.mapper.AdminUserMapper;
import com.xmyy.manage.mapper.AdminUserRoleMapper;
import com.xmyy.manage.model.AdminUser;
import com.xmyy.manage.model.AdminUserRole;
import com.xmyy.manage.service.AdminUserService;
import com.xmyy.manage.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.exception.BizException;
import top.ibase4j.core.support.Assert;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * 后台用户  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = AdminUserService.class)
@CacheConfig(cacheNames = "AdminUser")
public class AdminUserServiceImpl extends BaseServiceImpl<AdminUser, AdminUserMapper> implements AdminUserService {

    @Resource
    private AdminUserRoleMapper userRoleMapper;
    @Resource
    private AdminUserDao userDao;
    @Resource
    private AdminRoleDao roleDao;
    @Resource
    private AdminMenuDao adminMenuDao;
    @Resource
    private AdminUserRoleMapper adminUserRoleMapper;

    @Override
    public Pagination<ManageAdminUserQueryResult> queryForPageList(ManageAdminUserQueryParam params) {
        List<ManageAdminUserQueryResult> users = userDao.queryForList(params);
        Integer total = 0;
        if (!CollectionUtils.isEmpty(users)) {
            total = userDao.countForList(params);
        }
        Pagination<ManageAdminUserQueryResult> pager = new Pagination<>(params.getCurrent(), params.getSize());
        pager.setRecords(users);
        pager.setTotal(total);
        return pager;
    }

    @Override
    public List<ManagePermissionRole> queryPermissionRoles(Long userId, AdminUserInfo userInfo) {
        // 查询指定用户的所有角色权限
        List<ManagePermissionRole> roles = roleDao.queryPermissionRoles(userId);
        // 查询指定用户拥有所有菜单
//        List<ManageMenuNodeResult> menus = adminMenuDao.queryForPermissionList(null, userId);
        // 查询当前用户的菜单集合
        List<ManageRoleMenusMap> userRoleMenusMap = adminMenuDao.queryGroupByRole(userId);
        // 查询所有角色对应的菜单集合
        List<ManageRoleMenusMap> allRoleMenusMap = adminMenuDao.queryGroupByRole(null);
        // 根据有权限菜单进行角色过滤，只返回有权限菜单范围内对应的所有角色
        Set<Long> userMenuIds = new HashSet<>(); // 当前用户有权的菜单集合
        Set<Long> permissionRoleIds = new HashSet<>(); // 有权限的角色：当前用户有权限菜单范围内
        userRoleMenusMap.forEach(m -> {
            for (String id : m.getMenuIds().split(",")) {
                userMenuIds.add(Long.parseLong(id));
            }
        });

        allRoleMenusMap.forEach(m -> {
            String menuIds = m.getMenuIds();
            for (String id : menuIds.split(",")) {
                if (userMenuIds.contains(Long.parseLong(id))) {
                    permissionRoleIds.add(Long.parseLong(id));
                }
            }
        });
        List<ManagePermissionRole> permissionRoles = new ArrayList<>();
        //  移除没权限的角色
        roles.forEach(r -> {
            if (permissionRoleIds.contains(r.getRoleId())) {
                permissionRoles.add(r);
            }
        });
        return permissionRoles;
    }

    @Override
    @Transactional
    public AdminUser add(ManageAdminUserEditParam params, AdminUserInfo userInfo) throws BizException {
        Assert.notNull(params.getAccount(), "ACCOUNT");
        Assert.notNull(params.getPassword(), "PASSWORD");
        List<AdminUser> aus = queryList(InstanceUtil.newHashMap("account", params.getAccount()));
        if (!CollectionUtils.isEmpty(aus)) {
            throw new BizException("账号已被注册");
        }
        AdminUser user = new AdminUser();
        BeanUtils.copyProperties(params, user);
        String password = params.getPassword();
        user.setPassword(PasswordUtil.encodePassword(password));//SecurityUtil.encryptPassword(sysUser.getPassword())
        Date now = new Date();
        user.setCreateBy(userInfo.getId());
        user.setCreateTime(now);
        user.setUpdateBy(userInfo.getId());
        user.setUpdateTime(now);
        mapper.insert(user);
        AdminUser adminUser = mapper.selectById(user.getId());
        List<Long> roleIds = params.getRoleIds();
        if (!CollectionUtils.isEmpty(roleIds)) {
            for (Long id : roleIds) {
                AdminUserRole ur = new AdminUserRole();
                ur.setUserId(user.getId());
                ur.setRoleId(id);
                ur.setCreateTime(now);
                ur.setCreateBy(userInfo.getId());
                adminUserRoleMapper.insert(ur);
            }
        }
        return adminUser;
    }

    @Override
    public AdminUser update(AdminUser user, AdminUserInfo userInfo) {
        // 不允许管理员账户修改账号
        AdminUser adminUser = mapper.selectById(user.getId());
        boolean isAdminUser = AdminUserService.USER_ADMIN_ACCOUNT.equals(adminUser.getAccount());
        boolean isUpdateAccount = adminUser.getAccount().equals(user.getAccount());
        Assert.isTrue(isAdminUser && isUpdateAccount, "不允许修改系统管理员账号");

        user.setUpdateBy(userInfo.getId());
        user.setUpdateTime(new Date());
        if (StringUtils.hasLength(user.getPassword())) {
            user.setPassword(PasswordUtil.encodePassword(user.getPassword()));
        }
        mapper.updateById(user);
        return user;
    }

}
