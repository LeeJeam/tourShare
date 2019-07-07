package com.xmyy.member.web;

import com.xmyy.circle.service.UcMemberRelationService;
import com.xmyy.common.EnumConstants;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.member.vo.MemberInfoResult;
import com.xmyy.member.vo.MemberUpdateParam;
import com.xmyy.member.vo.SellerPageParam;
import com.xmyy.member.vo.SellerPageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.AppBaseController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * APP买手信息  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/api/seller")
@Api(value = "APP买手接口", description = "买手基本信息/搜索/推荐")
public class ApiSellerController extends AppBaseController<UcSeller, UcSellerService> {

    @Resource
    private UcMemberRelationService relationService;

    @PostMapping(value = "/listByIdOrNickname")
    @ApiOperation(value = "按ID或昵称搜索买手列表（参数只需要传keyword）", produces = MediaType.APPLICATION_JSON_VALUE, response = SellerPageResult.class)
    public Object list(@RequestBody @Valid SellerPageParam param, BindingResult bindingResult) {
        return exec(() -> service.querySellerListByName(param), bindingResult);
    }


    @PostMapping(value = "/topList")
    @ApiOperation(value = "推荐买手列表", produces = MediaType.APPLICATION_JSON_VALUE, response = SellerPageResult.class)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object topList(HttpServletRequest request, @RequestBody @Valid SellerPageParam param, BindingResult bindingResult) {
        return exec(() -> {
            param.setBuyerId(getCurrUser(request));
            return service.topList(param);
        }, bindingResult);
    }


    @ApiOperation(value = "买手个人信息", produces = MediaType.APPLICATION_JSON_VALUE, response = MemberInfoResult.class)
    @GetMapping(value = "/getInfo")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String")
    public Object getInfo(HttpServletRequest request) {
        return exec(() -> service.getInfo(getCurrUser(request)));
    }


    @ApiOperation(value = "买家端查看买手个人信息", produces = MediaType.APPLICATION_JSON_VALUE, response = MemberInfoResult.class)
    @GetMapping(value = "/getSellerInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "买手id", dataType = "Long")
    })
    public Object getSellerInfo(Long id, HttpServletRequest request) {
        Long currId = getCurrUser(request);
        MemberInfoResult result = service.getInfo(id);
        if (currId != null) {
            if (relationService.isFollowedByMember(currId, id)) {
                result.setIsFollowed(EnumConstants.YesOrNo.YES.getValue());
            } else {
                result.setIsFollowed(EnumConstants.YesOrNo.NO.getValue());
            }
        }
        return setSuccessModelMap(new ModelMap(), result);
    }


    @PostMapping("/updateInfo")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "修改个人信息", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateInfo(HttpServletRequest request, @RequestBody MemberUpdateParam params, BindingResult bindingResult) {
        return exec(() -> {
            Long memberId = this.getCurrUser(request);
            params.setId(memberId);
            return service.updateInfo(params);
        }, bindingResult);
    }


    @PostMapping("/updateMobile")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "newMobile", value = "新手机号码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "smsCode", value = "动态码", dataType = "String")
    })
    @ApiOperation(value = "修改个人手机号", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateMobile(HttpServletRequest request, String newMobile, String smsCode) {
        return exec(() -> service.updateMobile(super.getCurrUser(request), newMobile, smsCode), null);
    }


}