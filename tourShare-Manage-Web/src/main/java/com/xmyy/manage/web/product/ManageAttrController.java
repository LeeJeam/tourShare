package com.xmyy.manage.web.product;

import com.xmyy.product.model.PtAttr;
import com.xmyy.product.service.ManagePtAttrService;
import com.xmyy.product.vo.AttrItemAddParam;
import com.xmyy.product.vo.AttrItemListParam;
import com.xmyy.product.vo.AttrModifyParam;
import com.xmyy.product.vo.AttrTypeAndValueListResult;
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
 * 商品库属性后台管理  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/manage/product/attr")
@Api(value = "商品库属性接口", description = "商品库属性接口")
public class ManageAttrController extends BaseController<PtAttr, ManagePtAttrService> {

    @PostMapping(value = "/add")
    @ApiOperation(value = "新增属性、属性值", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addAttrItem(@Validated @RequestBody AttrItemAddParam params, BindingResult bindingResult) {
        Long currUser = this.getShiroCurrUser();
        return exec(() -> service.addAttrItem(params, currUser), bindingResult);
    }


    @PostMapping(value = "/list")
    @ApiOperation(value = "查询二级类目下的属性列表", produces = MediaType.APPLICATION_JSON_VALUE, response = AttrTypeAndValueListResult.class)
    public Object queryAttrItem(@Validated @RequestBody AttrItemListParam params, BindingResult bindingResult) {
        return exec(() -> service.queryAttrItem(params), bindingResult);
    }


    @PostMapping(value = "/modify")
    @ApiOperation(value = "修改类目属性")
    public Object modify(@Validated @RequestBody AttrModifyParam params, BindingResult bindingResult) {
        Long currUser = this.getShiroCurrUser();
        return exec(() -> service.saveAttrItemModify(params, currUser), bindingResult);
    }

}