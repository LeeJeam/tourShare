package com.xmyy.member.web;

import com.xmyy.common.EnumConstants;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.UcMemberLoginService;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.member.vo.LoginParam;
import com.xmyy.member.vo.MemberInfoResult;
import com.xmyy.member.vo.ModifyPwdParam;
import com.xmyy.member.vo.SellerAddParam;
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
 * 买手注册/登陆/登出/密码  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/api/seller")
@Api(value = "APP买手接口", description = "买手注册/登录/登出/密码重置接口")
public class ApiSellerLoginController extends AppBaseController<UcSeller, UcSellerService> {

    @Resource
    private UcMemberLoginService memberLoginService;

    @PostMapping(value = "/add")
    @ApiOperation(value = "买手注册", produces = MediaType.APPLICATION_JSON_VALUE, response = MemberInfoResult.class)
    public Object create(@Valid @RequestBody SellerAddParam param, BindingResult bindingResult) {
        return exec(() -> service.add(param), bindingResult);
    }


    @PostMapping(value = "/resetpwd")
    @ApiOperation(value = "买手找回/重置密码", produces = MediaType.APPLICATION_JSON_VALUE, response = MemberInfoResult.class)
    public Object resetpwd(@Valid @RequestBody ModifyPwdParam param, BindingResult bindingResult) {
        return exec(() -> service.resetpwd(param), bindingResult);
    }


    @PostMapping(value = "/login")
    @ApiOperation(value = "买手密码登录", produces = MediaType.APPLICATION_JSON_VALUE, response = MemberInfoResult.class)
    public Object login(@Valid @RequestBody LoginParam param, BindingResult bindingResult, HttpServletRequest request) {
        return exec(() -> service.login(param), bindingResult);
    }


    @GetMapping(value = "/logout")
    @ApiOperation(value = "买手登出", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String")
    public Object logout(HttpServletRequest request) {
        String token = request.getHeader("SESSID");
        if (StringUtils.isNotBlank(token)) {
            TokenUtil.delToken(token);
        }
        memberLoginService.logout(token, EnumConstants.MemberType.seller.getValue());

        ModelMap modelMap = new ModelMap();
        return setSuccessModelMap(modelMap);
    }

}