package com.xmyy.circle.web;

import com.xmyy.circle.model.DgFavorites;
import com.xmyy.circle.service.DgFavoritesService;
import com.xmyy.circle.vo.BatchCancelParam;
import com.xmyy.circle.vo.FavoritesAddParam;
import com.xmyy.circle.vo.MyFavoritesResult;
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
 * 我的收藏  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/api/circle/favorites")
@Api(value = "我的收藏", description = "我的收藏")
public class ApiFavoritesController extends AppBaseController<DgFavorites, DgFavoritesService> {

    @GetMapping(value = "/myList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "1，笔记；2，视频；3，商品.默认：2，视频", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "页码；默认：1", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小；默认：10", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "memberType", value = "当前登陆的用户类型(1,买手，2买家),可理解为买家端，还是买手端.默认：2买家", dataType = "Long")
    })
    @ApiOperation(value = "我的收藏", produces = MediaType.APPLICATION_JSON_VALUE, response = MyFavoritesResult.class)
    public Object mylist(Integer type, Integer current, Integer size, Integer memberType, HttpServletRequest request) {
        return exec(() -> service.mylist(type, this.getCurrUser(request), current, size, memberType));
    }


    @PostMapping(value = "/update")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "收藏/取消收藏", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateFavorites(HttpServletRequest request, @Valid @RequestBody FavoritesAddParam params, BindingResult bindingResult) {
        return exec(() -> {
            if (params.getMemberId() == null) params.setMemberId(this.getCurrUser(request));
            return service.updateFavorites(params);
        }, bindingResult);
    }


    @PostMapping(value = "/batchCancel")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "批量取消收藏", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object batchCancel(@Valid @RequestBody BatchCancelParam params, BindingResult bindingResult) {

        return exec(() -> service.batchCancel(params), bindingResult);
    }
}
