package com.xmyy.manage.web.order;

import com.xmyy.order.model.DgOrder;
import com.xmyy.order.service.DgOrderManageService;
import com.xmyy.order.vo.OrderDetailManageResult;
import com.xmyy.order.vo.OrderInTourManageResult;
import com.xmyy.order.vo.OrderManageResult;
import com.xmyy.order.vo.QueryOrderManageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;

/**
 * 订单后台管理  前端控制器
 *
 * @author AnCheng
 */
@RestController
@RequestMapping("/manage/order")
@Api(value = "订单后台管理", description = "订单后台管理")
public class ManageOrderController extends BaseController<DgOrder, DgOrderManageService> {

    @PostMapping(value = "/list")
    @ApiOperation(value = "订单管理列表", produces = MediaType.APPLICATION_JSON_VALUE, response = OrderManageResult.class)
    public Object queryOrderList(@ApiParam(value = "订单列表查询参数") @RequestBody QueryOrderManageParam params) {
        return exec(() -> service.queryOrderList(params), null);
    }


    @GetMapping(value = "/detail")
    @ApiImplicitParam(paramType = "query", name = "id", value = "订单id", required = true, dataType = "Long")
    @ApiOperation(value = "订单详情", produces = MediaType.APPLICATION_JSON_VALUE, response = OrderDetailManageResult.class)
    public Object queryOrderDetail(Long id) {
        return exec(() -> service.queryOrderDetail(id), null);
    }


    @GetMapping(value = "/orderInTour")
    @ApiImplicitParam(paramType = "query", name = "id", value = "行程id", required = true, dataType = "Long")
    @ApiOperation(value = "行程关联的订单", produces = MediaType.APPLICATION_JSON_VALUE, response = OrderInTourManageResult.class)
    public Object queryOrderInTour(Long id) {
        return exec(() -> service.queryOrderInTour(id), null);
    }
}