package com.xmyy.demand.web;

import com.xmyy.demand.model.DgDemandOrder;
import com.xmyy.demand.service.DgDemandOrderService;
import com.xmyy.demand.vo.*;
import com.xmyy.member.vo.SellerPageResult;
import com.xmyy.order.vo.ToPayOrderResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.AppBaseController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 需求  前端控制器
 *
 * @author AnCheng
 */
@RestController
@RequestMapping("/api/demand")
@Api(value = "APP需求接口", description = "APP需求接口")
public class ApiDemandController extends AppBaseController<DgDemandOrder, DgDemandOrderService> {

    @GetMapping(value = "/list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "role", value = "1买手，2买家，3背包客", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "页码（默认1）", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小（默认10）", dataType = "int")
    })
    @ApiOperation(value = "我的需求列表", produces = MediaType.APPLICATION_JSON_VALUE, response = DemandListResult.class)
    public Object queryDemandList(HttpServletRequest request, @RequestParam Integer role, Integer current, Integer size) {
        return exec(() -> service.queryDemandList(role, getCurrUser(request), current, size));
    }


    @GetMapping(value = "/waitingList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "tag", value = "标签", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "isPacker", value = "是否背包客（0否，1是）", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小（默认10）", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "页码（默认1）", dataType = "int")
    })
    @ApiOperation(value = "买手/背包客，查询待接单需求列表", produces = MediaType.APPLICATION_JSON_VALUE, response = DemandListByTagsResult.class)
    public Object queryWaitingAcceptList(HttpServletRequest request, String tag, Integer size, Integer current, Integer isPacker) {
        return exec(() -> service.queryWaitingAcceptList(tag, size, current, getCurrUser(request), isPacker));
    }


    @GetMapping(value = "/detail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "需求id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "role", value = "1买手，2买家，3背包客", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "需求详情", produces = MediaType.APPLICATION_JSON_VALUE, response = DemandDetailResult.class)
    public Object queryDemandDetail(HttpServletRequest request, @RequestParam Long id, Integer role) {
        return exec(() -> service.queryDemandDetail(id, role, getCurrUser(request)));
    }


    @PostMapping(value = "/createDemand")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "发布需求", produces = MediaType.APPLICATION_JSON_VALUE, response = AddDemandResult.class)
    public Object insertDemand(HttpServletRequest request, @Valid @RequestBody AddDemandParam params, BindingResult bindingResult) {
        return exec(() -> service.createDemand(params, getCurrUser(request)), bindingResult);
    }


    @PostMapping(value = "/payUpdate")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "Long")
    @ApiOperation(value = "支付需求(弃用)", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object payForDemand(HttpServletRequest request, @Valid @RequestBody PayDemandParam params, BindingResult bindingResult) {
        return exec(() -> service.payForDemand(params, getCurrUser(request)), bindingResult);
    }


    @PostMapping(value = "/cancelDemand")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "买家取消需求", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object cancelDemand(HttpServletRequest request, @Valid @RequestBody CancleDemandParam params, BindingResult bindingResult) {
        return exec(() -> service.cancelDemandOrder(params, getCurrUser(request)), bindingResult);
    }


    @PostMapping(value = "/addToilsome")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "添加辛苦费", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object saveToilsome(HttpServletRequest request, @Valid @RequestBody AddToilsomeParam params, BindingResult bindingResult) {
        return exec(() -> service.addToilsome(params, getCurrUser(request)), bindingResult);
    }


    @GetMapping(value = "/acceptorInfoList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "需求id", required = true, dataType = "Long")
    })
    @ApiOperation(value = "获取接单人列表", produces = MediaType.APPLICATION_JSON_VALUE, response = AcceptListResult.class)
    public Object listAcceptor(HttpServletRequest request, @RequestParam Long id) {
        return exec(() -> service.listAcceptor(id, getCurrUser(request)));
    }


    @PostMapping(value = "/appoint")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "指定接单人，生成待支付订单，获取支付所需数据", produces = MediaType.APPLICATION_JSON_VALUE, response = ToPayOrderResult.class)
    public Object appoint(HttpServletRequest request, @Valid @RequestBody DemandToOrderParam params, BindingResult bindingResult) {
        return exec(() -> service.appoint(params, getCurrUser(request)), bindingResult);
    }


    @GetMapping(value = "/cancelList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "页码（默认1）", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小（默认10）", dataType = "int"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "买家查询下架需求", produces = MediaType.APPLICATION_JSON_VALUE, response = DemandListResult.class)
    public Object queryCancelDemandList(HttpServletRequest request, Integer current, Integer size) {
        return exec(() -> service.queryCancelDemandList(getCurrUser(request), current, size));
    }


    @PostMapping(value = "/userDeleteDemand")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "删除需求", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteDemand(HttpServletRequest request, @Valid @RequestBody DeleteDemandParam params, BindingResult bindingResult) {
        return exec(() -> service.deleteDemand(params, getCurrUser(request)), bindingResult);
    }


    @GetMapping(value = "/queryListNoLogin")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tag", value = "标签", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "页码（默认1）", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小（默认10）", dataType = "int")
    })
    @ApiOperation(value = "未登录用户，查询需求列表（只能查询待受理的需求）", produces = MediaType.APPLICATION_JSON_VALUE, response = DemandListByTagsResult.class)
    public Object queryListNoLogin(Integer current, Integer size, String tag) {
        return exec(() -> service.queryListNoLogin(current, size, tag));
    }


    @GetMapping(value = "/detailNoLogin")
    @ApiImplicitParam(paramType = "query", name = "id", value = "需求id", required = true, dataType = "Long")
    @ApiOperation(value = "未登录用户，查询需求详情", produces = MediaType.APPLICATION_JSON_VALUE, response = DemandDetailResult.class)
    public Object queryDetailNoLogin(Long id) {
        return exec(() -> service.queryDetailNoLogin(id));
    }


    @GetMapping(value = "/againRelease")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "需求id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "重新发布需求，获取失效需求的数据，用于编辑", produces = MediaType.APPLICATION_JSON_VALUE, response = AgainReleaseResult.class)
    public Object againRelease(HttpServletRequest request, Long id) {
        return exec(() -> service.againRelease(id, getCurrUser(request)));
    }


    @GetMapping(value = "/searchDemand")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键字", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "isPacker", value = "是否背包客（0否，1是）", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "isDesc", value = "是否降序（0否，1是）", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "sortType", value = "排序类型：time时间，price价格", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "tag", value = "标签", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "页码", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小", dataType = "int")
    })
    @ApiOperation(value = "搜索需求", produces = MediaType.APPLICATION_JSON_VALUE, response = DemandListByTagsResult.class)
    public Object searchDemand(HttpServletRequest request, String keyword, Integer current, Integer isPacker,
                               Integer isDesc, String sortType, String tag) {
        return exec(() -> service.searchDemand(keyword, current, getCurrUser(request), isPacker, isDesc, sortType, tag));
    }

}