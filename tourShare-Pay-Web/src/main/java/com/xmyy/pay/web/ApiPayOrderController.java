package com.xmyy.pay.web;

import com.xmyy.pay.allinpay.response.*;
import com.xmyy.pay.model.DgPayLog;
import com.xmyy.pay.service.AllinPayOrderService;
import com.xmyy.pay.service.DgPayLogService;
import com.xmyy.pay.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.ibase4j.core.base.AppBaseController;
import top.ibase4j.core.exception.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 通联支付相关  前端控制器
 *
 * @author AnCheng
 */
@RestController
@RequestMapping("/api/pay/order")
@Api(value = "通联支付接口", description = "通联支付接口")
public class ApiPayOrderController extends AppBaseController<DgPayLog, DgPayLogService> {

    @Resource
    private AllinPayOrderService orderService;

    @PostMapping(value = "/apply")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "买家登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "申请支付（托管代收）", produces = MediaType.APPLICATION_JSON_VALUE, response = AgentCollectApplyResult.class)
    public Object agentCollectApply(HttpServletRequest request, @Valid @RequestBody AgentCollectApplyParam params, BindingResult bindingResult) {
        return exec(() -> {
            params.setMemberId(getCurrUser(request));
            return orderService.agentCollectApply(params);
        }, bindingResult);
    }


    @ApiIgnore
    @PostMapping(value = "/confirm")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "确认支付（后台+短信验证码确认）", produces = MediaType.APPLICATION_JSON_VALUE, response = PayResult.class)
    public Object pay(@Valid @RequestBody PayParam params, BindingResult bindingResult) {
        return exec(() -> orderService.pay(params), bindingResult);
    }


    @GetMapping(value = "/queryBalance")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberType", value = "用户角色（1买手，2买家，3背包客）", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "查询账户余额（后台开发用）", produces = MediaType.APPLICATION_JSON_VALUE, response = QueryBalanceResult.class)
    public Object queryBalance(HttpServletRequest request, Integer memberType) {
        return exec(() -> orderService.queryBalance(getCurrUser(request), memberType));
    }


    @GetMapping(value = "/getOrderDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "bizOrderNo", value = "支付单号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "查询支付状态（1未支付；3交易失败；4交易成功；5退款成功；6关闭；7退款受理；99进行中）", produces = MediaType.APPLICATION_JSON_VALUE, response = OrderDetailResult.class)
    public Object getOrderDetail(String bizOrderNo) {
        return exec(() -> orderService.getOrderDetail(bizOrderNo));
    }


    @GetMapping(value = "/queryInExpDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberType", value = "用户角色（1买手，2买家，3背包客）", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "页数", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "查询条数", dataType = "int"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "查询账户收支明细（后台开发用）", produces = MediaType.APPLICATION_JSON_VALUE, response = QueryInExpDetailResult.class)
    public Object queryInExpDetail(HttpServletRequest request, Integer memberType, Integer current, Integer size) {
        return exec(() -> orderService.queryInExpDetail(getCurrUser(request), memberType, current, size));
    }


    @GetMapping(value = "/wallet")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberType", value = "用户角色（1买手，2买家，3背包客）", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "交易明细页数（默认1）", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "明细查询条数（默认5）", dataType = "int"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "我的钱包", produces = MediaType.APPLICATION_JSON_VALUE, response = MyWalletResult.class)
    public Object wallet(HttpServletRequest request, Integer memberType, Integer current, Integer size) {
        return exec(() -> orderService.wallet(getCurrUser(request), memberType, current, size));
    }


    @GetMapping(value = "/withDrawPrepare")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberType", value = "用户角色（1买手，2买家，3背包客）", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "获取提现界面数据（余额与绑定卡列表）", produces = MediaType.APPLICATION_JSON_VALUE, response = PrepareToDrawResult.class)
    public Object prepareToDraw(HttpServletRequest request, Integer memberType) {
        return exec(() -> orderService.prepareToDraw(getCurrUser(request), memberType));
    }


    @PostMapping(value = "/withDrawApply")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "提现申请", produces = MediaType.APPLICATION_JSON_VALUE, response = WithdrawApplyResult.class)
    public Object withDrawApply(HttpServletRequest request, @Valid @RequestBody DrawApplyParam params, BindingResult bindingResult) {
        return exec(() -> orderService.withDrawApply(getCurrUser(request), params), bindingResult);
    }


    @GetMapping(value = "/withDrawStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberType", value = "用户角色（1买手，2买家，3背包客）", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "bizOrderNo", value = "提现交易录记号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "查询提现结果", produces = MediaType.APPLICATION_JSON_VALUE, response = WithDrawStatusResult.class)
    public Object withDrawStatus(HttpServletRequest request, Integer memberType, String bizOrderNo) {
        return exec(() -> orderService.withDrawStatus(getCurrUser(request), memberType, bizOrderNo));
    }

}