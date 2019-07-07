package com.xmyy.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.codingapi.tx.annotation.TxTransaction;
import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.manage.dao.AdminMenuDao;
import com.xmyy.manage.dao.AdminRoleDao;
import com.xmyy.manage.mapper.AdminRoleMapper;
import com.xmyy.manage.model.AdminRole;
import com.xmyy.manage.service.AdminRoleService;
import com.xmyy.manage.vo.ManageAdminRoleQueryParam;
import com.xmyy.manage.vo.ManageAdminRoleQueryResult;
import com.xmyy.manage.vo.ManagePermissionRole;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import top.ibase4j.core.base.BaseServiceImpl;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 权限-角色  服务实现类
 *
 * @author LinBo
 */
@Service(interfaceClass = AdminRoleService.class)
//@CacheConfig(cacheNames = "AdminRole")
public class AdminRoleServiceImpl extends BaseServiceImpl<AdminRole, AdminRoleMapper> implements AdminRoleService {

    @Resource
    private AdminRoleDao adminRoleDao;

    @Resource
    private AdminMenuDao adminMenuDao;

    @Override
    public List<AdminRole> queryByUserId(Long userId) {
        return adminRoleDao.queryByUserId(userId);
    }

    @Override
    public AdminRole add(AdminRole role) {
        validateRole(role);
        role.setCreateTime(new Date());
        mapper.insert(role);
        return mapper.selectById(role.getId());
    }

    @Override
    public Object delete(Long id, AdminUserInfo userInfo) {
        // 如果角色已被分配到用户，则不允许删除，反之，允许删除
        int count = adminRoleDao.countByUser(id);
        Assert.isTrue(count == 0, "角色已分配给用户，请撤销角色授权，再删除");
        // 不允许删除管理员角色
        AdminRole adminRole = mapper.selectById(id);
        boolean isAdminRole = AdminRoleService.ROLE_ADMIN_CODE.equals(adminRole.getRoleCode());
        Assert.isTrue(isAdminRole, "不允许删除管理员角色");
        mapper.deleteById(id);
        return null;
    }

    @Override
    public List<ManageAdminRoleQueryResult> queryForList(ManageAdminRoleQueryParam params) {
        return adminRoleDao.queryForList(params);
    }


    //TODO 根据用户
    @Override
    public List<ManagePermissionRole> queryPermissionRoles(Long userId, AdminUserInfo userInfo) {
        /*// 查询指定用户的所有角色权限
        List<ManagePermissionRole> roles = adminRoleDao.queryPermissionRoles(userId);
        // 查询指定用户拥有所有菜单
        List<ManageMenuNodeResult> menus = adminMenuDao.queryForPermissionList(null, userId);
        // 查询登陆用户的菜单集合
        List<ManageRoleMenusMap> userRoleMenusMap = adminMenuDao.queryGroupByRole(userInfo.getId());
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
        return permissionRoles;*/

        // 查询指定用户的所有角色权限
        return adminRoleDao.queryPermissionRoles(userId);
    }

    @Override
    @TxTransaction
    @Transactional
    public AdminRole update(AdminRole role) {
        validateRole(role);
        role.setUpdateTime(new Date());
        mapper.updateById(role);
        return mapper.selectById(role.getId());
    }

    private void validateRole(AdminRole role) {
        String roleCode = role.getRoleCode();
        String roleName = role.getRoleName();
        // 不允许角色名称、角色编码重复
        Wrapper<AdminRole> wrapper = new EntityWrapper<>();
        wrapper.eq("role_name", roleName)
                .and()
                .eq("role_code", roleCode);
        List<AdminRole> existsRoles = mapper.selectList(wrapper);
        Optional.ofNullable(existsRoles).ifPresent(
                roles -> roles.forEach(r -> {
                    Assert.isTrue(!roleName.equals(r.getRoleName()), "角色名称已存在");
                    Assert.isTrue(!roleCode.equals(r.getRoleCode()), "角色编码已存在");
                })
        );
    }

}
