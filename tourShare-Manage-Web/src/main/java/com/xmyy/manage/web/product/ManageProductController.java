package com.xmyy.manage.web.product;

import com.xmyy.product.model.PtProduct;
import com.xmyy.product.service.ManagePtProductService;
import com.xmyy.product.service.PtProductService;
import com.xmyy.product.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.ibase4j.core.base.BaseController;

import javax.annotation.Resource;

/**
 * 商品后台管理  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/manage/product")
@Api(value = "商品接口", description = "商品接口")
public class ManageProductController extends BaseController<PtProduct, ManagePtProductService> {

    @GetMapping(value = "/statistic")
    @ApiOperation(value = "查询商品、一级类目统计", response = ProductStatisticResult.class)
    public Object statistic() {
        return exec(() -> service.statistic());
    }


    @PostMapping(value = "/top/list")
    @ApiOperation(value = "查询商品置顶列表", response = ManageProductListResult.class)
    public Object queryTopList(@Validated @RequestBody ManageProductListParam params, BindingResult bindingResult) {
        return exec(() -> service.queryManageProductList(params), bindingResult);
    }


    @PostMapping(value = "/setTop")
    @ApiOperation(value = "商品置顶")
    public Object setTop(@Validated @RequestBody ManageProductTopParam params, BindingResult bindingResult) {
        params.setUpdateBy(getShiroCurrUser());
        return exec(() -> service.setTop(params), bindingResult);
    }


    @GetMapping(value = "/cancelTop")
    @ApiOperation(value = "取消商品置顶")
    @ApiImplicitParam(name = "id", value = "商品ID", dataType = "long", paramType = "query")
    public Object cancelTop(Long id) {
        Long currUser = getShiroCurrUser();
        return exec(() -> service.cancelTop(id, currUser));
    }


    @GetMapping(value = "/currTop")
    @ApiOperation(value = "查询当前已置顶的商品列表")
    public Object currTopList() {
        return exec(() -> service.getCurrTopList());
    }


    @PostMapping(value = "/list")
    @ApiOperation(value = "预售物品库列表", response = ManagePresellProductResult.class)
    public Object queryManagerList(@Validated @RequestBody ManagePresellProductParam params, BindingResult bindingResult) {
        return exec(() -> service.queryManagePresellProductList(params), bindingResult);
    }


/*    @ApiIgnore
    @ResponseBody
    @PostMapping(value = "/list")
    @ApiOperation(value = "获取商品信息集合", response = ProductManagerResult.class)
    public Object queryManagerList(@Validated @RequestBody ManageProductParams params, BindingResult bindingResult) {
        return exec(() -> service.queryProductManageList(params), bindingResult);
    }*/

}