package com.xmyy.order.web;

import com.xmyy.order.model.DgOrder;
import com.xmyy.order.service.DgOrderService;
import com.xmyy.order.vo.AgreeRefundParam;
import com.xmyy.order.vo.DeliverGoodsParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AppBaseController;

import javax.validation.Valid;

/**
 * APP买手端订单接口  前端控制器
 *
 * @author AnCheng
 */
@RestController
@RequestMapping("/api/order/seller")
@Api(value = "APP买手端订单接口", description = "APP买手端订单接口")
public class ApiSellerOrderController extends AppBaseController<DgOrder, DgOrderService> {

    @PostMapping(value = "/deliverGoods")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "买手登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "买手发货", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deliverGoods(@Valid @RequestBody DeliverGoodsParam params, BindingResult bindingResult) {
        return exec(() -> service.deliverGoods(params), bindingResult);
    }


    @PostMapping(value = "/agreeRefund")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "买手登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "买手确认退款", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object agreeRefund(@Valid @RequestBody AgreeRefundParam params, BindingResult bindingResult) {
        return exec(() -> service.agreeRefund(params), bindingResult);
    }

}