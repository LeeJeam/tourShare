package com.xmyy.cert.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "获取芝麻信用TOKEN令牌入参")
public class ThirdPartyParam implements Serializable {

    @ApiModelProperty(value = "商户请求的唯一标志，64位长度的字母数字下划线组合", example = "001")
    private String transactionId;

    @ApiModelProperty(value = "产品码参数是固定值 w1010100100000000001", example = "w1010100100000000001")
    private String productCode;

    @ApiModelProperty(value = "Token令牌")
    private String accessToken;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
