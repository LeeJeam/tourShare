package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "进入确认订单页接口的返回数据")
public class IntoConfirmOrderResult implements Serializable {

    @ApiModelProperty(value = "收货人姓名")
    private String name;

    @ApiModelProperty(value = "收货人电话")
    private String phone;

    @ApiModelProperty(value = "收货人地址")
    private String address;

    @ApiModelProperty(value = "买手ID")
    private Long buyerId;

    @ApiModelProperty(value = "买手昵称")
    private String nickName;

    @ApiModelProperty(value = "买手头像")
    private String avatarRsurl;

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarRsurl() {
        return avatarRsurl;
    }

    public void setAvatarRsurl(String avatarRsurl) {
        this.avatarRsurl = avatarRsurl;
    }
}
