package com.xmyy.common;

/**
 * 所有常量都定义在此类中
 *
 * @author zlp
 */
public abstract class Constants {
    public static final Long SYS_USER_ID = 0L;
    public static final String SYS_USER_NAME = "系统";
    public static final String AVATAR_DEFAULT = "http://xiabeibao-2017.oss-cn-shenzhen.aliyuncs.com/images/avatar_default_60x60.png";

    /*********************短信模板Code，需在阿里云上管理*****************/
    public static final String TEMPLATE_CODE_RESTPWD = "SMS_139231431";                                     // 重置密码动态码短信模板code
    public static final String TEMPLATE_CODE_ADD = "SMS_139226558";                                         // 注册验证码短信模板code
    public static final String TEMPLATE_CODE_LOGIN = "SMS_139241476";                                         // 登录动态码短信模板code

    public static final String TEMPLATE_CODE_INVITE_CODE = "SMS_139236500";                                         // 邀请码短信模板code
    public static final String TEMPLATE_CODE_SETPAYPWD = "SMS_144850522";                                            // 设置支付密码模板code
    /*********************redis key*****************/
    public static final String REDIS_BUYER_ADD_DYNAMIC_CODE = "buyer:add:dynamic_code:";                          // 买家注册发送的验证码
    public static final String REDIS_BUYER_ADD_DYNAMIC_CODE_COUNT = "buyer:add:dynamic_code_count:";              // 买家注册验证码当天发送的总数

    public static final String REDIS_BUYER_RESETPWD_DYNAMIC_CODE = "buyer:resetpwd:dynamic_code:";              // 买家找回密码验证码发送
    public static final String REDIS_BUYER_RESETPWD_DYNAMIC_CODE_COUNT = "buyer:resetpwd:dynamic_code_count:";  // 买家找回密码验证码当天发送总数

    public static final String REDIS_BUYER_QUICKLOGIN_DYNAMIC_CODE = "buyer:quicklogin:dynamic_code:";              // 买家快速登录动态验证码发送
    public static final String REDIS_BUYER_QUICKLOGIN_DYNAMIC_CODE_COUNT = "buyer:quicklogin:dynamic_code_count:";  // 买家快速登录动态验证码当天发送总数

    public static final String REDIS_BUYER_WXLOGIN_DYNAMIC_CODE = "buyer:wxlogin:dynamic_code:";              // 买家微信登录动态验证码发送
    public static final String REDIS_BUYER_WXLOGIN_DYNAMIC_CODE_COUNT = "buyer:wxlogin:dynamic_code_count:";  // 买家微信登录动态验证码当天发送总数

    public static final String REDIS_BUYER_UPDATEMOBILE_DYNAMIC_CODE = "buyer:updatemobile:dynamic_code:";              // 买家绑定手机验证码发送
    public static final String REDIS_BUYER_UPDATEMOBILE_DYNAMIC_CODE_COUNT = "buyer:updatemobile:dynami:code:count:";  // 买家绑定手机验证码当天发送总数

    public static final String REDIS_SELLER_UPDATEMOBILE_DYNAMIC_CODE = "seller:updatemobile:dynamic_code:";              // 买手绑定手机验证码发送
    public static final String REDIS_SELLER_UPDATEMOBILE_DYNAMIC_CODE_COUNT = "seller:updatemobile:dynami:code:count:";  // 买手绑定手机验证码当天发送总数


    public static final String REDIS_SELLER_ADD_DYNAMIC_CODE = "seller:add:dynamic_code:";                          // 买手注册验证码发送
    public static final String REDIS_SELLER_ADD_DYNAMIC_CODE_COUNT = "seller:add:dynamic_code_count:";              // 买手注册验证码当天发送总数

    public static final String REDIS_SELLER_RESETPWD_DYNAMIC_CODE = "seller:resetpwd:dynamic_code:";              // 买手找回密码验证码发送
    public static final String REDIS_SELLER_RESETPWD_DYNAMIC_CODE_COUNT = "seller:resetpwd:dynamic_code_count:";  // 买手找回密码验证码当天发送总数

    public static final String REDIS_BUYER_PAYPW_SMS_CODE = "buyer:paypw:sms_code";                 //买家设置支付密码验证码
    public static final String REDIS_BUYER_PAYPW_SMS_CODE_COUNT = "buyer:paypw:sms_code_count";     //买家设置支付密码验证码当天总数
    public static final String REDIS_SELLER_PAYPW_SMS_CODE = "seller:paypw:sms_code";               //买手设置支付密码验证码
    public static final String REDIS_SELLER_PAYPW_SMS_CODE_COUNT = "seller:paypw:sms_code_count";   //买手设置支付密码验证码当天总数


    /****************************配置阿里云OSS相关配置******************/

/*
    public static final String ENDPOINT = "oss-cn-shanghai.aliyuncs.com";  //阿里云API的外网域名

    public static final String ACCESS_KEY_ID = "LTAIRGxaf6yoUsj0";  //阿里云API的密钥Access Key ID

    public static final String ACCESS_KEY_SECRET = "3gcfQkeWjaJ3tunuv4yyY4DStgpriz";  //阿里云API的密钥Access Key Secret

    public static final String BACKET_NAME = "uploadpicture";  //阿里云API的bucket名称

    public static final String FOLDER="somnus/";    //阿里云API的文件夹名称
*/

    public static final String ENDPOINT = "oss-cn-shenzhen.aliyuncs.com";  //阿里云API的外网域名

    public static final String ACCESS_KEY_ID = "LTAIVzGgXg1pZpCx";  //阿里云API的密钥Access Key ID

    public static final String ACCESS_KEY_SECRET = "5rxtNXtYRLnpbLfjRVOLovWzD0XG98";  //阿里云API的密钥Access Key Secret

    public static final String BACKET_NAME = "xiabeibao-2017";  //阿里云API的bucket名称

    public static final String FOLDER="images/";    //阿里云API的文件夹名称

    /**************************配置芝麻信用分配置***********************/



    public static final String URL  = "https://zmopenapi.zmxy.com.cn/openapi.do"; //芝麻开放平台地址

    public static final  String APPID  = "asdas";    //商户应用 Id

    public static final  String PRIKEY  = "sadsad";  //商户 RSA 私钥

    public static final  String PUBKEY = "asdasd";   //芝麻 RSA 公钥

    public static final String CHARSET  = "UTF-8";  //统一字符集

    public static final String IDENTITYTYPE ="2";// 0：芝麻信用开放账号ID 1：按照手机号进行授权 2:按照身份证+姓名进行授权 3通过公安网验证进行授权 4.通过人脸验证进行授权

    public static final String CHANNEL ="apppc";  // PC端

    public static final String PLATFORM ="zmop"; //开放平台,zmop代表芝麻开放平台

    /****************************系统异常参数******************/
    public static final String BIND_PARAM_ERROR = "511";//参数绑定错误

    public static final String OUT_OF_ARRAY_INDEX_EORROR = "512";//数组角标越界

    public static final String WRONG_ARGUMENT_FMT_ERROR = "514";//参数格式错误

    public static final String CUSTOMER_BUINESS_ERRORCODE = "515";//自定义业务异常（没有指定情况下）

    //*************************

    public static void main(String[] args) {
        /*for (PayState s : PayState.values()) {
            System.out.println(s.value + "\t" + s.label);
        }*/

        //System.out.println(State.toPrerReserveMap());
    }
}
