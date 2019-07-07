package com.xmyy.manage.web.circle;

import com.xmyy.circle.model.UcDynamicCircle;
import com.xmyy.circle.service.DgCommentService;
import com.xmyy.circle.service.ManageCircleService;
import com.xmyy.circle.service.UcDynamicCircleService;
import com.xmyy.circle.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;
import top.ibase4j.core.support.HttpCode;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 动态后台管理  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/manage/circle")
@Api(value = "动态后台管理", description = "动态后台管理")
public class ManageCircleController extends BaseController<UcDynamicCircle, ManageCircleService> {

    @Resource
    private UcDynamicCircleService ucDynamicCircleService;
    @Resource
    private DgCommentService commentService;

    @PostMapping(value = "/list")
    @ResponseBody
    @ApiOperation(value = "动态列表&买手动态列表", produces = MediaType.APPLICATION_JSON_VALUE, response = CircleTopPageResult.class)
    public Object list(@RequestBody @Valid CircleTopPageParam param, BindingResult bindingResult) {
        return exec(() -> service.list(param), bindingResult);
    }


    @PostMapping("/resultCount")
    @ApiOperation(value = "获取统计结果", produces = MediaType.APPLICATION_JSON_VALUE, response = CircleCountResult.class)
    public Object getResultCount(@RequestBody @Valid CircleTopPageParam param, BindingResult bindingResult) {
        return exec(() -> service.getResultCount(param), bindingResult);
    }


    @RequestMapping(value = "/top", method = RequestMethod.POST)
    @ApiOperation(value = "置顶", produces = MediaType.APPLICATION_JSON_VALUE, response = UcDynamicCircle.class)
    public Object top(@Valid @RequestBody CircleTopParam param, BindingResult bindingResult) {
        param.setUpdateBy(this.getShiroCurrUser());//
        return exec(() -> service.top(param), bindingResult);
    }


    @RequestMapping(value = "/down", method = RequestMethod.POST)
    @ApiOperation(value = "取消置顶", produces = MediaType.APPLICATION_JSON_VALUE, response = UcDynamicCircle.class)
    public Object down(@Valid @RequestBody CircleDownParam param, BindingResult bindingResult) {
        param.setUpdateBy(this.getShiroCurrUser());
        return exec(() -> service.down(param), bindingResult);
    }


    @ApiOperation(value = "笔记/视频详情", response = CircleDetailResult.class)
    @PostMapping(value = "/getInfo")
    @ApiImplicitParam(paramType = "query", name = "id", value = "笔记/视频id", required = true, dataType = "Long")
    public Object get(Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "id不能为空");
        }

        return exec(() -> ucDynamicCircleService.getInfo(id, null), null);
    }


    @RequestMapping(value = "/setUp", method = RequestMethod.POST)
    @ApiOperation(value = "上架", produces = MediaType.APPLICATION_JSON_VALUE, response = UcDynamicCircle.class)
    @ApiImplicitParam(paramType = "query", name = "id", value = "笔记/视频id", required = true, dataType = "Long")
    public Object setUp(Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "id不能为空");
        }
        return exec(() -> service.setUp(id, this.getShiroCurrUser()), null);
    }


    @RequestMapping(value = "/setDown", method = RequestMethod.POST)
    @ApiOperation(value = "下架", produces = MediaType.APPLICATION_JSON_VALUE, response = UcDynamicCircle.class)
    @ApiImplicitParam(paramType = "query", name = "id", value = "笔记/视频id", required = true, dataType = "Long")
    public Object setDown(Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "id不能为空");
        }
        return exec(() -> service.setDown(id, this.getShiroCurrUser()), null);
    }


    @GetMapping(value = "/commentList")
    @ApiOperation(value = "笔记/视频详情页评论列表", produces = MediaType.APPLICATION_JSON_VALUE, response = CommentPageResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "笔记/视频id", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "页码；默认：1", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小；默认：10", dataType = "Long")
    })
    public Object commentList(Long id, Integer current, Integer size) {
        return exec(() -> commentService.getPageByCircleId(id, current, size, null), null);
    }


    @GetMapping(value = "/commentDel")
    @ApiOperation(value = "评论/回复删除", produces = MediaType.APPLICATION_JSON_VALUE, response = CommentPageResult.class)
    @ApiImplicitParam(paramType = "query", name = "id", value = "评论/回复id", dataType = "Long")
    public Object commentDel(Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "评论/回复id不能为空");
        }
        commentService.del(id, this.getShiroCurrUser());

        return setSuccessModelMap(new ModelMap());
    }

    /*@ResponseBody
    @PostMapping(value = "/commentAdd")
    @ApiOperation(value = "添加评论", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addComment(HttpServletRequest request, @Valid @RequestBody CommentAddParams params, BindingResult bindingResult) {

        return exec(() -> {
            if(params.getMemberId() == null) params.setMemberId(this.getShiroCurrUser(request));
            return  service.addComment(params);
        }, bindingResult);
    }*/
}