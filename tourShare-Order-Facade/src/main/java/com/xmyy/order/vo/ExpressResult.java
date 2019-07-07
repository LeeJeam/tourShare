package com.xmyy.order.vo;

import com.xmyy.common.vo.ExpressItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "物流信息")
public class ExpressResult implements Serializable{

    @ApiModelProperty(value = "物流公司")
    private String logisticsCompany;

    @ApiModelProperty(value = "物流单号")
    private String waybillNo;

    @ApiModelProperty(value = "物流信息")
    private List<ExpressItem> data;

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public List<ExpressItem> getData() {
        return data;
    }

    public void setData(List<ExpressItem> data) {
        this.data = data;
    }
}
