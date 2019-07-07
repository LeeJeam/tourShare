package com.xmyy.product.web;

import com.xmyy.product.model.PtSeries;
import com.xmyy.product.service.PtAttrService;
import com.xmyy.product.service.PtSeriesService;
import com.xmyy.product.vo.ApiAttrListResult;
import com.xmyy.product.vo.ApiSeriesListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AppBaseController;

import javax.annotation.Resource;

/**
 * 商品模版  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/api/product/series")
@Api(value = "APP商品系列接口", description = "APP商品系列接口")
public class ApiSeriesController extends AppBaseController<PtSeries, PtSeriesService> {

    @Resource
    private PtAttrService ptSeriesAttrService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "商品系列", response = ApiSeriesListResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "一级类目ID", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "categoryId2", value = "二级类目ID", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "brandId", value = "品牌ID", dataType = "Long", required = true, paramType = "query")
    })
    public Object getList(Long categoryId, Long categoryId2, Long brandId) {
        return exec(() -> service.getList(categoryId, categoryId2, brandId));
    }


    @GetMapping(value = "/attrs")
    @ApiOperation(value = "获取系列拥有的全部属性", produces = MediaType.APPLICATION_JSON_VALUE, response = ApiAttrListResult.class)
    @ApiImplicitParam(name = "seriesId", value = "系列ID", paramType = "query", dataType = "Long", required = true)
    public Object querySeriesAttr(Long seriesId) {
        return exec(() -> ptSeriesAttrService.getSeriesAttr(seriesId));
    }

}