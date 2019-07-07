package com.xmyy.circle.web;

import com.xmyy.circle.model.DgComment;
import com.xmyy.circle.service.DgCommentService;
import com.xmyy.circle.vo.CommentAddParam;
import com.xmyy.circle.vo.CommentPageResult;
import com.xmyy.circle.vo.MyCommentResult;
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
 * 评论/回复/点赞  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/api/circle/comment")
@Api(value = "动态详情页：评论/回复/点赞", description = "动态详情页：评论/回复/点赞")
public class ApiCommentController extends AppBaseController<DgComment, DgCommentService> {

    @GetMapping(value = "/list")
    @ApiOperation(value = "动态详情页评论列表", produces = MediaType.APPLICATION_JSON_VALUE, response = CommentPageResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "笔记/视频id", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "页码；默认：1", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小；默认：10", dataType = "Long")
    })
    public Object list(Long id, Integer current, Integer size, HttpServletRequest request) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "笔记/视频id不能为空");
        }
        return exec(() -> service.getPageByCircleId(id, current, size, this.getCurrUser(request)), null);
    }


    @GetMapping(value = "/sonlist")
    @ApiOperation(value = "回复列表", produces = MediaType.APPLICATION_JSON_VALUE, response = CommentPageResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "parentId", value = "评论id", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "页码；默认：1", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小；默认：10", dataType = "Long")
    })
    public Object getPageByParentId(Long parentId, Integer current, Integer size, HttpServletRequest request) {
        if (parentId == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "评论id不能为空");
        }
        return exec(() -> service.getPageByParentId(parentId, current, size, this.getCurrUser(request)), null);
    }


    @GetMapping(value = "/getInfo")
    @ApiOperation(value = "回复列表评论数据", produces = MediaType.APPLICATION_JSON_VALUE, response = CommentPageResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "parentId", value = "评论id", dataType = "Long")
    })
    public Object getInfo(Long parentId, HttpServletRequest request) {
        if (parentId == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "评论id不能为空");
        }
        return exec(() -> service.getInfo(parentId, this.getCurrUser(request)), null);
    }


    @PostMapping(value = "/add")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "发评论/发回复", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addComment(HttpServletRequest request, @Valid @RequestBody CommentAddParam params, BindingResult bindingResult) {

        return exec(() -> {
            if (params.getMemberId() == null) params.setMemberId(this.getCurrUser(request));
            return service.addComment(params);
        }, bindingResult);
    }


    @ApiOperation(value = "评论/回复 点赞/取消点赞")
    @PostMapping(value = "/praise")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "评论/回复id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "memberType", value = "当前登陆的用户类型(1买手端，2买家端)", required = true, dataType = "Long")
    })
    public Object praise(Long id, Integer memberType, HttpServletRequest request) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "id不能为空");
        }
        if (memberType == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "memberType不能为空");
        }

        return exec(() -> service.praise(id, this.getCurrUser(request), memberType));
    }


    @GetMapping(value = "/myList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "1收到的评论（默认）；2发出的评论", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "页码（默认1）", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小（默认10）", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "memberType", value = "当前用户类型(1买手端，2买家端)", dataType = "int")

    })
    @ApiOperation(value = "我的评论", produces = MediaType.APPLICATION_JSON_VALUE, response = MyCommentResult.class)
    public Object mylist(Integer type, Integer current, Integer size, Integer memberType, HttpServletRequest request) {
        return exec(() -> service.mylist(type, memberType, getCurrUser(request), current, size));
    }

}
