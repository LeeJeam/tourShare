package com.xmyy.product.web;

import com.xmyy.product.model.DgTour;
import com.xmyy.product.service.TourService;
import com.xmyy.product.service.TourSiteService;
import com.xmyy.product.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.ibase4j.core.base.AppBaseController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 行程  前端控制器
 *
 * @author wangzejun
 */
@RestController
@RequestMapping("/api/product/tour")
@Api(value = "APP行程接口", description = "APP行程接口")
public class ApiTourController extends AppBaseController<DgTour, TourService> {

    @Resource
    private TourSiteService tourSiteService;

    @PostMapping(value = "/add")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "新增行程", produces = MediaType.APPLICATION_JSON_VALUE, response = DgTour.class)
    public Object add(@Valid @RequestBody ApiTourAddParam tourAddParams, BindingResult bindingResult, HttpServletRequest request) {
        return exec(() -> service.add(tourAddParams, getCurrUser(request)), bindingResult);
    }


    @PostMapping(value = "/recordCheck")
    @ApiOperation(value = "记录上传的登机牌信息（图片上传调用服务路径：/api/fileupload/uploadImage，必须是File类型）",
            produces = MediaType.APPLICATION_JSON_VALUE, response = DgTour.class)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object recordCheck(@Valid @RequestBody ApiTourUploadParam params, BindingResult bindingResult) {
        return exec(() -> service.recordCheck(params), bindingResult);
    }


    @PostMapping(value = "/detail")
    @ApiOperation(value = "行程详情", produces = MediaType.APPLICATION_JSON_VALUE, response = ApiTourDetailResult.class)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object getDetail(@Valid @RequestBody ApiTourDetailParam params, BindingResult bindingResult) {
        return exec(() -> service.getDetail(params), bindingResult);
    }


    @GetMapping(value = "/listSeller")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "isPacker", value = "是否背包客（0否，1是）", required = true, dataType = "int")
    })
    @ApiOperation(value = "买手/背包客 我的行程列表", produces = MediaType.APPLICATION_JSON_VALUE, response = ApiTourInfoResult.class)
    public Object listSeller(HttpServletRequest request, Integer isPacker) {
        return exec(() -> service.listForSeller(getCurrUser(request), isPacker));
    }


    @PostMapping(value = "/match")
    @ApiOperation(value = "查询匹配的行程列表（开直播/接需求/发预售）", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object getMatchTourList(HttpServletRequest request, @Valid @RequestBody ApiTourMatchParam params, BindingResult bindingResult) {
        params.setCreateBy(getCurrUser(request));
        return exec(() -> service.getMatchTourList(params), bindingResult);
    }


    @PostMapping(value = "/signin")
    @ApiOperation(value = "行程签到", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object sigIn(@Valid @RequestBody ApiTourSiteSignInParam param, BindingResult bindingResult) {
        return exec(() -> tourSiteService.signTourSite(param), bindingResult);
    }


    @PostMapping(value = "/listBuyer")
    @ApiOperation(value = "买家端首页，更多行程列表", produces = MediaType.APPLICATION_JSON_VALUE, response = ApiTourInBuyerIndexResult.class)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object listBuyer(@Valid @RequestBody ApiTourListForBuyerParam params, BindingResult bindingResult) {
        return exec(() -> service.listPageForBuyer(params), bindingResult);
    }


    @ApiIgnore
    @PostMapping(value = "/listBuyerIndex")
    @ApiOperation(value = "买家端首页轮播行程列表（后台测试用）")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object listBuyerIndex(@RequestBody ApiTourInBuyerIndexParam params) {
        return exec(() -> service.listIndexForBuyer(params), null);
    }


    @ApiIgnore
    @GetMapping(value = "/showIndexForPacker")
    @ApiOperation(value = "背包客个人主页进行中的行程信息（后台测试用）")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object showIndexForPacker(HttpServletRequest request) {
        return exec(() -> service.getTourInfoForPackerIndex(getCurrUser(request)));
    }


    @GetMapping(value = "/stop")
    @ApiOperation(value = "行程下架", response = DgTour.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "行程ID", required = true, dataType = "String")
    })
    public Object stop(Long id, HttpServletRequest request) {
        return exec(() -> service.stop(id, getCurrUser(request)));
    }


    @GetMapping(value = "/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "行程id", required = true, dataType = "Long")
    })
    @ApiOperation(value = "行程删除", response = DgTour.class)
    public Object del(Long id, HttpServletRequest request) {
        return exec(() -> service.delete(id, getCurrUser(request)));
    }


    @ApiIgnore
    @GetMapping(value = "/queryTour")
    @ApiOperation(value = "今日消息推送")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public void queryAllToDayTours() {
        service.pushTodayToursInfo();
    }

}