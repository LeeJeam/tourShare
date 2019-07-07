package com.xmyy.common.vo;

public class ZhiMaConstants {

    //沙箱APPID
    public static final  String app_id = "2018052960270273";
    //沙箱私钥
    public static final  String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC4xJIl6uNYCweReeF2k0x/hvuSvYghNcgUBqHW7lqanB7s3IP/rKV6SN9GyweffTgnmefy5bu0sA01Q+ZYvsoNmzCGWF+RZ8CFsVXEXCnISI9T4hDHF0lkoWQhBIKVxlNPiQ753qUtqnjpVUreGIdwpJEuPy8gr2drpR9FAuPX4bhWjJVF13HodU0yh5zUf4OxPa9oPnkf2XT8RlwBqZzxpw3zf9NuPD8UpYc+kfGDp3DxrC0cTfVK2r3aAm9n84K6C0fFXuKixe9IrtxgJhNd5NByeou5ze20NqM6s3xdACEGhYhm/0KKZAtXiIEvi6QxQbqKYxmoBqIUhsKrby5jAgMBAAECggEBAK5bMN7/LNIkdlvde9l9Xq7pHfV3TUKpkcj0CTzdaDqGINi7rwRSU0V2BtyzMtpaO+eC4NLh36O+jng5PuF4CQuV6tCcDHMWSSLgGWNW5UbO0hICPnrhiqyfb/GIZAIaSPvBBpTceVsCh18I0cGLvCOb6DSQA1UAsMNv6nYhKAjKqWPHx19dUUNI3lT4wgTdPheCxapcPVOe0JDFUL2IuBYfJWsSn0DPfQmu3coRnwLJYqHM56nfVTcO1Gpj5CJ2i+13mJGpvmGae+6yU0EmmYT3CiXxFL3NQngKD+v29B9AKWXPcEmSCd+zQXG6JO10/qf7SObck4zY5k0CnvjM40ECgYEA4w5r1Ay5vO/socSAE3ItMc3Y+sjU9L8kjhZ6JNzDDaRLm/fbRmHSd4plN1KfvExCs5Bu0ULw6CtjXKtK2fc2td8t6oNK7Pc3S/PWzBvHn3PxphR4VaN+TxC1/5KSClGQkN7HJoYhBGdETt4+2xMt2+RIeB8Uxj+dX8292YMLbykCgYEA0FIj/K+I1U5C42/ruUdvTOJ6qAR9VovfSgOpAd2t9WLAx+RlPaw79NZZ6agpNVjfKYWrMnWsVW/8ltdbYShebW6tli24B9Y02vXxI/kW7bs4sgEsG6Hmxk5HAHRs4eZlFE+18hvvy9kfXgHsncAynaTOAcCKEqk3bGh+HorgPqsCgYBt8pLGWO8sDWYho1x9jiFSZ6J1NgDhDxntb/bvmLMfDPrL6MaWm1HmLu76ThkpfigieYMf8/FiC3iO7CdPysUEZrD+SVtjygZN0iKmLaGJlgRi7rCij4vfT3FB88CiHIMLk8UN449jaXoLUtVPEkd3nmB1Ql+17CIRvTf8dtd3mQKBgQC/+4MYwvhag/ZWvfVCWdfzxiB9qS2NTfgosLjXdGF17F13mj/HF2xl1gkksYnQlSeMj5hXN2Trk/n3VV7sahSjy0UESvv3lOw/AjsKCOudiVXVjgkFKi6QgOEkRSd3BCoRDujByAc1GNK0gxd5xf+fkpsbuEJGslXWfXIFWb0RYwKBgQC+koVR4RPX+abwZCwacjhqP947IU/T7iKXGCSIqDGPZ4s8Khkt/qhHADpC6wM1YRrNWonm1Rm8uPlcBGO/JRzsVL2jvPW+I2wJd+tUmYQ7pBjws0TFPCeTRE6BjGRp1NiUUy3RyDMUB3xSF6vu9ToqV7w8H2I4m7ueCL7rY3y7xA==";
    //支付宝公钥
    public static final  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnQmtNiDfd7Uyg4+EnmpQWJqHSYDg9vyJpTUpM90HBBPXDNSbdjOBlT0hLq+sJEaiBt5nhMf8eYATwcOvPrPVv5tSF038LnvyV/RBtujgHYETBmLo849sNBIxsTL4BOGtPS/DMHeeDY5OkAxw3/o3XbVy+hj4Nq+7vRVDcbN9BVof1PZHZKbLiwbdye/A4G7v5KOkeCoyzq9l6ooblSevcLvMwW0NBFqsNZTl8ct2a19HlcI5tRYIF4ksUfFIBzsBbQmqvVVZFKM/zowaSFYJjVZmc/X3p6L0PQrPuJVGw5h3uDPx9VvbSznOndEOV9q+spFeEjGs5iQTWUZU1qHKRQIDAQAB";
    //沙箱网关地址
    public static final  String gatewayUrl = "https://openapi.alipay.com/gateway.do";
    //回调地址
    public static final String  returnUrl = "https://www.taobao.com";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    //
    public static String format = "json";

}
