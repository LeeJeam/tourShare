package com.xmyy.cert.vo;

import com.xmyy.cert.model.UcMemberCert;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class MemberCertResult extends UcMemberCert implements Serializable  {

    @ApiModelProperty(value = "状态")
    private String  stateLabel;

    public String getStateLabel() {
        return stateLabel;
    }

    public void setStateLabel(String stateLabel) {
        this.stateLabel = stateLabel;
    }
}
