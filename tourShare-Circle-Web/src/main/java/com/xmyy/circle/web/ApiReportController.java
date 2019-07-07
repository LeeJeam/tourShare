package com.xmyy.circle.web;

import com.xmyy.circle.model.DgReport;
import com.xmyy.circle.service.DgReportService;
import com.xmyy.circle.vo.ReportAddParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import top.ibase4j.core.base.AppBaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * 举报  前端控制器
 *
 * @author yeyu
 */
@Controller
@RequestMapping("/api/circle/report")
@Api(value = "APP端举报接口", description = "APP端举报接口")
public class ApiReportController extends AppBaseController<DgReport, DgReportService> {

    @PostMapping(value = "/add")
    @ApiOperation(value = "提交举报")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object add(HttpServletRequest request, @Validated @RequestBody ReportAddParam params, BindingResult bindingResult) {
        return exec(() -> service.add(params, getCurrUser(request)), bindingResult);
    }

}