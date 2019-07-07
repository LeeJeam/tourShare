package com.xmyy.order.web;

import com.xmyy.order.model.DgOrder;
import com.xmyy.order.service.DgOrderService;
import com.xmyy.order.vo.*;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.ibase4j.core.base.AppBaseController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * APP买家端订单接口  前端控制器
 *
 * @author AnCheng
 */
@RestController
@RequestMapping("/api/order/buyer")
@Api(value = "APP买家端订单接口", description = "APP买家端订单接口")
public class ApiBuyerOrderController extends AppBaseController<DgOrder, DgOrderService> {

    /**
     * 支付接通后，不再需要此接口，暂时IOS端用到
     */
    @ApiIgnore
    @GetMapping(value = "/getPayStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "订单ID字符串", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "买家登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "查询订单支付状态（支付接通后弃用）", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getPayStatus(String ids) {
        return exec(() -> service.getPayStatus(ids));
    }


    @GetMapping(value = "/getPayParams")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "订单ID", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "买家登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "支付‘待支付’订单，获取支付所需数据", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getPayParams(Long id) {
        return exec(() -> service.getPayParams(id));
    }


    /**
     * 支付接通后，不再需要此接口
     */
    @ApiIgnore
    @PostMapping(value = "/toPay")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "买家登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "支付‘待支付’订单（支付接通后弃用）", produces = MediaType.APPLICATION_JSON_VALUE, response = ToPayOrderResult.class)
    public Object toPay(@Valid @RequestBody ToPayParam params, BindingResult bindingResult) {
        return exec(() -> service.toPay(params), bindingResult);
    }


    @PostMapping(value = "/createOrders")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "买家登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "订单结算，获取支付所需数据", produces = MediaType.APPLICATION_JSON_VALUE, response = ToPayOrderResult.class)
    public Object createOrders(HttpServletRequest request, @Valid @RequestBody CreateOrderListParam params, BindingResult bindingResult) {
        return exec(() -> service.createOrders(params, getCurrUser(request)), bindingResult);
    }


    @GetMapping(value = "/remind")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单ID", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "买家登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "提醒买手发货", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object remind(Long orderId) {
        return exec(() -> service.remind(orderId));
    }


    @PostMapping(value = "/cancel")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "买家登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "买家取消订单", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object cancelOrder(@Valid @RequestBody CancelOrderParam params, BindingResult bindingResult) {
        return exec(() -> service.cancelOrder(params), bindingResult);
    }


    @PostMapping(value = "/afterSale")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "买家登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "买家申请售后", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object afterSale(@Valid @RequestBody AfterSaleParam params, BindingResult bindingResult) {
        return exec(() -> service.afterSale(params), bindingResult);
    }


    @PostMapping(value = "/confirmReceive")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "买家登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "买家确认收货", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object confirmReceive(@Valid @RequestBody ConfirmReceiveParam params, BindingResult bindingResult) {
        return exec(() -> service.confirmReceive(params), bindingResult);
    }

}