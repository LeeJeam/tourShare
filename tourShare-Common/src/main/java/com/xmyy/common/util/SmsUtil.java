package com.xmyy.common.util;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.xmyy.common.vo.SendSmsResult;
import top.ibase4j.core.util.InstanceUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class SmsUtil {
    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    private static final String accessKeyId = "LTAIVzGgXg1pZpCx";
    private static final String accessKeySecret = "5rxtNXtYRLnpbLfjRVOLovWzD0XG98";
    private static final String signName = "夏木悠悠";    //在阿里大于申请的签名



    /**
     * 往某个手机号发送短信
     * @param mobile 手机号
     * @param templateCode 在阿里大于申请的模板编号 (测试用：SMS_135225173)
     * @param templateMap 模板中对应的参数值
     * @return
     * @throws ClientException
     */
    public static SendSmsResult sendSms(String mobile, String templateCode, Map<String,String> templateMap){
        SendSmsResult smsResult = new SendSmsResult();
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            smsResult.setCode(e.getErrCode());
            smsResult.setMessage(e.getMessage());
            smsResult.setRequestId(e.getRequestId());
            return smsResult;
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(JSON.toJSONString(templateMap));//JsonUtil.toJson(templateMap).replaceAll("\"","\\\\\""));//{\"code\":\"6666\"}

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            smsResult.setCode(e.getErrCode());
            smsResult.setMessage(e.getMessage());
            smsResult.setRequestId(e.getRequestId());
            return smsResult;
        }

        return InstanceUtil.to(sendSmsResponse,SendSmsResult.class);
    }

    /**
     * 查询某个手机号的短信发送信息
     * @param mobile 手机号
     * @param bizId 业务id
     * @return
     * @throws ClientException
     */
    public static QuerySendDetailsResponse querySendDetails(String mobile, String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(mobile);
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }
    public static void main(String[] args) {
       // Map<String,Object> param = new HashMap<>();
       // param.put("code","asfsadf");
       // param.put("name","f134");

       // System.out.print(JsonUtil.toJson(param).replaceAll("\"","\\\\\""));

        SendSmsResult response = SmsUtil.sendSms("15815812204","SMS_104900017", InstanceUtil.newHashMap("code", "1234"));


        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());

    }

}
