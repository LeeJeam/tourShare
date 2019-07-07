package com.xmyy.manage.web.admin;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.xmyy.common.util.PasswordUtil;
import com.xmyy.manage.model.AdminUser;
import com.xmyy.manage.service.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.ibase4j.core.base.BaseController;
import top.ibase4j.core.exception.LoginException;
import top.ibase4j.core.support.Assert;
import top.ibase4j.core.support.HttpCode;
import top.ibase4j.core.support.context.Resources;
import top.ibase4j.core.support.login.LoginHelper;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.WebUtil;
import top.ibase4j.model.Login;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 后台用户登陆/登出/注册  前端控制器
 *
 * @author zlp
 */
@RestController
@Api(value = "后台用户登录/注册接口", description = "后台用户登录/注册/登出接口")
public class ManageLoginController extends BaseController<AdminUser, AdminUserService> {

    // 登录
    @ApiOperation(value = "用户登录")
    @PostMapping("/manage/login")
    @ApiImplicitParam(paramType = "query", name = "clientIp", value = "IP", dataType = "String")
    public Object login(String clientIp,
                        @ApiParam(required = true, value = "登录帐号和密码") @RequestBody Login user,
                        HttpServletRequest request) {
        if (StringUtils.isBlank(clientIp)) {
            clientIp = WebUtil.getHost(request);
            //return setModelMap(HttpCode.BAD_REQUEST.value().toString(),"clientIp不能为空");
        }
        Assert.notNull(user.getAccount(), "ACCOUNT");
        Assert.notNull(user.getPassword(), "PASSWORD");

        if (LoginHelper.login(user.getAccount(), user.getPassword(), clientIp)) {
            request.setAttribute("msg", "[" + user.getAccount() + "]登录成功.");
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            Map<String, Object> result = InstanceUtil.newHashMap("IBASE4JSESSIONID", session.getId());

            return setSuccessModelMap(new ModelMap(), result);
        }
        request.setAttribute("msg", "[" + user.getAccount() + "]登录失败.");
        throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
    }

    // 登出
    @ApiOperation(value = "用户登出")
    @PostMapping("/manage/logout")
    public Object logout(HttpServletRequest request, ModelMap modelMap) {
        SecurityUtils.getSubject().logout();
        return setSuccessModelMap(modelMap);
    }

    // 注册
    @ApiOperation(value = "用户注册")
    @PostMapping("/manage/regin")
    @ApiImplicitParam(paramType = "query", name = "clientIp", value = "IP", dataType = "String")
    public Object regin(String clientIp, @RequestBody AdminUser adminUser,
                        HttpServletRequest request) {
        if (StringUtils.isBlank(clientIp)) {
            clientIp = WebUtil.getHost(request);
        }
        Assert.notNull(adminUser.getAccount(), "ACCOUNT");
        Assert.notNull(adminUser.getPassword(), "PASSWORD");
        List<AdminUser> aus = service.queryList(InstanceUtil.newHashMap("account", adminUser.getAccount()));
        if (!CollectionUtils.isEmpty(aus)) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "该账号已被注册");
        }
        String password = adminUser.getPassword();
        adminUser.setPassword(PasswordUtil.encodePassword(adminUser.getPassword()));//SecurityUtil.encryptPassword(sysUser.getPassword())
        super.update(adminUser);

        if (LoginHelper.login(adminUser.getAccount(), password, clientIp)) {
            return setSuccessModelMap(new ModelMap());
        }
        throw new IllegalArgumentException(Resources.getMessage("LOGIN_FAIL"));
    }

    // 没有登录
    @ApiIgnore
    @RequestMapping(value = "/unauthorized", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
    public Object unauthorized(ModelMap modelMap) throws Exception {
        return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
    }

    // 没有权限
    @ApiIgnore
    @RequestMapping(value = "/forbidden", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
    public Object forbidden(ModelMap modelMap) {
        return setModelMap(modelMap, HttpCode.FORBIDDEN);
    }
}