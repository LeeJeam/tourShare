package com.xmyy.manage.web.product;

import com.xmyy.product.model.PtSeries;
import com.xmyy.product.service.PtSeriesService;
import com.xmyy.product.vo.ManageSeriesParam;
import com.xmyy.product.vo.ManagerPtSeriesListResult;
import com.xmyy.product.vo.ProductStatisticResult;
import com.xmyy.product.vo.SeriesAddParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;

import javax.annotation.Resource;

/**
 * 商品模版  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/manage/product/series")
@Api(value = "商品系列接口", description = "商品系列接口")
public class ManageSeriesController extends BaseController<PtSeries, PtSeriesService> {

    @PostMapping(value = "/add")
    @ApiOperation(value = "新增商品系列", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object add(@Validated @RequestBody SeriesAddParam params, BindingResult bindingResult) {
        return exec(() -> service.add(params), bindingResult);
    }


    @PostMapping(value = "/list")
    @ApiOperation(value = "商品系列列表", produces = MediaType.APPLICATION_JSON_VALUE, response = ManagerPtSeriesListResult.class)
    public Object querySeriesLis(@Validated @RequestBody ManageSeriesParam params, BindingResult bindingResult) {
        return exec(() -> service.queryManagerPtSeriesList(params), bindingResult);
    }


    @GetMapping(value = "/statistic")
    @ApiOperation(value = "系列统计", response = ProductStatisticResult.class)
    public Object statistic() {
        return exec(() -> service.statistic());
    }

}