package com.xmyy.livevideo.web;

import com.xmyy.common.util.CacheUtils;
import com.xmyy.livevideo.model.VdLiveVideo;
import com.xmyy.livevideo.serivce.LiveVideoService;
import com.xmyy.livevideo.vo.LiveVideoResult;
import com.xmyy.product.service.PtProductService;
import com.xmyy.product.vo.ApiProductListByTourIdParam;
import com.xmyy.product.vo.ApiProductListByTourIdResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AppBaseController;

import javax.annotation.Resource;

/**
 * 买家端直播  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/api/live")
@Api(value = "买家端看直播接口", description = "买家端看直播接口")
public class ApiBuyerLiveController extends AppBaseController<VdLiveVideo, LiveVideoService> {

    @Resource
    private PtProductService ptProductService;

    @GetMapping(value = "/buyerLiveVideoList")
    @ApiOperation(value = "买家端买手直播列表", produces = MediaType.APPLICATION_JSON_VALUE, response = LiveVideoResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "countryCode", value = "国家码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "页码（默认1）", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小（默认10）", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登陆sessid", required = true, dataType = "String")
    })
    public Object buyerLiveVideoList(String countryCode, Integer current, Integer size) {
        return exec(() -> service.buyerLiveVideoList(countryCode, current, size), null, CacheUtils.getHeadInfoVersion());
    }


    @GetMapping(value = "/buyerByTourId")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tourId", value = "行程ID", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登陆sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "买家端通过行程观看直播", produces = MediaType.APPLICATION_JSON_VALUE, response = LiveVideoResult.class)
    public Object buyerByTourId(String tourId) {
        return exec(() -> service.buyerByTourId(tourId));
    }


    @GetMapping(value = "/productListByStrokeId")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tourId", value = "行程ID", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登陆sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "买手直播通过行程ID查询商品列表", produces = MediaType.APPLICATION_JSON_VALUE, response = ApiProductListByTourIdResult.class)
    public Object productListByStrokeId(ApiProductListByTourIdParam params) {
        return exec(() -> ptProductService.getListByTourId(params));
    }

}
