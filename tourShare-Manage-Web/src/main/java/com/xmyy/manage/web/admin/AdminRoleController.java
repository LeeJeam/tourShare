package com.xmyy.manage.web.admin;

import com.xmyy.common.shiro.ShiroUtils;
import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.manage.core.shiro.Realm;
import com.xmyy.manage.model.AdminRole;
import com.xmyy.manage.service.AdminRoleMenuService;
import com.xmyy.manage.service.AdminRoleService;
import com.xmyy.manage.vo.ManageAdminRoleQueryParam;
import com.xmyy.manage.vo.ManagePermissionRole;
import com.xmyy.manage.vo.ManageRoleAuthParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;

import javax.annotation.Resource;
import java.util.concurrent.Callable;

/**
 * 角色  前端控制器
 *
 * @author LinBo
 */
@RestController
@RequestMapping("/manage/roles")
@Api(value = "角色接口", description = "角色接口")
public class AdminRoleController extends BaseController<AdminRole, AdminRoleService> {

    @Resource
    private AdminRoleMenuService adminRoleMenuService;
    @Resource
    private Realm realm;

    @GetMapping(value = "/list")
    @ApiOperation(value = "查询角色", response = AdminRole.class)
    public Object queryForList(ManageAdminRoleQueryParam params, BindingResult bindingResult) {
        return exec(() -> service.queryForList(params), bindingResult);
    }


    @PostMapping(value = "/add")
    @ApiOperation(value = "新增角色", response = AdminRole.class)
    public Object add(@RequestBody AdminRole params, BindingResult bindingResult) {
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        params.setCreateBy(userInfo.getId());
        return exec(() -> service.add(params), bindingResult);
    }


    @PostMapping(value = "/update")
    @ApiOperation(value = "更新角色", response = AdminRole.class)
    public Object update(@RequestBody AdminRole params, BindingResult bindingResult) {
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        params.setUpdateBy(userInfo.getId());
        return exec(() -> service.update(params), bindingResult);
    }


    @PostMapping(value = "/auth")
    @ApiOperation(value = "角色授权菜单")
    public Object update(@RequestBody ManageRoleAuthParam params, BindingResult bindingResult) {
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        Callable<ResponseEntity<ModelMap>> exec = exec(() -> adminRoleMenuService.auth(params, userInfo), bindingResult);
        // 移除权限缓存
        realm.clearCache();
        return exec;
    }


    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除角色")
    public Object delete(@RequestBody AdminRole params, BindingResult bindingResult) {
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        return exec(() -> service.delete(params.getId(), userInfo), bindingResult);
    }


    @GetMapping(value = "/permissionList")
    @ApiOperation(value = "查询角色对应授权信息", response = ManagePermissionRole.class)
    public Object getPermissionRoles(@RequestParam(value = "userId", required = false) Long userId) {
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        return exec(() -> service.queryPermissionRoles(userId, userInfo), null);
    }

}