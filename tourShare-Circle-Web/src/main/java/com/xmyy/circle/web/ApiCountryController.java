package com.xmyy.circle.web;

import com.xmyy.circle.model.DgCountry;
import com.xmyy.circle.service.DgCountryService;
import com.xmyy.circle.vo.CountryResult;
import com.xmyy.circle.vo.CountryTreeResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.BaseController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 国家信息  前端控制器
 *
 * @author wangzejun
 */
@RestController
@RequestMapping("/api/circle/country")
@Api(value = "国家信息接口", description = "国家树，热门国家列表，国家列表")
public class ApiCountryController extends BaseController<DgCountry, DgCountryService> implements InitializingBean {

    //TODO
    @Override
    public void afterPropertiesSet() {
        /*try {
            service.loadRedisForCountryList();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("初始化国家字典数据失败", e.getMessage());
        }*/
    }

    @GetMapping(value = "/zhouTree")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "isContainHot", value = "国家树是否包含热门 数据格式：字母-国家 默认不包含，0-不包含，1-包含", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "keywords", value = "搜索关键字，目前仅支持中文搜索", dataType = "String")
    })
    @ApiOperation(value = "国家树", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE, response = CountryTreeResult.class)
    public Object zhouTree(Integer isContainHot, String keywords) {
        return exec(() -> service.getZhouCountryTree(isContainHot, keywords));
    }


    @GetMapping(value = "/zimuTree")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "isContainHot", value = "国家树是否包含热门 数据格式：字母-国家 默认不包含，0-不包含，1-包含", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "keywords", value = "搜索关键字，目前仅支持中文搜索", dataType = "String")
    })
    @ApiOperation(value = "国家树", response = CountryResult.class)
    public Object zimuTree(Integer isContainHot, String keywords) {
        return exec(() -> service.getZimuCountryTree(isContainHot, keywords));
    }


    @GetMapping(value = "/hotList")
    @ApiOperation(value = "热门国家列表", response = CountryResult.class)
    @ApiImplicitParam(paramType = "query", name = "keywords", value = "搜索关键字，目前仅支持中文搜索", dataType = "String")
    public Object hotList(String keywords) {
        return exec(() -> service.getHotCountryList(keywords));
    }


    @GetMapping(value = "/listByZhouCode")
    @ApiOperation(value = "根据洲获取国家", response = List.class)
    @ApiImplicitParam(paramType = "query", name = "zhouCode", value = "洲编码，例如需要亚洲 ASZ", dataType = "int")
    public Object listByZhouCode(String zhouCode) {
        return exec(() -> service.getCountrysByZhouCode(zhouCode));
    }


    @GetMapping(value = "/listCodeByZhouCode")
    @ApiOperation(value = "根据洲获取国家编码", response = List.class)
    @ApiImplicitParam(paramType = "query", name = "zhouCode", value = "洲编码，例如需要亚洲 ASZ", dataType = "int")
    public Object listCodeByZhouCode(String zhouCode) {
        return exec(() -> service.getCountryShortCodesByZhouCode(zhouCode));
    }
}