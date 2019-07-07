package com.xmyy.order.web;

import com.xmyy.order.model.DgOrder;
import com.xmyy.order.service.DgOrderService;
import com.xmyy.order.vo.DeleteOrderParam;
import com.xmyy.order.vo.QueryOrderParam;
import com.xmyy.order.vo.AfterSaleDetailResult;
import com.xmyy.order.vo.AfterSaleResult;
import com.xmyy.order.vo.OrderDetailResult;
import com.xmyy.order.vo.OrderResult;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.AppBaseController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * APP订单公共接口  前端控制器
 *
 * @author AnCheng
 */
@RestController
@RequestMapping("/api/order/common")
@Api(value = "APP订单公共接口", description = "APP订单公共接口")
public class ApiOrderController extends AppBaseController<DgOrder, DgOrderService> {

    @PostMapping(value = "/list")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "查询订单列表", produces = MediaType.APPLICATION_JSON_VALUE, response = OrderResult.class)
    public Object queryOrderList(HttpServletRequest request, @Valid @RequestBody QueryOrderParam params, BindingResult bindingResult) {
        return exec(() -> service.queryOrderList(params, getCurrUser(request)), bindingResult);
    }


    @PostMapping(value = "/listAfterSale")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "查询售后列表", produces = MediaType.APPLICATION_JSON_VALUE, response = AfterSaleResult.class)
    public Object queryAfterSaleList(HttpServletRequest request, @Valid @RequestBody QueryOrderParam params, BindingResult bindingResult) {
        return exec(() -> service.queryAfterSaleList(getCurrUser(request), params), bindingResult);
    }


    @GetMapping(value = "/detail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "role", value = "用户角色（1买手，2买家，3背包客）", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "查询订单详情", produces = MediaType.APPLICATION_JSON_VALUE, response = OrderDetailResult.class)
    public Object queryOrderDetail(Long orderId, Integer role) {
        return exec(() -> service.queryOrderDetail(orderId, role));
    }


    @GetMapping(value = "/afterSaleDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "售后单id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "查询售后详情", produces = MediaType.APPLICATION_JSON_VALUE, response = AfterSaleDetailResult.class)
    public Object queryAfterSaleDetail(Long id) {
        return exec(() -> service.queryAfterSaleDetail(id));
    }


    @PostMapping(value = "/delete")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "删除订单", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteOrder(@Valid @RequestBody DeleteOrderParam params, BindingResult bindingResult) {
        return exec(() -> service.deleteOrder(params), bindingResult);
    }

}