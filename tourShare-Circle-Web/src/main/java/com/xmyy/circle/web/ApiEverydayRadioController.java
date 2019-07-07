package com.xmyy.circle.web;

import com.xmyy.circle.model.DgEverydayRadio;
import com.xmyy.circle.service.DgEverydayRadioService;
import com.xmyy.circle.vo.EveryDayRadioResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AppBaseController;
import top.ibase4j.core.support.HttpCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 今日提醒  前端控制器
 *
 * @author yeyu
 */
@RestController
@RequestMapping("/api/circle/radio")
@Api(value = "今日提醒", description = "今日提醒")
public class ApiEverydayRadioController extends AppBaseController<DgEverydayRadio, DgEverydayRadioService> {

    @GetMapping(value = "/list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登陆sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "memberType", value = "用户类型(1买手，3背包客)", required = true, dataType = "String")
    })
    @ApiOperation(value = "查询今日提醒", produces = MediaType.APPLICATION_JSON_VALUE, response = EveryDayRadioResult.class)
    public Object queryRadio(HttpServletRequest request, Integer memberType) {
        if (memberType == null) {
            return setModelMap(HttpCode.BAD_REQUEST, "用户类型不能为空");
        }
        return exec(() -> service.queryEveryDayRadioList(getCurrUser(request), memberType));
    }

}