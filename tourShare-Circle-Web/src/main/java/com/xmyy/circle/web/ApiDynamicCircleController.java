package com.xmyy.circle.web;

import com.xmyy.circle.model.UcDynamicCircle;
import com.xmyy.circle.service.UcDynamicCircleService;
import com.xmyy.circle.vo.BatchCancelParam;
import com.xmyy.circle.vo.CirclePageParam;
import com.xmyy.circle.vo.DynamicCircleParam;
import com.xmyy.circle.vo.CircleDetailResult;
import com.xmyy.circle.vo.CirclePageResult;
import com.xmyy.common.util.CacheUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.AppBaseController;
import top.ibase4j.core.support.HttpCode;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 笔记，视频  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/api/circle/dynamic")
@Api(value = "笔记，视频", description = "笔记，视频")
public class ApiDynamicCircleController extends AppBaseController<UcDynamicCircle, UcDynamicCircleService> {

    @PostMapping(value = "/list")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String")
    @ApiOperation(value = "买家端：笔记/视频列表（买家端发现列表/买家端首页/买家端买手详情页动态/搜索动态）", produces = MediaType.APPLICATION_JSON_VALUE, response = CirclePageResult.class)
    public Object list(@RequestBody CirclePageParam params, HttpServletRequest request, BindingResult bindingResult) {
        return exec(() -> {
            params.setBuyerId(this.getCurrUser(request));
            return service.list(params);
        }, bindingResult, CacheUtils.getHeadInfoVersion());
    }


    @GetMapping(value = "/mylist")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "typeId", value = "1笔记（默认）；2视频", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "页码（默认1）", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小（默认10）", dataType = "int")
    })
    @ApiOperation(value = "买手端，我的动态列表", produces = MediaType.APPLICATION_JSON_VALUE, response = CirclePageResult.class)
    public Object mylist(HttpServletRequest request, Integer typeId, Integer current, Integer size) {
        return exec(() -> service.myList(getCurrUser(request), typeId, current, size));
    }


    @PostMapping(value = "/add")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "笔记/视频发布", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addCircle(HttpServletRequest request, @Valid @RequestBody DynamicCircleParam params, BindingResult bindingResult) {
        return exec(() -> {
            params.setSellerId(this.getCurrUser(request));
            return service.addCircle(params);
        }, bindingResult);
    }


    @ApiOperation(value = "笔记/视频详情", response = CircleDetailResult.class)
    @GetMapping(value = "/getInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "笔记/视频id", required = true, dataType = "Long")
    })
    public Object get(Long id, HttpServletRequest request) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "id不能为空");
        }
        return exec(() -> service.getInfo(id, this.getCurrUser(request)));
    }


    @ApiOperation(value = "播放量增加", response = CircleDetailResult.class)
    @PostMapping(value = "/addPlayCount")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "视频id", required = true, dataType = "Long")
    })
    public Object addPlayCount(Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "id不能为空");
        }
        return exec(() -> service.addPlayCount(id), null);
    }


    @ApiOperation(value = "笔记视频 点赞/取消点赞")
    @PostMapping(value = "/praise")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "笔记/视频id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "memberType", value = "登陆的用户端(1买手端，2买家端)", required = true, dataType = "Long")
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


    @PostMapping(value = "/batchDel")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "批量删除动态", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object batchCancel(HttpServletRequest request, @Valid @RequestBody BatchCancelParam params, BindingResult bindingResult) {

        return exec(() -> service.batchDel(params), bindingResult);
    }

}