package com.xmyy.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.internal.util.RSACoderUtil;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthorizeRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthqueryRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditScoreGetRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaAuthInfoAuthqueryResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditScoreGetResponse;
import com.xmyy.common.Constants;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ZhiMaUtil {
    /**
     * 查询授权信息
     */
    public void queryZhimaAuthInfoAuthorize(String certNo, String userName) {
        ZhimaAuthInfoAuthorizeRequest req = new ZhimaAuthInfoAuthorizeRequest();
        req.setIdentityType(Constants.IDENTITYTYPE);// 身份标识
        req.setChannel(Constants.CHANNEL); // PC端
        //必要参数 state: 用于给商户提供透传的参数，芝麻会将此参数透传给商户;
        req.setBizParams("{\"auth_code\":\"M_APPPC_CERT\",\"state\":\"100111211\"}");
        req.setIdentityParam("{\"certNo\":\"" + certNo + "\",\"certType\":\"IDENTITY_CARD\",\"name\":\"" + userName + "\"}");// 必要参数
        DefaultZhimaClient client = new DefaultZhimaClient(Constants.URL, Constants.APPID, Constants.CHARSET, Constants.PRIKEY, Constants.PUBKEY);
        try {
            String url = client.generatePageRedirectInvokeUrl(req);
            System.out.println(url);
        } catch (ZhimaApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取目标用户的open_id,没有openid,走授权
     */
    public void testZhimaAuthInfoReq(String certNo, String userName) {
        ZhimaAuthInfoAuthqueryRequest req = new ZhimaAuthInfoAuthqueryRequest();
        req.setIdentityType(Constants.IDENTITYTYPE);  // 0：芝麻信用开放账号ID 1：按照手机号进行授权 2:按照身份证+姓名进行授权 3通过公安网验证进行授权 4.通过人脸验证进行授权
        req.setIdentityParam("{\"certNo\":\"" + certNo + "\",\"certType\":\"IDENTITY_CARD\",\"name\":\"" + userName + "\"}");// 必要参数
        DefaultZhimaClient client = new DefaultZhimaClient(Constants.URL, Constants.APPID, Constants.CHARSET, Constants.PRIKEY, Constants.PUBKEY);
        try {
            // 如果正常返回,直接在对象里面获取结果值
            ZhimaAuthInfoAuthqueryResponse response = client.execute(req);
            System.out.println(JSON.toJSON(response));
        } catch (ZhimaApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询芝麻分.
     *
     * @throws ZhimaApiException
     */
    public static void QueryZhiMaScore(String productCode, String openId) throws ZhimaApiException {
        ZhimaCreditScoreGetRequest creditScoreGetRequest = new ZhimaCreditScoreGetRequest();
        creditScoreGetRequest.setPlatform(Constants.PLATFORM); // 开放平台,zmop代表芝麻开放平台
        creditScoreGetRequest.setChannel(Constants.CHANNEL); // pc端
        //transactionId，该标记是商户每次请求的唯一标识。建议使用uuid进行传递，
        creditScoreGetRequest.setTransactionId(UUID.randomUUID().toString());
        creditScoreGetRequest.setProductCode(productCode); // 商户配置那块儿的产品Code
        creditScoreGetRequest.setOpenId(openId); // appid,每个人的标识
        DefaultZhimaClient client = new DefaultZhimaClient(Constants.URL, Constants.APPID, Constants.CHARSET, Constants.PRIKEY, Constants.PUBKEY);
        // 如果正常返回,直接在对象里面获取结果值
        ZhimaCreditScoreGetResponse creditScoreGetResponse = client.execute(creditScoreGetRequest);
        creditScoreGetResponse.getZmScore();
        System.out.println(JSON.toJSON(creditScoreGetResponse));

    }

    /**
     * 自动生成页面授权的url.
     *
     * @throws Exception
     */
    public static void testPageAuth(String cerNo, String userName) throws Exception {
        ZhimaAuthInfoAuthorizeRequest authInfoAuthorizeRequest = new ZhimaAuthInfoAuthorizeRequest();
        authInfoAuthorizeRequest.setChannel(Constants.CHANNEL); // PC端
        authInfoAuthorizeRequest.setPlatform(Constants.PLATFORM); // 开放平台
        // 0：芝麻信用开放账号ID 1：按照手机号进行授权 2:按照身份证+姓名进行授权 3通过公安网验证进行授权 4.通过人脸验证进行授权
        authInfoAuthorizeRequest.setIdentityType(Constants.IDENTITYTYPE);
        Map<String, String> identityParams = new HashMap<String, String>();
        identityParams.put("certNo", cerNo); // 证件号码
        identityParams.put("name", userName); // 姓名
        identityParams.put("certType", "IDENTITY_CARD"); // 证件类型
        authInfoAuthorizeRequest.setIdentityParam(JSONObject.toJSONString(identityParams));
        DefaultZhimaClient client = new DefaultZhimaClient(Constants.URL, Constants.APPID, Constants.CHARSET, Constants.PRIKEY, Constants.PUBKEY);
        String pageAuthUrl = client.generatePageRedirectInvokeUrl(authInfoAuthorizeRequest);
        System.out.println(pageAuthUrl);
    }

    /**
     * 获取芝麻粉(GET方式)
     */
    public void testZhimaCreditWatchlistGet(String productCode, String openId) {
        ZhimaCreditScoreGetRequest req = new ZhimaCreditScoreGetRequest();
        req.setProductCode(productCode);// 必要参数
        req.setOpenId(openId);// 必要参数
        DefaultZhimaClient client = new DefaultZhimaClient(Constants.URL, Constants.APPID, Constants.CHARSET, Constants.PRIKEY, Constants.PUBKEY);
        try {
            // 如果正常返回,直接在对象里面获取结果值
            ZhimaCreditScoreGetResponse response = client.execute(req);
            System.out.println(JSON.toJSON(response));
        } catch (ZhimaApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理回调后的参数,然后解密params
     *
     * @param url 例如：http://xxxx.com?params=xxxxx&key1=xxxxx&key2=xxxxx
     * @throws Exception
     */
    public static void parseFromReturnUrl(String url) throws Exception {
        int index = url.indexOf("?");
        String urlParamString = url.substring(index + 1);
        String[] paraPairs = urlParamString.split("&");
        String encryptedParam = "";
        for (String paramPair : paraPairs) {
            String[] splits = paramPair.split("=");
            if ("params".equals(splits[0])) {
                encryptedParam = splits[1];
            }
        }
        String decryptedParam = RSACoderUtil.decrypt(URLDecoder.decode(encryptedParam, Constants.CHARSET),
                Constants.PRIKEY, Constants.CHARSET);
        //通过浏览器返回时，不需要decode
        System.out.println(URLDecoder.decode(decryptedParam, Constants.CHARSET));
/*
        params主要JSON参数如下：
                 名称	   	 	类型      	示例值    	 	备注
        success  		String 	success 	请求成功还是失败的标识
		error_code  	String 	000001    	失败时的错误码
		error_message	String	缺少appId	失败时的错误信息
		open_id			String	26881...	芝麻业务id
		state			String	239...		商户透传的值，芝麻不做解析
*/
    }

}
