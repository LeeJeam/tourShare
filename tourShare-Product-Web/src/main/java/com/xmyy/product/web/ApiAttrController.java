package com.xmyy.product.web;

import com.xmyy.product.model.PtAttr;
import com.xmyy.product.service.PtAttrService;
import com.xmyy.product.vo.ApiAttrSimpleListParam;
import com.xmyy.product.vo.ApiAttrSimpleListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.BaseController;

/**
 * 商品库属性  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/api/product/attr")
@Api(value = "APP商品属性接口", description = "APP商品属性接口")
public class ApiAttrController extends BaseController<PtAttr, PtAttrService> {

    @PostMapping(value = "/list")
    @ApiOperation(value = "属性列表", produces = MediaType.APPLICATION_JSON_VALUE, response = ApiAttrSimpleListResult.class)
    public Object queryAttrItem(@Validated @RequestBody ApiAttrSimpleListParam params, BindingResult bindingResult) {
        return exec(() -> service.getAttrList(params), bindingResult);
    }

}