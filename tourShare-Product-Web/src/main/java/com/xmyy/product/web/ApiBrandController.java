package com.xmyy.product.web;

import com.xmyy.product.model.PtBrand;
import com.xmyy.product.service.PtBrandService;
import com.xmyy.product.vo.ApiBrandListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AppBaseController;

/**
 * 品牌  前端控制器
 * @author simon
 */
@RestController
@RequestMapping("/api/product/brand")
@Api(value = "APP品牌接口", description = "APP品牌接口")
public class ApiBrandController extends AppBaseController<PtBrand, PtBrandService> {

    @GetMapping(value = "/simple/list")
    @ApiOperation(value = "查询品牌列表", response = ApiBrandListResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "一级类目ID", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "categoryId2", value = "二级类目ID", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "initial", value = "品牌首字母", dataType = "string", paramType = "query")
    })
    public Object query(Long categoryId, Long categoryId2, String initial) {
        return exec(() -> service.getList(categoryId, categoryId2, initial));
    }

}