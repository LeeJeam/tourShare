package com.xmyy.manage.web.product;

import com.xmyy.product.model.PtCategory;
import com.xmyy.product.service.ManagePtCategoryService;
import com.xmyy.product.vo.CategoryAddParam;
import com.xmyy.product.vo.CategoryListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;
import top.ibase4j.core.support.HttpCode;

/**
 * 商品类目后台管理  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/manage/product/category")
@Api(value = "类目接口", description = "类目接口")
public class ManageCategoryController extends BaseController<PtCategory, ManagePtCategoryService> {

    @PostMapping(value = "/add")
    @ApiOperation(value = "新增类目", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object add(@Validated @RequestBody CategoryAddParam param, BindingResult bindingResult) {
        return exec(() -> service.add(param), bindingResult);
    }


    @GetMapping(value = "/read/list")
    @ApiOperation(value = "查询类目列表", response = CategoryListResult.class)
    @ApiImplicitParam(name = "id", value = "类目ID（ID=0查询一级类目）", paramType = "query", dataType = "Long", required = true)
    public Object getList(Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "类目ID不能为空");
        }
        return exec(() -> service.getCategoryList(id));
    }


    @GetMapping(value = "/category2/list")
    @ApiOperation(value = "查询所有二级类目列表", response = CategoryListResult.class)
    public Object queryCategory2() {
        return exec(() -> service.getCategoryList2());
    }

}