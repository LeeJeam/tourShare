package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class CountryTreeResult implements Serializable {

    @ApiModelProperty(value = "组名中文")
    private String groupName;

    @ApiModelProperty(value="组名编码")
    private String groupCode;

    @ApiModelProperty(value = "成员")
    private List<CountryResult> children;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<CountryResult> getChildren() {
        return children;
    }

    public void setChildren(List<CountryResult> children) {
        this.children = children;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}

