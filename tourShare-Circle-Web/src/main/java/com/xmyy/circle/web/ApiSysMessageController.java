package com.xmyy.circle.web;


import com.xmyy.circle.model.DgSysMessage;
import com.xmyy.circle.service.DgSysMessageService;
import com.xmyy.circle.vo.DgSysMessageNotReadParam;
import com.xmyy.circle.vo.DgSysMessagePageParam;
import com.xmyy.circle.vo.DgSysMessageReadParam;
import com.xmyy.circle.vo.DgSysMessageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AppBaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统消息  前端控制器
 *
 * @author AnCheng
 */
@RestController
@RequestMapping("/api/circle/sysmessage")
@Api(value = "APP系统消息接口", description = "APP系统消息接口")
public class ApiSysMessageController extends AppBaseController<DgSysMessage, DgSysMessageService> {

    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String")
    @PostMapping(value = "/list")
    @ApiOperation(value = "查询系统消息列表", produces = MediaType.APPLICATION_JSON_VALUE, response = DgSysMessageResult.class)
    public Object list(HttpServletRequest request, @Validated @RequestBody DgSysMessagePageParam param, BindingResult bindingResult) {
        return exec(() -> service.list(getCurrUser(request), param), bindingResult);
    }


    @PostMapping(value = "/detail")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String")
    @ApiOperation(value = "根据ID查询系统消息详情", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object detail(HttpServletRequest request, @Validated @RequestBody DgSysMessageReadParam param, BindingResult bindingResult) {
        return exec(() -> service.detail(getCurrUser(request), param), bindingResult);
    }


    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String")
    @PostMapping(value = "/notReadCount")
    @ApiOperation(value = "查询未阅读的消息总数", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object countNotReadMessage(HttpServletRequest request, @Validated @RequestBody DgSysMessageNotReadParam param, BindingResult bindingResult) {
        return exec(() -> service.countNotReadMessage(getCurrUser(request), param), bindingResult);
    }

}