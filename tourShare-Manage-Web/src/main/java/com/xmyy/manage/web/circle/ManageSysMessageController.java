package com.xmyy.manage.web.circle;

import com.xmyy.circle.model.DgSysMessage;
import com.xmyy.circle.service.DgSysMessageService;
import com.xmyy.circle.vo.DgSysMessageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.BaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统消息后台管理  前端控制器
 *
 * @author yeyu
 */
@RestController
@RequestMapping("/manage/circle/sysmessage")
@Api(value = "系统消息后台管理接口", description = "系统消息后台管理接口")
public class ManageSysMessageController extends BaseController<DgSysMessage, DgSysMessageService> {

    @PostMapping(value = "/add")
    @ApiOperation(value = "发布系统消息", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addMessage(HttpServletRequest request, @Validated @RequestBody DgSysMessageParam params, BindingResult bindingResult) {
        return exec(() -> service.addSysMessage(params, getCurrUser(request)), bindingResult);
    }

}