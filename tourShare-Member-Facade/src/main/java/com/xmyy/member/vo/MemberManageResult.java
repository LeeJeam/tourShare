package com.xmyy.member.vo;

import com.xmyy.member.model.UcMemberAddress;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "买家信息")
public class MemberManageResult extends MemberInfoResult implements Serializable {

    public List<UcMemberAddress> addressList;

    public List<UcMemberAddress> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<UcMemberAddress> addressList) {
        this.addressList = addressList;
    }
}
