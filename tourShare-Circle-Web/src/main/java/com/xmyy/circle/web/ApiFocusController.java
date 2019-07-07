package com.xmyy.circle.web;

import com.xmyy.circle.model.UcMemberRelation;
import com.xmyy.circle.service.UcMemberRelationService;
import com.xmyy.circle.vo.UcMemberRelationPageParam;
import com.xmyy.circle.vo.UcMemberRelationParam;
import com.xmyy.circle.vo.UcMemberRelationResult;
import com.xmyy.member.vo.SellerPageResult;
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
 * 关注  前端控制器
 *
 * @author AnCheng
 */
@RestController
@RequestMapping("/api/circle/focus")
@Api(value = "关注买手", description = "关注买手")
public class ApiFocusController extends AppBaseController<UcMemberRelation, UcMemberRelationService> {

    @PostMapping(value = "/add")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登陆sessid", required = true, dataType = "String")
    @ApiOperation(value = "关注买手（不能关注背包客）", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addMember(HttpServletRequest request, @Valid @RequestBody UcMemberRelationParam params, BindingResult bindingResult) {
        return exec(() -> service.addMember(getCurrUser(request), params), bindingResult);
    }


    @PostMapping(value = "/cancel")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登陆sessid", required = true, dataType = "String")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "toMemberRelationId", value = "被关注的用户Id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "toMemberType", value = "用户类型(1,买手，2买家/背包客)", required = true, dataType = "String")

    })
    @ApiOperation(value = "取消关注买手", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delMember(HttpServletRequest request, Long toMemberRelationId, Integer toMemberType) {
        if (toMemberRelationId == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "toMemberRelationId不能为空");
        }
        if (toMemberType == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "toMemberType不能为空");
        }
        return exec(() -> {
            Long uuid = this.getCurrUser(request);
            return service.delMember(toMemberRelationId, uuid, toMemberType);
        }, null);
    }


    @PostMapping(value = "/list")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登陆sessid", required = true, dataType = "String")
    @ApiOperation(value = "查询关注列表", produces = MediaType.APPLICATION_JSON_VALUE, response = UcMemberRelationResult.class)
    public Object getFocusList(HttpServletRequest request, @Valid @RequestBody UcMemberRelationPageParam param, BindingResult bindingResult) {
        return exec(() -> service.getFocusList(param, this.getCurrUser(request)), bindingResult);
    }


    @GetMapping(value = "/listV2")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登陆sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页（默认1）", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小（默认10）", dataType = "int")

    })
    @ApiOperation(value = "查询关注买手列表，新接口", produces = MediaType.APPLICATION_JSON_VALUE, response = SellerPageResult.class)
    public Object getFocusList2(HttpServletRequest request, Integer current, Integer size) {
        return exec(() -> service.getFocusList2(getCurrUser(request), current, size));
    }


    @GetMapping(value = "/listSelected")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登陆sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页（默认1）", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小（默认10）", dataType = "int")

    })
    @ApiOperation(value = "查询指定过需求的买手列表", produces = MediaType.APPLICATION_JSON_VALUE, response = SellerPageResult.class)
    public Object listSelected(HttpServletRequest request, Integer current, Integer size) {
        return exec(() -> service.listSelected(getCurrUser(request), current, size));
    }

}
