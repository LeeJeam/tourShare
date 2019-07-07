package com.xmyy.manage.web.product;

import com.xmyy.product.model.PtBrand;
import com.xmyy.product.service.ManagePtBrandService;
import com.xmyy.product.vo.ApiBrandListResult;
import com.xmyy.product.vo.BrandListParam;
import com.xmyy.product.vo.BrandListResult;
import com.xmyy.product.vo.BrandSaveParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;

/**
 * 品牌后台管理  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/manage/product/brand")
@Api(value = "品牌接口", description = "品牌接口")
public class ManageBrandController extends BaseController<PtBrand, ManagePtBrandService> {

    @PostMapping(value = "/read/list")
    @ApiOperation(value = "查询品牌列表", produces = MediaType.APPLICATION_JSON_VALUE, response = BrandListResult.class)
    public Object query(@Validated @RequestBody BrandListParam params, BindingResult bindingResult) {
        return exec(() -> service.query(params), bindingResult);
    }


    @PostMapping(value = "/save")
    @ApiOperation(value = "新增/保存编辑 品牌（新增时id为空）", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object save(@Validated @RequestBody BrandSaveParam param, BindingResult bindingResult) {
        return exec(() -> service.save(param), bindingResult);
    }


    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑品牌（打开回显）", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "id", value = "品牌ID", dataType = "Long", required = true, paramType = "query")
    public Object edit(Long id) {
        return exec(() -> service.edit(id), null);
    }


    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除品牌", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "id", value = "品牌ID", dataType = "Long", required = true, paramType = "query")
    public Object delete(Long id) {
        return exec(() -> service.deleteBrand(id), null);
    }


    @GetMapping(value = "/simple/list")
    @ApiOperation(value = "查询品牌列表", response = ApiBrandListResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "一级类目ID", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "categoryId2", value = "二级类目ID", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "initial", value = "首字母", dataType = "string", paramType = "query")
    })
    public Object query(Long categoryId, Long categoryId2, String initial) {
        return exec(() -> service.getList(categoryId, categoryId2, initial));
    }

}