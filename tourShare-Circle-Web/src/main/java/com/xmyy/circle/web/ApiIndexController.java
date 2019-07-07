package com.xmyy.circle.web;

import com.xmyy.circle.model.UcDynamicCircle;
import com.xmyy.circle.service.ApiIndexService;
import com.xmyy.circle.vo.ApiAppResult;
import com.xmyy.circle.vo.BuyerApiIndexResult;
import com.xmyy.circle.vo.BuyerIndexParam;
import com.xmyy.circle.vo.SellerApiIndexResult;
import com.xmyy.common.util.CacheUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.ibase4j.core.base.AppBaseController;
import top.ibase4j.core.support.HttpCode;
import top.ibase4j.core.support.Token;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.KickOutTokenUtil;
import top.ibase4j.core.util.PropertiesUtil;
import top.ibase4j.core.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * APP首页  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/api/circle/base")
@Api(value = "首页、基础数据合并接口", description = "首页、基础数据合并接口")
public class ApiIndexController extends AppBaseController<UcDynamicCircle, ApiIndexService> {

    @GetMapping(value = "/get")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String")
    @ApiOperation(value = "买家端APP预加载数据接口", produces = MediaType.APPLICATION_JSON_VALUE, response = ApiAppResult.class)
    public Object get(HttpServletRequest request) {
        Integer code = null;
        try {
            code = checkToken(request);
        } catch (Exception e) {
            logger.error("token检查发生异常:", e);
        }

        Map<String, Object> modelMap = InstanceUtil.newLinkedHashMap();

        modelMap.put("code", HttpCode.OK.value());
        modelMap.put("msg", HttpCode.OK.msg());
        modelMap.put("serveCode", code);
        modelMap.put("data", service.get());
        modelMap.put("version", CacheUtils.getHeadInfoVersion());
        modelMap.put("timestamp", System.currentTimeMillis());

        return modelMap;
    }


    @GetMapping(value = "/seller/get")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String")
    @ApiOperation(value = "买手端APP预加数据接口", produces = MediaType.APPLICATION_JSON_VALUE, response = ApiAppResult.class)
    public Object sellerGet(HttpServletRequest request) {
        Integer code = null;
        try {
            code = checkToken(request);
        } catch (Exception e) {
            logger.error("token检查发生异常:", e);
        }

        Map<String, Object> modelMap = InstanceUtil.newLinkedHashMap();

        modelMap.put("code", HttpCode.OK.value());
        modelMap.put("msg", HttpCode.OK.msg());
        modelMap.put("serveCode", code);
        modelMap.put("data", service.sellerGet());
        modelMap.put("version", CacheUtils.getHeadInfoVersion());
        modelMap.put("timestamp", System.currentTimeMillis());

        return modelMap;
    }


    @PostMapping(value = "/index")
    @ApiOperation(value = "买家端首页", produces = MediaType.APPLICATION_JSON_VALUE, response = BuyerApiIndexResult.class)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String")
    public Object index(HttpServletRequest request, @RequestBody BuyerIndexParam params) {
        return exec(() -> {
            params.setBuyerId(getCurrUser(request));
            return service.index(params);
        }, null, CacheUtils.getHeadInfoVersion());
    }


    @GetMapping(value = "/seller/index")
    @ApiOperation(value = "买手端首页", produces = MediaType.APPLICATION_JSON_VALUE, response = SellerApiIndexResult.class)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", dataType = "String")
    public Object sellerIndex(HttpServletRequest request) {
        Integer code = null;
        try {
            code = checkToken(request);
        } catch (Exception e) {
            logger.error("token检查发生异常:", e);
        }

        Map<String, Object> modelMap = InstanceUtil.newLinkedHashMap();

        modelMap.put("code", HttpCode.OK.value());
        modelMap.put("msg", HttpCode.OK.msg());
        modelMap.put("serveCode", code);
        modelMap.put("data", service.sellerIndex(getCurrUser(request)));
        modelMap.put("version", CacheUtils.getHeadInfoVersion());
        modelMap.put("timestamp", System.currentTimeMillis());

        return modelMap;
    }


    private Integer checkToken(HttpServletRequest request) {
        String token = request.getHeader("SESSID");
        logger.info("SESSID==>" + token);
        long expire = PropertiesUtil.getLong("token.expire", 0);
        Integer code = null;
        if (StringUtils.isNotBlank(token)) {
            Token tokenInfo = TokenUtil.getTokenInfo(token);
            if (tokenInfo != null) {
                if (expire > 0) {
                    if (System.currentTimeMillis() - tokenInfo.getTime() > expire * 1000) {
                        code = HttpCode.UNAUTHORIZED.value();
                    }
                }
            } else {
                Token kickOutToken = KickOutTokenUtil.getKickOutTokenInfo(token);
                if (kickOutToken != null) {
                    code = HttpCode.REQUEST_KICKOUT.value();
                }
            }
        }
        return code;
    }
}
