package com.xmyy.pay.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台测试 确认支付（前台+短信验证码）生成JSP页面用
 *
 * @author wangzejun
 */
@Controller
@ApiIgnore
@RequestMapping("/api/pay/paytest")
public class PayTestController {

    @RequestMapping("/toConfirmPayPage")
    public ModelAndView toConfirmPayPage(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        String paramsJson = "";
        modelMap.put("params", paramsJson);
        ModelAndView modelAndView = new ModelAndView("/pay/confirmPay");

        response.setHeader("Access-Control-Allow-Origin", "*");//允许跨域访问的域，可以是通配符”*”；
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-Max-Age", "1800");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return modelAndView;
    }
}