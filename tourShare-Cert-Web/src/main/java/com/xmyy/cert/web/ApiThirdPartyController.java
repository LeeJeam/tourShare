package com.xmyy.cert.web;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoAuthRequest;
import com.alipay.api.request.ZhimaCreditScoreGetRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoAuthResponse;
import com.alipay.api.response.ZhimaCreditScoreGetResponse;
import com.xmyy.cert.model.UcMemberCert;
import com.xmyy.cert.service.UcMemberCertService;
import com.xmyy.cert.vo.ThirdPartyParam;
import com.xmyy.common.vo.ZhiMaConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;

/**
 * 芝麻信用  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/api/cert/zhiMaCredit")
@Api(value = "芝麻信用接口", description = "APP芝麻信用接口")
public class ApiThirdPartyController extends BaseController<UcMemberCert, UcMemberCertService> {

    /**
     * 调用【alipay.user.info.auth】(用户登陆授权)接口获取授权链接
     * 调用此接口需要注意一下几点：
     * ①ReturnUrl这个参数必须传入且必须和应用中的授权回调地址相同
     * ②授权的scope需要是auth_zhima，如果您不是调用芝麻信用评分接口，请根据您的接口传入对应的scope
     * ③请求调用的方法alipayClient.pageExecute不是alipayClient.execute，后面传入get获取的就是json字符串格式的授权地址，不传入则是获取到form表单。
     */
    @GetMapping("/aliPayUserAuth")
    @ApiOperation(value = "获取芝麻信用授权", httpMethod = "GET")
    @ResponseBody
    public String aliPayUserAuth() throws AlipayApiException {
        String userAuthUrl = null;
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(ZhiMaConstants.gatewayUrl, ZhiMaConstants.app_id, ZhiMaConstants.merchant_private_key, ZhiMaConstants.format, ZhiMaConstants.charset, ZhiMaConstants.alipay_public_key, ZhiMaConstants.sign_type);
            AlipayUserInfoAuthRequest request = new AlipayUserInfoAuthRequest();
            //传入ReturnUrl  必传且必须和应用中的授权回调地址相同
            request.setReturnUrl(ZhiMaConstants.returnUrl);
            //授权时请提供芝麻信用授权scope：auth_zhima
            request.setBizContent("{\"scopes\":[\"auth_zhima\"],\"state\":\"init\"}");
            AlipayUserInfoAuthResponse response1 = alipayClient.pageExecute(request, "GET");
            userAuthUrl = response1.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userAuthUrl;
    }


    /**
     * 通过【alipay.system.oauth.token】换取授权访问令牌接口使用code获取access_token
     * 注意：这里的access_token参数一定是authzma开头的才是正确的
     */
    @GetMapping("/aliPayOauthToken")
    @ApiOperation(value = "获取芝麻信用TOKEN令牌", httpMethod = "GET")
    @ApiImplicitParam(paramType = "query", name = "authCode", value = "授权码", required = false, dataType = "String")
    @ResponseBody
    public String aliPayOauthToken(@RequestParam(value = "authCode") String authCode) {
        String accessToken = null;
        AlipayClient alipayClient = new DefaultAlipayClient(ZhiMaConstants.gatewayUrl, ZhiMaConstants.app_id, ZhiMaConstants.merchant_private_key, ZhiMaConstants.format, ZhiMaConstants.charset, ZhiMaConstants.alipay_public_key, ZhiMaConstants.sign_type);
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(authCode);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            accessToken = oauthTokenResponse.getAccessToken();
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
        }
        return accessToken;
    }


    /**
     * 调用【zhima.credit.score.get】芝麻信用评分接口
     * 1、transaction_id 参数是商户请求的唯一标志，64位长度的字母数字下划线组合。由商户自行定义，该标识作为对账的关键信息，商户要保证其唯一性，对于用户使用相同transaction_id的查询，芝麻在一天（86400秒）内返回首次查询数据，超过有效期的查询即为无效并返回异常，有效期内的重复查询不重新计费
     * 2、product_code 产品码参数是固定值 w1010100100000000001 ，请勿更改
     * 3、access_token参数需要传在execute方法提交方法中
     **/
    @GetMapping("/aliPayCreditScore")
    @ApiOperation(value = "获取芝麻信用TOKEN令牌", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String aliPayCreditScore(ThirdPartyParam thirdPartyParams) throws AlipayApiException {
        String zmScore = null;
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(ZhiMaConstants.gatewayUrl, ZhiMaConstants.app_id, ZhiMaConstants.merchant_private_key, ZhiMaConstants.format, ZhiMaConstants.charset, ZhiMaConstants.alipay_public_key, ZhiMaConstants.sign_type);
            ZhimaCreditScoreGetRequest request = new ZhimaCreditScoreGetRequest();
            request.setBizContent("{" +
                    //transaction_id商户请求的唯一标志，64位长度的字母数字下划线组合
                    "\"transaction_id\":\"" + thirdPartyParams.getTransactionId() + "\"," +
                    "\"product_code\":\"" + thirdPartyParams.getProductCode() + "\"" +
                    "}");
            ZhimaCreditScoreGetResponse response = alipayClient.execute(request, thirdPartyParams.getAccessToken());
            zmScore = response.getZmScore();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return zmScore;
    }

}
