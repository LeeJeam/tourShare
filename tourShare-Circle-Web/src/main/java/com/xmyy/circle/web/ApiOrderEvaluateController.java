package com.xmyy.circle.web;

import com.xmyy.circle.model.DgOrderEvaluate;
import com.xmyy.circle.service.DgOrderEvaluateService;
import com.xmyy.circle.vo.OrderEvaluateAddParam;
import com.xmyy.circle.vo.OrderEvaluateListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.AppBaseController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 订单买手评价  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/api/circle/orderEvaluate")
@Api(value = "APP端订单评价接口", description = "APP端订单评价接口")
public class ApiOrderEvaluateController extends AppBaseController<DgOrderEvaluate, DgOrderEvaluateService> {

    @PostMapping(value = "/add")
    @ApiOperation(value = "添加评价")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object add(HttpServletRequest request, @Validated @RequestBody OrderEvaluateAddParam params, BindingResult result) {
        return exec(() -> service.add(params, getCurrUser(request)), result);
    }


    @GetMapping(value = "/list")
    @ApiOperation(value = "订单下的评价", response = OrderEvaluateListResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单ID", required = true, dataType = "long")
    })
    public Object list(HttpServletRequest request, Long orderId) {
        return exec(() -> service.list(orderId, getCurrUser(request)));
    }

}