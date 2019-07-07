package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "充值返回结果")
public class DepositApplyResult implements Serializable {

    @ApiModelProperty(value = "支付状态")
    private String payStatus;

    @ApiModelProperty(value = "支付失败信息")
    private String payFailMessage;

    @ApiModelProperty(value = "支付方式")
    private String payMethod;

    @ApiModelProperty(value = "微信APP支付信息")
    private String weChatAPPInfo;

    @ApiModelProperty(value = "支付宝支付信息")
    private String payInfo;

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayFailMessage() {
        return payFailMessage;
    }

    public void setPayFailMessage(String payFailMessage) {
        this.payFailMessage = payFailMessage;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getWeChatAPPInfo() {
        return weChatAPPInfo;
    }

    public void setWeChatAPPInfo(String weChatAPPInfo) {
        this.weChatAPPInfo = weChatAPPInfo;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }
}
