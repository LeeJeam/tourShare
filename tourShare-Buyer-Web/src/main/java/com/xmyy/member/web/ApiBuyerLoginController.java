package com.xmyy.member.web;

import com.xmyy.common.EnumConstants;
import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.service.UcBuyerService;
import com.xmyy.member.service.UcMemberLoginService;
import com.xmyy.member.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.AppBaseController;
import top.ibase4j.core.util.TokenUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 买家注册/登陆/登出/密码重置  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/api/buyer")
@Api(value = "买家注册/登录/登出/密码重置接口", description = "买家注册/登录/登出/密码重置接口")
public class ApiBuyerLoginController extends AppBaseController<UcBuyer, UcBuyerService> {

    @Resource
    private UcMemberLoginService memberLoginService;

    @PostMapping(value = "/add")
    @ApiOperation(value = "买家注册", produces = MediaType.APPLICATION_JSON_VALUE, response = MemberInfoResult.class)
    public Object add(@Valid @RequestBody BuyerAddParam param, BindingResult bindingResult) {
        return exec(() -> service.add(param), bindingResult);
    }


    @PostMapping(value = "/resetpwd")
    @ApiOperation(value = "买家找回/重置密码", produces = MediaType.APPLICATION_JSON_VALUE, response = MemberInfoResult.class)
    public Object resetpwd(@Valid @RequestBody ModifyPwdParam param, BindingResult bindingResult) {
        return exec(() -> service.resetpwd(param), bindingResult);
    }


    @PostMapping(value = "/login")
    @ApiOperation(value = "买家密码登录", produces = MediaType.APPLICATION_JSON_VALUE, response = MemberInfoResult.class)
    public Object login(@Valid @RequestBody LoginParam param, BindingResult bindingResult, HttpServletRequest request) {
        return exec(() -> service.login(param), bindingResult);
    }


    @PostMapping(value = "/quicklogin")
    @ApiOperation(value = "买家动态码登录", produces = MediaType.APPLICATION_JSON_VALUE, response = MemberInfoResult.class)
    public Object quicklogin(@Valid @RequestBody BuyerQuickLoginParam param, BindingResult bindingResult) {
        return exec(() -> service.quicklogin(param), bindingResult);
    }


    @PostMapping(value = "/wxlogin")
    @ApiOperation(value = "买家微信登录", produces = MediaType.APPLICATION_JSON_VALUE, response = MemberInfoResult.class)
    public Object wxlogin(@Valid @RequestBody WxLoginParam param, BindingResult bindingResult) {
        return exec(() -> service.wxlogin(param), bindingResult);
    }


    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String")
    @GetMapping(value = "/logout")
    @ApiOperation(value = "买家登出")
    public Object logout(HttpServletRequest request) {
        String token = request.getHeader("SESSID");

        if (StringUtils.isNotBlank(token)) {
            TokenUtil.delToken(token);
        }
        memberLoginService.logout(token, EnumConstants.MemberType.buyer.getValue());

        return setSuccessModelMap(new ModelMap());
    }

}