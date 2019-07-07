package com.xmyy.product.web;

import com.xmyy.product.model.PtCategory;
import com.xmyy.product.service.PtCategoryService;
import com.xmyy.product.vo.CategoryListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AppBaseController;
import top.ibase4j.core.support.HttpCode;

/**
 * 类目  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/api/product/category")
@Api(value = "APP商品类目接口", description = "APP商品类目接口")
public class ApiCategoryController extends AppBaseController<PtCategory, PtCategoryService> {

    @GetMapping(value = "/hasBrand/list")
    @ApiOperation(value = "查询类目列表(关联有品牌的类目)", response = CategoryListResult.class)
    @ApiImplicitParam(name = "id", value = "类目ID（ID=0查询一级类目）", paramType = "query", dataType = "Long", required = true)
    public Object queryhasBrandList(Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "类目ID不能为空");
        }
        return exec(() -> service.getList(id));
    }

}