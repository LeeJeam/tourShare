package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@ApiModel(value = "查询广告列表入参")
public class AdApiParam implements Serializable {

    @ApiModelProperty(value = "" +
            "买家端首页Banner:Buyer01;" +
            "买家端首页发现:Buyer02;" +
            "买家端直播Banner:Buyer03;" +
            "买家端启动页:Buyer05;买家端背包客左菜单Banner：Buyer06；"+
            "买手端首页Banner:Seller04;" +
            "买手端启动页:Seller06")
    @NotBlank
    private String code="Buyer01";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
