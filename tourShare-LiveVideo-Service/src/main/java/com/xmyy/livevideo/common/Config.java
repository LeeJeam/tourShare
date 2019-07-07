package com.xmyy.livevideo.common;

public class Config {

    /**
     * 需要开通云直播服务
     * 参考指引 @https://cloud.tencent.com/document/product/454/7953#1.-.E8.A7.86.E9.A2.91.E7.9B.B4.E6.92.AD.EF.BC.88lvb.EF.BC.89
     * 有介绍bizid 和 pushSecretKey的获取方法。
     */
    public class Live {
        /**
         * 云直播 APP_ID =  和 APIKEY 主要用于腾讯云直播common cgi请求。appid 用于表示您是哪个客户，APIKey参与了请求签名sign的生成。
         * 后台用他们来校验common cgi调用的合法性
         */
        public final static int APP_ID =1256807017;

        /**
         * 云直播 APP_BIZID = 和pushSecretKey 主要用于推流地址的生成，填写错误，会导致推流地址不合法，推流请求被腾讯云直播服务器拒绝
         */
        public final static int APP_BIZID = 25321;

        /**
         * 云直播 推流防盗链key = 和 APP_BIZID 主要用于推流地址的生成，填写错误，会导致推流地址不合法，推流请求被腾讯云直播服务器拒绝
         */
        public final static String PUSH_SECRET_KEY = "c7a06c220e7a2abe895819f53f8308e2";

        /**
         * 云直播 API鉴权key = 和appID 主要用于common cgi请求。appid 用于表示您是哪个客户，APIKey参与了请求签名sign的生成。
         * 后台用他们来校验common cgi调用的合法性。
         */
        public final static String APIKEY = "ea751532e2ea7b4b9a3755b1f92ba02a";

        // 云直播 推流有效期单位秒 默认7天
        public final static int validTime = 3600 * 24 * 7;

        // API回调地址
        public  final static String API_ADDRESS = "http://fcgi.video.qcloud.com/common_access";
    }

    /**
     * 需要开通云通信服务
     * 参考指引 @https://cloud.tencent.com/document/product/454/7953#3.-.E4.BA.91.E9.80.9A.E8.AE.AF.E6.9C.8D.E5.8A.A1.EF.BC.88im.EF.BC.89
     * 有介绍appid 和 accType的获取方法。以及私钥文件的下载方法。
     */
    public class IM {
        /**
         * 云通信 IM_SDKAPPID = IM_ACCOUNTTYPE 和 PRIVATEKEY 是云通信独立模式下，为您的独立账号 identifer，
         * 派发访问云通信服务的userSig票据的重要信息，填写错误会导致IM登录失败，IM功能不可用
         */
        public final static long IM_SDKAPPID = 1400098081;

        /**
         * 云通信 账号集成类型 IM_ACCOUNTTYPE = IM_SDKAPPID 和 PRIVATEKEY 是云通信独立模式下，为您的独立账户identifer，
         * 派发访问云通信服务的userSig票据的重要信息，填写错误会导致IM登录失败，IM功能不可用
         */
        public final static String IM_ACCOUNTTYPE = "28330";

        // 云通信 管理员账号
        public final static String ADMINISTRATOR = "admin";

        /**
         * 云通信 派发usersig 采用非对称加密算法RSA，用私钥生成签名。PRIVATEKEY就是用于生成签名的私钥，私钥文件可以在互动直播控制台获取
         * 配置privateKey
         * 将private_key文件的内容按下面的方式填写到 PRIVATEKEY。
         */
        public final static String PRIVATEKEY = "-----BEGIN PRIVATE KEY-----\n" +
                "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgZYbEclzWBE9kMeC9\n" +
                "gE19BRxJ4BzIZ6DS7O6ySgOo22ahRANCAASkqGHzv6Xc62xp4kcgKq8HT3owVQH3\n" +
                "8RI/N5ReFHkdU/8Hzhzc00h16fF7o2ABcMzIKDdBFJtoI+qkY7wqy/oz\n" +
                "-----END PRIVATE KEY-----";
        /**
         * 云通信 验证usersig 所用的公钥
         */
        public final static String PUBLICKEY = "-----BEGIN PUBLIC KEY-----\n"+
                "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEpKhh87+l3OtsaeJHICqvB096MFUB\n" +
                "9/ESPzeUXhR5HVP/B84c3NNIdenxe6NgAXDMyCg3QRSbaCPqpGO8Ksv6Mw==\n" +
                "-----END PUBLIC KEY-----";

    }

    public class LiveMsgEventType {
        /**
         * 断流
         */
        public final static int CUT_STREAM = 0;
        /**
         * 推流
         */
        public final static int PUSH_STREAM = 1;
        /**
         * 新的录制文件已生成
         */
        public final static int RECORD_AV_CREATED = 100;

        /**
         *  新的截图文件已生成
         */
        public final static int SCREENSHOT_CREATED = 200;
    }
}
