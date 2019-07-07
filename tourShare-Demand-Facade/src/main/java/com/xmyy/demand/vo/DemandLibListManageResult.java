package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "后台需求库列表")
public class DemandLibListManageResult implements Serializable {

    @ApiModelProperty(value = "需求单ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String buyerName;

    @ApiModelProperty(value = "需求编号")
    private String demandNo;

    @ApiModelProperty(value = "预算价格")
    private BigDecimal budgetPrice;

    @ApiModelProperty(value = "购买地")
    private String buyCountry;

    @ApiModelProperty(value = "需求描述")
    private String demandDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
    }

    public BigDecimal getBudgetPrice() {
        return budgetPrice;
    }

    public void setBudgetPrice(BigDecimal budgetPrice) {
        this.budgetPrice = budgetPrice;
    }

    public String getBuyCountry() {
        return buyCountry;
    }

    public void setBuyCountry(String buyCountry) {
        this.buyCountry = buyCountry;
    }

    public String getDemandDesc() {
        return demandDesc;
    }

    public void setDemandDesc(String demandDesc) {
        this.demandDesc = demandDesc;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
}
