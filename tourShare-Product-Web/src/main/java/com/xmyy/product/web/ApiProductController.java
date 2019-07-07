package com.xmyy.product.web;

import com.xmyy.product.model.PtProduct;
import com.xmyy.product.service.PtProductService;
import com.xmyy.product.vo.*;
import com.xmyy.search.vo.ProductSearchParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.AppBaseController;
import top.ibase4j.core.support.HttpCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 商品APP接口  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/api/product")
@Api(value = "APP商品接口", description = "APP商品接口")
public class ApiProductController extends AppBaseController<PtProduct, PtProductService> {

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增商品/编辑商品保存（新增时ID为空）", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object add(HttpServletRequest request, @Validated @RequestBody ApiProductSaveParam params, BindingResult bindingResult) {
        return exec(() -> service.save(params, getCurrUser(request)), bindingResult);
    }


    @PostMapping(value = "/saveV2")
    @ApiOperation(value = "新增商品（买手自定义属性值）", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object customAdd(HttpServletRequest request, @Validated @RequestBody ApiProductCustomSaveParam params, BindingResult bindingResult) {
        return exec(() -> service.saveV2(params, getCurrUser(request)), bindingResult);
    }


    @GetMapping(value = "/edit")
    @ApiOperation(value = "编辑商品（打开历史预售）", response = ApiProductEditResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "商品ID", dataType = "long", required = true, paramType = "query")
    })
    public Object getEdit(HttpServletRequest request, Long id) {
        return exec(() -> service.getEdit(id, getCurrUser(request)));
    }


    @GetMapping(value = "/setUp")
    @ApiOperation(value = "商品上架")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "商品ID", dataType = "Long", required = true, paramType = "query")
    })
    public Object setUp(HttpServletRequest request, Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "ID不能为空");
        }
        Integer isSale = 1;
        return exec(() -> service.updateSale(id, isSale, getCurrUser(request)));
    }


    @GetMapping(value = "/setDown")
    @ApiOperation(value = "商品下架")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "商品ID", dataType = "Long", required = true, paramType = "query")
    })
    public Object setDown(HttpServletRequest request, Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "ID不能为空");
        }
        Integer isSale = 0;
        return exec(() -> service.updateSale(id, isSale, getCurrUser(request)));
    }


    @GetMapping(value = "/detail")
    @ApiOperation(value = "商品详情", produces = MediaType.APPLICATION_JSON_VALUE, response = ApiProductDetailResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "商品ID", dataType = "long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "source", value = "来源（空值或0：其它，1：直播）", dataType = "int", paramType = "query")
    })
    public Object getDetail(HttpServletRequest request, Long id, Integer source) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "ID不能为空");
        }
        return exec(() -> service.getDetail(id, source, getCurrUser(request)));
    }


    @PostMapping(value = "/search")
    @ApiOperation(value = "商品搜索", response = ApiProductSearchListResult.class)
    public Object search(@RequestBody ProductSearchParam param) {
        return exec(() -> {
			/*long totalCount = searchService.getTotalCount();
			if(totalCount == 0){
				Integer ret = importService.fullImport();
				log.info("import to es total:"+ret);
			}else{
				searchService.search(param)
			}*/
            return service.search(param.getKeyword());
        });
    }


    @GetMapping(value = "/skus")
    @ApiOperation(value = "获取商品所有的SKU", response = ApiSkuListResult.class)
    @ApiImplicitParam(name = "id", value = "商品ID", dataType = "long", required = true, paramType = "query")
    public Object getSku(Long id) {
        return exec(() -> service.getSkus(id));
    }


    @GetMapping(value = "/attrs")
    @ApiOperation(value = "获取商品拥有的全部属性", response = ApiAttrListResult.class)
    @ApiImplicitParam(name = "id", value = "商品ID", dataType = "long", required = true, paramType = "query")
    public Object getAllAttrs(Long id) {
        return exec(() -> service.getAllAttrs(id));
    }


    @PostMapping(value = "/list")
    @ApiOperation(value = "商品列表", response = ApiProductListResult.class)
    public Object getList(@Validated @RequestBody ApiProductListParam params, BindingResult result) {
        return exec(() -> service.getList(params), result);
    }


    @PostMapping(value = "/tour/list")
    @ApiOperation(value = "根据行程ID查询商品列表", response = ApiProductListByTourIdResult.class)
    public Object getListByTour(@Validated @RequestBody ApiProductListByTourIdParam params, BindingResult result) {
        return exec(() -> service.getListByTourId(params), result);
    }

}