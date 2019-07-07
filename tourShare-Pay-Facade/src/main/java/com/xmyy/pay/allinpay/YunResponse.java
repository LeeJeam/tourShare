package com.xmyy.pay.allinpay;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class YunResponse<R> implements Serializable {

    private String status;

    private String message;

    private String errCode;

    private String sign;

    private String signedValue;

    private R result;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getSignedValue() {
        return signedValue;
    }

    public void setSignedValue(String signedValue) {
        this.signedValue = signedValue;
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public void wrap(Class<R> toBeanClazz){
        if("OK".equals(getStatus())) {
            R toBean = JSON.parseObject(this.getSignedValue(), toBeanClazz);
            setResult(toBean);
        }
    }

    public boolean isOK() {
        return "OK".equals(getStatus());
    }

    @Override
    public String toString() {
        return "YunResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", errCode='" + errCode + '\'' +
                ", sign='" + sign + '\'' +
                ", signedValue='" + signedValue + '\'' +
                ", result=" + result +
                '}';
    }
}
