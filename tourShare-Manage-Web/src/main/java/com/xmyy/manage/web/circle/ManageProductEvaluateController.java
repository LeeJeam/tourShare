package com.xmyy.manage.web.circle;

import com.xmyy.circle.model.DgProductEvaluate;
import com.xmyy.circle.service.DgProductEvaluateService;
import com.xmyy.circle.vo.ProductEvaluateParam;
import com.xmyy.circle.vo.ProductEvaluateResult;
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
import top.ibase4j.core.support.HttpCode;

/**
 * 商品评价后台管理  前端控制器
 *
 * @author yeyu
 */
@RestController
@RequestMapping("/manage/circle/comment")
@Api(value = "商品评价后台管理接口", description = "商品评价后台管理接口")
public class ManageProductEvaluateController extends BaseController<DgProductEvaluate, DgProductEvaluateService> {

    @PostMapping(value = "/list")
    @ApiOperation(value = "查询商品评价接口", produces = MediaType.APPLICATION_JSON_VALUE, response = ProductEvaluateResult.class)
    public Object queryList(@Validated @RequestBody ProductEvaluateParam param, BindingResult bindingResult) {
        if (param.getUserId() == null || param.getUserId() == 0) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "UserId不能为空,且不能为0");
        }
        return exec(() -> service.queryProductEvaluate(param), bindingResult);
    }
}
