package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "后台广告列表入参")
public class AdManageParam implements Serializable {

    @ApiModelProperty(value = "类型:Buyer:买家；Seller:买手， 默认是买家")
    private String type="Buyer";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
