package com.xmyy.circle.web;

import com.xmyy.circle.model.DgProductEvaluate;
import com.xmyy.circle.service.DgProductEvaluateService;
import com.xmyy.circle.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AppBaseController;
import top.ibase4j.core.support.HttpCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单商品评价  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/api/circle/productEvaluate")
@Api(value = "APP端商品评价", description = "APP端商品评价")
public class ApiProductEvaluateController extends AppBaseController<DgProductEvaluate, DgProductEvaluateService> {

    @PostMapping(value = "/review/open")
    @ApiOperation(value = "追加评价（打开）", response = ReviewOpenResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单ID", required = true, dataType = "long")
    })
    public Object reviewOpen(HttpServletRequest request, Long orderId) {
        return exec(() -> service.reviewOpen(orderId, getCurrUser(request)), null);
    }


    @PostMapping(value = "/review/save")
    @ApiOperation(value = "追加评价")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    public Object reviewSave(HttpServletRequest request, @RequestBody @Validated ReviewParam params, BindingResult bindingResult) {
        return exec(() -> service.reviewSave(params, getCurrUser(request)), null);
    }


    @PostMapping(value = "/list")
    @ApiOperation(value = "我的评价列表", response = ProductEvaluateListResult.class)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object evaluateList(HttpServletRequest request, @Validated @RequestBody ProductEvaluateListParam params, BindingResult bindingResult) {
        return exec(() -> service.evaluateList(params, getCurrUser(request)), bindingResult);
    }


    @PostMapping(value = "/product/list")
    @ApiOperation(value = "商品详情的评价列表", response = EvaluateByProductIdResult.class)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String")
    public Object evaluateByProductIdList(HttpServletRequest request, @Validated @RequestBody EvaluateByProductIdParam params, BindingResult bindingResult) {
        return exec(() -> service.evaluateByProductIdList(params, getCurrUser(request)), bindingResult);
    }


    @ApiOperation(value = "点赞/取消点赞")
    @PostMapping(value = "/praise")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "评价id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "memberType", value = "当前登陆的用户类型(1,买手，2买家),可理解为买家端，还是买手端", required = true, dataType = "Long")
    })
    public Object addPraiseCount(Long id, Integer memberType, HttpServletRequest request) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "id不能为空");
        }
        if (memberType == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "memberType不能为空");
        }
        return exec(() -> service.praise(id, this.getCurrUser(request), memberType), null);
    }

}