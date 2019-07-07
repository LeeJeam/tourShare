package com.xmyy.circle.vo;

import com.xmyy.circle.model.WsAdvertContent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "广告列表")
public class AdManageListResult implements Serializable {

    @ApiModelProperty(value = "广告位")
    private String position;

    @ApiModelProperty(value = "广告内容")
    private List<WsAdvertContent> contents;

    public List<WsAdvertContent> getContents() {
        return contents;
    }

    public void setContents(List<WsAdvertContent> contents) {
        this.contents = contents;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
