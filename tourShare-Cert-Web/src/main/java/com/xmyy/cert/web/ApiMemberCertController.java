package com.xmyy.cert.web;

import com.xmyy.cert.model.UcMemberCert;
import com.xmyy.cert.service.UcMemberCertService;
import com.xmyy.cert.vo.MemberCertAddParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.AppBaseController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 用户认证  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/api/cert")
@Api(value = "用户认证接口", description = "APP用户认证接口")
public class ApiMemberCertController extends AppBaseController<UcMemberCert, UcMemberCertService> {

    @PostMapping("/saveIdentityInfo")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "提交认证", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object saveIdentityInfo(HttpServletRequest request, @Valid @RequestBody MemberCertAddParam memberCertAddParams, BindingResult bindingResult) {
        return exec(() -> service.saveIdentityInfo(getCurrUser(request), memberCertAddParams), bindingResult);
    }


    @GetMapping("/userDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sessid", value = "登陆sessid", dataType = "String", required = true, paramType = "header"),
            @ApiImplicitParam(name = "memberType", value = "用户类型(1买手，2背包客)", dataType = "Integer", required = true, paramType = "query")})
    @ApiOperation(value = "买手/背包客认证详情", response = UcMemberCert.class)
    public Object userDetail(HttpServletRequest request, Integer memberType) {
        return exec(() -> service.queryUserCertDetail(getCurrUser(request), memberType));
    }

}