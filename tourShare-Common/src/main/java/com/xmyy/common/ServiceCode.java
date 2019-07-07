package com.xmyy.common;

/**
 * 业务码，APP部分接口需要根据不同业务码返回，做不同处理
 *
 * @author zlp
 */
public enum ServiceCode {

    ServeCode_600("600","%s"),
    ServeCode_601("601","%s"),
    ServeCode_602("602","绑定手机号码"),

    ServeCode_000("000","定义结束标记");

    //注意：message是用于直接显示给前台用户看的！
    private String msg;
    private String code;

    ServiceCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }

    public String code() {
        return code;
    }

}
