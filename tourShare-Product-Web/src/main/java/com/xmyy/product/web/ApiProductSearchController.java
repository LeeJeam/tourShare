package com.xmyy.product.web;

import com.xmyy.search.service.ProductImportService;
import com.xmyy.search.service.ProductSearchService;
import com.xmyy.search.vo.ProductSearchParam;
import io.swagger.annotations.Api;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.AbstractController;

import javax.annotation.Resource;

/**
 * 商品ES搜索  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/api/product/search")
@Api(value = "APP商品ES搜索接口", description = "APP商品ES搜索接口")
@Ignore
public class ApiProductSearchController extends AbstractController {

    @Resource
    private ProductImportService importService;
    @Resource
    private ProductSearchService searchService;

    @GetMapping(value = "fullImport")
    public Object importDoc() {
        return exec(() -> importService.fullImport());
    }

    @GetMapping(value = "incImport")
    public Object incrementImportDoc() {
        return exec(() -> importService.incrementImport());
    }

    @GetMapping(value = "importById")
    public Object importByIdDoc() {
        return exec(() -> importService.importByProductId(79L));
    }

    @PostMapping(value = "list")
    public Object searchDoc(@Validated @RequestBody ProductSearchParam param, BindingResult bindingResult) {
        return exec(() -> searchService.search(param), bindingResult);
    }

    @GetMapping(value = "total")
    public Object getTotalCount() {
        return exec(() -> searchService.getTotalCount());
    }

}
