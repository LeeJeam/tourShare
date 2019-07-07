package com.xmyy.demand.web;

import com.xmyy.demand.model.DgDemandAccept;
import com.xmyy.demand.service.DgDemandAcceptService;
import com.xmyy.demand.vo.AddAcceptParam;
import com.xmyy.demand.vo.CancelAcceptParam;
import com.xmyy.demand.vo.TourInfoResult;
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
 * 需求接单  前端控制器
 *
 * @author AnCheng
 */
@RestController
@RequestMapping("/api/demand")
@Api(value = "APP需求接单接口", description = "APP需求接单接口")
public class ApiDemandAcceptController extends AppBaseController<DgDemandAccept, DgDemandAcceptService> {

    @GetMapping(value = "/canAccept")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "需求ID", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "isPacker", value = "是否背包客（0否，1是）", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "判断能否接单，匹配行程", produces = MediaType.APPLICATION_JSON_VALUE, response = TourInfoResult.class)
    public Object canInsertAcc(HttpServletRequest request, Long id, Integer isPacker) {
        return exec(() -> service.canAccept(id, isPacker, getCurrUser(request)));
    }


    @PostMapping(value = "/createAccept")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "买手/背包客接单", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object accept(HttpServletRequest request, @Valid @RequestBody AddAcceptParam params, BindingResult bindingResult) {
        return exec(() -> service.accept(params, getCurrUser(request)), bindingResult);
    }


    @PostMapping(value = "/cancelAccept")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "买手/背包客取消接单", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object cancelDemAcc(HttpServletRequest request, @Valid @RequestBody CancelAcceptParam params, BindingResult bindingResult) {
        return exec(() -> service.cancelAccept(params, getCurrUser(request)), bindingResult);
    }

}