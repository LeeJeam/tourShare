package com.xmyy.order.web;

import com.xmyy.order.model.DgOrder;
import com.xmyy.order.service.DgOrderService;
import com.xmyy.order.vo.ExpressResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AppBaseController;

/**
 * APP物流接口  前端控制器
 *
 * @author AnCheng
 */
@RestController
@RequestMapping("/api/order/express")
@Api(value = "APP物流接口", description = "APP物流接口")
public class ApiExpressController extends AppBaseController<DgOrder, DgOrderService> {

    @GetMapping(value = "/query")
    @ApiOperation(value = "查询物流信息", produces = MediaType.APPLICATION_JSON_VALUE, response = ExpressResult.class)
    @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单id", required = true, dataType = "Long")
    public Object queryExpress(Long orderId) {
        return exec(() -> service.queryExpress(orderId));
    }


    @GetMapping(value = "/list")
    @ApiOperation(value = "获取快递公司列表", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object queryCompanyList() {
        return exec(() -> service.queryCompanyList());
    }


    @GetMapping(value = "/company")
    @ApiOperation(value = "根据物流单号获取快递公司", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(paramType = "query", name = "wayBillNo", value = "运单号", required = true, dataType = "String")
    public Object queryExpressCompany(String wayBillNo) {
        return exec(() -> service.queryExpressCompany(wayBillNo));
    }

}