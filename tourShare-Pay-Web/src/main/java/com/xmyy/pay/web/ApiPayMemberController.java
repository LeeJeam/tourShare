package com.xmyy.pay.web;

import com.xmyy.pay.model.DgPayLog;
import com.xmyy.pay.service.AllinPayMemberService;
import com.xmyy.pay.service.DgPayLogService;
import com.xmyy.pay.vo.*;
import com.xmyy.pay.vo.ApplyBindBankCardMyResult;
import com.xmyy.pay.vo.BankCardResult;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.ibase4j.core.base.AppBaseController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 通联会员相关  前端控制器
 *
 * @author AnCheng
 */
@RestController
@RequestMapping("/api/pay/member")
@Api(value = "通联会员接口", description = "通联会员接口")
public class ApiPayMemberController extends AppBaseController<DgPayLog, DgPayLogService> {

    @Resource
    private AllinPayMemberService memberService;

    @GetMapping(value = "/sendVerificationCode")
    @ApiOperation(value = "获取验证码（绑定手机专用）", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "绑定手机号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "memberType", value = "用户类型(1买手，2背包客/买家)", required = true, dataType = "int")
    })
    public Object sendVerificationCode(HttpServletRequest request, Integer memberType, String phone) {
        return exec(() -> memberService.sendVerificationCode(getCurrUser(request), memberType, phone));
    }


    @PostMapping(value = "/bindPhone")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "确认绑定手机（先调用获取验证码接口）", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object bindPhone(HttpServletRequest request, @Valid @RequestBody BindPhoneParam params, BindingResult bindingResult) {
        return exec(() -> memberService.bindPhone(getCurrUser(request), params), bindingResult);
    }


    @ApiIgnore
    @PostMapping(value = "/changeBindPhone")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "修改绑定手机（先调用获取验证码接口）", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object changeBindPhone(HttpServletRequest request, @Valid @RequestBody ChangeBindPhoneParam params, BindingResult bindingResult) {
        return exec(() -> memberService.changeBindPhone(getCurrUser(request), params), bindingResult);
    }


    @GetMapping(value = "/getSignContractParam")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberType", value = "用户类型(1买手，2背包客/买家)", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "获取会员电子协议签约参数", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getSignContract(HttpServletRequest request, Integer memberType) {
        return exec(() -> memberService.getSignContract(getCurrUser(request), memberType));
    }


    @GetMapping(value = "/getBankCardBin")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "cardNo", value = "银行卡号", dataType = "String"),
    })
    @ApiOperation(value = "查询银行卡BIN信息", produces = MediaType.APPLICATION_JSON_VALUE, response = BankCardResult.class)
    public Object getBankCardBin(String cardNo) {
        return exec(() -> memberService.getBankCardBin(cardNo));
    }


    @GetMapping(value = "/queryBankCardList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "cardNo", value = "银行卡号（如果不传，则查询全部）", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "memberType", value = "用户类型(1买手，2背包客/买家)", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "我的银行卡列表（可选择查询一张或者全部）", produces = MediaType.APPLICATION_JSON_VALUE, response = BankCardResult.class)
    public Object queryBankCardList(HttpServletRequest request, Integer memberType, String cardNo) {
        return exec(() -> memberService.queryBankCardList(getCurrUser(request), memberType, cardNo));
    }


    @PostMapping(value = "/applyBindBankCard")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "请求绑定银行卡", produces = MediaType.APPLICATION_JSON_VALUE, response = ApplyBindBankCardMyResult.class)
    public Object applyBindBankCard(HttpServletRequest request, @Valid @RequestBody ApplyBindBankCardParam params, BindingResult bindingResult) {
        return exec(() -> memberService.applyBindBankCard(getCurrUser(request), params), bindingResult);
    }


    @PostMapping(value = "/bindBankCard")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "确认绑定银行卡", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object bindBankCard(HttpServletRequest request, @Valid @RequestBody BindBankCardParam params, BindingResult bindingResult) {
        return exec(() -> memberService.bindBankCard(getCurrUser(request), params), bindingResult);
    }


    @PostMapping(value = "/unbindBankCard")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "解除绑定银行卡", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object unbindBankCard(HttpServletRequest request, @Valid @RequestBody UnbindBankCardParam params, BindingResult bindingResult) {
        return exec(() -> memberService.unbindBankCard(getCurrUser(request), params), bindingResult);
    }


    @PostMapping(value = "/setPayPwd")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "设置/修改 平台交易密码（获取验证码、校验验证码、设置密码，都是调用此接口，传参不同）", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object setPayPwd(HttpServletRequest request, @Valid @RequestBody SetPayPwdParam params, BindingResult bindingResult) {
        return exec(() -> memberService.setPayPwd(getCurrUser(request), params), bindingResult);
    }

}