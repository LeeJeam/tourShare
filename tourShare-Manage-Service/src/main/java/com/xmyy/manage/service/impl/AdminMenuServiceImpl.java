package com.xmyy.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.manage.dao.AdminMenuDao;
import com.xmyy.manage.mapper.AdminMenuMapper;
import com.xmyy.manage.model.AdminMenu;
import com.xmyy.manage.service.AdminMenuService;
import com.xmyy.manage.vo.ManageMenuFrontendResult;
import com.xmyy.manage.vo.ManageMenuNodeCheckResult;
import com.xmyy.manage.vo.ManageMenuNodeResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.util.Assert;
import top.ibase4j.core.base.BaseServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单  服务实现类
 *
 * @author LinBo
 */
@Service(interfaceClass = AdminMenuService.class)
@CacheConfig(cacheNames = "AdminMenu")
public class AdminMenuServiceImpl extends BaseServiceImpl<AdminMenu, AdminMenuMapper> implements AdminMenuService {

    @Resource
    private AdminMenuDao menuDao;

    @Override
    public List<ManageMenuFrontendResult> queryForPermissionTree(Long userId) {
        List<ManageMenuNodeResult> menus = menuDao.queryForPermissionList(null, userId);
        List<ManageMenuNodeResult> result = parseMenu2Tree(menus);
        return translateModel(result);
    }

    /**
     * 转换成前端模型
     *
     * @param src
     * @return
     */
    private List<ManageMenuFrontendResult> translateModel(List<ManageMenuNodeResult> src) {
        if (CollectionUtils.isEmpty(src)) {
            return new ArrayList<>(0);
        }
        List<ManageMenuFrontendResult> results = new ArrayList<>(src.size());
        src.forEach(s -> {
            ManageMenuFrontendResult tar = new ManageMenuFrontendResult();
            tar.setId(s.getId());
            tar.setpId(s.getpId());
            tar.setIcon(s.getIcon());
            tar.setSortNo(s.getSortNo());
            tar.setPath(s.getHtmlUrl());
            tar.setTitle(s.getMenuName());
            tar.setName(s.getComponentCode());
            List<ManageMenuFrontendResult> children = translateModel(s.getChildren());
            tar.setChildren(children);
            results.add(tar);
        });
        return results;
    }

    @Override
    public List<ManageMenuNodeCheckResult> queryForTree(Long roleId) {
        List<ManageMenuNodeCheckResult> menus = menuDao.queryForCheckList(roleId);
        if (CollectionUtils.isEmpty(menus)) {
            return new ArrayList<>(0);
        }
        ManageMenuNodeCheckResult rootMenu = new ManageMenuNodeCheckResult();
        rootMenu.setId(0L);
        return getChildrenWithCheck(rootMenu, menus);
    }

    /**
     * 转换列表菜单成带选中状态的树菜单
     *
     * @param menus
     * @return
     */
    private List<ManageMenuNodeCheckResult> getChildrenWithCheck(ManageMenuNodeCheckResult menu, List<ManageMenuNodeCheckResult> menus) {
        List<ManageMenuNodeCheckResult> results = new ArrayList<>();
        // 取出第一级菜单
        // 第一级菜单按同级排序升级排序
        Long id = menu.getId();
        menus.stream()
                .filter(m -> m.getpId() == id)
                .sorted((m1, m2) -> m1.getSortNo() - m2.getSortNo())
                .forEach(m -> {
                    // 获取下一级菜单
                    List<ManageMenuNodeCheckResult> children = getChildrenWithCheck(m, menus);
                    long count = children.stream().filter(cm -> cm.getPermission()).count();
                    boolean checked = (m.getPermission() && count == children.size());
                    m.setTitle(m.getMenuName());
                    m.setChecked(checked);
                    m.setChildren(children);
                    results.add(m);
                });
        return results;
    }


    /**
     * 转换列表菜单成树菜单
     *
     * @param menus
     * @return
     */
    private List<ManageMenuNodeResult> parseMenu2Tree(List<ManageMenuNodeResult> menus) {
        List<ManageMenuNodeResult> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(menus)) {
            // 取出第一级菜单
            // 第一级菜单按同级排序升级排序
            menus.stream()
                    .filter(m -> m.getpId() == 0L)
                    .sorted((m1, m2) -> m1.getSortNo() - m2.getSortNo())
                    .forEach(m -> {
                        // 获取下一级菜单
                        List<ManageMenuNodeResult> children = getChildren(m, menus);
                        m.setChildren(children);
                        result.add(m);
                    });
        }
        return result;
    }

    /**
     * 设置菜单的下一级菜单
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<ManageMenuNodeResult> getChildren(ManageMenuNodeResult menu, List<ManageMenuNodeResult> menus) {
        Long id = menu.getId();
        List<ManageMenuNodeResult> children = new ArrayList<>();
        // 查询上级ID匹配当前级ID，且按同级排序进行排序
        menus.stream()
                .filter(m -> m.getpId() == id)
                .sorted((m1, m2) -> m1.getSortNo() - m2.getSortNo())
                .forEach(m -> children.add(m));
        // 如果存在下一级菜单，则继续查询下下级菜单
        if (children.isEmpty()) {
            children.forEach(m -> {
                List<ManageMenuNodeResult> cMenus = getChildren(m, menus);
                m.setChildren(cMenus);
            });
        }
        return children;
    }

    @Override
    public List<ManageMenuNodeResult> queryForList(AdminMenu params) {
        return menuDao.queryForList(params);
    }

    @Override
    public AdminMenu update(AdminMenu menu, AdminUserInfo userInfo) {
        validateMenu(menu);
        menu.setUpdateBy(userInfo.getId());
        menu.setpId((menu.getpId() == null) ? 0L : menu.getpId());
        if (menu.getSortNo() == null) {
            Integer sortNo = calculateMenuSortNo(menu);
            menu.setSortNo(sortNo);
        }
        menu.setUpdateTime(new Date());
        mapper.updateById(menu);
        return menu;
    }

    /**
     * 计算菜单同级排序值<br/>
     * 如果已有sortNo属性，则不进行计算
     *
     * @param menu
     * @return
     */
    private Integer calculateMenuSortNo(AdminMenu menu) {
        Long pId = (menu.getpId() == null) ? 0L : menu.getpId();
        Integer sortNo = menu.getSortNo();
        if (sortNo == null) {
            Wrapper<AdminMenu> wrapper = new EntityWrapper<>();
            wrapper.setSqlSelect("max(sort_no) as max_sort_no")
                    .eq("p_id", pId);
            List<Object> objects = mapper.selectObjs(wrapper);
            if (CollectionUtils.isNotEmpty(objects) && objects.get(0) != null) {
                sortNo = (Integer) objects.get(0) + 1;
            } else {
                sortNo = 1;
            }
        }
        return sortNo;
    }

    /**
     * 验证菜单<br/>
     * 同级菜单名称不能重复
     *
     * @param menu
     */
    private void validateMenu(AdminMenu menu) {
        String menuName = menu.getMenuName();
        Long pId = (menu.getpId() == null) ? 0L : menu.getpId(); // 如果上级ID为null，代表为第一级菜单，设置上级ID为0
        Wrapper<AdminMenu> wrapper = new EntityWrapper<>();
        wrapper.eq("menu_name", menuName);
        wrapper.eq("p_id", pId);
        if (menu.getId() != null) {
            wrapper.ne("id_", menu.getId());
        }
        Integer count = mapper.selectCount(wrapper);
        Assert.isTrue(count == 0, "同级菜单已存在相同菜单名");
    }

    @Override
    public AdminMenu add(AdminMenu menu, AdminUserInfo userInfo) {
        validateMenu(menu);
        menu.setCreateBy(userInfo.getId());
        menu.setCreateTime(new Date());
        Integer sortNo = calculateMenuSortNo(menu);
        menu.setSortNo(sortNo);
        mapper.insert(menu);
        return menu;
    }

    @Override
    public Object delete(Long id, AdminUserInfo userInfo) {
        Integer count = menuDao.countForRole(id);
        Assert.isTrue(count == 0, "菜单已分配了角色，请撤销菜单分配，再删除菜单");
        mapper.deleteById(id);
        return null;
    }
}
