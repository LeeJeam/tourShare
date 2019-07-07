package com.xmyy.pay.allinpay.constants;

/**
 * @Description 支付方式枚举
 *
 * @author AnCheng
 * @date 2018/10/18
 */
public enum PayModeEnum {

    /**
     * 微信、小程序
     */
    WECHATPAY_MiniProgram("微信小程序支付（借、贷）"),
    WECHATPAY_MiniProgram_ORG("微信小程序支付（借、贷）_集团"),
    WeChatPAY_APP_OPEN("微信原生APP支付"),
    WeChatPAYAPP_VSP("微信APP支付（收银宝）"),
    WECHATPAY_APP_ORG("微信APP支付_集团"),
    WECHAT_PUBLIC("微信JS支付（公众号）"),
    WECHAT_PUBLIC_ORG("微信JS支付（公众号）_集团"),
    WECHATPAY_H5("微信H5支付"),
    SCAN_WEIXIN("微信扫码支付(正扫)"),
    SCAN_WEIXIN_ORG("微信扫码支付(正扫) _集团"),

    /**
     * 支付宝
     */
    ALIPAY_APP_OPEN("支付宝原生APP支付"),
    ALIPAY_SERVICE("支付宝JS支付（生活号）"),
    ALIPAY_SERVICE_ORG("支付宝JS支付_集团"),
    SCAN_ALIPAY("支付宝扫码支付(正扫)"),
    SCAN_ALIPAY_ORG("支付宝扫码支付(正扫) _集团"),

    /**
     * 刷卡，QQ钱包
     */
    CODEPAY_W("微信刷卡支付（被扫）"),
    CODEPAY_A("支付宝刷卡支付(被扫)"),
    CODEPAY_Q("QQ钱包刷卡支付(被扫)"),
    QQ_WALLET("QQ钱包JS支付"),
    QQ_WALLET_ORG("QQ钱包JS支付_集团"),

    /**
     * 通联
     */
    GATEWAY("网关支付"),
    REALNAMEPAY("实名付（单笔）"),
    REALNAMEPAY_BATCH("实名付（批量）"),
    BALANCE("账户余额"),
    COUPONLIST("批量代金券"),
    WITHHOLD_TLT("通联通代扣"),
    WITHDRAW_TLT("通联通代付"),
    QUICKPAY_TLT("通联通协议支付"),
    QUICKPAY_VSP("收银宝快捷支付"),
    ORDER_VSPPAY("收银宝POS当面付及订单模式支付"),
    QUICKPAY_H5("新移动H5快捷支付"),
    QUICKPAY_PC("新移动PC快捷支付"),
    VIRTUAL_OUT("通用虚拟出金"),
    VIRTUAL_IN("通用虚拟入金");

    private String payModeName;

    PayModeEnum(String payModeName) {
        this.payModeName = payModeName;
    }

    public String getPayModeName() {
        return payModeName;
    }

    public void setPayModeName(String payModeName) {
        this.payModeName = payModeName;
    }
}
