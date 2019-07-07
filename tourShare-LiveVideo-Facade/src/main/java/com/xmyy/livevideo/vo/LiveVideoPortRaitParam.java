package com.xmyy.livevideo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "买手端用户信息设置表")
public class LiveVideoPortRaitParam implements Serializable {

    private String From_Account;

    private List<LiveVideoProfileItem> ProfileItem;

    @JSONField(name = "From_Account")
    public String getFrom_Account() {
        return From_Account;
    }

    public void setFrom_Account(String from_Account) {
        From_Account = from_Account;
    }

    @JSONField(name = "ProfileItem")
    public List<LiveVideoProfileItem> getProfileItem() {
        return ProfileItem;
    }

    public void setProfileItem(List<LiveVideoProfileItem> profileItem) {
        ProfileItem = profileItem;
    }
}
