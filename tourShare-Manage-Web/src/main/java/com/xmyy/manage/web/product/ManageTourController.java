package com.xmyy.manage.web.product;

import com.xmyy.common.shiro.ShiroUtils;
import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.product.model.DgTour;
import com.xmyy.product.service.ManageTourService;
import com.xmyy.product.service.TourService;
import com.xmyy.product.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;

import javax.validation.Valid;
import java.util.Map;

/**
 * 行程后台管理  前端控制器
 *
 * @author linbo
 */
@RestController
@RequestMapping("/manage/product/tours")
@Api(value = "行程相关接口", description = "行程相关接口")
public class ManageTourController extends BaseController<DgTour, ManageTourService> {

    @PostMapping("/list")
    @ApiOperation(value = "查询行程", response = ManageTourDetailResult.class)
    public Object query(@Valid @RequestBody ManageTourQueryParam params, BindingResult bindingResult) {
        return exec(() -> service.queryForBuyer(params), bindingResult);
    }


    @GetMapping("/region/count")
    @ApiOperation(value = "查询行程目的地统计数", response = ManageTourDestRegionCountResult.class)
    public Object queryRegionCount(ManageTourQueryParam params, BindingResult bindingResult) {
        return exec(() -> service.queryRegionCount(params), bindingResult);
    }


    @PostMapping("/validate")
    @ApiOperation(value = "行程验证")
    public Object validate(@RequestBody ManageTourValidateParam params, BindingResult bindingResult) {
        // 获取当前登录用户
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        return exec(() -> service.validateTour(params, userInfo), bindingResult);
    }


    @GetMapping("/validate/nextUnaudit")
    @ApiOperation(value = "查询下一个未认证的行程信息", response = ManageTourDetailResult.class)
    public Object queryNextUnaudit() {
        return exec(() -> service.queryNextUnaudit());
    }


    @GetMapping("/validate/list")
    @ApiOperation(value = "查询行程验证数据", response = ManageTourValidateDetailResult.class)
    public Object queryForValidate(ManageTourGetValidateParam params, BindingResult bindingResult) {
        return exec(() -> service.queryForValidate(params), bindingResult);
    }


    @GetMapping("/validate/count")
    @ApiOperation(value = "统计行程验证情况", response = ManageTourValidateCountResult.class)
    public Object countForValidate(ManageTourGetValidateParam params, BindingResult bindingResult) {
        return exec(() -> service.countForValidate(params), bindingResult);
    }


    @PostMapping("/setTop")
    @ApiOperation(value = "行程置顶", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object setTop(@RequestBody MananageTourTopParam params) {
        return exec(() -> service.setTop(params), null);
    }


    @GetMapping("/top/list")
    @ApiOperation(value = "查询前5个置顶行程", response = ManageTourTopResult.class)
    public Object topList() {
        return exec(() -> service.queryForTopList(), null);
    }


    @PostMapping("/top/cancle")
    @ApiOperation(value = "取消行程置顶")
    public Object topList(@RequestBody Map<String, Long> params, BindingResult bindingResult) {
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        Long id = params.get("id");
        return exec(() -> service.cancleTop(id, userInfo), bindingResult);
    }

}