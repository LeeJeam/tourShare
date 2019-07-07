package com.xmyy.circle.web;

import com.xmyy.circle.model.UcFeedback;
import com.xmyy.circle.service.UcFeedbackService;
import com.xmyy.circle.vo.UcFeedbackParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.AppBaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * 意见反馈  前端控制器
 *
 * @author yeyu
 */
@RestController
@RequestMapping("/api/circle/feedback")
@Api(value = "APP意见反馈接口", description = "APP意见反馈接口")
public class ApiUcFeedbackController extends AppBaseController<UcFeedback, UcFeedbackService> {

    @PostMapping(value = "/save")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登陆sessid", required = true, dataType = "String")
    @ApiOperation(value = "新增意见反馈", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addFeedBack(HttpServletRequest request, @Validated @RequestBody UcFeedbackParam param, BindingResult bindingResult) {
        return exec(() -> service.addFeedBack(param, getCurrUser(request)), bindingResult);
    }
}