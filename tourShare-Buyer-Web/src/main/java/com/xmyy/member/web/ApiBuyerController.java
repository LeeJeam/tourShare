package com.xmyy.member.web;

import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.service.UcBuyerService;
import com.xmyy.member.vo.MemberInfoResult;
import com.xmyy.member.vo.MemberUpdateParam;
import com.xmyy.member.vo.SellerPageParam;
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
 * 买家/背包客 个人信息  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/api/buyer")
@Api(value = "(买家/背包客)信息 查询/修改接口", description = "(买家/背包客)信息 查询/修改接口")
public class ApiBuyerController extends AppBaseController<UcBuyer, UcBuyerService> {

    @PostMapping(value = "/topList")
    @ApiOperation(value = "买家首页推荐买手->背包客列表", produces = MediaType.APPLICATION_JSON_VALUE, response = SellerPageResult.class)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object topList(HttpServletRequest request, @RequestBody @Valid SellerPageParam param, BindingResult bindingResult) {
        return exec(() -> {
            param.setBuyerId(getCurrUser(request));
            return service.topList(param);
        }, bindingResult);
    }


    @ApiOperation(value = "买家个人信息", response = MemberInfoResult.class)
    @GetMapping(value = "/getInfo")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String")
    public Object getInfo(HttpServletRequest request) {
        return exec(() -> service.getInfo(getCurrUser(request), 1));
    }


    @ApiOperation(value = "背包客个人信息", response = MemberInfoResult.class)
    @GetMapping(value = "/getPackInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "背包客ID（背包客查看自己的信息不需要传此ID）", dataType = "Long")
    })
    public Object getPackInfo(Long id, HttpServletRequest request) {
        if (id != null) {
            return exec(() -> service.getInfo(id, 2));
        }

        //不传id，背包客查看自己从信息
        Long memberId = getCurrUser(request);
        if (memberId != null) {
            return exec(() -> service.getInfo(memberId, 2));
        }

        return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "请重新登录或者指定id");
    }


    @PostMapping("/updateInfo")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "修改个人信息", produces = MediaType.APPLICATION_JSON_VALUE, response = MemberInfoResult.class)
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
    @ApiOperation(value = "修改个人手机号")
    public Object updateMobile(HttpServletRequest request, String newMobile, String smsCode) {
        return exec(() -> service.updateMobile(super.getCurrUser(request), newMobile, smsCode), null);
    }

}