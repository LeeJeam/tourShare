package com.xmyy.pay.allinpay.constants;

/**
 * @author AnCheng
 * @version v1.0
 * @Description 云商通常量类（测试环境）
 * @date 2018/10/10
 */
public class YunConfig {

    //sysid 系统分配应用ID
    public static final String SYSID = "100009001000";

    //系统版本号
    public static final String VERSION = "1.0";

    //证书地址
    public static final String PATH = "classpath:config/100009001000.pfx";

    //证书名称
    public static final String ALIAS = "100009001000";

    //证书密码
    public static final String PWD = "900724";

    //加密方法
    public static final String SIGNMETHOD = "SHA1WithRSA";

    //接口地址
    public static final String SERVER_URL = "http://116.228.64.55:9092/service/soa";

    //签约成功后页面跳转地址
    public static final String SIGN_CONTRACT_SUCCESS_FRONT_URL = "https://zf.gzxmyy.com/contract_success.html";

    //签约成功后后台回调地址
    public static final String SIGN_CONTRACT_SUCCESS_BACK_URL = "https://zf.gzxmyy.com/api/pay/callBack/signContract";

    //支付异步回调地址
    public static final String BACK_URL = "https://zf.gzxmyy.com/api/pay/callBack/order";

    //公司公网IP
    public static final String COMPANY_IP = "183.6.152.210";

    //商户APP名称
    public static final String APPNAME = "";

    //商户APP包名
    public static final String APPPACKAGE = "";

}
