package com.xmyy.member.vo;

import com.xmyy.member.model.UcSeller;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("后台买手/自营买手")
public class SellerManagePageResult extends UcSeller implements Serializable {

    @ApiModelProperty(value = "性别（女，男）")
    private String genderLabel;

    @ApiModelProperty(value = "置顶操作人")
    private String toperName;

    public String getToperName() {
        return toperName;
    }

    public void setToperName(String toperName) {
        this.toperName = toperName;
    }

    public String getGenderLabel() {
        return genderLabel;
    }

    public void setGenderLabel(String genderLabel) {
        this.genderLabel = genderLabel;
    }
}
