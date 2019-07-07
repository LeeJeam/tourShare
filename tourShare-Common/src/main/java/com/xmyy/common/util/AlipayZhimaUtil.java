package com.xmyy.common.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.xmyy.common.vo.ZhiMaConstants;


/**
 * 蚂蚁金服开发平台授权调用芝麻信用获取芝麻分
 * @author james
 *
 */
public class AlipayZhimaUtil {

	public static void main(String[] args) throws AlipayApiException {
		AlipayZhimaUtil initialize = new AlipayZhimaUtil();
		initialize.zhiMaCreditScore();
	}

   /**调用【alipay.user.info.auth】(用户登陆授权)接口获取授权链接
	调用此接口需要注意一下几点：
	①ReturnUrl这个参数必须传入且必须和应用中的授权回调地址相同
	②授权的scope需要是auth_zhima，如果您不是调用芝麻信用评分接口，请根据您的接口传入对应的scope
	③请求调用的方法alipayClient.pageExecute不是alipayClient.execute，后面传入get获取的就是json字符串格式的授权地址，不传入则是获取到form表单。 */
	public void aliPayUserInfoAuth() throws AlipayApiException {
	   AlipayClient alipayClient = new DefaultAlipayClient(ZhiMaConstants.gatewayUrl, ZhiMaConstants.app_id, ZhiMaConstants.merchant_private_key, ZhiMaConstants.format, ZhiMaConstants.charset, ZhiMaConstants.alipay_public_key, ZhiMaConstants.sign_type);
	   AlipayUserInfoAuthRequest request = new AlipayUserInfoAuthRequest();
	   //传入ReturnUrl  必传且必须和应用中的授权回调地址相同
	   request.setReturnUrl("https://www.taobao.com");
	   //授权时请提供芝麻信用授权scope：auth_zhima
	   request.setBizContent("{\"scopes\":[\"auth_zhima\"],\"state\":\"init\"" +
			   "}");
	   AlipayUserInfoAuthResponse response = alipayClient.pageExecute(request,"GET");

	   if(response.isSuccess()){
		   System.out.println("调用成功");
		   System.out.println(response.getBody());
	   } else {
		   System.out.println("调用失败");
		   System.out.println(response.getBody());
	   }
   }

	/**通过【alipay.system.oauth.token】换取授权访问令牌接口使用code获取access_token
	 * 注意：这里的access_token参数一定是authzma开头的才是正确的
	 */
	public void aliPaySystemOauthToken(){
	  AlipayClient alipayClient = new DefaultAlipayClient(ZhiMaConstants.gatewayUrl, ZhiMaConstants.app_id, ZhiMaConstants.merchant_private_key, ZhiMaConstants.format, ZhiMaConstants.charset, ZhiMaConstants.alipay_public_key, ZhiMaConstants.sign_type);
	  AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
	  request.setCode("cc9221c669aa40f798e11ac711d3VX43");
	  request.setGrantType("authorization_code");
	  try {
		  AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
		  System.out.println(oauthTokenResponse.getBody());
	  } catch (AlipayApiException e) {
		  //处理异常
		  e.printStackTrace();
	  }
  }
  /**调用【zhima.credit.score.get】芝麻信用评分接口
   * 1、transaction_id 参数是商户请求的唯一标志，64位长度的字母数字下划线组合。由商户自行定义，该标识作为对账的关键信息，商户要保证其唯一性，对于用户使用相同transaction_id的查询，芝麻在一天（86400秒）内返回首次查询数据，超过有效期的查询即为无效并返回异常，有效期内的重复查询不重新计费
   * 2、product_code 产品码参数是固定值 w1010100100000000001 ，请勿更改
   * 3、access_token参数需要传在execute方法提交方法中 **/
  public void zhiMaCreditScore() throws AlipayApiException {
	  AlipayClient alipayClient = new DefaultAlipayClient(ZhiMaConstants.gatewayUrl, ZhiMaConstants.app_id, ZhiMaConstants.merchant_private_key, ZhiMaConstants.format, ZhiMaConstants.charset, ZhiMaConstants.alipay_public_key, ZhiMaConstants.sign_type);
	  ZhimaCreditScoreGetRequest request = new ZhimaCreditScoreGetRequest();
	  request.setBizContent("{" +
              //transaction_id商户请求的唯一标志，64位长度的字母数字下划线组合
			  "\"transaction_id\":\"201801100936688040000010466879\"," +
			  "\"product_code\":\"w1010100100000000001\"" +
			  "}");
	  ZhimaCreditScoreGetResponse response = alipayClient.execute(request,"authbseB92c26c6d5fb24fdc9f91682effef3X43");

	  if(response.isSuccess()){
		  System.out.println("调用成功");
		  System.out.println(response.getBody());
	  } else {
		  System.out.println("调用失败");
		  System.out.println(response.getBody());
	  }
  }
}
