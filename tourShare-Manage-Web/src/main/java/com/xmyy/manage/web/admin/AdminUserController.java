package com.xmyy.manage.web.admin;

import com.xmyy.common.shiro.ShiroUtils;
import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.manage.core.shiro.Realm;
import com.xmyy.manage.model.AdminUser;
import com.xmyy.manage.service.AdminUserRoleService;
import com.xmyy.manage.service.AdminUserService;
import com.xmyy.manage.vo.ManageAdminUserEditParam;
import com.xmyy.manage.vo.ManageAdminUserQueryParam;
import com.xmyy.manage.vo.ManageAuthParam;
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
 * 后台用户权限  前端控制器
 *
 * @author LinBo
 */
@RestController
@RequestMapping("/manage/users")
@Api(value = "用户接口", description = "用户接口")
public class AdminUserController extends BaseController<AdminUser, AdminUserService> {

    @Resource
    private AdminUserRoleService adminUserRoleService;
    @Resource
    private Realm realm;

    @GetMapping(value = "/list")
    @ApiOperation(value = "查询用户", response = AdminUser.class)
    public Object queryForRole(ManageAdminUserQueryParam params, BindingResult bindingResult) {
        return exec(() -> service.queryForPageList(params), bindingResult);
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "新增用户", response = AdminUser.class)
    public Object add(@RequestBody ManageAdminUserEditParam params, BindingResult bindingResult) {
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        return exec(() -> service.add(params, userInfo), bindingResult);
    }

    @PostMapping(value = "/update")
    @ApiOperation(value = "修改用户", response = AdminUser.class)
    public Object update(@RequestBody AdminUser user, BindingResult bindingResult) {
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        return exec(() -> service.update(user, userInfo), bindingResult);
    }

    @PostMapping(value = "/auth")
    @ApiOperation(value = "授权用户角色")
    public Object auth(@RequestBody ManageAuthParam params, BindingResult bindingResult) {
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        Callable<ResponseEntity<ModelMap>> exec = exec(() -> adminUserRoleService.auth(params, userInfo), bindingResult);
        realm.clearCachedAuthorizationInfo(params.getUserId());
        return exec;
    }

}