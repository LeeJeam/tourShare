package com.xmyy.product.web;

import com.xmyy.product.model.DgCurrencyExchange;
import com.xmyy.product.service.DgCurrencyExchangeService;
import com.xmyy.product.vo.ApiExchangeListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AppBaseController;

/**
 * 汇率  前端控制器
 * 在线接口文档：http://www.juhe.cn/docs/80
 *
 * @author simon
 */
@RestController
@RequestMapping("/api/product/exchange")
@Api(value = "APP汇率接口", description = "APP汇率接口")
public class ApiExchangeController extends AppBaseController<DgCurrencyExchange, DgCurrencyExchangeService> {

    @GetMapping(value = "/list")
    @ApiOperation(value = "所有货币汇率", response = ApiExchangeListResult.class)
    public Object getList() {
        return exec(() -> service.getList());
    }

}