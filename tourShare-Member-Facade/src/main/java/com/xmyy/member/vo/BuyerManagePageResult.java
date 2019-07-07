package com.xmyy.member.vo;

import com.xmyy.member.model.UcBuyer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "后台买家/背包客列表")
public class BuyerManagePageResult extends UcBuyer implements Serializable {

    @ApiModelProperty(value = "性别（女，男）")
    private String genderLabel;

    public String getGenderLabel() {
        return genderLabel;
    }

    public void setGenderLabel(String genderLabel) {
        this.genderLabel = genderLabel;
    }
}
