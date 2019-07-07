package com.xmyy.manage.web.admin;

import com.xmyy.common.shiro.ShiroUtils;
import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.manage.model.AdminMenu;
import com.xmyy.manage.service.AdminMenuService;
import com.xmyy.manage.vo.ManageMenuFrontendResult;
import com.xmyy.manage.vo.ManageMenuNodeCheckResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;

/**
 * 菜单  前端控制器
 *
 * @author LinBo
 */
@RestController
@RequestMapping("/manage/menus")
@Api(value = "菜单接口", description = "菜单接口")
public class AdminMenuController extends BaseController<AdminMenu, AdminMenuService> {

    @GetMapping(value = "/list")
    @ApiOperation(value = "查询所有菜单列表", response = AdminMenu.class)
    public Object queryForList(AdminMenu params, BindingResult bindingResult) {
        return exec(() -> service.queryForList(params), bindingResult);
    }


    @GetMapping(value = "/permissionTree")
    @ApiOperation(value = "查询当前用户的菜单树", response = ManageMenuFrontendResult.class)
    public Object queryForTree() {
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        Long userId = userInfo.getId();
        return exec(() -> service.queryForPermissionTree(userId));
    }


    @GetMapping(value = "/tree")
    @ApiOperation(value = "查询菜单树", response = ManageMenuNodeCheckResult.class)
    public Object queryForPermissionTree(@RequestParam(name = "roleId", required = false) Long roleId) {
        return exec(() -> service.queryForTree(roleId), null);
    }


    @PostMapping(value = "/add")
    @ApiOperation(value = "新增菜单", response = AdminMenu.class)
    public Object add(@RequestBody AdminMenu params, BindingResult bindingResult) {
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        return exec(() -> service.add(params, userInfo), null);
    }


    @PostMapping(value = "/update")
    @ApiOperation(value = "修改菜单", response = AdminMenu.class)
    public Object update(@RequestBody AdminMenu params, BindingResult bindingResult) {
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        return exec(() -> service.update(params, userInfo), null);
    }


    @PostMapping("/delete")
    @ApiOperation(value = "删除菜单", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delete(@RequestBody AdminMenu menu, BindingResult bindingResult) {
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        return exec(() -> service.delete(menu.getId(), userInfo), bindingResult);
    }
}