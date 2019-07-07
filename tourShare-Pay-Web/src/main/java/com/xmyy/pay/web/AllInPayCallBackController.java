package com.xmyy.pay.web;

import com.xmyy.pay.service.CallBackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付回调  前端控制器
 *
 * @author AnCheng
 */
@RestController
@RequestMapping("/api/pay/callBack")
@ApiIgnore
@Api(value = "通联回调接口", description = "通联回调接口")
public class AllInPayCallBackController {

    @Resource
    private CallBackService service;

    @PostMapping(value = "/signContract")
    @ApiOperation(value = "会员电子协议签约回调", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object signContractCallBack(HttpServletRequest request) {
        //回调是表单提交，提取数据
        Map<String, String> map = new HashMap<>();
        Enumeration paramNames = request.getParameterNames();
        getRequestValue(request, map, paramNames);

        return service.signContractCallBack(map.get("rps"));
    }


    @PostMapping(value = "/order")
    @ApiOperation(value = "订单支付回调", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object orderPayCallBack(HttpServletRequest request) {
        //回调是表单提交，提取数据
        Map<String, String> map = new HashMap<>();
        Enumeration paramNames = request.getParameterNames();
        getRequestValue(request, map, paramNames);

        return service.orderPayCallBack(map.get("rps"));
    }


    //获取表单请求的参数
    private void getRequestValue(HttpServletRequest request, Map<String, String> map, Enumeration paramNames) {
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
    }

}